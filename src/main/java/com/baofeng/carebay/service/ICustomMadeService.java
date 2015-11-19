package com.baofeng.carebay.service;

import com.baofeng.carebay.entity.CustomMade;
import com.baofeng.carebay.entity.CustomMadeDetails;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

public interface ICustomMadeService {

	boolean addCustomMade(CustomMade custom);

	CustomMade readCustomMade(String id);

	boolean deleteCustomMade(String id);

	boolean addCustomMadeDetails(CustomMadeDetails details, String gid);

	CustomMadeDetails readCustomMadeDetails(String id);

	boolean onLineCustomMadeDetails(String id);

	boolean deleteCustomMadeDetails(String id);

	PageResult readPagesSkip(int rows, int page,SearchFilter filter);

	boolean onLineCustomMade(String id);

	PageResult readAllPages(int rows, int page, SearchFilter filter);

	PageResult readAllPagesDetails(int rows, int page, String gid, SearchFilter filter);
}
