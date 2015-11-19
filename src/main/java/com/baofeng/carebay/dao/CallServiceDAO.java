package com.baofeng.carebay.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baofeng.carebay.entity.ButlerService;
import com.baofeng.carebay.entity.MonthsMealOrder;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.commons.entity.Operator;
import com.baofeng.utils.DetachedCriteriaUtil;
import com.baofeng.utils.IBaseDAO;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Repository("callServiceDAO")
public class CallServiceDAO {

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
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ButlerService.class);
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("createDT"));
			DetachedCriteriaUtil.addSearchFilter(detachedCriteria, filter);
			return baseDAO.findByPages(detachedCriteria, pageSize, currentPage);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public List<ButlerService> readButlerService(Operator operator) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ButlerService.class);
			detachedCriteria.add(Restrictions.eq("service", Integer.valueOf(0)));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.asc("createDT"));
			return baseDAO.findAllByCriteria(detachedCriteria);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public ButlerService readButlerService(String id) {
		if (id != null && id.trim().length() > 0) {
			ButlerService butler = (ButlerService) this.baseDAO.get(ButlerService.class, id);
			if (butler != null)
				return butler;
		}
		return null;
	}

	public void saveButlerService(ButlerService butler) {
		try {
			if (butler != null && butler.getId() != null && butler.getId().length() > 0)
				this.baseDAO.mrege(butler);
			else
				this.baseDAO.save(butler);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void saveButlerServiceHql(String hql) {
		if (hql != null && hql.trim().length() > 0) {
			this.baseDAO.execute(hql);
		}
	}

	public List<MonthsMealOrder> readServiceMonthsMealOrders(Operator user) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MonthsMealOrder.class);
			detachedCriteria.add(Restrictions.eq("service", Integer.valueOf(0)));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.asc("createDT"));
			return baseDAO.findAllByCriteria(detachedCriteria);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
