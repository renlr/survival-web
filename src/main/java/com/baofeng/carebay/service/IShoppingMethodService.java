package com.baofeng.carebay.service;

import com.baofeng.carebay.entity.ShoppingMethod;
import com.baofeng.utils.IBaseService;

public interface IShoppingMethodService extends IBaseService {

	boolean addShoppingMedthod(ShoppingMethod $method);

	ShoppingMethod readShoppingMethod(String id);

	boolean deleteShoppingMethod(String id);

	boolean editShoppingMethodDefaults(String id);

}
