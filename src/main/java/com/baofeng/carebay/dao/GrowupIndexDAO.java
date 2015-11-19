package com.baofeng.carebay.dao;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baofeng.carebay.entity.GrowupIndex;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.BaseException;
import com.baofeng.utils.IBaseDAO;
import com.baofeng.utils.PageResult;

@Repository("growupIndexDAO")
public class GrowupIndexDAO {

	@Autowired
	private IBaseDAO baseDAO;

	public IBaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public PageResult readAllPages(int rows, int page, String groupId) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(GrowupIndex.class);
			detachedCriteria.createAlias("growup", "growup").add(Restrictions.eq("growup.id", groupId));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("createDT"));
			return baseDAO.findByPages(detachedCriteria, rows, page);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BaseException("查询所有分页异常");
		}
	}

	public GrowupIndex readGrowupIndex(String id) {
		GrowupIndex growup = (GrowupIndex) this.baseDAO.get(GrowupIndex.class, id);
		return growup;
	}

	public boolean deleteGrowupIndex(String id) {
		GrowupIndex index = this.readGrowupIndex(id);
		if (index != null) {
			index.setGrowup(null);
			this.baseDAO.mrege(index);
			this.baseDAO.delete(index);
			return true;
		}
		return false;
	}
	
	public boolean addGrowupIndex(GrowupIndex gid) {
		try {
			if (gid != null && gid.getId() != null && gid.getId().length() > 0)
				this.baseDAO.mrege(gid);
			else
				this.baseDAO.save(gid);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
}
