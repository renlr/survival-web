package com.baofeng.carebay.controller;

import java.io.File;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.baofeng.carebay.entity.Slimming;
import com.baofeng.carebay.entity.SlimmingDetails;
import com.baofeng.carebay.service.ISlimmingDetailsService;
import com.baofeng.carebay.service.ISlimmingService;
import com.baofeng.carebay.util.Constants;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.ResultMsg;

@Controller
@RequestMapping("slimDetails")
public class SlimmingDetailsController {

	@Autowired
	private ISlimmingService slimmingService;

	@Autowired
	private ISlimmingDetailsService slimmingDetailsService;

	public ISlimmingService getSlimmingService() {
		return slimmingService;
	}

	public void setSlimmingService(ISlimmingService slimmingService) {
		this.slimmingService = slimmingService;
	}

	public ISlimmingDetailsService getSlimmingDetailsService() {
		return slimmingDetailsService;
	}

	public void setSlimmingDetailsService(ISlimmingDetailsService slimmingDetailsService) {
		this.slimmingDetailsService = slimmingDetailsService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/slimmingDetails");
		return mav;
	}

	@RequestMapping(value = "/editContent", method = RequestMethod.GET)
	public ModelAndView editContent(String id, String gid) {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/slimmingContent");
		SlimmingDetails details = this.slimmingDetailsService.readSlimmingDetails(id);
		Slimming slim = this.slimmingService.readSlimming(gid);
		details.setContent("");
		mav.addObject("slim", slim);
		mav.addObject("details", details);
		return mav;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/readPages", method = RequestMethod.POST)
	public PageResult readPages(int page, int rows, String groupId) {
		PageResult pages = this.slimmingDetailsService.readAllPages(rows, page, groupId);
		return pages;
	}

	/**
	 * 功能：添加修改
	 * */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(String name, String indexs, String groupId, @RequestParam MultipartFile[] images) throws Exception {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/slimmingDetails");
		SlimmingDetails details = new SlimmingDetails();
		if (images != null && images.length > 0) {
			for (MultipartFile $file : images) {
				if (!$file.isEmpty()) {
					String $name = $file.getOriginalFilename();
					String ext = $name.substring($name.lastIndexOf("."), $name.length());
					String sha1 = DigestUtils.shaHex($file.getInputStream());
					String fileName = sha1 + ext;
					String path = Constants.DEFAULT_UPLOADIMAGEPATH + File.separator + Constants.sha1ToPath(sha1);
					Constants.mkdirs(path);
					FileUtils.copyInputStreamToFile($file.getInputStream(), new File(path, fileName));
					details.setImageSha1(sha1);
					details.setImage(fileName);
				}
			}
		}
		details.setName(name);
		details.setIndexs(Integer.valueOf(indexs));
		if (this.slimmingDetailsService.addSlimmingDetails(details, groupId)) {
			mav.addObject("result", "success");
		}
		Slimming slim = this.slimmingService.readSlimming(groupId);
		if (slim != null) {
			mav.addObject("slim", slim);
		}
		return mav;
	}
	
	/**
	 * 功能：编辑内容
	 * */
	@ResponseBody
	@RequestMapping(value = "/saveContent", method = RequestMethod.POST)
	public ModelAndView saveContent(String id, String gid, String content) throws Exception {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/slimmingDetails");
		SlimmingDetails details = new SlimmingDetails();
		StringBuffer $temp = new StringBuffer();
		$temp.append(Constants.HTMLCENTERCODE);
		if (content != null && content.trim().length() > 0) {
			File file = new File(Constants.DEFAULT_BUILDHTMLPATH + File.separator + id + ".html");
			if(file.exists())
				file.delete();
			$temp.append(content);
			FileUtils.writeStringToFile(file, $temp.toString(), "utf-8");
			details.setContent(id + ".html");
		}
		if (this.slimmingDetailsService.addSlimmingContent(id, gid, content)) {
			Slimming slim = this.slimmingService.readSlimming(gid);
			if (slim != null) {
				mav.addObject("slim", slim);
			}
		}
		return mav;
	}

	/**
	 * 功能：修改显示顺序
	 * */
	@ResponseBody
	@RequestMapping(value = "/saveIndexs", method = RequestMethod.POST)
	public ResultMsg saveIndexs(String id1, String gid1, String indexs1) throws Exception {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.slimmingDetailsService.saveIndexs(id1, gid1, indexs1)) {
			result.setResultStatus(200);
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：编辑
	 * */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public SlimmingDetails edit(String id) {
		SlimmingDetails details = this.slimmingDetailsService.readSlimmingDetails(id);
		if (details != null) {
			return details;
		}
		return null;
	}

	/**
	 * 功能：上下线
	 * */
	@ResponseBody
	@RequestMapping(value = "/online", method = RequestMethod.GET)
	public ResultMsg online(String id) {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.slimmingDetailsService.onLineSlimmingDetails(id)) {
			result.setResultStatus(200);
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
		if (this.slimmingDetailsService.delSlimmingDetails(id)) {
			result.setResultStatus(200);
			result.setResultMessage("success");
		}
		return result;
	}

}
