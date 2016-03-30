package com.book.portal.web.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.book.portal.entity.po.book.BookInfo;
import com.book.portal.entity.po.portal.PortalUser;
import com.book.portal.entity.vo.QueryBookInfo;
import com.book.portal.entity.vo.QueryBorrowInfo;
import com.book.portal.entity.vo.QueryGiveBack;
import com.book.portal.entity.vo.QueryUserVo;
import com.book.portal.service.book.BookService;
import com.book.portal.service.book.BorrowService;
import com.book.portal.service.book.GiveBackService;
import com.book.portal.web.common.MVCUtil;
import com.huisa.common.database.model.PageBean;
import com.huisa.common.exception.ServiceException;

@Controller
public class GiveBackController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GiveBackController.class);
    private static final String GIVEBACK_SEARCH = "GIVEBACK_SEARCH";
    private static final int PAGESIZE = 10;
    
	@Autowired
	private GiveBackService giveBackService;
	/**
	 * 获取图书列表
	 */
	@RequestMapping(value = "giveback/show_giveback_list",method = RequestMethod.GET)
    public String showUserList(Model model) throws ServiceException {
		int pageid = MVCUtil.getIntParam("pageid");
		QueryGiveBack queryGiveBack = null;
		if (pageid <= 0) {
			pageid = 1;
			MVCUtil.removeSessionAttribute(GIVEBACK_SEARCH);
		} else {
			Object obj = MVCUtil.getSessionAttribute(GIVEBACK_SEARCH);
			if (obj != null) {
				queryGiveBack = (QueryGiveBack) obj;
			}
		}
		PageBean pagebean = new PageBean(pageid, PAGESIZE);
		List<QueryGiveBack> givebackList = giveBackService.queryGivebackListByPage(
				queryGiveBack, pagebean);
		model.addAttribute("givebackList", givebackList);
		model.addAttribute("queryGiveBack", queryGiveBack);
		model.addAttribute("pagebean", pagebean);
        model.addAttribute("pageuri", "/giveback/show_giveback_list?");
        return "giveback/show_giveback_list";
    }
	/**
	 * 查询图书
	 */
	@RequestMapping(value = "giveback/search", method = RequestMethod.POST)
    public String searchUser(QueryGiveBack queryGiveBack,Model model) throws ServiceException {
	    int pageid = 0;
        if (queryGiveBack != null) {
            MVCUtil.setSessionAttribute(GIVEBACK_SEARCH, queryGiveBack);
            pageid = 1;
        }
        return "redirect:/giveback/show_giveback_list?pageid=" + pageid;
    }
	@RequestMapping(value = "giveback/delete", method = RequestMethod.GET)
    public String deleteBorrow(Model model) throws ServiceException {
       int id = MVCUtil.getIntParam("id");
       if(id>0)
    	   giveBackService.deleteGiveback(id);
       return "redirect:/giveback/show_giveback_list";
    }
}
