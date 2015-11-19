package com.baofeng.carebay.dao;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baofeng.carebay.entity.Activity;
import com.baofeng.carebay.entity.ActivityDetails;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.DetachedCriteriaUtil;
import com.baofeng.utils.IBaseDAO;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Repository("activityDAO")
public class ActivityDAO {

	@Autowired
	private IBaseDAO baseDAO;

	public IBaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public PageResult readAllPages(int rows, int page, SearchFilter filter) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Activity.class);
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("online"));
			detachedCriteria.addOrder(Order.desc("createDT"));
			DetachedCriteriaUtil.addSearchFilter(detachedCriteria, filter);
			return baseDAO.findByPages(detachedCriteria, rows, page);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public PageResult readAllPagesDetails(int rows, int page, String gid) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ActivityDetails.class);
			detachedCriteria.createAlias("act", "act").add(Restrictions.eq("act.id", gid));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("createDT"));
			return baseDAO.findByPages(detachedCriteria, rows, page);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public boolean saveActivity(Activity act) {
		try {
			if (act != null && act.getId() != null && act.getId().length() > 0)
				this.baseDAO.mrege(act);
			else
				this.baseDAO.save(act);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public Activity readActivity(String id) {
		if (id != null && id.trim().length() > 0) {
			Activity act = (Activity) this.baseDAO.get(Activity.class, id);
			if (act != null)
				return act;
		}
		return null;
	}

	public void deleteActivity(Activity act) {
		if (act != null)
			this.baseDAO.delete(act);
	}

	public void saveActivityHql(String hql) {
		if (hql != null && hql.trim().length() > 0)
			this.baseDAO.execute(hql);
	}

}
