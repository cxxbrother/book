package com.book.portal.dao.user;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.book.portal.entity.po.portal.PortalRole;
import com.book.portal.entity.vo.QueryBookInfo;
import com.huisa.common.database.BaseDao;
import com.huisa.common.database.model.PageBean;
import com.huisa.common.exception.ServiceException;
@Repository
public class UserTypeDao  extends BaseDao{
    public List<PortalRole> querylistPortalUserRole(Integer id)
            throws ServiceException {
        return list(
                "SELECT * FROM  role where is_delete=0",
                PortalRole.class, id);
    }

	public List<PortalRole> queryRoleByPage(PageBean pagebean, String sql,
			List<Object> queryParams) throws ServiceException {
		return listByPage(pagebean, sql, PortalRole.class, queryParams);
	}

	public boolean hasExistsRole(PortalRole role) throws ServiceException {
		int value=getInt("SELECT 1 FROM role WHERE (role_code=? OR role_name=?) AND is_delete=0",role.getRoleCode(),role.getRoleName());
		return value==1 ? true : false;
	}

	public void updateDelete(int id) throws ServiceException {
		update("UPDATE role SET is_delete=1 WHERE id=?",id);
	}

	public PortalRole getRoleById(int id) throws ServiceException {
		return get("select * from role where id=?",PortalRole.class,id);
	}
}
