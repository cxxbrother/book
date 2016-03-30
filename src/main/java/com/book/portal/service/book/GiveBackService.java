package com.book.portal.service.book;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.portal.dao.book.BookInfoDao;
import com.book.portal.dao.book.BorrowDao;
import com.book.portal.dao.book.GiveBackDao;
import com.book.portal.entity.po.portal.PortalRole;
import com.book.portal.entity.po.portal.PortalUser;
import com.book.portal.entity.po.portal.PortalUserRole;
import com.book.portal.entity.vo.QueryBookInfo;
import com.book.portal.entity.vo.QueryGiveBack;
import com.book.portal.entity.vo.QueryUserVo;
import com.huisa.common.database.model.PageBean;
import com.huisa.common.exception.ServiceException;

@Service
public class GiveBackService {
    @Autowired
    private GiveBackDao giveBackDao;
    




	public List<QueryGiveBack> queryGivebackListByPage(
			QueryGiveBack queryGiveBack, PageBean pagebean) throws ServiceException {
		StringBuffer querySqlBuffer = new StringBuffer();
        List<Object> queryParams = new ArrayList<Object>();
        
        querySqlBuffer.append("SELECT gb.*,b.reader_id,b.book_id,u.user_name,u.library_card,bi.book_name,bi.author,bi.publisher,"
        		+ "bi.ISBN,u.account"
        		+ " FROM borrow AS b JOIN user_info as u JOIN book_info as bi JOIN giveback as gb"
        		+ " ON bi.id=b.book_id AND u.id=b.reader_id AND gb.borrow_id=b.id");
        
        if (queryGiveBack != null) {
            if (StringUtils.isNotBlank(queryGiveBack.getBookName())) {
                querySqlBuffer.append(" AND bi.book_name LIKE ?");
                queryParams.add("%" + queryGiveBack.getBookName() + "%");
            }
            if (StringUtils.isNotBlank(queryGiveBack.getAuthor())) {
                querySqlBuffer.append(" AND bi.author LIKE ?");
                queryParams.add("%"+queryGiveBack.getAuthor()+"%");
            }
            if (StringUtils.isNotBlank(queryGiveBack.getAccount())) {
                querySqlBuffer.append(" AND u.account LIKE ?");
                queryParams.add("%"+queryGiveBack.getAccount()+"%");
            }
            if (StringUtils.isNotBlank(queryGiveBack.getISBN())) {
            	querySqlBuffer.append(" AND bi.ISBN LIKE ?");
            	queryParams.add("%"+queryGiveBack.getISBN()+"%");
            }
            if (StringUtils.isNotBlank(queryGiveBack.getReaderName())) {
            	querySqlBuffer.append(" AND u.user_name LIKE ?");
            	queryParams.add("%"+queryGiveBack.getReaderName()+"%");
            }
            if (StringUtils.isNotBlank(queryGiveBack.getPublisher())) {
            	querySqlBuffer.append(" AND bi.publisher LIKE ?");
            	queryParams.add("%"+queryGiveBack.getPublisher()+"%");
            }
            if (queryGiveBack.getIsLost()!=null) {
            	querySqlBuffer.append(" AND gb.is_lost=?");
            	queryParams.add(queryGiveBack.getIsLost());
            }
            if (queryGiveBack.getIsOverdue()!=null) {
            	querySqlBuffer.append(" AND gb.is_overdue=?");
            	queryParams.add(queryGiveBack.getIsOverdue());
            }
        }
        
        
        List<QueryGiveBack> givebackList=giveBackDao.queryGuideInfoByPage(
                pagebean, querySqlBuffer.toString(), queryParams);
        return givebackList;
	}


	public void deleteGiveback(int id) {
		// TODO Auto-generated method stub
		
	}
}
