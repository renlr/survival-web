package com.baofeng.carebay.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.baofeng.carebay.entity.MonthsMeal;
import com.baofeng.carebay.entity.MonthsMealCarousel;
import com.baofeng.carebay.entity.MonthsMealDetails;
import com.baofeng.carebay.entity.MonthsMealOrderDetails;
import com.baofeng.carebay.service.IMonthsMealService;
import com.baofeng.commons.entity.Operator;
import com.baofeng.utils.Constants;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.ResultMsg;
import com.baofeng.utils.SearchFilter;
import com.baofeng.utils.SearchRule;

@Controller
@RequestMapping("meal")
public class MonthsMealController {

	@Autowired
	private IMonthsMealService monthsMealService;

	public IMonthsMealService getMonthsMealService() {
		return monthsMealService;
	}

	public void setMonthsMealService(IMonthsMealService monthsMealService) {
		this.monthsMealService = monthsMealService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/monthsMeal");
		return mav;
	}

	/**
	 * 功能：套餐图片
	 * */
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(String gid) {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/monthsMealDetails");
		mav.addObject("gid", gid);
		return mav;
	}

	/**
	 * 功能：套餐轮播
	 * */
	@RequestMapping(value = "carousel", method = RequestMethod.GET)
	public ModelAndView carousel() {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/monthsMealCarousel");
		return mav;
	}

