package com.baofeng.carebay.service;

import com.baofeng.carebay.entity.WeekService;
import com.baofeng.utils.IBaseService;

public interface IWeekServiceService extends IBaseService{

	boolean addWeekService(String id, String name);

	boolean readWeekServiceByName(String name);

	WeekService readWeekService(String id);

	boolean onLineWeekService(String id);
}
