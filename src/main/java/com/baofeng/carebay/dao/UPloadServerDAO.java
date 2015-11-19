package com.baofeng.carebay.dao;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baofeng.carebay.entity.UPloadServer;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.BaseException;
import com.baofeng.utils.DetachedCriteriaUtil;
import com.baofeng.utils.IBaseDAO;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Repository("uploadServerDAO")
public class UPloadServerDAO {

	@Autowired
	private IBaseDAO baseDAO;

	public IBaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public UPloadServer readUPloadServer(String id) {
		if (id != null) {
			UPloadServer uploadServer = (UPloadServer) this.baseDAO.get(UPloadServer.class, id);
			return uploadServer;
		}
		return null;
	}

	public boolean saveUPloadServer(UPloadServer upserver) {
		try {
			if (upserver != null && upserver.getId() != null && upserver.getId().length() > 0)
				this.baseDAO.mrege(upserver);
			else
				this.baseDAO.save(upserver);
			return true;
		} catch (Exception ex) {
			throw new BaseException("添加异常");
		}
	}

	public PageResult readAllPages(Integer pageSize, Integer currentPage, SearchFilter filter) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UPloadServer.class);
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("createDT"));
			DetachedCriteriaUtil.addSearchFilter(detachedCriteria, filter);
			return baseDAO.findByPages(detachedCriteria, pageSize, currentPage);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
