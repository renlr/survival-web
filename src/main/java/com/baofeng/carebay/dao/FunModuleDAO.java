package com.baofeng.carebay.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baofeng.carebay.entity.FunModule;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.BaseException;
import com.baofeng.utils.DetachedCriteriaUtil;
import com.baofeng.utils.IBaseDAO;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Repository("functionIdxDAO")
public class FunModuleDAO {

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
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(FunModule.class);
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("createDT"));
			DetachedCriteriaUtil.addSearchFilter(detachedCriteria, filter);
			return baseDAO.findByPages(detachedCriteria, pageSize, currentPage);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BaseException("查询所有分页异常");
		}
	}

	public PageResult readPagesViews(int rows, int page) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(FunModule.class);
			detachedCriteria.add(Restrictions.eq("views", Integer.valueOf(1)));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("indexs"));
			detachedCriteria.addOrder(Order.desc("createDT"));
			return baseDAO.findByPages(detachedCriteria, rows, page);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BaseException("查询所有分页异常");
		}
	}

	public boolean saveFunModule(FunModule idx) {
		try {
			if (idx != null && idx.getId() != null && idx.getId().length() > 0)
				this.baseDAO.mrege(idx);
			else
				this.baseDAO.save(idx);
			return true;
		} catch (Exception ex) {
			throw new BaseException("添加异常");
		}
	}

	public FunModule readFunModule(String id) {
		FunModule idx = (FunModule) this.baseDAO.get(FunModule.class, id);
		return idx;
	}

	public FunModule readFunModulebyName(String name) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(FunModule.class);
			detachedCriteria.add(Restrictions.eq("name", name));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			List<FunModule> list = this.baseDAO.findAllByCriteria(detachedCriteria);
			if (list != null && list.size() > 0) {
				FunModule module = list.get(0);
				return module;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return null;
	}

	public boolean deleteFunModule(String id) {
		FunModule fidx = this.readFunModule(id);
		if (fidx != null) {
			this.baseDAO.delete(fidx);
			return true;
		}
		return false;
	}

}
