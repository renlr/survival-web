package com.baofeng.carebay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.baofeng.carebay.service.IFindingsService;
import com.baofeng.utils.Constants;

/**
 * 功能：调查结果
 * */
@Controller
@RequestMapping("fds")
public class FindingsController {

	@Autowired
	private IFindingsService findingsService;

	public IFindingsService getFindingsService() {
		return findingsService;
	}

	public void setFindingsService(IFindingsService findingsService) {
		this.findingsService = findingsService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/questionnaire");
		return mav;
	}

}
