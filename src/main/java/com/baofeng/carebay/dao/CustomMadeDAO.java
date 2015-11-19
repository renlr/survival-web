package com.baofeng.carebay.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baofeng.carebay.entity.CustomMade;
import com.baofeng.carebay.entity.CustomMadeDetails;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.DetachedCriteriaUtil;
import com.baofeng.utils.IBaseDAO;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Repository("customMadeDAO")
public class CustomMadeDAO {

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
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CustomMade.class);
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("online"));
			detachedCriteria.addOrder(Order.desc("indexs"));
			detachedCriteria.addOrder(Order.desc("createDT"));
			DetachedCriteriaUtil.addSearchFilter(detachedCriteria, filter);
			return baseDAO.findByPages(detachedCriteria, rows, page);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public PageResult readPagesSkip(int rows, int page, SearchFilter filter) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CustomMadeDetails.class);
			detachedCriteria.add(Restrictions.eq("online", Integer.valueOf(1)));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("online"));
			detachedCriteria.addOrder(Order.desc("indexs"));
			detachedCriteria.addOrder(Order.desc("createDT"));
			DetachedCriteriaUtil.addSearchFilter(detachedCriteria, filter);
			return baseDAO.findByPages(detachedCriteria, rows, page);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public PageResult readAllPagesDetails(int rows, int page, String gid, SearchFilter filter) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CustomMadeDetails.class);
			detachedCriteria.createAlias("custom", "custom").add(Restrictions.eq("custom.id", gid));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("online"));
			detachedCriteria.addOrder(Order.desc("indexs"));
			detachedCriteria.addOrder(Order.desc("createDT"));
			DetachedCriteriaUtil.addSearchFilter(detachedCriteria, filter);
			return baseDAO.findByPages(detachedCriteria, rows, page);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public boolean saveCustomMade(CustomMade custom) {
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

	public CustomMade readCustomMade(String id) {
		if (id != null && id.trim().length() > 0) {
			CustomMade custom = (CustomMade) this.baseDAO.get(CustomMade.class, id);
			if (custom != null)
				return custom;
		}
		return null;
	}

	public void saveCustomMadeHql(String hql) {
		this.baseDAO.execute(hql);
	}

	public boolean saveCustomMadeDetails(CustomMadeDetails details) {
		try {
			if (details != null && details.getId() != null && details.getId().length() > 0)
				this.baseDAO.mrege(details);
			else
				this.baseDAO.save(details);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public CustomMadeDetails readCustomMadeDetails(String id) {
		if (id != null && id.trim().length() > 0) {
			CustomMadeDetails details = (CustomMadeDetails) this.baseDAO.get(CustomMadeDetails.class, id);
			return details;
		}
		return null;
	}

	public void saveCustomMadeDetailsHql(String hql) {
		if (hql != null && hql.trim().length() > 0) {
			this.baseDAO.execute(hql);
		}
	}

	public List<CustomMadeDetails> readCustomDetailsList(String id) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CustomMadeDetails.class);
			detachedCriteria.createAlias("custom", "custom").add(Restrictions.eq("custom.id", id));
			detachedCriteria.add(Restrictions.eq("online", Integer.valueOf(1)));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			return baseDAO.findAllByCriteria(detachedCriteria);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public List<CustomMade> readCustomMade() {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CustomMade.class);
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.add(Restrictions.or(Restrictions.eq("tailor", Integer.valueOf(0)), Restrictions.isNull("tailor")));
			List<CustomMade> detailsList = (List<CustomMade>) this.baseDAO.QueryDetachedCriteria(detachedCriteria);
			if (detailsList != null && detailsList.size() > 0) {
				return detailsList;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return null;
	}

	public void updateCustomMade(String id, Integer tailor) {
		try {
			String hql = "update CustomMade set tailor=" + tailor.intValue() + " where id = '" + id + "'";
			this.baseDAO.executeOpenSession(hql);
		} catch (Exception ex) {
		}
	}
}
