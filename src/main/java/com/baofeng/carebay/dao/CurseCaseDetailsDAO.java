package com.baofeng.carebay.dao;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baofeng.carebay.entity.CurseCaseDetails;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.BaseException;
import com.baofeng.utils.IBaseDAO;
import com.baofeng.utils.PageResult;

@Repository("curseCaseDetailsDAO")
public class CurseCaseDetailsDAO {

	@Autowired
	private IBaseDAO baseDAO;

	public IBaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 * 功能：读取分页数据
	 * */
	public PageResult readAllPages(int pageSize, int currentPage, String groupid) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CurseCaseDetails.class);
			detachedCriteria.createAlias("curse", "curse").add(Restrictions.eq("curse.id", groupid));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.asc("online"));
			detachedCriteria.addOrder(Order.desc("indexs"));
			detachedCriteria.addOrder(Order.desc("createDT"));
			return baseDAO.findByPages(detachedCriteria, pageSize, currentPage);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BaseException("查询所有分页异常");
		}
	}

	public boolean saveCurseCaseDetails(CurseCaseDetails details) {
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

	public CurseCaseDetails readCurseCaseDetails(String id) {
		CurseCaseDetails details = (CurseCaseDetails) this.baseDAO.get(CurseCaseDetails.class, id);
		if (details != null) {
			return details;
		}
		return null;
	}

	public void deleteCurseCaseDetails(CurseCaseDetails details) {
		this.baseDAO.delete(details);
	}
}
