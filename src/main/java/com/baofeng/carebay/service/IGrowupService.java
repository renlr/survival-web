package com.baofeng.carebay.service;

import com.baofeng.carebay.entity.Growup;
import com.baofeng.utils.IBaseService;

public interface IGrowupService extends IBaseService {

	boolean addGrowup(Growup growup);

	Growup readGrowup(String id);

	boolean delGrowup(String id);

	boolean readGrowupByName(String name);

}
