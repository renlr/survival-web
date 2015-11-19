package com.baofeng.carebay.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.baofeng.carebay.entity.Products;
import com.baofeng.carebay.entity.ProductsDetails;
import com.baofeng.carebay.entity.ProductsOrderDetails;
import com.baofeng.carebay.service.IProductsService;
import com.baofeng.carebay.util.Constants;
import com.baofeng.commons.entity.Operator;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.ResultMsg;
import com.baofeng.utils.SearchFilter;
import com.baofeng.utils.SearchRule;

@Controller
@RequestMapping("products")
public class ProductsController {

	@Autowired
	private IProductsService productsService;

	public IProductsService getProductsService() {
		return productsService;
	}

	public void setProductsService(IProductsService productsService) {
		this.productsService = productsService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/products");
		return mav;
	}

	@RequestMapping(value = "/news", method = RequestMethod.GET)
	public ModelAndView news(String id) {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/productsNew");
		mav.addObject("gid", id);
		return mav;
	}

	@RequestMapping(value = "/orders", method = RequestMethod.GET)
	public ModelAndView orders() {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/productsOrders");
		return mav;
	}

	@RequestMapping(value = "/onlineShow", method = RequestMethod.GET)
	public ModelAndView onlineShow() {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/productsOnline");
		return mav;
	}

