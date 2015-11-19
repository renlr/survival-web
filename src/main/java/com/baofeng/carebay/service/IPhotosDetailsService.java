package com.baofeng.carebay.service;

import java.util.List;

import com.baofeng.carebay.entity.Photos;
import com.baofeng.carebay.entity.PhotosDetails;
import com.baofeng.utils.IBaseService;

public interface IPhotosDetailsService extends IBaseService{

	List<PhotosDetails> readListPhotosDetails(Integer type);

	boolean updatePhotosDetails(String id,Integer vals,String status);

	List<Photos> readPhotosList();

	boolean isPhotosCutter(Photos $photos);

	void updatePhotosCutter(Photos $photos);

	void updatePhotosDetails(PhotosDetails details);

}
