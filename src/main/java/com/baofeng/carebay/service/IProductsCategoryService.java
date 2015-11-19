package com.baofeng.carebay.service;

import com.baofeng.carebay.entity.ProductsCategory;
import com.baofeng.commons.entity.Operator;
import com.baofeng.utils.IBaseService;

public interface IProductsCategoryService extends IBaseService {

	boolean addProductsCategory(String id, String name, String navigtor, Operator operator);

	ProductsCategory readProductsCategory(String id);

	boolean delProductsCategory(String id);

	boolean readProductsCategoryByName(String name);

}
