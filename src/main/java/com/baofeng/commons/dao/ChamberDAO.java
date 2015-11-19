package com.baofeng.commons.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baofeng.carebay.entity.Chamber;
import com.baofeng.carebay.entity.ChamberDetails;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.DetachedCriteriaUtil;
import com.baofeng.utils.IBaseDAO;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Repository("chamberDAO")
public class ChamberDAO {

	@Autowired
	private IBaseDAO baseDAO;

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public Chamber readChamber(String id) {
		if (id != null && id.trim().length() > 0) {
			Chamber chamber = (Chamber) this.baseDAO.get(Chamber.class, id);
			if (chamber != null)
				return chamber;
		}
		return null;
	}

	public boolean saveChamber(Chamber chamber) {
		try {
			if (chamber != null && chamber.getId() != null && chamber.getId().length() > 0)
				this.baseDAO.mrege(chamber);
			else
				this.baseDAO.save(chamber);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public PageResult readAllPages(Integer pageSize, Integer currentPage, SearchFilter filter) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Chamber.class);
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("createDT"));
			DetachedCriteriaUtil.addSearchFilter(detachedCriteria, filter);
			return baseDAO.findByPages(detachedCriteria, pageSize, currentPage);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public PageResult readAllPages(int rows, int page, String gid, String inds) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ChamberDetails.class);
			detachedCriteria.createAlias("chamber", "chamber").add(Restrictions.eq("chamber.id", gid));
			detachedCriteria.add(Restrictions.eq("type", Integer.valueOf(inds)));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.asc("indexs"));
			detachedCriteria.addOrder(Order.desc("createDT"));
			return baseDAO.findByPages(detachedCriteria, rows, page);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public boolean deleteChamber(Chamber chamber) {
		if (chamber != null) {
			this.baseDAO.delete(chamber);
			return true;
		}
		return false;
	}

	public boolean saveChamberDetails(ChamberDetails details) {
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

	public ChamberDetails readChamberDetails(String id) {
		if (id != null && id.trim().length() > 0) {
			ChamberDetails details = (ChamberDetails) this.baseDAO.get(ChamberDetails.class, id);
			if (details != null)
				return details;
		}
		return null;
	}

	public void saveChamberDetailsHql(String hql) {
		if (hql != null && hql.trim().length() > 0) {
			this.baseDAO.execute(hql);
		}
	}

	public void delChamberDetails(ChamberDetails details) {
		if (details != null)
			this.baseDAO.delete(details);
	}

	public List<Chamber> readChamberList(SearchFilter filter) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Chamber.class);
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("createDT"));
			DetachedCriteriaUtil.addSearchFilter(detachedCriteria, filter);
			return baseDAO.findAllByCriteria(detachedCriteria);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
