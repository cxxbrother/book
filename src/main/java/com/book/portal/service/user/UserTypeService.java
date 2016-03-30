package com.book.portal.service.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.portal.common.ErrorCode;
import com.book.portal.dao.user.UserTypeDao;
import com.book.portal.entity.po.portal.PortalRole;
import com.huisa.common.database.model.PageBean;
import com.huisa.common.exception.ServiceException;

@Service
public class UserTypeService {
    @Autowired
    private UserTypeDao userTypeDao;

	public List<PortalRole> queryRoleListByPage(PortalRole portalRole,
			PageBean pagebean) throws ServiceException {
		StringBuffer querySqlBuffer = new StringBuffer();
        List<Object> queryParams = new ArrayList<Object>();
        
        querySqlBuffer.append("SELECT * from role WHERE is_delete=0");
		
		
		return userTypeDao.queryRoleByPage(
                pagebean, querySqlBuffer.toString(), queryParams);
	}

	public void addRole(PortalRole role) throws ServiceException {
		boolean hasExistsRole=userTypeDao.hasExistsRole(role);
		if(hasExistsRole){
			throw new ServiceException(ErrorCode.CODE_PARAM,
					"该类型code或用户类型已存在！");
		}
		role.setCreateTime(new Date());
		userTypeDao.add(role);
		
	}

	public void deleteRole(int id) throws ServiceException {
		userTypeDao.updateDelete(id);
		
	}

	public PortalRole getRoleById(int id) throws ServiceException {
		return userTypeDao.getRoleById(id);
	}

	public void updateRole(PortalRole role) throws ServiceException {
		userTypeDao.update(role, "id");
	}
}
