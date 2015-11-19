package com.baofeng.carebay.service;

import com.baofeng.carebay.entity.RecductList;
import com.baofeng.utils.IBaseService;
import com.baofeng.utils.PageResult;

public interface IRecductListService extends IBaseService {

	boolean addProductCat(RecductList category);

	RecductList readProductCat(String id);

	boolean deleteProductCat(String id);

	boolean readProductCatByName(String name);

	PageResult readPagesDetails(int rows, int page, String gid);

	boolean addProductCatDetails(String gid, String ids);

	boolean deleteProductCatDetails(String gid, String ids);
}
