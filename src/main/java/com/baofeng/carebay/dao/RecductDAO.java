package com.baofeng.carebay.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baofeng.carebay.entity.Recduct;
import com.baofeng.carebay.entity.RecductDetails;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.IBaseDAO;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Repository("recductDAO")
public class RecductDAO {

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
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Recduct.class);
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.asc("online"));
			detachedCriteria.addOrder(Order.desc("indexs"));
			detachedCriteria.addOrder(Order.desc("createDT"));
			return baseDAO.findByPages(detachedCriteria, pageSize, currentPage);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public boolean saveRecduct(Recduct recduct) {
		try {
			if (recduct != null && recduct.getId() != null && recduct.getId().length() > 0)
				this.baseDAO.mrege(recduct);
			else
				this.baseDAO.save(recduct);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public Recduct readRecduct(String id) {
		if (id != null && id.trim().length() > 0) {
			Recduct duct = (Recduct) this.baseDAO.get(Recduct.class, id);
			if (duct != null) {
				return duct;
			}
		}
		return null;
	}

	public PageResult readPagesDetails(int rows, int page, String groupId) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(RecductDetails.class);
			detachedCriteria.createAlias("recduct", "recduct").add(Restrictions.eq("recduct.id", groupId));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("indexs"));
			detachedCriteria.addOrder(Order.desc("createDT"));
			return baseDAO.findByPages(detachedCriteria, rows, page);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public boolean saveRecductDetails(RecductDetails details) {
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

	public RecductDetails readRecductDetails(String id) {
		if (id != null && id.trim().length() > 0) {
			RecductDetails details = (RecductDetails) this.baseDAO.get(RecductDetails.class, id);
			if (details != null) {
				return details;
			}
		}
		return null;
	}

	public void deleteRecductDetails(RecductDetails details) {
		try {
			if (details != null) {
				details.setRecduct(null);
				this.baseDAO.mrege(details);
				this.baseDAO.delete(details);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public List<RecductDetails> readRecductDetailsByParentId(String id) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(RecductDetails.class);
			detachedCriteria.createAlias("recduct", "recduct").add(Restrictions.eq("recduct.id", id));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.asc("indexs"));
			return this.baseDAO.findAllByCriteria(detachedCriteria);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public RecductDetails recductDetailsByIndexs(String id, String indexs) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(RecductDetails.class);
			detachedCriteria.add(Restrictions.eq("indexs", Integer.valueOf(indexs)));
			detachedCriteria.createAlias("recduct", "recduct").add(Restrictions.eq("recduct.id", id));
			List<RecductDetails> list = this.baseDAO.findAllByCriteria(detachedCriteria);
			if (list != null && list.size() > 0) {
				RecductDetails details = list.get(0);
				return details;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return null;
	}
}
