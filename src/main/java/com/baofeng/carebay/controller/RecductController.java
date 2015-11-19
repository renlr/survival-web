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

import com.baofeng.carebay.entity.Recduct;
import com.baofeng.carebay.entity.RecductDetails;
import com.baofeng.carebay.service.IRecductService;
import com.baofeng.carebay.util.Constants;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.ResultMsg;

@Controller
@RequestMapping("recduct")
public class RecductController {

	@Autowired
	private IRecductService recductService;

	public IRecductService getRecductService() {
		return recductService;
	}

	public void setRecductService(IRecductService recductService) {
		this.recductService = recductService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/recduct");
		return mav;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/readPages", method = RequestMethod.POST)
	public PageResult readPages(int page, int rows) {
		PageResult pages = this.recductService.readAllPages(rows, page, null);
		return pages;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/readPagesDetails", method = RequestMethod.POST)
	public PageResult readPagesDetails(int page, int rows, String groupId) {
		PageResult pages = this.recductService.readPagesDetails(rows, page, groupId);
		return pages;
	}

	/**
	 * 功能：添加修改
	 * */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(String model, String type, String ductCat, String products, Integer indexs, @RequestParam MultipartFile[] images) throws Exception {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/recduct");
		Recduct recduct = new Recduct();
		RecductDetails details = new RecductDetails();
		if (images != null && images.length > 0) {
			for (MultipartFile $file : images) {
				if (!$file.isEmpty()) {
					String name = $file.getOriginalFilename();
					String ext = name.substring(name.lastIndexOf("."), name.length());
					String sha1 = DigestUtils.shaHex($file.getInputStream());
					String fileName = sha1 + ext;
					String path = Constants.DEFAULT_UPLOADIMAGEPATH + File.separator + Constants.sha1ToPath(sha1);
					Constants.mkdirs(path);
					FileUtils.copyInputStreamToFile($file.getInputStream(), new File(path, fileName));
					details.setImage(fileName);
					details.setImageSha1(sha1);
				}
			}
		}
		recduct.setModel(Integer.valueOf(model));
		recduct.setOnline(Integer.valueOf(0));
		recduct.setIndexs(Integer.valueOf(1));
		details.setType(Integer.valueOf(type));
		if (Integer.valueOf(1).intValue() == Integer.valueOf(type).intValue()) {
			details.setParams(ductCat);
		} else {
			details.setParams(products);
		}
		if (this.recductService.addRecduct(recduct, details)) {
			mav.addObject("result", "success");
		}
		return mav;
	}

	/**
	 * 功能：添加小图片
	 * */
	@RequestMapping(value = "/saveDetails", method = RequestMethod.POST)
	public ModelAndView saveDetails(String groupId, String type, String ductCat, String products, Integer indexs, @RequestParam MultipartFile[] images) throws Exception {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/recductDetails");
		RecductDetails details = new RecductDetails();
		if (images != null && images.length > 0) {
			for (MultipartFile $file : images) {
				if (!$file.isEmpty()) {
					String name = $file.getOriginalFilename();
					String ext = name.substring(name.lastIndexOf("."), name.length());
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
		details.setIndexs(indexs);
		details.setType(Integer.valueOf(type));
		if (Integer.valueOf(1).intValue() == Integer.valueOf(type).intValue()) {
			details.setParams(ductCat);
		} else {
			details.setParams(products);
		}
		if (this.recductService.addRecductDetails(details, groupId)) {
			Recduct recduct = this.recductService.readRecduct(groupId);
			if (recduct != null) {
				mav.addObject("duct", recduct);
			}
		}
		return mav;
	}

	/**
	 * 功能：修改显示顺序
	 * */
	@RequestMapping(value = "/saveIndexs", method = RequestMethod.POST)
	public ModelAndView saveIndexs(String id1, String indexs1) throws Exception {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/recduct");
		if (this.recductService.updateRecduct(id1, indexs1)) {
			mav.addObject("result", "success");
		}
		return mav;
	}

	/**
	 * 功能：修改显示顺序
	 * */
	@RequestMapping(value = "/saveDetailsIndexs", method = RequestMethod.POST)
	public ModelAndView saveDetailsIndexs(String id1, String indexs1, String groupId1) throws Exception {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/recductDetails");
		if (this.recductService.updateRecductDetails(id1, indexs1)) {
			Recduct recduct = this.recductService.readRecduct(groupId1);
			mav.addObject("duct", recduct);
			mav.addObject("result", "success");
		}
		return mav;
	}

	/**
	 * 功能：上传图片
	 * */
	@ResponseBody
	@RequestMapping(value = "/upLoadImagesDetails", method = RequestMethod.POST)
	public ResultMsg upLoadImagesDetails(String id3, String model3, String type3, String ductCat3, String products3, @RequestParam MultipartFile[] images3) throws Exception {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		String image = String.valueOf("");
		String imageSha1 = String.valueOf("");
		if (images3 != null && images3.length > 0) {
			for (MultipartFile $file : images3) {
				if (!$file.isEmpty()) {
					String name = $file.getOriginalFilename();
					String ext = name.substring(name.lastIndexOf("."), name.length());
					String sha1 = DigestUtils.shaHex($file.getInputStream());
					String fileName = sha1 + ext;
					String path = Constants.DEFAULT_UPLOADIMAGEPATH + File.separator + Constants.sha1ToPath(sha1);
					Constants.mkdirs(path);
					FileUtils.copyInputStreamToFile($file.getInputStream(), new File(path, fileName));
					image = fileName;
					imageSha1 = sha1;
				}
			}
		}
		if (this.recductService.uploadImagesDetails(id3, model3, type3, ductCat3, products3, image, imageSha1)) {
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
		if (this.recductService.onLinePoster(id)) {
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
		if (this.recductService.delRecduct(id)) {
			result.setResultMessage("success");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/deleteDetails", method = RequestMethod.GET)
	public ResultMsg deleteDetails(String id) {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.recductService.deleteRecductDetails(id)) {
			result.setResultMessage("success");
		}
		return result;
	}

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView showDetails(String id) {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/recductDetails");
		Recduct recduct = this.recductService.readRecduct(id);
		if (recduct != null) {
			mav.addObject("duct", recduct);
		}
		return mav;
	}

	/**
	 * 功能：编辑
	 * */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public Recduct edit(String id) {
		Recduct recduct = this.recductService.readRecduct(id);
		if (recduct != null) {
			return recduct;
		}
		return null;
	}

	/**
	 * 功能：编辑展示图片
	 * */
	@ResponseBody
	@RequestMapping(value = "/editDetails", method = RequestMethod.GET)
	public RecductDetails editDetails(String id) {
		RecductDetails details = this.recductService.readRecductDetails(id);
		if (details != null) {
			return details;
		}
		return null;
	}

}
