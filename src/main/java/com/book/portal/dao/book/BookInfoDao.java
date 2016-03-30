package com.book.portal.dao.book;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.book.portal.entity.po.book.BookInfo;
import com.book.portal.entity.vo.QueryBookInfo;
import com.huisa.common.database.BaseDao;
import com.huisa.common.database.model.PageBean;
import com.huisa.common.exception.ServiceException;

@Repository
public class BookInfoDao  extends BaseDao{

	public List<QueryBookInfo> queryBookInfoByPage(PageBean pagebean,
			String sql, List<Object> queryParams) throws ServiceException {
		return listByPage(pagebean, sql, QueryBookInfo.class, queryParams);
	}

	public boolean hasExistsBookISBN(String isbn) throws ServiceException {
		int value=getInt("SELECT 1 FROM book_info WHERE ISBN=? AND is_delete=0",isbn);
		return value==1 ? true : false;
	}

	public void updateBookDelete(int id) throws ServiceException {
		 update("UPDATE book_info SET is_delete=1 WHERE id=?"
					,id);
	}

	public BookInfo getBookInfoById(int id) throws ServiceException {
		return get("select * from book_info where id=?",BookInfo.class,id);
	}

	public void updateBookBorrow(String iSBN) throws ServiceException {
		 update("UPDATE book_info SET has_number=has_number-1 WHERE ISBN=? AND is_delete=0"
				 ,new Object[] {iSBN});
	}
	public void updateBookBorrowAdd(String iSBN) throws ServiceException {
		 update("UPDATE book_info SET has_number=has_number+1 WHERE ISBN=? AND is_delete=0"
				 ,new Object[] {iSBN});
	}
	public boolean isUnableBeBorrow(String iSBN) throws ServiceException {
		int value=getInt("SELECT 1 FROM book_info WHERE ISBN=? AND is_delete=0 AND has_number<=1",iSBN);
		return value==1 ? true : false;
	}

	public BookInfo getBookIdByISBN(String iSBN) throws ServiceException {
		return get("select id from book_info where ISBN=? AND is_delete=0",BookInfo.class,iSBN);
	}

}
