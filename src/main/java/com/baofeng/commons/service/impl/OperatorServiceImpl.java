package com.baofeng.commons.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.entity.Chamber;
import com.baofeng.commons.dao.ChamberDAO;
import com.baofeng.commons.dao.MenuitemDAO;
import com.baofeng.commons.dao.MonitorLogDAO;
import com.baofeng.commons.dao.OperatorDao;
import com.baofeng.commons.entity.MenuItem;
import com.baofeng.commons.entity.MonitorLog;
import com.baofeng.commons.entity.Operator;
import com.baofeng.commons.entity.UserPages;
import com.baofeng.commons.service.IOperatorService;
import com.baofeng.utils.MD5;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("userService")
public class OperatorServiceImpl implements IOperatorService {

	@Autowired
	private OperatorDao userDao;
	@Autowired
	private ChamberDAO chamberDAO;
	@Autowired
	private MenuitemDAO menuitemDAO;
	@Autowired
	private MonitorLogDAO monitorLogDAO;

	public OperatorDao getUserDao() {
		return userDao;
	}

	public void setChamberDAO(ChamberDAO chamberDAO) {
		this.chamberDAO = chamberDAO;
	}

	@Resource(name = "userDao")
	public void setUserDao(OperatorDao userDao) {
		this.userDao = userDao;
	}

	public MenuitemDAO getMenuitemDAO() {
		return menuitemDAO;
	}

	public void setMenuitemDAO(MenuitemDAO menuitemDAO) {
		this.menuitemDAO = menuitemDAO;
	}

	public MonitorLogDAO getMonitorLogDAO() {
		return monitorLogDAO;
	}

	public void setMonitorLogDAO(MonitorLogDAO monitorLogDAO) {
		this.monitorLogDAO = monitorLogDAO;
	}

	/**
	 * 功能：登录验证
	 * */
	@Override
	public Operator validation(String loginName, String loginPwd) {
		Operator user = this.userDao.findUser(loginName, MD5.MD5Encode(loginPwd));
		if (user != null)
			return user;
		return null;
	}

	@Override
	public boolean addUser(Operator user) {
		if (user != null && user.getId() != null && user.getId().intValue() > 0) {
			Operator $operator = this.readOperator(user.getId());
			if ($operator != null) {
				$operator.setName(user.getName());
				if (user.getPassword().length() != 32) {
					$operator.setPassword(MD5.MD5Encode(user.getPassword()));
				}
				if (user.getType().intValue() == Integer.valueOf(1).intValue())
					$operator.setChamber(user.getChamber());
				$operator.setPhone(user.getPhone());
				$operator.setEmail(user.getEmail());
				$operator.setType(user.getType());
				$operator.setCallService(user.getCallService());
				user = $operator;
			}
		} else {
			user.setPassword(MD5.MD5Encode(user.getPassword()));
			user.setCreateDt(new Date());
		}
		return this.userDao.saveUser(user);
	}

	/**
	 * 功能：读取帐号
	 * */
	@Override
	public Operator readOperator(Integer id) {
		return this.userDao.readOperator(id);
	}

	/**
	 * 功能：分页数据查询
	 * */
	@Override
	public PageResult readAllPages(Integer pageSize, Integer currentPage, SearchFilter filter) {
		PageResult $page = this.userDao.readAllPages(pageSize, currentPage, filter);
		if ($page != null && $page.getRows().size() > 0) {
			List<Operator> list = new ArrayList<Operator>();
			for (Object o : $page.getRows()) {
				Operator operator = (Operator) o;
				if (operator.getType().intValue() == Integer.valueOf(1)) {
					
					Chamber chamber = this.chamberDAO.readChamber(operator.getChamber());
					if (chamber != null) {
						operator.setChamber(chamber.getName());
					}
					list.add(operator);
				} else {
					list.add(operator);
				}
			}
			$page.setRows(list);
		}
		return $page;
	}

	/**
	 * 功能：商家页面
	 * */
	@Override
	public PageResult readAllPagesDetails(int rows, int page, int gid) {
		PageResult $page = this.userDao.readAllPagesDetails(rows, page, gid);
		if ($page != null && $page.getRows().size() > 0) {
			List<UserPages> list = new ArrayList<UserPages>();
			for (Object o : $page.getRows()) {
				UserPages pages = (UserPages) o;
				MenuItem item = this.menuitemDAO.readMenuitem(Integer.valueOf(pages.getItemId()));
				if (item != null) {
					pages.setItemId(item.getName());
					list.add(pages);
				}
			}
			$page.setRows(list);
		}
		return $page;
	}