	/**
	 * 功能：套餐点单
	 * */
	@RequestMapping(value = "orders", method = RequestMethod.GET)
	public ModelAndView orders() {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/monthsMealOrder");
		return mav;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@RequestMapping(value = "/readPages", method = RequestMethod.POST)
	public PageResult readPages(int page, int rows, HttpServletRequest request) {
		PageResult pages = null;
		Operator user = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		if (user != null) {
			SearchFilter filter = new SearchFilter();
			if (user.getChamber() != null && user.getChamber().trim().length() > 0) {
				SearchRule search = new SearchRule();
				search.setData(user.getChamber());
				search.setField("operator.chamber");
				search.setOp("eq");
				List<SearchRule> rules = new ArrayList<SearchRule>();
				rules.add(search);
				filter.setRules(rules);
			}
			pages = this.monthsMealService.readAllPages(rows, page, filter);
		}
		return pages;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@RequestMapping(value = "/readPagesDetails", method = RequestMethod.POST)
	public PageResult readPagesDetails(int page, int rows, String gid, HttpServletRequest request) {
		PageResult pages = null;
		Operator user = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		if (user != null) {
			SearchFilter filter = new SearchFilter();
			if (user.getChamber() != null && user.getChamber().trim().length() > 0) {
				SearchRule search = new SearchRule();
				search.setData(user.getChamber());
				search.setField("operator.chamber");
				search.setOp("eq");
				List<SearchRule> rules = new ArrayList<SearchRule>();
				rules.add(search);
				filter.setRules(rules);
			}
			pages = this.monthsMealService.readAllPagesDetails(rows, page, gid, filter);
		}
		return pages;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@RequestMapping(value = "/readPagesCarousel", method = RequestMethod.POST)
	public PageResult readPagesCarousel(int page, int rows, HttpServletRequest request) {
		PageResult pages = null;
		Operator user = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		if (user != null) {
			SearchFilter filter = new SearchFilter();
			if (user.getChamber() != null && user.getChamber().trim().length() > 0) {
				SearchRule search = new SearchRule();
				search.setData(user.getChamber());
				search.setField("operator.chamber");
				search.setOp("eq");
				List<SearchRule> rules = new ArrayList<SearchRule>();
				rules.add(search);
				filter.setRules(rules);
			}
			pages = this.monthsMealService.readAllPagesCarousel(rows, page, filter);
		}
		return pages;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@RequestMapping(value = "/readPagesOrders", method = RequestMethod.POST)
	public PageResult readPagesOrders(int page, int rows, String orderNo, String userId, String tranStatus, String chamber, String beginDT, String endDT,
			HttpServletRequest request) {
		PageResult pages = null;
		Operator user = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		if (user != null) {
			SearchFilter filter = new SearchFilter();
			if (user.getChamber() != null && user.getChamber().trim().length() > 0) {
				SearchRule search = new SearchRule();
				search.setData(user.getChamber());
				search.setField("user.chamber");
				search.setOp("eq");
				List<SearchRule> rules = new ArrayList<SearchRule>();
				rules.add(search);
				filter.setRules(rules);
			}
			pages = this.monthsMealService.readAllPagesOrders(rows, page, filter, orderNo, userId, tranStatus, chamber, beginDT, endDT);
		}
		return pages;
	}

	/**
	 * 功能：订单详细
	 * */
	@ResponseBody
	@RequestMapping(value = "/readOrdersDetails", method = RequestMethod.POST)
	public List<MonthsMealOrderDetails> readOrdersDetails(String gid) {
		return this.monthsMealService.readOrdersDetails(gid);
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@RequestMapping(value = "/readPagesSkip", method = RequestMethod.POST)
	public PageResult readPagesSkip(int page, int rows, String filter, HttpServletRequest request) {
		PageResult pages = null;
		Operator user = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		if (user != null) {
			SearchFilter $filter = new SearchFilter();
			if (user.getChamber() != null && user.getChamber().trim().length() > 0) {
				SearchRule search = new SearchRule();
				search.setData(user.getChamber());
				search.setField("operator.chamber");
				search.setOp("eq");
				List<SearchRule> rules = new ArrayList<SearchRule>();
				rules.add(search);
				$filter.setRules(rules);
			}
			pages = this.monthsMealService.readAllPagesSkip(rows, page, $filter, filter);
		}
		return pages;
	}

	/**
	 * 功能：添加菜品到菜单
	 * */
	@ResponseBody
	@RequestMapping(value = "/saveMonthsMealOrders", method = RequestMethod.POST)
	public ResultMsg saveMonthsMealOrders(String parentIds, String detailIds, String numbers) {
		ResultMsg msg = new ResultMsg();
		msg.setResultMessage("errors");
		if (parentIds != null && parentIds.trim().length() > 0 && detailIds != null && detailIds.trim().length() > 0 && numbers != null
				&& numbers.trim().length() > 0) {
			if (this.monthsMealService.saveMonthsMealOrders(parentIds, detailIds, numbers)) {
				msg.setResultMessage("success");
			}
		}
		return msg;
	}

	/**
	 * 功能：读取订单菜品
	 * */
	@ResponseBody
	@RequestMapping(value = "/readMonthsMealOrdersDetails", method = RequestMethod.GET)
	public MonthsMealOrderDetails readMonthsMealOrdersDetails(String id) {
		if (id != null && id.trim().length() > 0) {
			MonthsMealOrderDetails details = this.monthsMealService.readMonthsMealOrdersDetails(id);
			if (details != null) {
				return details;
			}
		}
		return null;
	}

	/**
	 * 功能：修改订单菜品
	 * */
	@ResponseBody
	@RequestMapping(value = "/updateMonthsMenalOrdersDetails", method = RequestMethod.GET)
	public ResultMsg updateMonthsMenalOrdersDetails(String id, String nums) {
		ResultMsg msg = new ResultMsg();
		msg.setResultMessage("errors");
		if (id != null && id.trim().length() > 0) {
			if (this.monthsMealService.updateMonthsMealOrderDetails(id, nums)) {
				msg.setResultMessage("success");
			}
		}
		return msg;
	}

	/**
	 * 功能：删除
	 * */
	@ResponseBody
	@RequestMapping(value = "/deleteOrderDetails", method = RequestMethod.GET)
	public ResultMsg deleteOrderDetails(String id) {
		ResultMsg msg = new ResultMsg();
		if (id != null) {
			if (this.monthsMealService.deleteOrderDetails(id)) {
				msg.setResultMessage("success");
			}
		}
		return msg;
	}

	/**
	 * 功能：添加修改
	 * */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(String id, String name, String author, String productionMethods, String flavor, String heat, String describes,
			String methodDescribes, Float price, String indexs, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/monthsMeal");
		Operator operator = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		MonthsMeal meal = new MonthsMeal();
		meal.setId(id);
		meal.setName(name);
		meal.setAuthor(author);
		meal.setProductionMethods(productionMethods);
		meal.setFlavor(flavor);
		meal.setHeat(heat);
		meal.setDescribes(describes);
		meal.setMethodDescribes(methodDescribes);
		meal.setPrice(price);
		meal.setIndexs(Integer.valueOf(indexs));
		meal.setOperator(operator);
		if (this.monthsMealService.addMonthsMeal(meal)) {
			mav.addObject("result", "success");
		}
		return mav;
	}

	/**
	 * 功能：添加修改
	 * */
	@RequestMapping(value = "/saveDetails", method = RequestMethod.POST)
	public ModelAndView saveDetails(String id, String gid, String indexs, @RequestParam MultipartFile[] images, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/monthsMealDetails");
		Operator operator = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		MonthsMealDetails details = new MonthsMealDetails();
		if (images != null && images.length > 0) {
			for (MultipartFile $file : images) {
				if (!$file.isEmpty()) {
					String $name = $file.getOriginalFilename();
					String ext = $name.substring($name.lastIndexOf("."), $name.length());
					String sha1 = DigestUtils.shaHex($file.getInputStream());
					String fileName = sha1 + ext;
					String path = Constants.DEFAULT_UPLOADIMAGEPATH + File.separator + Constants.sha1ToPath(sha1);
					Constants.mkdirs(path);
					FileUtils.copyInputStreamToFile($file.getInputStream(), new File(path, fileName));
					details.setImage(fileName);
					details.setImageSha1(sha1);
				}
			}
		}
		details.setId(id);
		details.setOperator(operator);
		if (indexs != null)
			details.setIndexs(Integer.valueOf(indexs));
		if (this.monthsMealService.addMonthsMealDetails(details, gid)) {
			mav.addObject("gid", gid);
			mav.addObject("result", "success");
		}
		return mav;
	}

	/**
	 * 功能：添加修改
	 * */
	@RequestMapping(value = "/saveCarousel", method = RequestMethod.POST)
	public ModelAndView saveCarousel(String id, String mealId, String indexs, @RequestParam MultipartFile[] images, HttpServletRequest request)
			throws Exception {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/monthsMealCarousel");
		Operator operator = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		MonthsMealCarousel carousel = new MonthsMealCarousel();
		if (images != null && images.length > 0) {
			for (MultipartFile $file : images) {
				if (!$file.isEmpty()) {
					String $name = $file.getOriginalFilename();
					String ext = $name.substring($name.lastIndexOf("."), $name.length());
					String sha1 = DigestUtils.shaHex($file.getInputStream());
					String fileName = sha1 + ext;
					String path = Constants.DEFAULT_UPLOADIMAGEPATH + File.separator + Constants.sha1ToPath(sha1);
					Constants.mkdirs(path);
					FileUtils.copyInputStreamToFile($file.getInputStream(), new File(path, fileName));
					carousel.setImage(fileName);
					carousel.setImageSha1(sha1);
				}
			}
		}
		carousel.setId(id);
		carousel.setOperator(operator);
		carousel.setIndexs(Integer.valueOf(indexs));
		if (this.monthsMealService.addMonthsMealCarousel(carousel, mealId)) {
			mav.addObject("result", "success");
		}
		return mav;
	}

	/**
	 * 功能：修改显示顺序
	 * */
	@RequestMapping(value = "/saveIndexs1", method = RequestMethod.POST)
	public ModelAndView saveIndexs1(String id1, String indexs1) throws Exception {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/monthsMeal");
		if (this.monthsMealService.updateMonthsMeal(id1, indexs1)) {
			mav.addObject("result", "success");
		}
		return mav;
	}

	/**
	 * 功能：编辑
	 * */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public MonthsMeal edit(String id) {
		MonthsMeal meal = this.monthsMealService.readMonthsMeal(id);
		if (meal != null) {
			return meal;
		}
		return null;
	}

	/**
	 * 功能：编辑
	 * */
	@ResponseBody
	@RequestMapping(value = "/editDetails", method = RequestMethod.GET)
	public MonthsMealDetails editDetails(String id) {
		MonthsMealDetails details = this.monthsMealService.readMonthsMealDetails(id);
		if (details != null) {
			return details;
		}
		return null;
	}

	/**
	 * 功能：编辑
	 * */
	@ResponseBody
	@RequestMapping(value = "/editCarousel", method = RequestMethod.GET)
	public MonthsMealCarousel editCarousel(String id) {
		MonthsMealCarousel details = this.monthsMealService.readMonthsMealCarousel(id);
		if (details != null) {
			return details;
		}
		return null;
	}

	/**
	 * 功能：删除
	 * */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ResultMsg delete(String id) {
		ResultMsg msg = new ResultMsg();
		msg.setResultMessage("errors");
		if (this.monthsMealService.deleteMonthsMeal(id)) {
			msg.setResultStatus(200);
			msg.setResultMessage("success");
		}
		return msg;
	}

	/**
	 * 功能：删除图片
	 * */
	@ResponseBody
	@RequestMapping(value = "/deleteDetails", method = RequestMethod.GET)
	public ResultMsg deleteDetails(String id) {
		ResultMsg msg = new ResultMsg();
		msg.setResultMessage("errors");
		if (this.monthsMealService.deleteMonthsMealDetails(id)) {
			msg.setResultStatus(200);
			msg.setResultMessage("success");
		}
		return msg;
	}

	/**
	 * 功能：删除轮播
	 * */
	@ResponseBody
	@RequestMapping(value = "/deleteCarousel", method = RequestMethod.GET)
	public ResultMsg deleteCarousel(String id) {
		ResultMsg msg = new ResultMsg();
		msg.setResultMessage("errors");
		if (this.monthsMealService.deleteMonthsMealCarousel(id)) {
			msg.setResultStatus(200);
			msg.setResultMessage("success");
		}
		return msg;
	}

	/**
	 * 功能：上下线
	 * */
	@ResponseBody
	@RequestMapping(value = "/online", method = RequestMethod.GET)
	public ResultMsg online(String id) {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.monthsMealService.onlineMonthsMeal(id)) {
			result.setResultStatus(200);
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：上下线
	 * */
	@ResponseBody
	@RequestMapping(value = "/onlineCarousel", method = RequestMethod.GET)
	public ResultMsg onlineCarousel(String id) {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.monthsMealService.onlineMonthsMealCarousel(id)) {
			result.setResultStatus(200);
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：修改状态
	 * */
	@ResponseBody
	@RequestMapping(value = "/editStatus", method = RequestMethod.GET)
	public ResultMsg editStatus(String id, String status) {
		ResultMsg msg = new ResultMsg();
		msg.setResultMessage("errors");
		if (id != null) {
			if (this.monthsMealService.updateStatus(id, status)) {
				msg.setResultMessage("success");
			}
		}
		return msg;
	}

	/**
	 * 功能：定时任务
	 * */
	@ResponseBody
	@RequestMapping(value = "/saveLineTask", method = RequestMethod.POST)
	public ResultMsg saveLineTask(String ids, String onlineDT, String offlineDT) throws Exception {
		ResultMsg result = new ResultMsg();
		if (this.monthsMealService.saveOnLineTask(ids, onlineDT, offlineDT)) {
			result.setResultMessage("success");
		}
		return result;
	}
}
