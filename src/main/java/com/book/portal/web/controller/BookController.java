package com.book.portal.web.controller;

import java.util.Date;
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
import com.book.portal.entity.po.portal.PortalUser;
import com.book.portal.entity.vo.QueryBookInfo;
import com.book.portal.entity.vo.QueryUserVo;
import com.book.portal.service.book.BookService;
import com.book.portal.web.common.AjaxData;
import com.book.portal.web.common.MVCUtil;
import com.huisa.common.database.model.PageBean;
import com.huisa.common.exception.ServiceException;

@Controller
public class BookController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);
    private static final String BOOK_SEARCH = "BOOK_SEARCH";
    private static final int PAGESIZE = 10;
    
	@Autowired
	private BookService bookService;
	/**
	 * 获取图书列表
	 */
	@RequestMapping(value = "book/show_book_list",method = RequestMethod.GET)
    public String showUserList(Model model) throws ServiceException {
		int pageid = MVCUtil.getIntParam("pageid");
		QueryBookInfo queryBookInfo = null;
		if (pageid <= 0) {
			pageid = 1;
			MVCUtil.removeSessionAttribute(BOOK_SEARCH);
		} else {
			Object obj = MVCUtil.getSessionAttribute(BOOK_SEARCH);
			if (obj != null) {
				queryBookInfo = (QueryBookInfo) obj;
			}
		}
		PageBean pagebean = new PageBean(pageid, PAGESIZE);
		List<QueryBookInfo> bookInfoList = bookService.queryBookInfoListByPage(
				queryBookInfo, pagebean);
		model.addAttribute("bookInfoList", bookInfoList);
		model.addAttribute("queryBookInfo", queryBookInfo);
		model.addAttribute("pagebean", pagebean);
        model.addAttribute("pageuri", "/book/show_book_list?");
        return "book/show_book_list";
    }
	/**
	 * 查询图书
	 */
	@RequestMapping(value = "book/search", method = RequestMethod.POST)
    public String searchUser(QueryBookInfo queryBookInfo,Model model) throws ServiceException {
	    int pageid = 0;
        if (queryBookInfo != null) {
            MVCUtil.setSessionAttribute(BOOK_SEARCH, queryBookInfo);
            pageid = 1;
        }
        return "redirect:/book/show_book_list?pageid=" + pageid;
    }
	@RequestMapping(value = "book/delete", method = RequestMethod.GET)
    public String deleteBook(Model model) throws ServiceException {
       int id = MVCUtil.getIntParam("id");
       if(id>0)
    	   bookService.deleteBook(id);
       return "redirect:/book/show_book_list";
    }
	@RequestMapping(value = "book/add", method = RequestMethod.POST)
	@ResponseBody
	public void addBook(Model model,BookInfo bookInfo) throws ServiceException {
		   AjaxData ajaxdata;
		   String msg = null;
			PortalUser portalUser = (PortalUser) SecurityUtils.getSubject()
					.getPrincipal();
	       if (StringUtils.isBlank(bookInfo.getBookName())) {
	           msg = "书名不能为空！";
	       } else if (StringUtils.isBlank(bookInfo.getISBN())) {
	           msg = "书号不能为空！";
	       } else if(StringUtils.isBlank(bookInfo.getType())){
	           msg = "类型不能为空！";
		   } else if(bookInfo.getPrice()==null||bookInfo.getPrice()<=0){
			   msg = "价格必须大于0！";
		   } else if(bookInfo.getTotalNumber()==null||bookInfo.getTotalNumber()<=0){
			   msg = "总数量必须大于0！";
		   }
	       if (StringUtils.isNotBlank(msg)) {
	           ajaxdata = new AjaxData(false, null, msg);
	           MVCUtil.ajaxJson(ajaxdata);
	           return;
	       }
	       try {
	    	   bookInfo.setInTime(new Date());
	    	   bookInfo.setOperatorId(portalUser.getId());
	    	   bookInfo.setHasNumber(bookInfo.getTotalNumber());
	    	   bookService.addBook(bookInfo);
	           ajaxdata = new AjaxData(true, null, null);
	       } catch (ServiceException e){
	           ajaxdata = new AjaxData(false, null, "该图书号已经存在图书！请更换图书号");
	           LOGGER.error(e.getMessage());
	       }
	       MVCUtil.ajaxJson(ajaxdata);
	   }
	@RequestMapping(value = "book/user_borrow", method = RequestMethod.POST)
	@ResponseBody
	public void userBorrow(Model model) {
		AjaxData ajaxdata;
		String msg = null;
		PortalUser portalUser = (PortalUser) SecurityUtils.getSubject()
				.getPrincipal();
	    String ISBN = MVCUtil.getParam("ISBN");
	    String libraryCard = MVCUtil.getParam("libraryCard");
		
	    
	    
		if (StringUtils.isNotBlank(msg)) {
			ajaxdata = new AjaxData(false, null, msg);
			MVCUtil.ajaxJson(ajaxdata);
			return;
		}
		try {
			bookService.userBorrow(ISBN,libraryCard,portalUser.getId());
			ajaxdata = new AjaxData(true, null, null);
		} catch (ServiceException e){
			ajaxdata = new AjaxData(false, null, e.getMessage());
			LOGGER.error(e.getMessage());
		}
		MVCUtil.ajaxJson(ajaxdata);
	}
    @RequestMapping(value = "book/edit_book", method = RequestMethod.GET)
    public String showBook(Model model) {
        int id = MVCUtil.getIntParam("id");
        try {
        	BookInfo bookInfo=bookService.getBookInfoById(id);
            model.addAttribute("bookInfo", bookInfo);
        } catch (ServiceException e) {
            model.addAttribute("msg","系统繁忙！请稍候再试！");
            LOGGER.error(e.getMessage());
        }
        return "book/edit_book";
    }

    @RequestMapping(value = "book/ajax/edit", method = RequestMethod.POST)
    @ResponseBody
    public void editBook(BookInfo bookInfo) {
        AjaxData ajaxdata = null;
		   String msg = null;
		   System.out.println(bookInfo.getId());
		   if (StringUtils.isBlank(bookInfo.getBookName())) {
		       msg = "书名不能为空！";
		   } else if (StringUtils.isBlank(bookInfo.getISBN())) {
		       msg = "书号不能为空！";
		   } else if(StringUtils.isBlank(bookInfo.getType())){
		       msg = "类型不能为空！";
		   } else if(bookInfo.getPrice()<=0){
			   msg = "价格不能为空！";
		   } else if(bookInfo.getTotalNumber()<=0){
				msg = "总数量不能为空！";
		   } else if(StringUtils.isBlank(bookInfo.getType())){
				msg = "类型不能为空！";
		   }
	       if (StringUtils.isNotBlank(msg)) {
	           ajaxdata = new AjaxData(false, null, msg);
	           MVCUtil.ajaxJson(ajaxdata);
	           return;
	       }
        try {  
        	bookService.updateBookInfo(bookInfo);
            ajaxdata = new AjaxData(true, null, msg);
        } catch (Exception e) {
            ajaxdata = new AjaxData(false, null, "系统繁忙！请稍候再试！");
            LOGGER.error(e.getMessage());
        }
        MVCUtil.ajaxJson(ajaxdata);
    }
}
