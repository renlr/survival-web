package com.baofeng.carebay.service;

import com.baofeng.carebay.entity.MookDetails;
import com.baofeng.utils.PageResult;

public interface IMookDetailsService {

	PageResult readAllPages(int rows, int page, String gid);

	boolean addMookDetails(MookDetails details, String gid);

	MookDetails readMookDetails(String id);

	boolean deleteMookDetails(String id);

	boolean addMookDetailsContent(String id, String gid, String content);

}
