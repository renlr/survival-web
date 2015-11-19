package com.baofeng.carebay.dao;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baofeng.carebay.entity.MookDetails;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.IBaseDAO;
import com.baofeng.utils.PageResult;

@Repository("mookDetailsDAO")
public class MookDetailsDAO {

	@Autowired
	private IBaseDAO baseDAO;

	public IBaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public PageResult readAllPages(int rows, int page, String gid) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MookDetails.class);
			detachedCriteria.createAlias("mook", "mook").add(Restrictions.eq("mook.id", gid));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.asc("name"));
			detachedCriteria.addOrder(Order.desc("createDT"));
			return baseDAO.findByPages(detachedCriteria, rows, page);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public boolean saveMookDetails(MookDetails details) {
		try {
			if (details != null && details.getId() != null && details.getId().length() > 0)
				this.baseDAO.mrege(details);
			else
				this.baseDAO.save(details);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public MookDetails readMookDetails(String id) {
		MookDetails details = (MookDetails) this.baseDAO.get(MookDetails.class, id);
		return details;
	}

	public boolean deleteMookDetails(String id) {
		MookDetails details = this.readMookDetails(id);
		if (details != null) {
			details.setMook(null);
			this.baseDAO.mrege(details);
			this.baseDAO.delete(details);
			return true;
		}
		return false;
	}

}
