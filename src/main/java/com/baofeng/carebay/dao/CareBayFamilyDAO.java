package com.baofeng.carebay.dao;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baofeng.carebay.entity.CareBayFamily;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.BaseException;
import com.baofeng.utils.DetachedCriteriaUtil;
import com.baofeng.utils.IBaseDAO;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Repository("careBayFamilyDAO")
public class CareBayFamilyDAO {

	@Autowired
	private IBaseDAO baseDAO;

	public IBaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 * 功能：读取分页数据
	 * */
	public PageResult readAllPages(int pageSize, int currentPage, SearchFilter filter) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CareBayFamily.class);
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("createDT"));
			DetachedCriteriaUtil.addSearchFilter(detachedCriteria, filter);
			return baseDAO.findByPages(detachedCriteria, pageSize, currentPage);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BaseException("查询所有分页异常");
		}
	}

	/**
	 * 功能：添加修改
	 * */
	public boolean saveCareBayFamily(CareBayFamily careBayFamily) {
		try {
			if (careBayFamily != null && careBayFamily.getId() != null && careBayFamily.getId().length() > 0)
				this.baseDAO.mrege(careBayFamily);
			else
				this.baseDAO.save(careBayFamily);
			return true;
		} catch (Exception ex) {
			throw new BaseException("添加亲属关系异常");
		}
	}

	/**
	 * 功能：读取亲属关系
	 * */
	public CareBayFamily readCareBayFamily(String id) {
		if (id != null && id.trim().length() > 0) {
			CareBayFamily family = (CareBayFamily) this.baseDAO.get(CareBayFamily.class, id);
			if (family != null)
				return family;
		}
		return null;
	}

	/**
	 * 功能：删除记录
	 * */
	public boolean deleteCareBayFamily(String id) {
		CareBayFamily family = this.readCareBayFamily(id);
		if (family != null) {
			family.setStatus(EntityStatus.DELETED);
			this.baseDAO.mrege(family);
			return true;
		}
		return false;
	}
}
