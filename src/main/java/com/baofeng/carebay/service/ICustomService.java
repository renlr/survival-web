package com.baofeng.carebay.service;

import com.baofeng.carebay.entity.CustomService;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

public interface ICustomService {

	boolean addCustomService(CustomService custom, String gid);

	CustomService readCustomService(String id);

	boolean deleteCustomService(String id);

	boolean onLineCustomService(String id);

	PageResult readAllPages(int rows, int page, String gid, SearchFilter filter);

}
