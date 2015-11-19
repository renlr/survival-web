package com.baofeng.carebay.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baofeng.carebay.entity.CustomMade;
import com.baofeng.carebay.entity.CustomService;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.DetachedCriteriaUtil;
import com.baofeng.utils.IBaseDAO;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Repository("customServiceDAO")
public class CustomServiceDAO {

	@Autowired
	private IBaseDAO baseDAO;

	public IBaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public PageResult readAllPages(int rows, int page, String gid, SearchFilter filter) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CustomService.class);
			detachedCriteria.createAlias("tos", "tos").add(Restrictions.eq("tos.id", gid));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("indexs"));
			detachedCriteria.addOrder(Order.desc("createDT"));
			DetachedCriteriaUtil.addSearchFilter(detachedCriteria, filter);
			return baseDAO.findByPages(detachedCriteria, rows, page);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public List<CustomService> readCustomServiceList(String id) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CustomService.class);
			detachedCriteria.createAlias("tos", "tos").add(Restrictions.eq("tos.id", id));
			detachedCriteria.add(Restrictions.eq("online", Integer.valueOf(1)));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			return this.baseDAO.findAllByCriteria(detachedCriteria);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public boolean saveCustomService(CustomService custom) {
		try {
			if (custom != null && custom.getId() != null && custom.getId().length() > 0)
				this.baseDAO.mrege(custom);
			else
				this.baseDAO.save(custom);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public CustomService readCustomService(String id) {
		if (id != null && id.trim().length() > 0) {
			CustomService service = (CustomService) this.baseDAO.get(CustomService.class, id);
			if (service != null)
				return service;
		}
		return null;
	}

	public void saveCustomServiceHql(String hql) {
		if (hql != null) {
			this.baseDAO.execute(hql);
		}
	}

	public void deleteCustomService(CustomService custom) {
		if (custom != null) {
			this.baseDAO.delete(custom);
		}
	}

	public List<CustomService> readCustomService() {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CustomService.class);
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.add(Restrictions.or(Restrictions.eq("tailor", Integer.valueOf(0)), Restrictions.isNull("tailor")));
			List<CustomService> detailsList = (List<CustomService>) this.baseDAO.QueryDetachedCriteria(detachedCriteria);
			if (detailsList != null && detailsList.size() > 0) {
				return detailsList;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return null;
	}

	public void updateCustomService(String id, Integer tailor) {
		try {
			String hql = "update CustomService set tailor=" + tailor.intValue() + " where id = '" + id + "'";
			this.baseDAO.executeOpenSession(hql);
		} catch (Exception ex) {
		}
	}
}
