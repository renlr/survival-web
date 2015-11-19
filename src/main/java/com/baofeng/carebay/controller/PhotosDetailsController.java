package com.baofeng.carebay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baofeng.carebay.service.IPhotosDetailsService;
import com.baofeng.utils.Constants;
import com.baofeng.utils.PageResult;

@Controller
@RequestMapping("details")
public class PhotosDetailsController {

	@Autowired
	private IPhotosDetailsService photosDetailsService;

	public IPhotosDetailsService getPhotosDetailsService() {
		return photosDetailsService;
	}

	public void setPhotosDetailsService(IPhotosDetailsService photosDetailsService) {
		this.photosDetailsService = photosDetailsService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/photosDetails");
		return mav;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/readPages", method = RequestMethod.POST)
	public PageResult readPages(int page, int rows) {
		PageResult pages = this.photosDetailsService.readAllPages(rows, page, null);
		return pages;
	}
}
