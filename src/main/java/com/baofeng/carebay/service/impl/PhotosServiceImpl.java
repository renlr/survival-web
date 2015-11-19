package com.baofeng.carebay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.PhotosDAO;
import com.baofeng.carebay.service.IPhotosService;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("photosService")
public class PhotosServiceImpl implements IPhotosService {

	@Autowired
	private PhotosDAO photosDAO;

	public PhotosDAO getPhotosDAO() {
		return photosDAO;
	}

	public void setPhotosDAO(PhotosDAO photosDAO) {
		this.photosDAO = photosDAO;
	}

	@Override
	public PageResult readAllPages(Integer pageSize, Integer currentPage, SearchFilter filter) {
		return this.photosDAO.readAllPages(pageSize, currentPage, filter);
	}
}
