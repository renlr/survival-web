package com.baofeng.carebay.service;

import com.baofeng.carebay.entity.GrowupIndex;
import com.baofeng.utils.PageResult;

public interface IGrowupIndexService {

	PageResult readAllPages(int rows, int page, String groupId);

	boolean delGrowupIndex(String id);

	GrowupIndex readGrowupIndex(String id);

	boolean addGrowupIndex(GrowupIndex gid,String groupId);

}
