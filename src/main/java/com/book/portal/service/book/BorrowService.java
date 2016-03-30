package com.book.portal.service.book;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.portal.common.ErrorCode;
import com.book.portal.dao.book.BookInfoDao;
import com.book.portal.dao.book.BorrowDao;
import com.book.portal.dao.user.PortalUserDao;
import com.book.portal.entity.po.book.BookInfo;
import com.book.portal.entity.po.book.Borrow;
import com.book.portal.entity.po.book.Giveback;
import com.book.portal.entity.po.portal.PortalRole;
import com.book.portal.entity.po.portal.PortalUser;
import com.book.portal.entity.po.portal.PortalUserRole;
import com.book.portal.entity.vo.QueryBookInfo;
import com.book.portal.entity.vo.QueryBorrowInfo;
import com.book.portal.entity.vo.QueryUserVo;
import com.huisa.common.database.model.PageBean;
import com.huisa.common.exception.ServiceException;

@Service
public class BorrowService {
    @Autowired
    private BorrowDao borrowDao;
    

    @Autowired
    private BookInfoDao bookInfoDao;
    @Autowired
    private PortalUserDao portalUserDao;

	public void deleteBorrow(int id) {
		// TODO Auto-generated method stub
		
	}

	public List<QueryBorrowInfo> queryBorrowInfoListByPage(
			QueryBorrowInfo queryBorrowInfo, PageBean pagebean) throws ServiceException {
		StringBuffer querySqlBuffer = new StringBuffer();
        List<Object> queryParams = new ArrayList<Object>();
        
        querySqlBuffer.append("SELECT b.*,u.user_name,u.library_card,bi.book_name,bi.author,bi.publisher,"
        		+ "bi.ISBN,u.account"
        		+ " FROM borrow AS b JOIN user_info as u JOIN book_info as bi "
        		+ " ON bi.id=b.book_id AND u.id=b.reader_id");
        
        if (queryBorrowInfo != null) {
            if (StringUtils.isNotBlank(queryBorrowInfo.getBookName())) {
                querySqlBuffer.append(" AND bi.book_name LIKE ?");
                queryParams.add("%" + queryBorrowInfo.getBookName() + "%");
            }
            if (StringUtils.isNotBlank(queryBorrowInfo.getAuthor())) {
                querySqlBuffer.append(" AND bi.author LIKE ?");
                queryParams.add("%"+queryBorrowInfo.getAuthor()+"%");
            }
            if (StringUtils.isNotBlank(queryBorrowInfo.getAccount())) {
                querySqlBuffer.append(" AND u.account LIKE ?");
                queryParams.add("%"+queryBorrowInfo.getAccount()+"%");
            }
            if (StringUtils.isNotBlank(queryBorrowInfo.getISBN())) {
            	querySqlBuffer.append(" AND bi.ISBN LIKE ?");
            	queryParams.add("%"+queryBorrowInfo.getISBN()+"%");
            }
            if (StringUtils.isNotBlank(queryBorrowInfo.getPublisher())) {
            	querySqlBuffer.append(" AND bi.publisher LIKE ?");
            	queryParams.add("%"+queryBorrowInfo.getPublisher()+"%");
            }
            if (StringUtils.isNotBlank(queryBorrowInfo.getReaderName())) {
            	querySqlBuffer.append(" AND u.user_name LIKE ?");
            	queryParams.add("%"+queryBorrowInfo.getReaderName()+"%");
            }
            if (queryBorrowInfo.getIsBack()!=null) {
            	querySqlBuffer.append(" AND b.is_back=?");
            	queryParams.add(queryBorrowInfo.getIsBack());
            }
            if (queryBorrowInfo.getDeadline()!=null) {
            	querySqlBuffer.append(" AND b.deadline>=?");
            	queryParams.add(queryBorrowInfo.getDeadline());
            }
            if (queryBorrowInfo.getBackTime()!=null) {
            	querySqlBuffer.append(" AND b.back_time>=?");
            	queryParams.add(queryBorrowInfo.getDeadline());
            }
            if (queryBorrowInfo.getBorrowTime()!=null) {
            	querySqlBuffer.append(" AND b.borrow_time>=?");
            	queryParams.add(queryBorrowInfo.getBorrowTime());
            }
        }
        
        List<QueryBorrowInfo> bookInfoList=borrowDao.queryBorrowInfoByPage(
                pagebean, querySqlBuffer.toString(), queryParams);
        return bookInfoList;
	}

	public String userBack(String iSBN, String libraryCard, Integer id,int isLost) throws ServiceException {
		String message=null;
		boolean hasExistsBookISBN=bookInfoDao.hasExistsBookISBN(iSBN);
		if(!hasExistsBookISBN){
			throw new ServiceException(ErrorCode.CODE_PARAM,
					"该图书号不存在！");
		}
		boolean hasExistsUserLibraryCard=portalUserDao.hasExistsUserLibraryCard(libraryCard);
		if(!hasExistsUserLibraryCard){
			throw new ServiceException(ErrorCode.CODE_PARAM,
					"该图书证件号不存在！");
		}

		int bookId=bookInfoDao.getBookIdByISBN(iSBN).getId();
		int userId=portalUserDao.getUserIdByLibraryCard(libraryCard).getId();
		boolean isExistsBorrow=borrowDao.isExistsBorrow(bookId,userId);
		if(!isExistsBorrow){
			throw new ServiceException(ErrorCode.CODE_PARAM,
					"不存在此借书记录！");
		}

		Borrow borrow=borrowDao.getBorrowByUserIdANDBookId(bookId,userId);
		borrow.setBackTime(new Date());
		borrow.setIsBack(1);
		borrowDao.update(borrow, "id");
		
		long backtime=borrow.getBackTime().getTime()/1000;
		long deadline=borrow.getDeadline().getTime()/1000;
		Giveback gb=new Giveback();
		gb.setIsOverdue(0);
		gb.setIsLost(0);
		if(isLost==1){
			gb.setIsLost(1);
			List<PortalRole> role=portalUserDao.listPortalUserRoles(userId);
			BookInfo bookInfo=bookInfoDao.getBookInfoById(bookId);
			float perlost=role.get(0).getLostFine();
			float money=bookInfo.getPrice()*perlost;
			gb.setNeedFeedMoney(money);
			message="用户遗失该书，需赔付"+money+"元";
			portalUserDao.updateUserBorrowDown(libraryCard);
			borrow.setIsBack(0);
			borrowDao.update(borrow, "id");
		}
		else if(backtime>deadline){
			gb.setIsOverdue(1);
			List<PortalRole> role=portalUserDao.listPortalUserRoles(userId);
			float perover=role.get(0).getOverdueFine();
			float money=((backtime-deadline)/(3600*24))*perover;
			gb.setNeedFeedMoney(money);
			message="用户借书超期，需赔付"+money+"元";
			bookInfoDao.updateBookBorrowAdd(iSBN);
			portalUserDao.updateUserBorrowDown(libraryCard);
		}else{
			message="还书成功";
			bookInfoDao.updateBookBorrowAdd(iSBN);
			portalUserDao.updateUserBorrowDown(libraryCard);
		}
		gb.setBorrowId(borrow.getId());
		gb.setOperatorId(id);
		borrowDao.add(gb);
		return message;
	}
}
