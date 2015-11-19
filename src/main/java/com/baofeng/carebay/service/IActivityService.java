package com.baofeng.carebay.service;

import com.baofeng.carebay.entity.Activity;
import com.baofeng.utils.IBaseService;
import com.baofeng.utils.PageResult;

public interface IActivityService extends IBaseService {

	boolean addActivity(Activity act);

	Activity readActivity(String id);

	boolean deleteActivity(String id);

	boolean onLineActivity(String id);

	PageResult readAllPagesDetails(int rows, int page, String gid);

	boolean saveOnLineTask(String ids, String onlineDT, String offlineDT);

}
