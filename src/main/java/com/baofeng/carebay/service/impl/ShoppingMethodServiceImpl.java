package com.baofeng.carebay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.ShoppingMethodDAO;
import com.baofeng.carebay.entity.ShoppingMethod;
import com.baofeng.carebay.service.IShoppingMethodService;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("shoppingMethodService")
public class ShoppingMethodServiceImpl implements IShoppingMethodService {

	@Autowired
	private ShoppingMethodDAO shoppingMethodDAO;

	public ShoppingMethodDAO getShoppingMethodDAO() {
		return shoppingMethodDAO;
	}

	public void setShoppingMethodDAO(ShoppingMethodDAO shoppingMethodDAO) {
		this.shoppingMethodDAO = shoppingMethodDAO;
	}

	@Override
	public PageResult readAllPages(Integer pageSize, Integer currentPage, SearchFilter filter) {
		return this.shoppingMethodDAO.readAllPages(pageSize, currentPage, filter);
	}

	@Override
	public boolean addShoppingMedthod(ShoppingMethod method) {
		if (method != null && method.getId() != null && method.getId().trim().length() > 0) {
			ShoppingMethod $method = this.readShoppingMethod(method.getId());
			if ($method != null) {
				$method.setMethod(method.getMethod());
				method = $method;
			}
		} else {
			method.setDefaults(Integer.valueOf(0));
		}
		return this.shoppingMethodDAO.saveShoppingMethod(method);
	}

	@Override
	public ShoppingMethod readShoppingMethod(String id) {
		return this.shoppingMethodDAO.readShoppingMethod(id);
	}

	@Override
	public boolean deleteShoppingMethod(String id) {
		return this.shoppingMethodDAO.deleteShoppingMethod(id);
	}

	/**
	 * 功能：默认
	 * */
	@Override
	public boolean editShoppingMethodDefaults(String id) {
		this.shoppingMethodDAO.defaults();
		ShoppingMethod method = this.readShoppingMethod(id);
		if (method != null) {
			method.setDefaults(Integer.valueOf(1));
			this.shoppingMethodDAO.saveShoppingMethod(method);
			return true;
		}
		return false;
	}

}
