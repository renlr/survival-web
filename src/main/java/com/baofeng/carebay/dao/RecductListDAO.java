package com.baofeng.carebay.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baofeng.carebay.entity.RecductList;
import com.baofeng.carebay.entity.RecductListDetails;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.DetachedCriteriaUtil;
import com.baofeng.utils.IBaseDAO;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Repository("recductListDAO")
public class RecductListDAO {

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
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(RecductList.class);
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("createDT"));
			DetachedCriteriaUtil.addSearchFilter(detachedCriteria, filter);
			return baseDAO.findByPages(detachedCriteria, pageSize, currentPage);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public PageResult readPagesDetails(int rows, int page, String gid) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(RecductListDetails.class);
			detachedCriteria.createAlias("ductCat", "ductCat").add(Restrictions.eq("ductCat.id", gid));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("createDT"));
			return baseDAO.findByPages(detachedCriteria, rows, page);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public boolean saveProductCat(RecductList category) {
		try {
			if (category != null && category.getId() != null && category.getId().length() > 0)
				this.baseDAO.mrege(category);
			else
				this.baseDAO.save(category);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public RecductList readProductCat(String id) {
		if (id != null && id.trim().length() > 0) {
			RecductList category = (RecductList) this.baseDAO.get(RecductList.class, id);
			if (category != null)
				return category;
		}
		return null;
	}

	public boolean readProductCatByName(String name) {
		if (name != null && name.trim().length() > 0) {
			try {
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(RecductList.class);
				detachedCriteria.add(Restrictions.eq("name", name));
				detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
				detachedCriteria.addOrder(Order.desc("createDT"));
				List<RecductList> list = this.baseDAO.findAllByCriteria(detachedCriteria);
				if (list != null && list.size() > 0) {
					return true;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}
		}
		return false;
	}

	public boolean saveProductCatDetails(RecductListDetails details) {
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

	public RecductListDetails readProductCatDetails(String id) {
		if (id != null && id.trim().length() > 0) {
			RecductListDetails details = (RecductListDetails) this.baseDAO.get(RecductListDetails.class, id);
			if (details != null) {
				return details;
			}
		}
		return null;
	}

	public void deleteProductCatDetails(RecductListDetails details) {
		if (details != null)
			this.baseDAO.delete(details);
	}
}
