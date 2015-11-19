package com.baofeng.carebay.dao;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baofeng.carebay.entity.GestationTips;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.IBaseDAO;
import com.baofeng.utils.PageResult;

@Repository("gestationTipsDAO")
public class GestationTipsDAO {

	@Autowired
	private IBaseDAO baseDAO;

	public IBaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public PageResult readAllPages(Integer pageSize, Integer currentPage, String gid) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(GestationTips.class);
			detachedCriteria.createAlias("week", "week").add(Restrictions.eq("week.id", gid));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("online"));
			detachedCriteria.addOrder(Order.desc("createDT"));
			return baseDAO.findByPages(detachedCriteria, pageSize, currentPage);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public boolean saveGestationTips(GestationTips tips) {
		try {
			if (tips != null && tips.getId() != null && tips.getId().length() > 0)
				this.baseDAO.mrege(tips);
			else
				this.baseDAO.save(tips);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public GestationTips readGestationTips(String id) {
		GestationTips tips = (GestationTips) this.baseDAO.get(GestationTips.class, id);
		return tips;
	}

	public boolean delGestationTips(String id) {
		GestationTips tips = this.readGestationTips(id);
		if (tips != null) {
			tips.setStatus(EntityStatus.DELETED);
			this.saveGestationTips(tips);
			return true;
		}
		return false;
	}

}
