package com.baofeng.carebay.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baofeng.carebay.entity.CurseCase;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.DetachedCriteriaUtil;
import com.baofeng.utils.IBaseDAO;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Repository("curseCaseDAO")
public class CurseCaseDAO {

	@Autowired
	private IBaseDAO baseDAO;

	public IBaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public PageResult readAllPages(Integer pageSize, Integer currentPage, String gid, SearchFilter filter) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CurseCase.class);
			detachedCriteria.createAlias("custom", "custom").add(Restrictions.eq("custom.id", gid));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.asc("online"));
			detachedCriteria.addOrder(Order.desc("indexs"));
			detachedCriteria.addOrder(Order.desc("createDT"));
			DetachedCriteriaUtil.addSearchFilter(detachedCriteria, filter);
			return baseDAO.findByPages(detachedCriteria, pageSize, currentPage);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public PageResult readPagesSkip(int rows, int page,SearchFilter filter) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CurseCase.class);
			detachedCriteria.add(Restrictions.eq("online", Integer.valueOf(1)));
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

	public boolean saveCurseCase(CurseCase curse) {
		try {
			if (curse != null && curse.getId() != null && curse.getId().length() > 0)
				this.baseDAO.mrege(curse);
			else
				this.baseDAO.save(curse);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public CurseCase readCurseCase(String id) {
		CurseCase curse = (CurseCase) this.baseDAO.get(CurseCase.class, id);
		return curse;
	}

	public boolean delCurseCase(String id) {
		CurseCase curse = this.readCurseCase(id);
		if (curse != null) {
			String hql = "update CurseCase set status = 0 where id = '" + curse.getId() + "'";
			this.baseDAO.execute(hql);
			return true;
		}
		return false;
	}

	public void saveCurseCase(String hql) {
		if (hql != null && hql.trim().length() > 0) {
			this.baseDAO.execute(hql);
		}
	}

	public List<CurseCase> readCurseList(String id) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CurseCase.class);
			detachedCriteria.createAlias("custom", "custom").add(Restrictions.eq("custom.id", id));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			return baseDAO.findAllByCriteria(detachedCriteria);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
