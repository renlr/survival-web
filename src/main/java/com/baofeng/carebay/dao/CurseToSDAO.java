package com.baofeng.carebay.dao;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baofeng.carebay.entity.CurseToS;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.DetachedCriteriaUtil;
import com.baofeng.utils.IBaseDAO;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Repository("curseToSDAO")
public class CurseToSDAO {

	@Autowired
	private IBaseDAO baseDAO;

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public PageResult readAllPages(int rows, int page, SearchFilter filter) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CurseToS.class);
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

	public boolean saveCurseToS(CurseToS curseTos) {
		try {
			if (curseTos != null && curseTos.getId() != null && curseTos.getId().length() > 0)
				this.baseDAO.mrege(curseTos);
			else
				this.baseDAO.save(curseTos);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public CurseToS readCurseToS(String id) {
		if (id != null && id.trim().length() > 0) {
			CurseToS tos = (CurseToS) this.baseDAO.get(CurseToS.class, id);
			if (tos != null)
				return tos;
		}
		return null;
	}

	public void saveCurseTosHql(String hql) {
		if (hql != null && hql.trim().length() > 0) {
			this.baseDAO.execute(hql);
		}
	}

	public void deleteCurseToS(CurseToS tos) {
		if (tos != null) {
			this.baseDAO.delete(tos);
		}
	}

}
