package com.baofeng.carebay.service;

import com.baofeng.carebay.entity.PhotosCover;
import com.baofeng.utils.IBaseService;

public interface IPhotosCoverService extends IBaseService{

	boolean addPhotosCover(PhotosCover cover);

	PhotosCover editPhotosCover(String id);

	boolean delPhotosCover(String id);

	PhotosCover readPhotosCover(String id);
}
