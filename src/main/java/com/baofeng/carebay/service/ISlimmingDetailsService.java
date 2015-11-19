package com.baofeng.carebay.service;

import com.baofeng.carebay.entity.SlimmingDetails;
import com.baofeng.utils.PageResult;

public interface ISlimmingDetailsService {

	PageResult readAllPages(int rows, int page, String groupId);

	boolean addSlimmingDetails(SlimmingDetails details, String groupId);

	boolean onLineSlimmingDetails(String id);

	boolean delSlimmingDetails(String id);

	SlimmingDetails readSlimmingDetails(String id);

	boolean saveIndexs(String id1, String gid1, String indexs1);

	boolean addSlimmingContent(String id, String gid, String content);
}
