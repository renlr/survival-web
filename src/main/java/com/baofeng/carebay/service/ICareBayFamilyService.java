package com.baofeng.carebay.service;

import com.baofeng.carebay.entity.CareBayFamily;
import com.baofeng.utils.IBaseService;

public interface ICareBayFamilyService extends IBaseService {

	boolean addCareBayFamily(CareBayFamily careBayFamily);

	CareBayFamily readCareBayFamily(String id);

	boolean delCareBayFamily(String id);
}
