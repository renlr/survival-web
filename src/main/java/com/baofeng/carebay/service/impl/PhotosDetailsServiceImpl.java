package com.baofeng.carebay.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.PhotosDAO;
import com.baofeng.carebay.dao.PhotosDetailsDAO;
import com.baofeng.carebay.entity.Photos;
import com.baofeng.carebay.entity.PhotosDetails;
import com.baofeng.carebay.service.IPhotosDetailsService;
import com.baofeng.carebay.util.Constants;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("photosDetailsService")
public class PhotosDetailsServiceImpl implements IPhotosDetailsService {

	@Autowired
	private PhotosDAO photosDAO;
	@Autowired
	private PhotosDetailsDAO photosDetailsDAO;

	public PhotosDAO getPhotosDAO() {
		return photosDAO;
	}

	public void setPhotosDAO(PhotosDAO photosDAO) {
		this.photosDAO = photosDAO;
	}

	public PhotosDetailsDAO getPhotosDetailsDAO() {
		return photosDetailsDAO;
	}

	public void setPhotosDetailsDAO(PhotosDetailsDAO photosDetailsDAO) {
		this.photosDetailsDAO = photosDetailsDAO;
	}

	@Override
	public PageResult readAllPages(Integer pageSize, Integer currentPage, SearchFilter filter) {
		PageResult rows = this.photosDetailsDAO.readAllPages(pageSize, currentPage, filter);
		if (rows != null && rows.getRows().size() > 0) {
			List<PhotosDetails> list = new ArrayList<PhotosDetails>();
			for (Object o : rows.getRows()) {
				PhotosDetails details = (PhotosDetails) o;
				details.setFile(Constants.DEFAULT_HTTPIMAGES + "/" + Constants.sha1ToPath(details.getFileSha1()).replace(File.separator, "/") + "/" + details.getFile());
				list.add(details);
			}
			rows.setRows(list);
		}
		return rows;
	}

	/**
	 * 功能：读取图片上传记录
	 * */
	@Override
	public List<PhotosDetails> readListPhotosDetails(Integer type) {
		return this.photosDetailsDAO.readListPhotosDetails(type);
	}

	@Override
	public boolean updatePhotosDetails(String id, Integer vals, String status) {
		PhotosDetails details = this.photosDetailsDAO.readPhotosDetails(id);
		if (details != null) {
			if ("cutter".equals(status)) {
				details.setCutter(vals.intValue());
			}
			if ("convert".equals(status)) {
				details.setIsConvert(vals.intValue());
			}
			this.photosDetailsDAO.savePhotosDetails(details);
			return true;
		}
		return false;
	}

	@Override
	public List<Photos> readPhotosList() {
		return this.photosDetailsDAO.readPhotosList();
	}

	@Override
	public boolean isPhotosCutter(Photos $photos) {
		int totalCount = this.photosDetailsDAO.totalCount($photos.getId());
		int cutteCount = this.photosDetailsDAO.cutteCount($photos.getId());
		int convertCount = this.photosDetailsDAO.ConvertCount($photos.getId());
		if (totalCount > 0 && totalCount == cutteCount && totalCount == convertCount) {
			return true;
		}
		return false;
	}

	@Override
	public void updatePhotosCutter(Photos $photos) {
		$photos.setCutter(Integer.valueOf(2));
		this.photosDAO.savePhotos($photos);
	}

	@Override
	public void updatePhotosDetails(PhotosDetails details) {
		this.photosDetailsDAO.savePhotosDetails(details);
	}
}
