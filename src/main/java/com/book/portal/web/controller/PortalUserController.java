package com.book.portal.web.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.book.portal.entity.po.portal.PortalRole;
import com.book.portal.entity.po.portal.PortalUser;
import com.book.portal.entity.vo.QueryUserVo;
import com.book.portal.service.user.PortalUserService;
import com.book.portal.web.common.AjaxData;
import com.book.portal.web.common.MVCUtil;
import com.huisa.common.database.model.PageBean;
import com.huisa.common.exception.ServiceException;

@Controller
public class PortalUserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PortalUserController.class);
    private static final String USER_SEARCH = "USER_SEARCH";
    private static final int PAGESIZE = 10;
    
	@Autowired
	private PortalUserService portalUserService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
//		PaymentUser paymentUser = (PaymentUser) SecurityUtils.getSubject()
//				.getPrincipal();
//		if (paymentUser != null) {
//			return "redirect:/index";
//		}
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String check(Model model) throws ServiceException {
		String uname = MVCUtil.getParam("uname");
		String passwd = MVCUtil.getParam("passwd");
		if (StringUtils.isBlank(uname) || StringUtils.isBlank(passwd)) {
			model.addAttribute("msg", "用户名或密码不能为空!");
			return "login";
		}
		passwd = DigestUtils.md5Hex(passwd);

		UsernamePasswordToken token = new UsernamePasswordToken(uname, passwd);
		token.setRememberMe(true);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			return "redirect:/index";
		} catch (UnknownAccountException e) {
			model.addAttribute("msg", "用户名或密码错误");
			return "login";
		} catch (IncorrectCredentialsException e) {
			model.addAttribute("msg", "用户名或密码错误");
			return "login";
		}
	}

	@RequestMapping(value = "/logout")
	public String logout(Model model) {
		SecurityUtils.getSubject().logout();
		return "redirect:/login";
	}

	@RequestMapping(value = "/index")
	public String index(Model model) {
		PortalUser portalUser = (PortalUser) SecurityUtils.getSubject()
				.getPrincipal();
		model.addAttribute("loginInfo", portalUser);
		return "index";
	}
	@RequestMapping(value = "/reset_passwd")
	@ResponseBody
	public void resetPasswd(Model model) {
		PortalUser portalUser = (PortalUser) SecurityUtils.getSubject()
				.getPrincipal();
        String newPasswd = MVCUtil.getParam("passwd");
        String rePasswd = MVCUtil.getParam("rePasswd");
 	   AjaxData ajaxdata;
 	   String msg = null;
       
        if (StringUtils.isBlank(newPasswd)||StringUtils.isBlank(rePasswd)) {
            msg = "输入密码不能为空！";
        }  else if (!newPasswd.equals(rePasswd)) {
            msg = "两次输入的密码不相同！";
        }
        if (StringUtils.isNotBlank(msg)) {
            ajaxdata = new AjaxData(false, null, msg);
            MVCUtil.ajaxJson(ajaxdata);
            return;
        }
        try {
        	portalUser.setPasswd(DigestUtils.md5Hex(newPasswd));
            portalUserService.updateUser(portalUser);
            ajaxdata = new AjaxData(true, null, null);
        } catch (ServiceException e){
            ajaxdata = new AjaxData(false, null, "系统繁忙!");
            LOGGER.error(e.getMessage());
        }
        MVCUtil.ajaxJson(ajaxdata);
	}
	@RequestMapping(value = "/home")
	public String home(Model model) {
		return "home";
	}
	
	/**
	 * 获取用户列表
	 */
	@RequiresPermissions("portal:user:manage")
	@RequestMapping(value = "portal_user/show_user_list",method = RequestMethod.GET)
    public String showUserList(Model model) throws ServiceException {
        int pageid = MVCUtil.getIntParam("pageid");
        QueryUserVo queryUserVo = null;
        if (pageid <= 0) {
            pageid = 1;
            MVCUtil.removeSessionAttribute(USER_SEARCH);
        } else {
            Object obj = MVCUtil
                    .getSessionAttribute(USER_SEARCH);
            if (obj != null) {
                queryUserVo = (QueryUserVo) obj;
            }
        }
        PageBean pagebean = new PageBean(pageid, PAGESIZE);
        List<PortalUser> portaltUserList = portalUserService
                .queryPortalUserListByPage(queryUserVo, pagebean);
        List<QueryUserVo> userVoList=portalUserService
                .getQueryUserVoList(portaltUserList);
        List<PortalRole> userRoleList=portalUserService
                .getRoleVoList();
        model.addAttribute("userVoList", userVoList);
        model.addAttribute("queryUserVo", queryUserVo);
        model.addAttribute("userRoleList", userRoleList);
        model.addAttribute("pagebean", pagebean);
        model.addAttribute("pageuri", "/portal_user/show_user_list?");
        return "portal_user/show_user_list";
    }
	
	/**
	 * 查询用户
	 */
	@RequiresPermissions("portal:user:manage")
	@RequestMapping(value = "portal_user/search", method = RequestMethod.POST)
    public String searchUser(QueryUserVo queryUserVo,Model model) throws ServiceException {
	    int pageid = 0;
        if (queryUserVo != null) {
            MVCUtil.setSessionAttribute(USER_SEARCH, queryUserVo);
            pageid = 1;
        }
        return "redirect:/portal_user/show_user_list?pageid=" + pageid;
    }
	@RequiresPermissions("portal:user:manage")
	@RequestMapping(value = "portal_user/delete", method = RequestMethod.GET)
    public String deletePaymentUser(Model model) throws ServiceException {
       int id = MVCUtil.getIntParam("id");
       if(id>0)
           portalUserService.deletePortalUser(id);
       return "redirect:/portal_user/show_user_list";
    }
	@RequiresPermissions("portal:user:manage")
	@RequestMapping(value = "portal_user/reset_password", method = RequestMethod.GET)
	public String resetPassword(Model model) throws ServiceException {
		int id = MVCUtil.getIntParam("id");
		if(id>0)
			portalUserService.resetPassword(id);
		return "redirect:/portal_user/show_user_list";
	}
	
	/**
	 * 添加新用户
	 */
	@RequiresPermissions("portal:user:manage")
    @RequestMapping(value = "portal_user/addPortalUser", method = RequestMethod.POST)
	@ResponseBody
    public void addPortalUser(PortalUser portalUser,Model model){
	   AjaxData ajaxdata;
	   String msg = null;
	   String rePasswd=MVCUtil.getParam("rePasswd");
	   String role=MVCUtil.getParam("role");
      
       if (StringUtils.isBlank(portalUser.getAccount())) {
           msg = "账号不能为空！";
       } else if (StringUtils.isBlank(portalUser.getUserName())) {
           msg = "用户姓名不能为空！";
       } else if(StringUtils.isBlank(role)){
           msg = "请选择用户的角色！";
       } else if (StringUtils.isBlank(portalUser.getPasswd())) {
           msg = "密码不能为空！";       
       } else if (StringUtils.isBlank(rePasswd)) {
           msg = "新密码没有重复输入！";
       } else if (!portalUser.getPasswd().equals(rePasswd)) {
           msg = "两次输入的密码不相同！";
       }

       if (StringUtils.isNotBlank(msg)) {
           ajaxdata = new AjaxData(false, null, msg);
           MVCUtil.ajaxJson(ajaxdata);
           return;
       }
       try {
           portalUser.setCreateTime(new Date());
           portalUserService.addPortalUser(portalUser,role);
           ajaxdata = new AjaxData(true, null, null);
       } catch (ServiceException e){
           ajaxdata = new AjaxData(false, null, "该帐号或该图书证件号已经存在！");
           LOGGER.error(e.getMessage());
       }
       MVCUtil.ajaxJson(ajaxdata);
    }
}
