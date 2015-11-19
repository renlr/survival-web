package com.baofeng.carebay.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baofeng.carebay.entity.WeekService;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.BaseException;
import com.baofeng.utils.IBaseDAO;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Repository("weekServiceDAO")
public class WeekServiceDAO {

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
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(WeekService.class);
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.asc("name"));
			detachedCriteria.addOrder(Order.desc("createDT"));
			return baseDAO.findByPages(detachedCriteria, pageSize, currentPage);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BaseException("查询所有分页异常");
		}
	}

	public boolean saveWeekService(WeekService week) {
		try {
			if (week != null && week.getId() != null && week.getId().length() > 0)
				this.baseDAO.mrege(week);
			else
				this.baseDAO.save(week);
			return true;
		} catch (Exception ex) {
			throw new BaseException("添加异常");
		}
	}

	public WeekService readWeekServiceByName(String name) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(WeekService.class);
			detachedCriteria.add(Restrictions.eq("name", Integer.valueOf(name)));
			List<WeekService> list = this.baseDAO.findAllByCriteria(detachedCriteria);
			if (list != null && list.size() > 0) {
				WeekService week = list.get(0);
				if (week != null) {
					return week;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return null;
	}

	public WeekService readWeekService(String id) {
		if (id != null) {
			WeekService week = (WeekService) this.baseDAO.get(WeekService.class, id);
			if (week != null) {
				return week;
			}
		}
		return null;
	}

}
