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

import com.baofeng.carebay.entity.Questionnaire;
import com.baofeng.carebay.service.IPSQService;
import com.baofeng.commons.entity.Operator;
import com.baofeng.utils.Constants;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.ResultMsg;
import com.baofeng.utils.SearchFilter;
import com.baofeng.utils.SearchRule;

/**
 * 功能：问卷试题
 * */
@Controller
@RequestMapping("psq")
public class PSQController {

	@Autowired
	private IPSQService pSQService;

	public void setpSQService(IPSQService pSQService) {
		this.pSQService = pSQService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(String inds) {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/questionnaire");
		mav.addObject("inds", inds);
		return mav;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/readPages", method = RequestMethod.POST)
	public PageResult readPages(int page, int rows, String inds, HttpServletRequest request) {
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
			pages = this.pSQService.readAllPages(rows, page, inds, filter);
		}
		return pages;
	}

	/**
	 * 功能：添加修改
	 * */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(String inds, String id, String type, String subject, String options, String indexs, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/questionnaire");
		Operator operator = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		Questionnaire quest = new Questionnaire();
		quest.setType(Integer.valueOf(type));
		quest.setSubject(subject);
		quest.setOptions(options);
		quest.setIndexs(Integer.valueOf(indexs));
		quest.setOperator(operator);
		if (this.pSQService.addQusetionnaire(id, inds, quest)) {
			mav.addObject("inds", inds);
			mav.addObject("result", "success");
		}
		return mav;
	}

	/**
	 * 功能：编辑
	 * */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public Questionnaire read(String id) {
		Questionnaire questionnaire = this.pSQService.readQuestionnaire(id);
		if (questionnaire != null) {
			return questionnaire;
		}
		return null;
	}

	/**
	 * 功能：上线
	 * */
	@ResponseBody
	@RequestMapping(value = "/online", method = RequestMethod.GET)
	public ResultMsg online(String id) {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.pSQService.onLineQuestionnaire(id, Integer.valueOf(1))) {
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：删除
	 * */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ResultMsg delete(String id) {
		ResultMsg msg = new ResultMsg();
		msg.setResultMessage("errors");
		if (this.pSQService.deleteQuestionnaire(id)) {
			msg.setResultStatus(200);
			msg.setResultMessage("success");
		}
		return msg;
	}

}
