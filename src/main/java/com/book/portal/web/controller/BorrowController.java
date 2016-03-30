package com.book.portal.web.controller;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.book.portal.entity.po.portal.PortalUser;
import com.book.portal.entity.vo.QueryBorrowInfo;
import com.book.portal.service.book.BorrowService;
import com.book.portal.web.common.AjaxData;
import com.book.portal.web.common.MVCUtil;
import com.huisa.common.database.model.PageBean;
import com.huisa.common.exception.ServiceException;

@Controller
public class BorrowController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BorrowController.class);
    private static final String BORROW_SEARCH = "BORROW_SEARCH";
    private static final int PAGESIZE = 10;
    
	@Autowired
	private BorrowService borrowService;
	/**
	 * 获取图书列表
	 */
	@RequestMapping(value = "borrow/show_borrow_list",method = RequestMethod.GET)
    public String showUserList(Model model) throws ServiceException {
		int pageid = MVCUtil.getIntParam("pageid");
		QueryBorrowInfo queryBorrowInfo = null;
		if (pageid <= 0) {
			pageid = 1;
			MVCUtil.removeSessionAttribute(BORROW_SEARCH);
		} else {
			Object obj = MVCUtil.getSessionAttribute(BORROW_SEARCH);
			if (obj != null) {
				queryBorrowInfo = (QueryBorrowInfo) obj;
			}
		}
		PageBean pagebean = new PageBean(pageid, PAGESIZE);
		List<QueryBorrowInfo> borrowInfoList = borrowService.queryBorrowInfoListByPage(
				queryBorrowInfo, pagebean);
		model.addAttribute("borrowInfoList", borrowInfoList);
		model.addAttribute("queryBorrowInfo", queryBorrowInfo);
		model.addAttribute("pagebean", pagebean);
        model.addAttribute("pageuri", "/borrow/show_borrow_list?");
        return "borrow/show_borrow_list";
    }
	@RequestMapping(value = "borrow/user_back", method = RequestMethod.POST)
	@ResponseBody
	public void userBack(Model model) {
		AjaxData ajaxdata;
		String msg = null;
		PortalUser portalUser = (PortalUser) SecurityUtils.getSubject()
				.getPrincipal();
	    String ISBN = MVCUtil.getParam("ISBN");
	    String libraryCard = MVCUtil.getParam("libraryCard");
	    int isLost=MVCUtil.getIntParam("isLost");
		if (StringUtils.isNotBlank(msg)) {
			ajaxdata = new AjaxData(false, null, msg);
			MVCUtil.ajaxJson(ajaxdata);
			return;
		}
		try {
			String message =borrowService.userBack(ISBN,libraryCard,portalUser.getId(),isLost);
			ajaxdata = new AjaxData(true, null, message);
		} catch (ServiceException e){
			ajaxdata = new AjaxData(false, null, e.getMessage());
			LOGGER.error(e.getMessage());
		}
		MVCUtil.ajaxJson(ajaxdata);
	}
	
	/**
	 * 查询图书
	 */
	@RequestMapping(value = "borrow/search", method = RequestMethod.POST)
    public String searchUser(QueryBorrowInfo queryBorrowInfo,Model model) throws ServiceException {
	    int pageid = 0;
        if (queryBorrowInfo != null) {
            MVCUtil.setSessionAttribute(BORROW_SEARCH, queryBorrowInfo);
            pageid = 1;
        }
        return "redirect:/borrow/show_borrow_list?pageid=" + pageid;
    }
	@RequestMapping(value = "borrow/delete", method = RequestMethod.GET)
    public String deleteBorrow(Model model) throws ServiceException {
       int id = MVCUtil.getIntParam("id");
       if(id>0)
    	   borrowService.deleteBorrow(id);
       return "redirect:/borrow/show_borrow_list";
    }
}
