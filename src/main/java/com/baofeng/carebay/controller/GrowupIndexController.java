package com.baofeng.carebay.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.baofeng.carebay.entity.Growup;
import com.baofeng.carebay.entity.GrowupIndex;
import com.baofeng.carebay.service.IGrowupIndexService;
import com.baofeng.carebay.service.IGrowupService;
import com.baofeng.utils.Constants;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.ResultMsg;

@Controller
@RequestMapping("growupIndex")
public class GrowupIndexController {

	@Autowired
	private IGrowupService growupService;

	@Autowired
	private IGrowupIndexService growupIndexService;

	public IGrowupIndexService getGrowupIndexService() {
		return growupIndexService;
	}

	public void setGrowupIndexService(IGrowupIndexService growupIndexService) {
		this.growupIndexService = growupIndexService;
	}

	public IGrowupService getGrowupService() {
		return growupService;
	}

	public void setGrowupService(IGrowupService growupService) {
		this.growupService = growupService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/growupIndex");
		return mav;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/readPages", method = RequestMethod.POST)
	public PageResult readPages(int page, int rows, String groupId) {
		PageResult pages = this.growupIndexService.readAllPages(rows, page, groupId);
		return pages;
	}

	/**
	 * 功能：添加修改
	 * */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@RequestParam MultipartFile[] data,String groupId) throws Exception {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/growupIndex");
		if (data != null && data.length > 0) {
			for (MultipartFile $file : data) {
				if (!$file.isEmpty()) {
					BufferedReader reader = null;
					String line = String.valueOf("");
					try {
						int index = 0;
						reader = new BufferedReader(new InputStreamReader($file.getInputStream()));
						while((line = reader.readLine()) != null){
							if(index != 0){
								String[] str = line.split("	");
								GrowupIndex gid = new GrowupIndex();
								Float weight = Float.valueOf(0);
								Float height = Float.valueOf(0);
								Float bmi = Float.valueOf(0);
								int month = Integer.valueOf(0);
								if(str != null && str.length >= 1 && str[0] != null){
									weight = Float.valueOf(str[0].trim());
								}
								if(str != null && str.length >= 2 && str[1] != null){
									height = Float.valueOf(str[1].trim());
								}
								if(str != null && str.length >= 3 && str[2] != null){
									month = Integer.valueOf(str[2].trim());
								}
								if(weight != null && weight.floatValue() > 0 && height != null && height.floatValue() > 0){
									bmi = weight / ((height / 100) * (height / 100));
								}
								gid.setHeight(height+"");
								gid.setWeight(weight+"");
								gid.setBmi(bmi.intValue()+"");
								gid.setMonth(month);
								this.growupIndexService.addGrowupIndex(gid,groupId);
							}
							index++;
						}
					} catch (Exception e) {
						reader.close();
					}
				}
			}
		}
		Growup growup = this.growupService.readGrowup(groupId);
		if(growup != null){
			mav.addObject("growup", growup);
		}
		return mav;
	}
	
	/**
	 * 功能：删除
	 * */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ResultMsg delete(String id) {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.growupIndexService.delGrowupIndex(id)) {
			result.setResultStatus(200);
			result.setResultMessage("success");
		}
		return result;
	}

}
