package com.baofeng.carebay.service;

import com.baofeng.carebay.entity.ButlerService;
import com.baofeng.carebay.entity.MonthsMealOrder;
import com.baofeng.commons.entity.Operator;
import com.baofeng.utils.IBaseService;

public interface ICallService extends IBaseService {

	ButlerService readButlerService(Operator operator);

	boolean serviceing(String id, Operator operator);

	ButlerService readButlerService(String id);

	MonthsMealOrder readServiceMonthsMealOrders(Operator user);

	boolean updateServiceMeal(String id, Operator user);

}