	/**
	 * 功能：添加页面
	 * */
	@Override
	public boolean addUserPages(String gid, String ids, Operator operator) {
		if (gid != null && ids != null && gid.trim().length() > 0 && ids.trim().length() > 0) {
			Operator user = this.readOperator(Integer.valueOf(gid));
			List<String> menuName = new ArrayList<String>();
			if (user != null) {
				for (String id : ids.split(",")) {
					if (id != null && id.trim().length() > 0) {
						UserPages pages = new UserPages();
						MenuItem menu = this.menuitemDAO.readMenuitem(Integer.valueOf(id));
						if (menu != null) {
							menuName.add(menu.getName());
							pages.setUser(user);
							pages.setItemId(menu.getId().toString());
							pages.setCreateDT(new Date());
							this.userDao.saveUserPages(pages);
						}
					}
				}
			}
			MonitorLog log = new MonitorLog();
			log.setOperator(operator);
			log.setLog("添加页面" + Arrays.toString(menuName.toArray(new String[menuName.size()])) + "到[" + user.getName() + "]成功!");
			this.monitorLogDAO.saveMonitorLog(log);
			return true;
		}
		return false;
	}

	/**
	 * 功能：删除页面
	 * */
	@Override
	public boolean deleteUserPages(String id, Operator operator) {
		if (id != null && id.trim().length() > 0) {
			UserPages pages = this.readUserPages(id);
			MenuItem menu = this.menuitemDAO.readMenuitem(Integer.valueOf(pages.getItemId()));

			MonitorLog log = new MonitorLog();
			log.setOperator(operator);
			log.setLog("删除[" + pages.getUser().getName() + "]中[" + menu.getName() + "]面页成功!");
			this.monitorLogDAO.saveMonitorLog(log);
			if (pages != null) {
				pages.setUser(null);
				pages.setItemId(null);
				this.userDao.saveUserPages(pages);
				this.userDao.deleteUserPages(pages);
				return true;
			}
		}
		return false;
	}

	/**
	 * 功能：禁用|启用
	 * */
	@Override
	public boolean disableOperator(Integer id) {
		if (id != null && id.intValue() > 0) {
			Operator operator = this.readOperator(id);
			if (operator != null) {
				if (operator.getStatus() == null) {
					operator.setStatus(Integer.valueOf(0));
				}
				if (operator.getStatus().intValue() == Integer.valueOf(1).intValue()) {
					operator.setStatus(Integer.valueOf(0));
				} else {
					operator.setStatus(Integer.valueOf(1));
				}
				this.userDao.saveUser(operator);
				return true;
			}
		}
		return false;
	}

	/**
	 * 功能：删除帐号
	 * */
	@Override
	public boolean deleteOperator(Integer id) {
		if (id != null && id.intValue() > 0) {
			Operator operator = this.readOperator(id);
			if (operator != null) {
				operator.setDelFlag(Integer.valueOf(1));
				this.userDao.saveUser(operator);
				return true;
			}
		}
		return false;
	}

	/**
	 * 功能：读取菜单页面
	 * */
	@Override
	public UserPages readUserPages(String id) {
		return this.userDao.readUserPages(id);
	}

	/**
	 * 功能：读取系统管理用户
	 * */
	@Override
	public Operator readUser(Integer id) {
		if (id != null && id.intValue() > 0)
			return this.userDao.readUser(id);
		return null;
	}

	/**
	 * 功能：删除系统管理用户
	 * */
	@Override
	public boolean delUser(Integer id) {
		if (id != null && id.intValue() > 0)
			return this.userDao.deleteUser(id);
		return false;
	}

	/**
	 * 功能：读取顶部菜单
	 * */
	@Override
	public List<MenuItem> readMenuTopList(Operator user) {
		return this.userDao.readMenuTopList(user);
	}

	/**
	 * 功能：读取左边菜单
	 * */
	@Override
	public List<MenuItem> readMenuLeftList(Operator user) {
		return this.userDao.readMenuLeftList(user);
	}
}
