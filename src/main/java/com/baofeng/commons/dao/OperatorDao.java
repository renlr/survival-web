package com.baofeng.commons.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baofeng.commons.entity.MenuItem;
import com.baofeng.commons.entity.Operator;
import com.baofeng.commons.entity.UserPages;
import com.baofeng.utils.BaseException;
import com.baofeng.utils.DetachedCriteriaUtil;
import com.baofeng.utils.IBaseDAO;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Repository("userDao")
public class OperatorDao {

	@Autowired
	private IBaseDAO baseDAO;

	public IBaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 * 功能：查询管理用户
	 * 
	 * @param loginName
	 * @param loginPwd
	 * */
	public Operator findUser(String loginName, String loginPwd) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Operator.class);
		detachedCriteria.add(Restrictions.eq("accounts", loginName));
		detachedCriteria.add(Restrictions.eq("password", loginPwd));
		detachedCriteria.add(Restrictions.eq("status", Integer.valueOf(0)));
		detachedCriteria.add(Restrictions.eq("delFlag", Integer.valueOf(0)));
		List<?> list = this.baseDAO.findAllByCriteria(detachedCriteria);
		if (list.size() > 0) {
			return (Operator) list.get(0);
		}
		return null;
	}

	/**
	 * 功能：添加系统管理用户
	 * */
	public boolean saveUser(Operator user) {
		try {
			if (user != null && user.getId() != null && user.getId().intValue() > 0)
				this.baseDAO.mrege(user);
			else
				this.baseDAO.save(user);
			return true;
		} catch (Exception ex) {
			throw new BaseException("添加系统管理会员异常");
		}
	}

	/**
	 * 功能：读取分页数据
	 * */
	public PageResult readAllPages(int pageSize, int currentPage, SearchFilter filter) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Operator.class);
			detachedCriteria.add(Restrictions.eq("delFlag", Integer.valueOf(0)));
			DetachedCriteriaUtil.addSearchFilter(detachedCriteria, filter);
			return baseDAO.findByPages(detachedCriteria, pageSize, currentPage);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BaseException("查询所有分页异常");
		}
	}

	/**
	 * 功能：读取系统管理用户
	 * */
	public Operator readUser(Integer id) {
		Operator user = (Operator) this.baseDAO.get(Operator.class, id);
		return user;
	}

	/**
	 * 功能：删除系统 管理用户
	 * */
	public boolean deleteUser(Integer id) {
		Operator user = readUser(id);
		if (user != null) {
			user.setDelFlag(Integer.valueOf(1));
			this.baseDAO.update(user);
			return true;
		}
		return false;
	}

	/**
	 * 功能：读取帐号
	 * */
	public Operator readOperator(Integer id) {
		if (id != null && id.intValue() > 0) {
			Operator operator = (Operator) this.baseDAO.get(Operator.class, id);
			return operator;
		}
		return null;
	}

	/**
	 * 功能：商家页面
	 * */
	public PageResult readAllPagesDetails(int rows, int page, int gid) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserPages.class);
			detachedCriteria.createAlias("user", "user").add(Restrictions.eq("user.id", Integer.valueOf(gid)));
			return baseDAO.findByPages(detachedCriteria, rows, page);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 功能：添加菜单页面
	 * */
	public boolean saveUserPages(UserPages pages) {
		try {
			if (pages != null && pages.getId() != null && pages.getId().intValue() > 0)
				this.baseDAO.mrege(pages);
			else
				this.baseDAO.save(pages);
			return true;
		} catch (Exception ex) {
			throw new BaseException("添加菜单页面错误");
		}
	}

	/**
	 * 功能：读取菜单页面
	 * */
	public UserPages readUserPages(String id) {
		if (id != null && id.trim().length() > 0) {
			UserPages pages = (UserPages) this.baseDAO.get(UserPages.class, Integer.valueOf(id));
			return pages;
		}
		return null;
	}

	/**
	 * 功能：删除菜单页面
	 * */
	public void deleteUserPages(UserPages pages) {
		if (pages != null)
			this.baseDAO.delete(pages);
	}

	/**
	 * 功能：读取顶部菜单
	 * */
	public List<MenuItem> readMenuTopList(Operator user) {
		try {
			if (user.getType().intValue() == Integer.valueOf(1).intValue()) {
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserPages.class);
				detachedCriteria.createAlias("user", "user").add(Restrictions.eq("user.id", user.getId()));
				detachedCriteria.addOrder(Order.desc("id"));
				List<UserPages> list = this.baseDAO.findAllByCriteria(detachedCriteria);
				Set<MenuItem> temp = new HashSet<MenuItem>();
				if (list != null && list.size() > 0) {
					for (Iterator<UserPages> it = list.iterator(); it.hasNext();) {
						UserPages page = it.next();
						if (page.getItemId() != null) {
							MenuItem menu = this.readMenuItem(Integer.valueOf(page.getItemId()));
							if (menu != null && menu.getItem() != null) {
								temp.add(menu.getItem());
							}
						}
					}
				}
				return new ArrayList<MenuItem>(temp);
			} else {
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MenuItem.class);
				detachedCriteria.addOrder(Order.asc("indexs"));
				List<MenuItem> list = this.baseDAO.findAllByCriteria(detachedCriteria);
				Set<MenuItem> temp = new HashSet<MenuItem>();
				if (list != null && list.size() > 0) {
					for (Iterator<MenuItem> it = list.iterator(); it.hasNext();) {
						MenuItem menu = it.next();
						if (menu.getItem() != null) {
							temp.add(menu.getItem());
						}
					}
				}
				return new ArrayList<MenuItem>(temp);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public MenuItem readMenuItem(Integer itemId) {
		MenuItem item = (MenuItem) this.baseDAO.get(MenuItem.class, itemId.intValue());
		if (item != null)
			return item;
		return null;
	}

	/**
	 * 功能：读取左边菜单
	 * */
	public List<MenuItem> readMenuLeftList(Operator user) {
		try {
			if (user.getType().intValue() == Integer.valueOf(1).intValue()) {
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserPages.class);
				detachedCriteria.createAlias("user", "user").add(Restrictions.eq("user.id", user.getId()));
				detachedCriteria.addOrder(Order.desc("id"));
				List<UserPages> list = this.baseDAO.findAllByCriteria(detachedCriteria);
				Set<MenuItem> temp = new HashSet<MenuItem>();
				if (list != null && list.size() > 0) {
					for (Iterator<UserPages> it = list.iterator(); it.hasNext();) {
						UserPages page = it.next();
						if (page.getItemId() != null) {
							MenuItem menu = this.readMenuItem(Integer.valueOf(page.getItemId()));
							if (menu != null && menu.getItem() != null) {
								temp.add(menu.getItem());
							}
							temp.add(menu);
						}
					}
				}
				return new ArrayList<MenuItem>(temp);
			} else {
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MenuItem.class);
				detachedCriteria.addOrder(Order.asc("indexs"));
				List<MenuItem> list = this.baseDAO.findAllByCriteria(detachedCriteria);
				Set<MenuItem> temp = new HashSet<MenuItem>();
				if (list != null && list.size() > 0) {
					for (Iterator<MenuItem> it = list.iterator(); it.hasNext();) {
						MenuItem menu = it.next();
						if (menu.getItem() != null) {
							temp.add(menu.getItem());
						}
						temp.add(menu);
					}
				}
				return new ArrayList<MenuItem>(temp);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
