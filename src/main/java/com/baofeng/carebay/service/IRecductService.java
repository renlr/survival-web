package com.baofeng.carebay.service;

import java.util.List;

import com.baofeng.carebay.entity.Recduct;
import com.baofeng.carebay.entity.RecductDetails;
import com.baofeng.utils.IBaseService;
import com.baofeng.utils.PageResult;

public interface IRecductService extends IBaseService {

	boolean onLinePoster(String id);

	Recduct readRecduct(String id);

	boolean delRecduct(String id);

	PageResult readPagesDetails(int rows, int page, String groupId);

	boolean addRecductDetails(RecductDetails details, String groupId);

	boolean deleteRecductDetails(String id);

	boolean addRecduct(Recduct recduct, RecductDetails details);

	boolean updateRecduct(String id1, String indexs1);

	RecductDetails readRecductDetails(String id);

	boolean updateRecductDetails(String id1, String indexs1);

	List<RecductDetails> readRecductDetailsByParentId(String id);

	boolean uploadImagesDetails(String id3, String model3, String type3, String ductCat3, String products3, String image, String imageSha1);

}
