package com.baofeng.commons.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baofeng.commons.entity.MenuItem;
import com.baofeng.commons.entity.Operator;
import com.baofeng.utils.BaseException;
import com.baofeng.utils.IBaseDAO;
import com.baofeng.utils.PageResult;

@Repository("menuitemDAO")
public class MenuitemDAO {

	@Autowired
	private IBaseDAO baseDAO;

	public IBaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public PageResult readAllPages(int rows, int page, Operator user) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MenuItem.class);
			detachedCriteria.createAlias("item", "item").add(Restrictions.isNotNull("item.id"));
			return baseDAO.findByPages(detachedCriteria, rows, page);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public boolean saveMenuItem(MenuItem menuItem) {
		try {
			if (menuItem != null && menuItem.getId() != null && menuItem.getId().intValue() > 0)
				this.baseDAO.mrege(menuItem);
			else
				this.baseDAO.save(menuItem);
			return true;
		} catch (Exception ex) {
			throw new BaseException("添加系统菜单异常");
		}
	}

	public boolean validateMenu(){
		List<?> list=baseDAO.executeQuery("select count(*) from MenuItem");
		if(list.size()>0){
			Long l=(Long) list.get(0);
			if(l>0){
				return false;
			}
		}
		
		
		return true;
	}
	public MenuItem readMenuitem(Integer id) {
		if (id != null && id.intValue() > 0) {
			MenuItem menu = (MenuItem) this.baseDAO.get(MenuItem.class, id);
			return menu;
		}
		return null;
	}
}
