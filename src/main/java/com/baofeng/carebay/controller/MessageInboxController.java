package com.baofeng.carebay.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baofeng.carebay.service.IMessageInboxService;
import com.baofeng.commons.entity.Operator;
import com.baofeng.utils.Constants;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.ResultMsg;
import com.baofeng.utils.SearchFilter;
import com.baofeng.utils.SearchRule;

@Controller
@RequestMapping("msgInbox")
public class MessageInboxController {

	@Autowired
	private IMessageInboxService messageInboxService;

	public IMessageInboxService getMessageInboxService() {
		return messageInboxService;
	}

	public void setMessageInboxService(IMessageInboxService messageInboxService) {
		this.messageInboxService = messageInboxService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/messageInbox");
		return mav;
	}
	
	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@RequestMapping(value = "/readPages", method = RequestMethod.POST)
	public PageResult readPages(int page, int rows, HttpServletRequest request) {
		PageResult pages = null;
		Operator user = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		if (user != null) {
			SearchFilter filter = new SearchFilter();
			if (user.getChamber() != null && user.getChamber().trim().length() > 0) {
				SearchRule search = new SearchRule();
				search.setData(user.getChamber());
				search.setField("user.chamber");
				search.setOp("eq");
				List<SearchRule> rules = new ArrayList<SearchRule>();
				rules.add(search);
				filter.setRules(rules);
			}
			pages = this.messageInboxService.readAllPages(rows, page, filter);
		}
		return pages;
	}
	
	
	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@RequestMapping(value = "/dealWith", method = RequestMethod.GET)
	public ResultMsg dealWith(String id) {
		ResultMsg msg = new ResultMsg();
		msg.setResultMessage("errors");
		if(id != null && id.trim().length() > 0){
			if(this.messageInboxService.updateDealWith(id)){
				msg.setResultMessage("success");
			}
		}
		return msg;
	}
}
