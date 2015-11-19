package com.baofeng.carebay.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baofeng.carebay.entity.ProductsCategory;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.DetachedCriteriaUtil;
import com.baofeng.utils.IBaseDAO;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Repository("productsCategoryDAO")
public class ProductsCategoryDAO {

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
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ProductsCategory.class);
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("createDT"));
			DetachedCriteriaUtil.addSearchFilter(detachedCriteria, filter);
			return baseDAO.findByPages(detachedCriteria, pageSize, currentPage);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public boolean saveProductCategory(ProductsCategory category) {
		try {
			if (category != null && category.getId() != null && category.getId().length() > 0)
				this.baseDAO.mrege(category);
			else
				this.baseDAO.save(category);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public ProductsCategory readProductsCategory(String id) {
		if (id != null && id.trim().length() > 0) {
			ProductsCategory category = (ProductsCategory) this.baseDAO.get(ProductsCategory.class, id);
			return category;
		}
		return null;
	}

	public ProductsCategory readProductsCategoryByName(String name) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ProductsCategory.class);
			detachedCriteria.add(Restrictions.eq("name", name));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			List<ProductsCategory> list = this.baseDAO.findAllByCriteria(detachedCriteria);
			if (list != null && list.size() > 0) {
				ProductsCategory category = list.get(0);
				return category;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return null;
	}

	public void saveProductCategoryHql(String hql) {
		this.baseDAO.execute(hql);
	}
}
