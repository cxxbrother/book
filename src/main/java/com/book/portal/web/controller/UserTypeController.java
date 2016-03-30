package com.book.portal.web.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.book.portal.entity.po.book.BookInfo;
import com.book.portal.entity.po.portal.PortalRole;
import com.book.portal.entity.po.portal.PortalUser;
import com.book.portal.entity.vo.QueryBookInfo;
import com.book.portal.entity.vo.QueryGiveBack;
import com.book.portal.entity.vo.QueryUserVo;
import com.book.portal.service.book.BookService;
import com.book.portal.service.book.GiveBackService;
import com.book.portal.service.user.UserTypeService;
import com.book.portal.web.common.AjaxData;
import com.book.portal.web.common.MVCUtil;
import com.huisa.common.database.model.PageBean;
import com.huisa.common.exception.ServiceException;

@Controller
public class UserTypeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserTypeController.class);
    private static final String ROLE_SEARCH = "ROLE_SEARCH";
    private static final int PAGESIZE = 10;
    
	@Autowired
	private UserTypeService userTypeService;
	/**
	 * 获取图书列表
	 */
	@RequestMapping(value = "role/show_role_list",method = RequestMethod.GET)
    public String showUserList(Model model) throws ServiceException {
		int pageid = MVCUtil.getIntParam("pageid");
		PortalRole portalRole = null;
		if (pageid <= 0) {
			pageid = 1;
			MVCUtil.removeSessionAttribute(ROLE_SEARCH);
		} else {
			Object obj = MVCUtil.getSessionAttribute(ROLE_SEARCH);
			if (obj != null) {
				portalRole = (PortalRole) obj;
			}
		}
		PageBean pagebean = new PageBean(pageid, PAGESIZE);
		List<PortalRole> roleList = userTypeService.queryRoleListByPage(
				portalRole, pagebean);
		model.addAttribute("roleList", roleList);
		model.addAttribute("portalRole", portalRole);
		model.addAttribute("pagebean", pagebean);
        model.addAttribute("pageuri", "role/show_role_list?");
        return "role/show_role_list";
    }
	@RequestMapping(value = "role/add", method = RequestMethod.POST)
	@ResponseBody
	public void addRole(Model model,PortalRole role) {
		AjaxData ajaxdata;
		String msg = null;
	   if (StringUtils.isBlank(role.getRoleCode())) {
	       msg = "类型code不能为空！";
	   } else if (StringUtils.isBlank(role.getRoleName())) {
	       msg = "用户类型不能为空！";
	   } else if(role.getCanBorrowNumber()==null||role.getCanBorrowNumber()<=0){
	       msg = "可借书数量必须大于0！";
	   } else if(role.getOverdueFine()==null||role.getOverdueFine()<=0){
		   msg = "逾期罚款值必须大于0！";
	   } else if(role.getLostFine()==null||role.getLostFine()<=0){
			msg = "遗失罚款值必须大于0！";
	   }
		if (StringUtils.isNotBlank(msg)) {
			ajaxdata = new AjaxData(false, null, msg);
			MVCUtil.ajaxJson(ajaxdata);
			return;
		}
		try {
			userTypeService.addRole(role);
			ajaxdata = new AjaxData(true, null, null);
		} catch (ServiceException e){
			ajaxdata = new AjaxData(false, null, e.getMessage());
			LOGGER.error(e.getMessage());
		}
		MVCUtil.ajaxJson(ajaxdata);
	}
	@RequestMapping(value = "role/delete", method = RequestMethod.GET)
    public String deletePaymentUser(Model model) throws ServiceException {
       int id = MVCUtil.getIntParam("id");
       if(id>0)
    	   userTypeService.deleteRole(id);
       return "redirect:/role/show_role_list";
    }
    @RequestMapping(value = "role/edit", method = RequestMethod.GET)
    public String showRole(Model model) {
        int id = MVCUtil.getIntParam("id");
        try {
        	PortalRole role=userTypeService.getRoleById(id);
            model.addAttribute("role", role);
        } catch (ServiceException e) {
            model.addAttribute("msg","系统繁忙！请稍候再试！");
            LOGGER.error(e.getMessage());
        }
        return "role/edit_role";
    }
    @RequestMapping(value = "role/ajax/edit", method = RequestMethod.POST)
    @ResponseBody
    public void editBook(PortalRole role) {
        AjaxData ajaxdata = null;
		   String msg = null;
		   if (StringUtils.isBlank(role.getRoleCode())) {
		       msg = "类型code不能为空！";
		   } else if (StringUtils.isBlank(role.getRoleName())) {
		       msg = "用户类型不能为空！";
		   } else if(role.getCanBorrowNumber()==null||role.getCanBorrowNumber()<=0){
		       msg = "可借书数量必须大于0！";
		   } else if(role.getOverdueFine()==null||role.getOverdueFine()<=0){
			   msg = "逾期罚款值必须大于0！";
		   } else if(role.getLostFine()==null||role.getLostFine()<=0){
				msg = "遗失罚款值必须大于0！";
		   }
	       if (StringUtils.isNotBlank(msg)) {
	           ajaxdata = new AjaxData(false, null, msg);
	           MVCUtil.ajaxJson(ajaxdata);
	           return;
	       }
        try {  
        	userTypeService.updateRole(role);
            ajaxdata = new AjaxData(true, null, msg);
        } catch (Exception e) {
            ajaxdata = new AjaxData(false, null, "系统繁忙！请稍候再试！");
            LOGGER.error(e.getMessage());
        }
        MVCUtil.ajaxJson(ajaxdata);
    }
}
