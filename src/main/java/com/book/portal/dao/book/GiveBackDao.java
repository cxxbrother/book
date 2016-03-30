package com.book.portal.dao.book;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.book.portal.entity.vo.QueryGiveBack;
import com.huisa.common.database.BaseDao;
import com.huisa.common.database.model.PageBean;
import com.huisa.common.exception.ServiceException;

@Repository
public class GiveBackDao  extends BaseDao{

	public List<QueryGiveBack> queryGuideInfoByPage(PageBean pagebean,
			String sql, List<Object> queryParams) throws ServiceException {
		return listByPage(pagebean, sql, QueryGiveBack.class, queryParams);
	}

}
