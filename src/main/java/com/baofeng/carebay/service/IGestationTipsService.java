package com.baofeng.carebay.service;

import com.baofeng.carebay.entity.GestationTips;
import com.baofeng.utils.PageResult;

public interface IGestationTipsService {

	public GestationTips readGestationTips(String id);

	boolean delGestationTips(String id);

	boolean addTips(String id, String content);

	PageResult readAllPages(Integer pageSize, Integer currentPage, String gid);

	boolean addGestationTips(GestationTips tips, String gid);

	boolean onLineTips(String id);
}
