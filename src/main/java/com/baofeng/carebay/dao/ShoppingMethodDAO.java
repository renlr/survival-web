package com.baofeng.carebay.dao;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baofeng.carebay.entity.ShoppingMethod;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.DetachedCriteriaUtil;
import com.baofeng.utils.IBaseDAO;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Repository("shoppingMethodDAO")
public class ShoppingMethodDAO {

	@Autowired
	private IBaseDAO baseDAO;

	public IBaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public PageResult readAllPages(Integer pageSize, Integer currentPage, SearchFilter filter) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ShoppingMethod.class);
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("defaults"));
			detachedCriteria.addOrder(Order.desc("createDT"));
			DetachedCriteriaUtil.addSearchFilter(detachedCriteria, filter);
			return baseDAO.findByPages(detachedCriteria, pageSize, currentPage);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public boolean saveShoppingMethod(ShoppingMethod method) {
		try {
			if (method != null && method.getId() != null && method.getId().length() > 0)
				this.baseDAO.mrege(method);
			else
				this.baseDAO.save(method);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public ShoppingMethod readShoppingMethod(String id) {
		if (id != null && id.trim().length() > 0) {
			ShoppingMethod method = (ShoppingMethod) this.baseDAO.get(ShoppingMethod.class, id);
			return method;
		}
		return null;
	}

	public boolean deleteShoppingMethod(String id) {
		if (id != null && id.trim().length() > 0) {
			ShoppingMethod method = this.readShoppingMethod(id);
			this.baseDAO.delete(method);
			return true;
		}
		return false;
	}

	public void defaults() {
		try {
			String hql = "update ShoppingMethod set defaults = 0";
			this.baseDAO.execute(hql);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
