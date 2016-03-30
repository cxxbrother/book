package com.book.portal.service.book;

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
import com.book.portal.entity.po.portal.PortalRole;
import com.book.portal.entity.po.portal.PortalUser;
import com.book.portal.entity.po.portal.PortalUserRole;
import com.book.portal.entity.vo.QueryBookInfo;
import com.book.portal.entity.vo.QueryUserVo;
import com.huisa.common.database.model.PageBean;
import com.huisa.common.exception.ServiceException;

@Service
public class BookService {
    @Autowired
    private BookInfoDao bookInfoDao;
    @Autowired
    private PortalUserDao portalUserDao;
    @Autowired
    private BorrowDao borrowDao;
	public List<QueryBookInfo> queryBookInfoListByPage(
			QueryBookInfo queryBookInfo, PageBean pagebean) throws ServiceException {
		StringBuffer querySqlBuffer = new StringBuffer();
        List<Object> queryParams = new ArrayList<Object>();
        
        querySqlBuffer.append("SELECT bi.*,u.user_name FROM book_info as bi JOIN user_info as u ON u.id=bi.operator_id"
        		+ " AND bi.is_delete=0");
        if (queryBookInfo != null) {
            if (StringUtils.isNotBlank(queryBookInfo.getBookName())) {
                querySqlBuffer.append(" AND bi.book_name LIKE ?");
                queryParams.add("%" + queryBookInfo.getBookName() + "%");
            }
            if (StringUtils.isNotBlank(queryBookInfo.getType())) {
                querySqlBuffer.append(" AND bi.type LIKE ?");
                queryParams.add("%"+queryBookInfo.getType()+"%");
            }
            if (StringUtils.isNotBlank(queryBookInfo.getAuthor())) {
                querySqlBuffer.append(" AND bi.author LIKE ?");
                queryParams.add("%"+queryBookInfo.getAuthor()+"%");
            }
            if (StringUtils.isNotBlank(queryBookInfo.getPublisher())) {
            	querySqlBuffer.append(" AND bi.publisher LIKE ?");
            	queryParams.add("%"+queryBookInfo.getPublisher()+"%");
            }
            if (queryBookInfo.getPublishTime()!=null) {
            	querySqlBuffer.append(" AND bi.publish_time>=?");
            	queryParams.add(queryBookInfo.getPublishTime());
            }
            if (StringUtils.isNotBlank(queryBookInfo.getISBN())) {
            	querySqlBuffer.append(" AND bi.ISBN LIKE ?");
            	queryParams.add("%"+queryBookInfo.getISBN()+"%");
            }
        }    
        List<QueryBookInfo> bookInfoList=bookInfoDao.queryBookInfoByPage(
                pagebean, querySqlBuffer.toString(), queryParams);
        return bookInfoList;
	}

	public void deleteBook(int id) throws ServiceException {
		bookInfoDao.updateBookDelete(id);
		
	}

	public void addBook(BookInfo bookInfo) throws ServiceException {
		boolean hasExistsBookISBN=bookInfoDao.hasExistsBookISBN(bookInfo.getISBN());
		if(hasExistsBookISBN){
			throw new ServiceException(ErrorCode.CODE_PARAM,
					"该图书号已存在！");
		}
		bookInfoDao.addReturnGeneratedKey(bookInfo);
		
	}

	public BookInfo getBookInfoById(int id) throws ServiceException {
		return bookInfoDao.getBookInfoById(id);
	}

	public void updateBookInfo(BookInfo bookInfo) throws ServiceException {
		bookInfoDao.update(bookInfo,"id");
	}

	public void userBorrow(String iSBN, String libraryCard,int operatorId) throws ServiceException {
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
		boolean isUnableBeBorrow=bookInfoDao.isUnableBeBorrow(iSBN);
		if(isUnableBeBorrow){
			throw new ServiceException(ErrorCode.CODE_PARAM,
					"该图书现存量已不足！");
		}
		boolean isAbleToBorrow=portalUserDao.isAbleToBorrow(libraryCard);
		if(!isAbleToBorrow){
			throw new ServiceException(ErrorCode.CODE_PARAM,
					"该用户已经无法再借书！");
		}
		
		int bookId=bookInfoDao.getBookIdByISBN(iSBN).getId();
		int userId=portalUserDao.getUserIdByLibraryCard(libraryCard).getId();
		
		
		Borrow borrow=new Borrow();
		Date borrowDate=new Date();
		Date deadline=new Date();
		deadline.setMonth(borrowDate.getMonth()+2);
		borrow.setBookId(bookId);
		borrow.setReaderId(userId);
		borrow.setOperatorId(operatorId);
		borrow.setBorrowTime(borrowDate);
		borrow.setDeadline(deadline);
		borrow.setIsBack(0);
		bookInfoDao.updateBookBorrow(iSBN);
		portalUserDao.updateUserBorrow(libraryCard);
		bookInfoDao.add(borrow);
		
	}
}
