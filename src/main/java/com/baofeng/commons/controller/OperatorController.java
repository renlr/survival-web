package com.baofeng.commons.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baofeng.commons.entity.MenuItem;
import com.baofeng.commons.entity.Operator;
import com.baofeng.commons.service.IOperatorService;
import com.baofeng.utils.Constants;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.ResultMsg;
import com.baofeng.utils.SearchFilter;
import com.baofeng.utils.SearchRule;

@Controller
public class OperatorController {

	@Autowired
	private IOperatorService userService;

	public IOperatorService getUserService() {
		return userService;
	}

	public void setUserService(IOperatorService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(Model model, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("page/login");
		return mav;
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home(Model model, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("page/home");
		return mav;
	}

	@RequestMapping(value = "/frames", method = RequestMethod.GET)
	public ModelAndView frames(Model model, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("page/frames");
		Operator user = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		if (user != null) {
			List<MenuItem> menuTop = this.userService.readMenuTopList(user);
			List<MenuItem> menuLeft = this.userService.readMenuLeftList(user);
			if (menuTop != null) {
				Collections.sort(menuTop, new Comparator<MenuItem>() {
					@Override
					public int compare(MenuItem o1, MenuItem o2) {
						if (o1.getIndexs().intValue() > o2.getIndexs().intValue())
							return -1;
						return 1;
					}
				});
				mav.addObject("menuTop", menuTop);
			}
			if (menuLeft != null) {
				Collections.sort(menuLeft, new Comparator<MenuItem>() {
					@Override
					public int compare(MenuItem o1, MenuItem o2) {
						if (o1.getIndexs().intValue() > o2.getIndexs().intValue())
							return 1;
						return -1;
					}
				});
				mav.addObject("menuLeft", menuLeft);
			}
		}
		return mav;
	}

	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public ModelAndView top(Model model, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("page/top");
		Operator user = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		if (user != null) {
			List<MenuItem> menuTop = this.userService.readMenuTopList(user);
			List<MenuItem> menuLeft = this.userService.readMenuLeftList(user);
			if (menuTop != null) {
				Collections.sort(menuTop, new Comparator<MenuItem>() {
					@Override
					public int compare(MenuItem o1, MenuItem o2) {
						if (o1.getIndexs().intValue() > o2.getIndexs().intValue())
							return -1;
						return 0;
					}
				});
				mav.addObject("menuTop", menuTop);
			}
			if (menuLeft != null) {
				Collections.sort(menuLeft, new Comparator<MenuItem>() {
					@Override
					public int compare(MenuItem o1, MenuItem o2) {
						if (o1.getIndexs().intValue() > o2.getIndexs().intValue())
							return 1;
						return -1;
					}
				});
				mav.addObject("menuLeft", menuLeft);
			}
			mav.addObject("user", user);
		}
		return mav;
	}

	@RequestMapping(value = "/left", method = RequestMethod.GET)
	public ModelAndView left(Model model, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("page/left");
		Operator user = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		if (user != null) {
			List<MenuItem> menuTop = this.userService.readMenuTopList(user);
			List<MenuItem> menuLeft = this.userService.readMenuLeftList(user);
			if (menuTop != null) {
				Collections.sort(menuTop, new Comparator<MenuItem>() {
					@Override
					public int compare(MenuItem o1, MenuItem o2) {
						if (o1.getIndexs().intValue() > o2.getIndexs().intValue())
							return -1;
						return 1;
					}
				});
				mav.addObject("menuTop", menuTop);
			}
			if (menuLeft != null) {
				Collections.sort(menuLeft, new Comparator<MenuItem>() {
					@Override
					public int compare(MenuItem o1, MenuItem o2) {
						if (o1.getIndexs().intValue() > o2.getIndexs().intValue())
							return 1;
						return -1;
					}
				});
				mav.addObject("menuLeft", menuLeft);
			}
			mav.addObject("user", user);
		}
		return mav;
	}

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView main(Model model, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("page/main");
		Operator user = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		if (user != null) {
			mav.addObject("user", user);
		}
		return mav;
	}

	@RequestMapping(value = "/user/login", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView login(String loginName, String loginPwd, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("page/login");
		Operator user = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		if (user != null) {
			mav.setViewName("page/baseframe");
			mav.addObject("user", user);
		} else {
			if (loginName != null && loginName.trim().length() > 0 && loginPwd != null && loginPwd.trim().length() > 0) {
				user = this.userService.validation(loginName, loginPwd);
				if (user != null) {
					request.getSession().setAttribute(Constants.CURRENT_USER, user);
					mav.setViewName("page/baseframe");
					mav.addObject("user", user);
				}
			}
		}
		return mav;
	}

	@RequestMapping(value = "/user/logout", method = RequestMethod.GET)
	public ModelAndView logout(Model model, HttpServletRequest request) {
		request.getSession().removeAttribute(Constants.CURRENT_USER);
		ModelAndView mav = new ModelAndView("page/login");
		return mav;
	}

	@RequestMapping(value = "/user/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView("page/user");
		return mav;
	}

	@RequestMapping(value = "/user/info", method = RequestMethod.GET)
	public ModelAndView change() {
		ModelAndView mav = new ModelAndView("page/joinInfo");
		return mav;
	}

	@RequestMapping(value = "/user/stage", method = RequestMethod.GET)
	public ModelAndView backstage() {
		ModelAndView mav = new ModelAndView("page/backstage");
		return mav;
	}

	/**
	 * 功能：修改
	 * */
	@RequestMapping(value = "/user/saveUser", method = RequestMethod.POST)
	public ModelAndView saveUser(Integer id, String accounts, String name, String password, String phone, String email, String callService, String tempType, String chamber,
			HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("page/user");
		Operator user = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		Operator operator = new Operator();
		if (id != null && id.intValue() > 0)
			operator.setId(Integer.valueOf(id));
		operator.setAccounts(accounts);
		operator.setName(name);
		operator.setPassword(password);
		operator.setPhone(phone);
		operator.setEmail(email);
		if (callService != null && callService.trim().length() > 0) {
			operator.setCallService(Integer.valueOf(callService));
		}
		if (tempType != null && tempType.trim().length() > 0)
			operator.setType(Integer.valueOf(tempType));
		if (tempType != null && tempType.trim().length() > 0 && Integer.valueOf(tempType).intValue() == Integer.valueOf(1).intValue()) {
			operator.setChamber(chamber);
		} else {
			operator.setType(Integer.valueOf(1));
			operator.setChamber(user.getChamber());
		}
		if (this.userService.addUser(operator)) {
			mav.addObject("result", "success");
		}
		return mav;
	}

	/**
	 * 功能：编辑
	 * */
	@ResponseBody
	@RequestMapping(value = "/user/edit", method = RequestMethod.GET)
	public Operator edit(Integer id) {
		Operator user = this.userService.readUser(id);
		if (user != null) {
			return user;
		}
		return null;
	}

	/**
	 * 功能：禁用|启用
	 * */
	@ResponseBody
	@RequestMapping(value = "/user/disable", method = RequestMethod.GET)
	public ResultMsg disable(Integer id) {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.userService.disableOperator(id)) {
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：删除
	 * */
	@ResponseBody
	@RequestMapping(value = "/user/delete", method = RequestMethod.GET)
	public ResultMsg delete(Integer id) {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.userService.deleteOperator(id)) {
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@RequestMapping(value = "/user/readPages", method = RequestMethod.POST)
	public PageResult readPages(int page, int rows, HttpServletRequest request) {
		Operator user = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		if (user != null) {
			SearchFilter filter = new SearchFilter();
			if (user.getChamber() != null && user.getChamber().trim().length() > 0) {
				SearchRule search = new SearchRule();
				search.setData(user.getChamber());
				search.setField("chamber");
				search.setOp("eq");
				List<SearchRule> rules = new ArrayList<SearchRule>();
				rules.add(search);
				filter.setRules(rules);
			}
			PageResult pages = this.userService.readAllPages(rows, page, filter);
			return pages;
		}
		return null;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@RequestMapping(value = "/user/readPagesDetails", method = RequestMethod.POST)
	public PageResult readPagesDetails(int page, int rows, int gid) {
		PageResult pages = this.userService.readAllPagesDetails(rows, page, gid);
		return pages;
	}

	/**
	 * 功能：页面管理
	 * */
	@RequestMapping(value = "/user/details", method = RequestMethod.GET)
	public ModelAndView showDetails(String id) {
		ModelAndView mav = new ModelAndView("page/userDetails");
		Operator operator = this.userService.readOperator(Integer.valueOf(id));
		if (operator != null) {
			mav.addObject("operator", operator);
		}
		return mav;
	}

	/**
	 * 功能：添加页面
	 * */
	@ResponseBody
	@RequestMapping(value = "/user/addUserPages", method = RequestMethod.POST)
	public ResultMsg addUserPages(String gid, String ids, HttpServletRequest request) throws Exception {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		Operator user = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		if (this.userService.addUserPages(gid, ids, user)) {
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：删除页面
	 * */
	@ResponseBody
	@RequestMapping(value = "/user/deleteUserPages", method = RequestMethod.GET)
	public ResultMsg deleteUserPages(String id, HttpServletRequest request) throws Exception {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		Operator user = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		if (this.userService.deleteUserPages(id, user)) {
			result.setResultMessage("success");
		}
		return result;
	}
}
