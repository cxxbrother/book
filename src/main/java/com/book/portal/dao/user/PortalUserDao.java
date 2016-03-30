package com.book.portal.dao.user;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Repository;

import com.book.portal.entity.po.portal.PortalFunction;
import com.book.portal.entity.po.portal.PortalRole;
import com.book.portal.entity.po.portal.PortalUser;
import com.huisa.common.database.BaseDao;
import com.huisa.common.database.model.PageBean;
import com.huisa.common.exception.ServiceException;

@Repository
public class PortalUserDao extends BaseDao {
    public PortalUser getUserByAccount(String username)
            throws ServiceException {
        return get("SELECT * FROM user_info WHERE account=?",
                PortalUser.class, username);
    }

    public List<PortalRole> listPortalUserRoles(Integer id)
            throws ServiceException {
        return list(
                "SELECT pr.* FROM  role AS pr JOIN  user_role AS pur ON pr.id=pur.role_id AND pur.user_id=? AND pr.is_delete=0",
                PortalRole.class, id);
    }

    public List<PortalFunction> listPortalUserFunctions(Integer id)
            throws ServiceException {
        return list(
                "SELECT pf.* FROM  function AS pf JOIN  role_function AS prf ON pf.id=prf.function_id"
                        + " JOIN  user_role AS pur ON prf.role_id=pur.role_id AND pur.user_id=?",
                        PortalFunction.class, id);
    }
    
    public List<PortalUser> queryUserByPage(PageBean pageBean, String sql,
            List<Object> params) throws ServiceException {
        return listByPage(pageBean, sql, PortalUser.class, params);
    }
    
    public void deleteUserById(int id) throws ServiceException {
            delete("DELETE  user, user_role from  user_info LEFT JOIN  user_role "
                    + "ON  user_info.id= user_role.user_id WHERE  user_info.id=?", id);
    }

    public int addPortalUser(PortalUser PortalUser) throws ServiceException {
        return addReturnGeneratedKey(PortalUser);
    }
    public void addPortalUserRole(Object object) throws ServiceException {
        add(object);
    }

	public void setIsDelete(int id) throws ServiceException {
		 update("UPDATE user_info SET is_delete=1 WHERE id=?"
					,id);
		
	}

	public void resetPassword(int id) throws ServiceException {
        String passwd =DigestUtils.md5Hex("123456");
		 update("UPDATE user_info SET passwd=? WHERE id=?"
					,new Object[] { passwd, id });
		
	}
	public boolean hasExistsUserLibraryCard(String libraryCard) throws ServiceException {
		int value=getInt("SELECT 1 FROM user_info WHERE library_card=? AND is_delete=0",libraryCard);
		return value==1 ? true : false;
	}

	public PortalUser getUserIdByLibraryCard(String libraryCard) throws ServiceException {
        return get("SELECT id FROM user_info WHERE library_card=? AND is_delete=0",
        		PortalUser.class,libraryCard);
	}

	public boolean isAbleToBorrow(String libraryCard) throws ServiceException {
		int value=getInt("SELECT 1 FROM user_info as ui join role as r join user_role as ur"
				+ " on ui.library_card=? AND ui.is_delete=0 AND r.id=ur.role_id "
				+ " AND ur.user_id=ui.id AND r.can_borrow_number>ui.has_borrow_number",libraryCard);
		return value==1 ? true : false;
	}

	public void updateUserBorrow(String libraryCard) throws ServiceException {
		 update("UPDATE user_info SET has_borrow_number=has_borrow_number+1 WHERE library_card=? AND is_delete=0"
					,new Object[] {libraryCard});
	}

	public void updateUserBorrowDown(String libraryCard) throws ServiceException {
		 update("UPDATE user_info SET has_borrow_number=has_borrow_number-1 WHERE library_card=? AND is_delete=0"
					,new Object[] {libraryCard});
	}

	public List<PortalRole> getRoleVoList() throws ServiceException {
		return list("select * from role where is_delete=0",PortalRole.class);
	}

}
