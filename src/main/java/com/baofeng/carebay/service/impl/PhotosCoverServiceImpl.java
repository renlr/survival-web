package com.baofeng.carebay.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.PhotosCoverDAO;
import com.baofeng.carebay.entity.PhotosCover;
import com.baofeng.carebay.service.IPhotosCoverService;
import com.baofeng.carebay.util.Constants;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("photosCoverService")
public class PhotosCoverServiceImpl implements IPhotosCoverService {

	@Autowired
	private PhotosCoverDAO photosCoverDAO;

	public PhotosCoverDAO getPhotosCoverDAO() {
		return photosCoverDAO;
	}

	public void setPhotosCoverDAO(PhotosCoverDAO photosCoverDAO) {
		this.photosCoverDAO = photosCoverDAO;
	}

	@Override
	public PageResult readAllPages(Integer pageSize, Integer currentPage, SearchFilter filter) {
		PageResult rows = this.photosCoverDAO.readAllPages(pageSize, currentPage, filter);
		if (rows != null && rows.getRows().size() > 0) {
			List<PhotosCover> list = new ArrayList<PhotosCover>();
			for (Object o : rows.getRows()) {
				PhotosCover cover = (PhotosCover) o;
				cover.setBabyImage(Constants.DEFAULT_HTTPIMAGES + "/" + Constants.sha1ToPath(cover.getBabyImageSha1()).replace(File.separator, "/") + "/" + cover.getBabyImage());
				cover.setUserImage(Constants.DEFAULT_HTTPIMAGES + "/" + Constants.sha1ToPath(cover.getUserImageSha1()).replace(File.separator, "/") + "/" + cover.getUserImage());
				cover.setPhotosImage(Constants.DEFAULT_HTTPIMAGES + "/" + Constants.sha1ToPath(cover.getPhotosImageSha1()).replace(File.separator, "/") + "/"
						+ cover.getPhotosImage());
				cover.setPersonalImage(Constants.DEFAULT_HTTPIMAGES + "/" + Constants.sha1ToPath(cover.getPersonalImageSha1()).replace(File.separator, "/") + "/"
						+ cover.getPersonalImage());
				list.add(cover);
			}
			rows.setRows(list);
		}
		return rows;
	}

	@Override
	public boolean addPhotosCover(PhotosCover cover) {
		if (cover.getId() != null && cover.getId().trim().length() > 0) {
			PhotosCover $cover = this.readPhotosCover(cover.getId());
			if ($cover != null) {
				if (cover.getBabyImage() != null && cover.getBabyImage().trim().length() > 0) {
					$cover.setBabyImage(cover.getBabyImage());
					$cover.setBabyImageSha1(cover.getBabyImageSha1());
				}
				if (cover.getUserImage() != null && cover.getUserImage().trim().length() > 0) {
					$cover.setUserImage(cover.getUserImage());
					$cover.setUserImageSha1(cover.getUserImageSha1());
				}
				if (cover.getPhotosImage() != null && cover.getPhotosImage().trim().length() > 0) {
					$cover.setPhotosImage(cover.getUserImage());
					$cover.setPhotosImageSha1(cover.getPhotosImageSha1());
				}
				if (cover.getPersonalImage() != null && cover.getPersonalImage().trim().length() > 0) {
					$cover.setPersonalImage(cover.getPersonalImage());
					$cover.setPersonalImageSha1(cover.getPersonalImageSha1());
				}
				cover = $cover;
			}
		} else {
			cover.setDefaults(Integer.valueOf(1));
		}
		return this.photosCoverDAO.savePhotosCover(cover);
	}

	@Override
	public PhotosCover readPhotosCover(String id) {
		return this.photosCoverDAO.readPhotosCover(id);
	}

	@Override
	public PhotosCover editPhotosCover(String id) {
		return this.photosCoverDAO.readPhotosCover(id);
	}

	@Override
	public boolean delPhotosCover(String id) {
		PhotosCover cover = this.photosCoverDAO.readPhotosCover(id);
		if (cover != null) {
			cover.setStatus(EntityStatus.DELETED);
			this.photosCoverDAO.savePhotosCover(cover);
			return true;
		}
		return false;
	}

}
