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

import com.baofeng.carebay.entity.PhotosCover;
import com.baofeng.carebay.service.IPhotosCoverService;
import com.baofeng.carebay.util.Constants;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.ResultMsg;

@Controller
@RequestMapping("cover")
public class PhotosCoverController {

	@Autowired
	private IPhotosCoverService photosCoverService;

	public IPhotosCoverService getPhotosCoverService() {
		return photosCoverService;
	}

	public void setPhotosCoverService(IPhotosCoverService photosCoverService) {
		this.photosCoverService = photosCoverService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/photosCover");
		return mav;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/readPages", method = RequestMethod.POST)
	public PageResult readPages(int page, int rows) {
		PageResult pages = this.photosCoverService.readAllPages(rows, page, null);
		return pages;
	}

	/**
	 * 功能：添加修改
	 * */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResultMsg save(String id, @RequestParam MultipartFile[] babyImage, @RequestParam MultipartFile[] userImage, @RequestParam MultipartFile[] photosImage,
			@RequestParam MultipartFile[] personalImage) throws Exception {
		ResultMsg result = new ResultMsg();
		PhotosCover cover = new PhotosCover();
		cover.setId(id);
		if (babyImage != null && babyImage.length > 0) {
			for (MultipartFile $file : babyImage) {
				if (!$file.isEmpty()) {
					String $name = $file.getOriginalFilename();
					String ext = $name.substring($name.lastIndexOf("."), $name.length());
					String sha1 = DigestUtils.shaHex($file.getInputStream());
					String fileName = sha1 + ext;
					String path = Constants.DEFAULT_UPLOADIMAGEPATH + File.separator + Constants.sha1ToPath(sha1);
					Constants.mkdirs(path);
					FileUtils.copyInputStreamToFile($file.getInputStream(), new File(path, fileName));
					cover.setBabyImage(fileName);
					cover.setBabyImageSha1(sha1);
				}
			}
		}
		if (userImage != null && userImage.length > 0) {
			for (MultipartFile $file : userImage) {
				if (!$file.isEmpty()) {
					String $name = $file.getOriginalFilename();
					String ext = $name.substring($name.lastIndexOf("."), $name.length());
					String sha1 = DigestUtils.shaHex($file.getInputStream());
					String fileName = sha1 + ext;
					String path = Constants.DEFAULT_UPLOADIMAGEPATH + File.separator + Constants.sha1ToPath(sha1);
					Constants.mkdirs(path);
					FileUtils.copyInputStreamToFile($file.getInputStream(), new File(path, fileName));
					cover.setUserImage(fileName);
					cover.setUserImageSha1(sha1);
				}
			}
		}
		if (photosImage != null && photosImage.length > 0) {
			for (MultipartFile $file : photosImage) {
				if (!$file.isEmpty()) {
					String $name = $file.getOriginalFilename();
					String ext = $name.substring($name.lastIndexOf("."), $name.length());
					String sha1 = DigestUtils.shaHex($file.getInputStream());
					String fileName = sha1 + ext;
					String path = Constants.DEFAULT_UPLOADIMAGEPATH + File.separator + Constants.sha1ToPath(sha1);
					Constants.mkdirs(path);
					FileUtils.copyInputStreamToFile($file.getInputStream(), new File(path, fileName));
					cover.setPhotosImage(fileName);
					cover.setPhotosImageSha1(sha1);
				}
			}
		}
		if (personalImage != null && personalImage.length > 0) {
			for (MultipartFile $file : personalImage) {
				if (!$file.isEmpty()) {
					String $name = $file.getOriginalFilename();
					String ext = $name.substring($name.lastIndexOf("."), $name.length());
					String sha1 = DigestUtils.shaHex($file.getInputStream());
					String fileName = sha1 + ext;
					String path = Constants.DEFAULT_UPLOADIMAGEPATH + File.separator + Constants.sha1ToPath(sha1);
					Constants.mkdirs(path);
					FileUtils.copyInputStreamToFile($file.getInputStream(), new File(path, fileName));
					cover.setPersonalImage(fileName);
					cover.setPersonalImageSha1(sha1);
				}
			}
		}
		if (this.photosCoverService.addPhotosCover(cover)) {
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：编辑
	 * */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public PhotosCover edit(String id) {
		PhotosCover cover = this.photosCoverService.editPhotosCover(id);
		if (cover != null) {
			return cover;
		}
		return null;
	}
	
	/**
	 * 功能：删除
	 * */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(String id) {
		if (this.photosCoverService.delPhotosCover(id)) {
			return "success";
		}
		return "error";
	}

}
