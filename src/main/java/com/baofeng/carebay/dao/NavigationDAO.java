package com.baofeng.carebay.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baofeng.carebay.entity.Navigation;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.DetachedCriteriaUtil;
import com.baofeng.utils.IBaseDAO;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Repository("navigationDAO")
public class NavigationDAO {

	@Autowired
	private IBaseDAO baseDAO;

	public IBaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public PageResult readAllPages(int rows, int page, String gid, SearchFilter filter) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Navigation.class);
			if (gid != null && gid.trim().length() > 0)
				detachedCriteria.createAlias("nav", "nav").add(Restrictions.eq("nav.id", gid));
			else
				detachedCriteria.add(Restrictions.isNull("nav.id"));
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

	public boolean saveNavigation(Navigation nav) {
		try {
			if (nav != null && nav.getId() != null && nav.getId().length() > 0)
				this.baseDAO.mrege(nav);
			else
				this.baseDAO.save(nav);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public Navigation readNavigation(String id) {
		if (id != null && id.trim().length() > 0) {
			Navigation nav = (Navigation) this.baseDAO.get(Navigation.class, id);
			if (nav != null)
				return nav;
		}
		return null;
	}

	public List<Navigation> readGidList(String id) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Navigation.class);
			detachedCriteria.createAlias("nav", "nav").add(Restrictions.eq("nav.id", id));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			List<Navigation> navList = this.baseDAO.findAllByCriteria(detachedCriteria);
			return navList;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public List<Navigation> readNavigtorList(String keys) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Navigation.class);
			detachedCriteria.add(Restrictions.eq("nkey", keys));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			List<Navigation> navList = this.baseDAO.findAllByCriteria(detachedCriteria);
			if (navList != null && navList.size() > 0) {
				return navList;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return null;
	}
}
