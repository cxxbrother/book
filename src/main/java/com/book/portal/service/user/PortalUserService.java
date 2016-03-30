package com.book.portal.service.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.portal.common.ErrorCode;
import com.book.portal.dao.user.PortalUserDao;
import com.book.portal.entity.po.portal.PortalRole;
import com.book.portal.entity.po.portal.PortalUser;
import com.book.portal.entity.po.portal.PortalUserRole;
import com.book.portal.entity.vo.QueryUserVo;
import com.huisa.common.database.model.PageBean;
import com.huisa.common.exception.ServiceException;

@Service
public class PortalUserService {
    @Autowired
    private PortalUserDao portalUserDao;
    
    /**
     * 根据account,user_name,role来分页查询用户
     */
    public List<PortalUser> queryPortalUserListByPage(QueryUserVo queryUserVo,
            PageBean pageBean) throws ServiceException {
        StringBuffer querySqlBuffer = new StringBuffer();
        List<Object> queryParams = new ArrayList<Object>();

        querySqlBuffer.append("SELECT pu.* FROM  user_info AS pu JOIN  user_role AS pur ON pur.user_id=pu.id WHERE 1=1");

        if (queryUserVo != null) {
            if (StringUtils.isNotBlank(queryUserVo.getUser_name())) {
                querySqlBuffer.append(" AND user_name LIKE ?");
                queryParams.add("%" + queryUserVo.getUser_name() + "%");
            }
            if (StringUtils.isNotBlank(queryUserVo.getAccount())) {
                querySqlBuffer.append(" AND account LIKE ?");
                queryParams.add("%" + queryUserVo.getAccount() + "%");
            }
            if (StringUtils.isNotBlank(queryUserVo.getRole())) {
                querySqlBuffer.append(" AND role_id=?");
                queryParams.add(queryUserVo.getRole());
            }
            if (StringUtils.isNotBlank(queryUserVo.getLibrary_card())) {
            	querySqlBuffer.append(" AND library_card=?");
            	queryParams.add(queryUserVo.getLibrary_card());
            }
            if (StringUtils.isNotBlank(queryUserVo.getIdcard())) {
            	querySqlBuffer.append(" AND idcard=?");
            	queryParams.add(queryUserVo.getIdcard());
            }
        }
        querySqlBuffer.append(" group by pu.id");
        List<PortalUser> portalUserList = portalUserDao.queryUserByPage(pageBean, querySqlBuffer.toString(), queryParams);

        return portalUserList;
    }

    /**
     * 获取用于页面显示的QueryUserVo列表
     */
    public List<QueryUserVo> getQueryUserVoList(
            List<PortalUser> PortalUserList) throws ServiceException {
        List<QueryUserVo> queryUserVoList = new ArrayList<QueryUserVo>();
        for (PortalUser portalUser : PortalUserList) {
            List<PortalRole> roleList = portalUserDao.listPortalUserRoles(portalUser.getId());
            StringBuffer roleStr = new StringBuffer();
            for (int i = 0; i < roleList.size(); i++) {
                PortalRole portalRole = roleList.get(i);
                if (i == roleList.size() - 1)
                    roleStr.append(portalRole.getRoleName());
                else
                    roleStr.append(portalRole.getRoleName() + ",");
            }
            QueryUserVo quv = new QueryUserVo();
            quv.setId(portalUser.getId());
            quv.setIdcard(portalUser.getIdcard());
            quv.setIs_delete(portalUser.getIsDelete());
            quv.setLibrary_card(portalUser.getLibraryCard());
            quv.setSex(portalUser.getSex());
            quv.setAccount(portalUser.getAccount());
            quv.setUser_name(portalUser.getUserName());
            quv.setRole(roleStr.toString());
            queryUserVoList.add(quv);
        }
        return queryUserVoList;
    }

    /**
     * 添加新用户
     */
    public void addPortalUser(PortalUser PortalUser, String role)
            throws ServiceException {
		boolean hasExistsUserLibraryCard=portalUserDao.hasExistsUserLibraryCard(PortalUser.getLibraryCard());
		if(hasExistsUserLibraryCard){
			throw new ServiceException(ErrorCode.CODE_PARAM,
					"该图书证件号已存在！");
		}
        String passwd = PortalUser.getPasswd();
        passwd = DigestUtils.md5Hex(passwd);
        PortalUser.setPasswd(passwd);
        int userId = portalUserDao.addPortalUser(PortalUser);
        
        PortalUserRole pur = new PortalUserRole();
        pur.setRoleId(Integer.parseInt(role));
        pur.setUserId(userId);
        pur.setCreateTime(new Date());
        portalUserDao.addPortalUserRole(pur);
    }

    /**
     * 删除用户
     */
    public void deletePortalUser(int id) throws ServiceException {
        portalUserDao.setIsDelete(id);
    }

	public void resetPassword(int id) throws ServiceException {
		portalUserDao.resetPassword(id);
		
	}

	public List<PortalRole> getRoleVoList() throws ServiceException {
		return portalUserDao.getRoleVoList();
	}

	public void updateUser(PortalUser portalUser) throws ServiceException {
		portalUserDao.update(portalUser, "id");
		
	}
}
