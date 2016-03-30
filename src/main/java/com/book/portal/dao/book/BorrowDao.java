package com.book.portal.dao.book;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.book.portal.entity.po.book.BookInfo;
import com.book.portal.entity.po.book.Borrow;
import com.book.portal.entity.vo.QueryBookInfo;
import com.book.portal.entity.vo.QueryBorrowInfo;
import com.huisa.common.database.BaseDao;
import com.huisa.common.database.model.PageBean;
import com.huisa.common.exception.ServiceException;

@Repository
public class BorrowDao  extends BaseDao{

	public List<QueryBorrowInfo> queryBorrowInfoByPage(PageBean pagebean,
			String sql, List<Object> queryParams) throws ServiceException {
		return listByPage(pagebean, sql, QueryBorrowInfo.class, queryParams);
	}


	public boolean isExistsBorrow(int bookId, int userId) throws ServiceException {
		int value=getInt("SELECT 1 FROM borrow WHERE reader_id=? AND book_id=? and is_back=0",userId,bookId);
		return value==1 ? true : false;
	}


	public void updateBorrowBacktime(int bookId, int userId, String bt) throws ServiceException {
		 update("UPDATE borrow SET back_time=? WHERE reader_id=? AND book_id=?"
				 ,bt,userId,bookId);
	}


	public Borrow getBorrowByUserIdANDBookId(int bookId, int userId) throws ServiceException {
		return get("select * from borrow where reader_id=? AND book_id=? and is_back=0",Borrow.class,userId,bookId);
	}

}
