package com.baofeng.carebay.service;

import com.baofeng.carebay.entity.Slimming;
import com.baofeng.utils.IBaseService;

public interface ISlimmingService extends IBaseService {

	boolean addSlimming(Slimming slim);

	Slimming readSlimming(String id);

	boolean onLineSlimming(String id);

	boolean delSlimming(String id);

}
