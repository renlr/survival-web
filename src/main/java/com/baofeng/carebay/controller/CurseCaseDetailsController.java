package com.baofeng.carebay.controller;

import java.io.File;
import java.io.IOException;

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

import com.baofeng.carebay.entity.CurseCase;
import com.baofeng.carebay.entity.CurseCaseDetails;
import com.baofeng.carebay.service.ICurseCaseDetailsService;
import com.baofeng.carebay.service.ICurseCaseService;
import com.baofeng.carebay.util.Constants;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.ResultMsg;

/**
 * 功能：护理套餐内容
 * */

@Controller
@RequestMapping("curseDetails")
public class CurseCaseDetailsController {

	@Autowired
	private ICurseCaseService curseCaseService;
	@Autowired
	private ICurseCaseDetailsService curseCaseDetailsService;

	public ICurseCaseService getCurseCaseService() {
		return curseCaseService;
	}

	public void setCurseCaseService(ICurseCaseService curseCaseService) {
		this.curseCaseService = curseCaseService;
	}

	public ICurseCaseDetailsService getCurseCaseDetailsService() {
		return curseCaseDetailsService;
	}

	public void setCurseCaseDetailsService(ICurseCaseDetailsService curseCaseDetailsService) {
		this.curseCaseDetailsService = curseCaseDetailsService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/curseCaseDetails");
		return mav;
	}

	/**
	 * 功能：编辑内容
	 * */
	@RequestMapping(value = "/editContent", method = RequestMethod.GET)
	public ModelAndView editContent(String id, String gid) {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/curseCaseContent");
		CurseCaseDetails details = this.curseCaseDetailsService.readCurseCaseDetails(id);
		CurseCase curse = this.curseCaseService.readCurseCase(gid);
		File file = new File(Constants.DEFAULT_BUILDHTMLPATH + File.separator + details.getId() + ".html");
		try {
			if (file.exists()) {
				String content = FileUtils.readFileToString(file, "utf-8");
				details.setContent("");
				//details.setContent(URLEncoder.encode(content, "utf-8"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		mav.addObject("curse", curse);
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
		PageResult pages = this.curseCaseDetailsService.readAllPages(rows, page, groupId);
		return pages;
	}

	/**
	 * 功能：添加修改
	 * */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(String name, String indexs, String groupId, @RequestParam MultipartFile[] images) throws Exception {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/curseCaseDetails");
		CurseCaseDetails details = new CurseCaseDetails();
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
		if (this.curseCaseDetailsService.addCurseCaseDetails(details, groupId)) {
			mav.addObject("result", "success");
		}
		CurseCase curse = this.curseCaseService.readCurseCase(groupId);
		if (curse != null) {
			mav.addObject("curse", curse);
		}
		return mav;
	}

	/**
	 * 功能：添加修改
	 * */
	@ResponseBody
	@RequestMapping(value = "/saveContent", method = RequestMethod.POST)
	public ModelAndView saveContent(String id, String gid, String content) throws Exception {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/curseCaseDetails");
		CurseCaseDetails details = new CurseCaseDetails();
		StringBuffer $temp = new StringBuffer();
		$temp.append(Constants.HTMLCENTERCODE);
		if (content != null && content.trim().length() > 0) {
			File file = new File(Constants.DEFAULT_BUILDHTMLPATH + File.separator + id + ".html");
			$temp.append(content);
			if(file.exists())
				file.deleteOnExit();
			FileUtils.writeStringToFile(file, $temp.toString(), "utf-8");
			details.setContent(id + ".html");
		}
		if (this.curseCaseDetailsService.addDetails(id, gid, content, details)) {
			CurseCase curse = this.curseCaseService.readCurseCase(gid);
			if (curse != null) {
				mav.addObject("curse", curse);
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
		if (this.curseCaseDetailsService.saveIndexs(id1, gid1, indexs1)) {
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
	public CurseCaseDetails edit(String id) {
		CurseCaseDetails details = this.curseCaseDetailsService.readCurseCaseDetails(id);
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
		if (this.curseCaseDetailsService.onLineCurseCaseDetails(id)) {
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
		if (this.curseCaseDetailsService.delCurseCaseDetails(id)) {
			result.setResultStatus(200);
			result.setResultMessage("success");
		}
		return result;
	}

}