	@RequestMapping(value = "/editContent", method = RequestMethod.GET)
	public ModelAndView editContent(String id, String gid) {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/productsContent");
		Products ucts = this.productsService.readProducts(id);
		mav.addObject("ucts", ucts);
		return mav;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@RequestMapping(value = "/readPages", method = RequestMethod.POST)
	public PageResult readPages(int page, int rows, String gid, HttpServletRequest request) {
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
			pages = this.productsService.readAllPages(rows, page, gid, filter);
		}
		return pages;
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
			pages = this.productsService.readAllPagesSkip(rows, page, $filter, filter);
		}
		return pages;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@RequestMapping(value = "/readPagesOrders", method = RequestMethod.POST)
	// {"orderNo":orderNo,"userId":user,"tranStatus":tranStatus,"beginDT":beginDT,"endDT":endDT}
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
			pages = this.productsService.readPagesOrders(rows, page, filter, orderNo, userId, tranStatus, chamber, beginDT, endDT);
		}
		return pages;
	}

	/**
	 * 功能：订单详细
	 * */
	@ResponseBody
	@RequestMapping(value = "/readOrdersDetails", method = RequestMethod.POST)
	public List<ProductsOrderDetails> readOrdersDetails(String gid) {
		return this.productsService.readOrdersDetails(gid);
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@RequestMapping(value = "/readPagesOnline", method = RequestMethod.POST)
	public PageResult readPagesOnline(int page, int rows) {
		PageResult pages = this.productsService.readAllPagesOnline(rows, page, null);
		return pages;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@RequestMapping(value = "/readPagesDetails", method = RequestMethod.POST)
	public PageResult readPagesDetails(int page, int rows, String groupId) {
		PageResult pages = this.productsService.readPagesDetails(rows, page, groupId);
		return pages;
	}

	/**
	 * 功能：销售属性分页
	 * */
	@ResponseBody
	@RequestMapping(value = "/readPagesParams", method = RequestMethod.POST)
	public PageResult readPagesParams(int page, int rows, String groupId) {
		PageResult pages = this.productsService.readPagesParams(rows, page, groupId);
		return pages;
	}

	/**
	 * 功能：添加修改
	 * */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(String id, String gid, String name, String imageDetails, String articleNumber, String costPrice, String bazaarPrice,
			String sellPrice, String inventory, String quotaNumber, String madeDate, String expirationDate, String category, String indexs,
			HttpServletRequest request) throws Exception {
		Operator operator = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/productsNew");
		Products products = new Products();
		products.setId(id);
		products.setName(name);
		products.setImageDetails(imageDetails);
		if (articleNumber != null && articleNumber.trim().length() > 0)
			products.setArticleNumber(articleNumber);
		if (costPrice != null && costPrice.trim().length() > 0)
			products.setCostPrice(Float.valueOf(costPrice));
		if (bazaarPrice != null && bazaarPrice.trim().length() > 0)
			products.setBazaarPrice(Float.valueOf(bazaarPrice));
		products.setSellPrice(Float.valueOf(sellPrice));
		if (inventory != null && inventory.trim().length() > 0)
			products.setInventory(Long.valueOf(inventory));
		if (quotaNumber != null && quotaNumber.trim().length() > 0)
			products.setQuotaNumber(Long.valueOf(quotaNumber));
		if (madeDate != null && madeDate.trim().length() > 0)
			products.setMadeDate(DateUtils.parseDate(madeDate, new String[] { "yyyy-MM-dd HH:mm:ss" }));
		if (expirationDate != null && expirationDate.trim().length() > 0)
			products.setExpirationDate(Integer.valueOf(expirationDate));
		if (indexs != null && indexs.trim().length() > 0)
			products.setIndexs(Integer.valueOf(indexs));
		products.setOperator(operator);
		if (this.productsService.addProducts(products, category, gid)) {
			mav.addObject("gid", gid);
			mav.addObject("result", "success");
		}
		return mav;
	}

	/**
	 * 功能：添加商品到订单
	 * */
	@ResponseBody
	@RequestMapping(value = "/saveProductsOrders", method = RequestMethod.POST)
	public ResultMsg saveProductsOrders(String parentIds, String detailIds, String numbers) {
		ResultMsg msg = new ResultMsg();
		msg.setResultMessage("errors");
		if (parentIds != null && parentIds.trim().length() > 0 && detailIds != null && detailIds.trim().length() > 0 && numbers != null
				&& numbers.trim().length() > 0) {
			if (this.productsService.saveProductsOrdersDetails(parentIds, detailIds, numbers)) {
				msg.setResultMessage("success");
			}
		}
		return msg;
	}

	/**
	 * 功能：修改显示顺序
	 * */
	@RequestMapping(value = "/saveIndexs1", method = RequestMethod.POST)
	public ModelAndView saveIndexs1(String id1, String gid, String indexs1) throws Exception {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/productsNew");
		if (this.productsService.updateProducts(id1, indexs1)) {
			mav.addObject("gid", gid);
			mav.addObject("result", "success");
		}
		return mav;
	}

	/**
	 * 功能：设置折扣
	 * */
	@RequestMapping(value = "/saveDiscount", method = RequestMethod.POST)
	public ModelAndView saveDiscount(String disIds, String gid, String discount) throws Exception {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/productsNew");
		if (disIds != null && discount != null && disIds.trim().length() > 0 && discount.trim().length() > 0) {
			if (this.productsService.updateDisCount(disIds, discount)) {
				mav.addObject("result", "success");
			}
		}
		mav.addObject("gid", gid);
		return mav;
	}

	/**
	 * 功能：编辑内容
	 * */
	@ResponseBody
	@RequestMapping(value = "/saveContent", method = RequestMethod.POST)
	public ResultMsg saveContent(String id, String content) throws Exception {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		Products ucts = new Products();
		StringBuffer $temp = new StringBuffer();
		$temp.append(Constants.HTMLCENTERCODE);
		if (content != null && content.trim().length() > 0) {
			File file = new File(Constants.DEFAULT_BUILDHTMLPATH + File.separator + id + ".html");
			$temp.append(content);
			FileUtils.writeStringToFile(file, $temp.toString(), "utf-8");
			ucts.setImageDetails(id + ".html");
		}
		if (this.productsService.addProductsContent(id, content)) {
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：添加修改
	 * */
	@RequestMapping(value = "/saveDetails", method = RequestMethod.POST)
	public ModelAndView saveDetails(String groupId, String pid, Integer indexs, @RequestParam MultipartFile[] images) throws Exception {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/productsDetails");
		ProductsDetails details = new ProductsDetails();
		if (images != null && images.length > 0) {
			for (MultipartFile $file : images) {
				if (!$file.isEmpty()) {
					String name = $file.getOriginalFilename();
					String ext = name.substring(name.lastIndexOf("."), name.length());
					String sha1 = DigestUtils.shaHex($file.getInputStream());
					String fileName = sha1 + ext;
					String path = Constants.DEFAULT_UPLOADIMAGEPATH + File.separator + Constants.sha1ToPath(sha1);
					Constants.mkdirs(path);
					FileUtils.copyInputStreamToFile($file.getInputStream(), new File(path, fileName));
					details.setImageSha1(sha1);
					details.setImage(fileName);
				}
			}
		}
		details.setIndexs(indexs);
		if (this.productsService.addProductsDetails(details, groupId)) {
			Products products = this.productsService.readProducts(groupId);
			if (products != null) {
				mav.addObject("pid", pid);
				mav.addObject("duct", products);
			}
		}
		return mav;
	}

	/**
	 * 功能：添加销售属性
	 * */
	@ResponseBody
	@RequestMapping(value = "/saveParams", method = RequestMethod.POST)
	public ResultMsg saveParams(String groupId, String name1, String name2, String nameVal1, String nameVal2, String costPrice, String bazaarPrice,
			String sellPrice, String inventory) throws Exception {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.productsService.addProductsParams(groupId, name1, name2, nameVal1, nameVal2, costPrice, bazaarPrice, sellPrice, inventory)) {
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：按字段修改
	 * */
	@ResponseBody
	@RequestMapping(value = "/saveEditFields", method = RequestMethod.POST)
	public ResultMsg saveEditField(String id, String field, String value) throws Exception {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.productsService.editFiles(id, field, value)) {
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：按字段修改销售参数
	 * */
	@ResponseBody
	@RequestMapping(value = "/saveParamsFields", method = RequestMethod.POST)
	public ResultMsg saveParamsFields(String id, String field, String value) throws Exception {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.productsService.editParamsFiles(id, field, value)) {
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：定时任务
	 * */
	@ResponseBody
	@RequestMapping(value = "/saveLineTask", method = RequestMethod.POST)
	public ResultMsg saveLineTask(String ids, String onlineDT, String offlineDT) throws Exception {
		ResultMsg result = new ResultMsg();
		if (this.productsService.saveOnLineTask(ids, onlineDT, offlineDT)) {
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：下拉列表
	 * */
	@ResponseBody
	@RequestMapping(value = "/listProducts", method = RequestMethod.GET)
	public List<Products> listProducts() throws Exception {
		List<Products> list = this.productsService.listProducts();
		if (list != null && list.size() > 0) {
			return list;
		}
		return new ArrayList<Products>();
	}

	/**
	 * 功能：上线
	 * */
	@ResponseBody
	@RequestMapping(value = "/online1", method = RequestMethod.GET)
	public ResultMsg online1(String id) {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.productsService.onLineProducts(id, Integer.valueOf(1))) {
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：下线
	 * */
	@ResponseBody
	@RequestMapping(value = "/online0", method = RequestMethod.GET)
	public ResultMsg online0(String id) {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.productsService.onLineProducts(id, Integer.valueOf(0))) {
			result.setResultMessage("success");
		}
		return result;
	}

	@RequestMapping(value = "/addImages", method = RequestMethod.GET)
	public ModelAndView addImages(String id, String pid) {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/productsImages");
		Products products = this.productsService.readProducts(id);
		if (products != null) {
			mav.addObject("pid", pid);
			mav.addObject("duct", products);
		}
		return mav;
	}

	// ///////////////////////////////////////////////////////////////////////////////////

	// 销售属性
	@RequestMapping(value = "/addParams", method = RequestMethod.GET)
	public ModelAndView addParams(String id) {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/productsParams");
		Products products = this.productsService.readProducts(id);
		if (products != null) {
			mav.addObject("duct", products);
		}
		return mav;
	}

	/**
	 * 功能：删除销售属性
	 * */
	@ResponseBody
	@RequestMapping(value = "/deleteParams", method = RequestMethod.GET)
	public ResultMsg deleteParams(String id) {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.productsService.onLineProductsParams(id)) {
			result.setResultMessage("success");
		}
		return result;
	}

	@RequestMapping(value = "/addComment", method = RequestMethod.GET)
	public ModelAndView addComment(String id) {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/productsParams");
		Products products = this.productsService.readProducts(id);
		if (products != null) {
			mav.addObject("duct", products);
		}
		return mav;
	}

	// ///////////////////////////////////////////////////////////////////////////////
	// 图片

	@ResponseBody
	@RequestMapping(value = "/deleteImage", method = RequestMethod.GET)
	public ResultMsg deleteImage(String id) {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.productsService.deleteProductsImages(id)) {
			result.setResultMessage("success");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/editIndexs", method = RequestMethod.GET)
	public ProductsDetails editIndexs(String id) {
		ProductsDetails details = this.productsService.readProductsImages(id);
		if (details != null) {
			return details;
		}
		return null;
	}

	/**
	 * 功能：编辑
	 * */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public Products edit(String id) {
		Products products = this.productsService.readProducts(id);
		if (products != null) {
			return products;
		}
		return null;
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
			if (this.productsService.updateStatus(id, status)) {
				msg.setResultMessage("success");
			}
		}
		return msg;
	}

	/**
	 * 功能：删除
	 * */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ResultMsg delete(String id) {
		ResultMsg msg = new ResultMsg();
		if (id != null) {
			if (this.productsService.deleteProducts(id)) {
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
			if (this.productsService.deleteOrderDetails(id)) {
				msg.setResultMessage("success");
			}
		}
		return msg;
	}

	/**
	 * 功能：读取订单商品
	 * */
	@ResponseBody
	@RequestMapping(value = "/readProductsOrdersDetails", method = RequestMethod.GET)
	public ProductsOrderDetails readProductsOrdersDetails(String id) {
		if (id != null && id.trim().length() > 0) {
			ProductsOrderDetails details = this.productsService.readProductsOrderDetails(id);
			if (details != null) {
				return details;
			}
		}
		return null;
	}

	/**
	 * 功能：修改订单商品
	 * */
	@ResponseBody
	@RequestMapping(value = "/updateProductsOrdersDetails", method = RequestMethod.GET)
	public ResultMsg updateProductsOrdersDetails(String id, String nums) {
		ResultMsg msg = new ResultMsg();
		msg.setResultMessage("errors");
		if (id != null && id.trim().length() > 0) {
			if (this.productsService.updateProductsOrderDetails(id, nums)) {
				msg.setResultMessage("success");
			}
		}
		return msg;
	}

	/**
	 * 功能：修改显示顺序
	 * */
	@ResponseBody
	@RequestMapping(value = "/saveIndexs", method = RequestMethod.POST)
	public ResultMsg saveIndexs(String id1, String groupId1, String indexs1) throws Exception {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.productsService.saveIndexs(id1, groupId1, indexs1)) {
			result.setResultMessage("success");
		}
		return result;
	}
}
