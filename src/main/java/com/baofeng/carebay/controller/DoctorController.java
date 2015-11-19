package com.baofeng.carebay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baofeng.carebay.entity.Doctor;
import com.baofeng.carebay.service.IDoctorService;
import com.baofeng.utils.Constants;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.ResultMsg;

@Controller
@RequestMapping("doctor")
public class DoctorController {

	@Autowired
	private IDoctorService doctorService;

	public IDoctorService getDoctorService() {
		return doctorService;
	}

	public void setDoctorService(IDoctorService doctorService) {
		this.doctorService = doctorService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/doctor");
		return mav;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@RequestMapping(value = "/readPages", method = RequestMethod.POST)
	public PageResult readPages(int page, int rows) {
		PageResult pages = this.doctorService.readAllPages(rows, page, null);
		return pages;
	}

	/**
	 * 功能：添加修改
	 * */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResultMsg save(String id, String doctorUser, String doctorPWD, String doctorNickname, Integer level, String describes) throws Exception {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		Doctor doctor = new Doctor();
		doctor.setId(id);
		doctor.setDoctorUser(doctorUser);
		doctor.setDoctorPWD(doctorPWD);
		doctor.setDoctorNickname(doctorNickname);
		doctor.setLevel(level);
		doctor.setDescribes(describes);
		doctor.setRoomName(doctorNickname);
		if (this.doctorService.addDoctor(doctor)) {
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：按字段修改
	 * */
	@ResponseBody
	@RequestMapping(value = "/saveEditFields", method = RequestMethod.POST)
	public ResultMsg saveEditField(String id, String field, String value) throws Exception {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.doctorService.editFiles(id, field, value)) {
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
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.doctorService.deleteDoctor(id)) {
			result.setResultMessage("success");
		}
		return result;
	}
	
	/**
	 * 功能：上下线
	 * */
	@ResponseBody
	@RequestMapping(value = "/online", method = RequestMethod.GET)
	public ResultMsg online(String id) {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.doctorService.onLineDoctor(id)) {
			result.setResultMessage("success");
		}
		return result;
	}
}
