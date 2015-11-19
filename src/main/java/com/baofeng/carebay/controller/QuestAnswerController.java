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

import com.baofeng.carebay.service.IQuestAnswerService;
import com.baofeng.commons.entity.Operator;
import com.baofeng.utils.Constants;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;
import com.baofeng.utils.SearchRule;

@Controller
@RequestMapping("qans")
public class QuestAnswerController {

	@Autowired
	private IQuestAnswerService questAnswerService;

	public IQuestAnswerService getQuestAnswerService() {
		return questAnswerService;
	}

	public void setQuestAnswerService(IQuestAnswerService questAnswerService) {
		this.questAnswerService = questAnswerService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(String gid, String inds) {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/questAnswer");
		if (gid != null && gid.trim().length() > 0) {
			mav.addObject("userId", gid);
		}
		if (inds != null && inds.trim().length() > 0) {
			mav.addObject("inds", inds);
		}
		return mav;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@RequestMapping(value = "/readPages", method = RequestMethod.POST)
	public PageResult readPages(int page, int rows, String userId, String inds,HttpServletRequest request) {
		PageResult pages = null;
		Operator user = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		if (user != null) {
			SearchFilter filter = new SearchFilter();
			if (user.getChamber() != null && user.getChamber().trim().length() > 0) {
				SearchRule search = new SearchRule();
				search.setData(user.getChamber());
				search.setField("operator.chamber");
				search.setOp("eq");
				List<SearchRule> rules = new ArrayList<SearchRule>();
				rules.add(search);
				filter.setRules(rules);
			}
			pages = this.questAnswerService.readAllPages(rows, page, userId, inds,filter);
		}
		return pages;
	}
}
