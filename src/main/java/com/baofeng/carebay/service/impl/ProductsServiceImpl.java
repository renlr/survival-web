package com.baofeng.carebay.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.ProductsCategoryDAO;
import com.baofeng.carebay.dao.ProductsDAO;
import com.baofeng.carebay.entity.Chamber;
import com.baofeng.carebay.entity.Products;
import com.baofeng.carebay.entity.ProductsCategory;
import com.baofeng.carebay.entity.ProductsDetails;
import com.baofeng.carebay.entity.ProductsOrder;
import com.baofeng.carebay.entity.ProductsOrderDetails;
import com.baofeng.carebay.entity.ProductsParams;
import com.baofeng.carebay.service.IProductsService;
import com.baofeng.carebay.util.Constants;
import com.baofeng.commons.dao.ChamberDAO;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.commons.entity.Operator;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("productsService")
public class ProductsServiceImpl implements IProductsService {

	@Autowired
	private ChamberDAO chamberDAO;
	@Autowired
	private ProductsDAO productsDAO;
	@Autowired
	private ProductsCategoryDAO productsCategoryDAO;

	public ChamberDAO getChamberDAO() {
		return chamberDAO;
	}

	public void setChamberDAO(ChamberDAO chamberDAO) {
		this.chamberDAO = chamberDAO;
	}

	public ProductsDAO getProductsDAO() {
		return productsDAO;
	}

	public void setProductsDAO(ProductsDAO productsDAO) {
		this.productsDAO = productsDAO;
	}

	public ProductsCategoryDAO getProductsCategoryDAO() {
		return productsCategoryDAO;
	}

	public void setProductsCategoryDAO(ProductsCategoryDAO productsCategoryDAO) {
		this.productsCategoryDAO = productsCategoryDAO;
	}

	@Override
	public PageResult readAllPages(int rows, int page, String gid, SearchFilter filter) {
		PageResult $page = this.productsDAO.readAllPages(rows, page, gid, filter);
		if ($page != null && $page.getRows().size() > 0) {
			List<Products> list = new ArrayList<Products>();
			for (Object o : $page.getRows()) {
				Products products = (Products) o;
				ProductsDetails details = this.readProductsDetails(products.getId());
				if (details != null) {
					products.setImage(Constants.DEFAULT_HTTPIMAGES + "/" + Constants.sha1ToPath(details.getImageSha1()).replace(File.separator, "/") + "/" + details.getImage());
				}
				list.add(products);
			}
			$page.setRows(list);
		}
		return $page;
	}

	@Override
	public List<ProductsOrderDetails> readOrdersDetails(String gid) {
		List<ProductsOrderDetails> detailsList = this.productsDAO.readOrdersDetails(gid);
		List<ProductsOrderDetails> orderDetailsList = new ArrayList<ProductsOrderDetails>();
		if (detailsList != null && detailsList.size() > 0) {
			for (Iterator<ProductsOrderDetails> it = detailsList.iterator(); it.hasNext();) {
				ProductsOrderDetails orders = it.next();
				Products products = (Products) orders.getProducts();
				ProductsDetails details = this.readProductsDetails(products.getId());
				if (details != null) {
					products.setImage(Constants.DEFAULT_HTTPIMAGES + "/" + Constants.sha1ToPath(details.getImageSha1()).replace(File.separator, "/") + "/" + details.getImage());
				}
				orders.setProducts(products);
				orderDetailsList.add(orders);
			}
		}
		return orderDetailsList;
	}

	@Override
	public PageResult readAllPagesSkip(int rows, int page, SearchFilter filter, String queryFilter) {
		return this.productsDAO.readAllPagesSkip(rows, page, filter, queryFilter);
	}

	@Override
	public PageResult readPagesOrders(int rows, int page, SearchFilter filter, String orderNo, String userId, String tranStatus, String chambers, String beginDT, String endDT) {
		PageResult $page = this.productsDAO.readPagesOrders(rows, page, filter, orderNo, userId, tranStatus, chambers, beginDT, endDT);
		if ($page != null && $page.getRows().size() > 0) {
			List<ProductsOrder> list = new ArrayList<ProductsOrder>();
			for (Object o : $page.getRows()) {
				ProductsOrder details = (ProductsOrder) o;
				String cid = details.getUser().getChamber();
				Chamber chamber = this.chamberDAO.readChamber(cid);
				if (chamber != null)
					details.setChamber(chamber.getName());
				Float sumDetails = this.productsDAO.sumProductsOrdersDetails(details.getId());
				details.setTotalPrice(sumDetails.floatValue());
				list.add(details);
			}
			$page.setRows(list);
		}
		return $page;
	}

	@Override
	public ProductsDetails readProductsDetails(String gid) {
		if (gid != null && gid.trim().length() > 0) {
			return this.productsDAO.readProductsDetails(gid);
		}
		return null;
	}

	@Override
	public PageResult readAllPagesOnline(int rows, int page, Object object) {
		PageResult $page = this.productsDAO.readAllPagesOnLine(rows, page, object);
		if ($page != null && $page.getRows().size() > 0) {
			List<Products> list = new ArrayList<Products>();
			for (Object o : $page.getRows()) {
				Products products = (Products) o;
				ProductsDetails details = this.readProductsDetails(products.getId());
				if (details != null) {
					products.setImage(Constants.DEFAULT_HTTPIMAGES + "/" + Constants.sha1ToPath(details.getImageSha1()).replace(File.separator, "/") + "/" + details.getImage());
				}
				list.add(products);
			}
			$page.setRows(list);
		}
		return $page;
	}

	@Override
	public Products readProducts(String id) {
		return this.productsDAO.readProducts(id);
	}

	@Override
	public PageResult readPagesDetails(int rows, int page, String groupId) {
		PageResult $page = this.productsDAO.readPagesDetails(rows, page, groupId);
		if ($page != null && $page.getRows().size() > 0) {
			List<ProductsDetails> list = new ArrayList<ProductsDetails>();
			for (Object o : $page.getRows()) {
				ProductsDetails details = (ProductsDetails) o;
				details.setImage(Constants.DEFAULT_HTTPIMAGES + "/" + Constants.sha1ToPath(details.getImageSha1()).replace(File.separator, "/") + "/" + details.getImage());
				list.add(details);
			}
			$page.setRows(list);
		}
		return $page;
	}

	@Override
	public PageResult readPagesParams(int rows, int page, String groupId) {
		return this.productsDAO.readPagesParams(rows, page, groupId);
	}

	@Override
	public boolean addProductsDetails(ProductsDetails details, String groupId) {
		Products products = this.readProducts(groupId);
		details.setProducts(products);
		return this.productsDAO.saveProductsDetails(details);
	}

	@Override
	public List<Products> listProducts() {
		return this.productsDAO.listProducts();
	}

	@Override
	public boolean editFiles(String id, String field, String value) {
		return this.productsDAO.editFiles(id, field, value);
	}

	@Override
	public boolean editParamsFiles(String id, String field, String value) {
		return this.productsDAO.editParamsFiles(id, field, value);
	}

	@Override
	public boolean addProducts(Products products, String category, String gid) {
		category = gid;
		if (products != null && products.getId() != null && products.getId().trim().length() > 0) {
			Products $products = this.readProducts(products.getId());
			if ($products != null) {
				$products.setName(products.getName());
				if (products.getSubheading() != null)
					$products.setSubheading(products.getSubheading());
				if (products.getArticleNumber() != null)
					$products.setArticleNumber(products.getArticleNumber());
				if (products.getCostPrice() != null)
					$products.setCostPrice(products.getCostPrice());
				if (products.getBazaarPrice() != null)
					$products.setBazaarPrice(products.getBazaarPrice());
				if (products.getSellPrice() != null)
					$products.setSellPrice(products.getSellPrice());
				$products.setSalesvolume(Long.valueOf(0));
				if (products.getInventory() != null)
					$products.setInventory(products.getInventory());
				if (products.getQuotaNumber() != null)
					$products.setQuotaNumber(products.getQuotaNumber());
				if (products.getImageDetails() != null)
					$products.setImageDetails(products.getImageDetails());
				if (products.getIndexs() != null)
					$products.setIndexs(products.getIndexs());
				$products.setMadeDate(products.getMadeDate());
				$products.setExpirationDate(products.getExpirationDate());
				products = $products;
			}
		} else {
			products.setOnline(Integer.valueOf(0));
			ProductsCategory $category = this.productsCategoryDAO.readProductsCategory(category);
			products.setCategory($category);
		}
		return this.productsDAO.saveProducts(products);
	}

	@Override
	public boolean onLineProducts(String ids, Integer online) {
		if (ids != null && ids.trim().length() > 0) {
			for (String id : ids.split(",")) {
				if (id != null && id.trim().length() > 0) {
					Products products = this.readProducts(id);
					if (products != null) {
						if (products.getOnline() == null || products.getOnline().intValue() == Integer.valueOf(0).intValue())
							this.productsDAO.saveProductsHql("update Products set online = 1 where id = '" + id + "'");
						else
							this.productsDAO.saveProductsHql("update Products set online = 0 where id = '" + id + "'");
					}
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean addProductsContent(String id, String content) {
		Products products = this.productsDAO.readProducts(id);
		if (products != null) {
			products.setImageDetails(id + ".html");
			this.productsDAO.saveProducts(products);
			return true;
		}
		return false;
	}

	@Override
	public boolean onLineProductsParams(String id) {
		if (id != null && id.trim().length() > 0) {
			ProductsParams params = this.readProductsParams(id);
			if (params != null) {
				params.setProducts(null);
				this.productsDAO.saveProductsParams(params);
				this.productsDAO.deleteProductsParams(params);
				return true;
			}
		}
		return false;
	}

	private ProductsParams readProductsParams(String id) {
		return this.productsDAO.readProductsParams(id);
	}

	@Override
	public boolean addProductsParams(String groupId, String name1, String name2, String nameVal1, String nameVal2, String costPrice, String bazaarPrice, String sellPrice,
			String inventory) {
		Products products = this.productsDAO.readProducts(groupId);
		if (products != null) {
			String[] $auts1 = nameVal1.split(",");
			String[] $auts2 = nameVal2.split(",");
			String[] $costPrice = costPrice.split(",");
			String[] $bazaarPrice = bazaarPrice.split(",");
			String[] $sellPrice = sellPrice.split(",");
			String[] $inventory = inventory.split(",");
			if ($auts1 != null && $auts1.length > 0) {
				for (int i = 0; i < $auts1.length; i++) {
					if ($auts1[i] != null && $auts1[i].trim().length() > 0) {
						ProductsParams params = new ProductsParams();
						params.setName1(name1);
						params.setName2(name2);
						params.setAuts1($auts1[i]);
						params.setProducts(products);
						if ($auts2.length > 0 && $auts2[i] != null && $auts2[i].trim().length() > 0) {
							params.setAuts2($auts2[i]);
						}
						if ($costPrice.length > i && $costPrice[i] != null && $costPrice[i].trim().length() > 0) {
							params.setCostPrice(Float.valueOf($costPrice[i]));
						}
						if ($bazaarPrice.length > i && $bazaarPrice[i] != null && $bazaarPrice[i].trim().length() > 0) {
							params.setBazaarPrice(Float.valueOf($bazaarPrice[i]));
						}
						if ($sellPrice.length > i && $sellPrice[i] != null && $sellPrice[i].trim().length() > 0) {
							params.setSellPrice(Float.valueOf($sellPrice[i]));
						}
						if ($inventory.length > i && $inventory[i] != null && $inventory[i].trim().length() > 0) {
							params.setInventory(Long.valueOf($inventory[i]));
						}
						this.productsDAO.saveProductsParams(params);
					}
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteProductsImages(String id) {
		if (id != null && id.trim().length() > 0) {
			ProductsDetails details = this.readProductsImages(id);
			if (details != null) {
				details.setProducts(null);
				this.productsDAO.saveProductsDetails(details);
				this.productsDAO.deleteProductsImages(details);
				return true;
			}
		}
		return false;
	}

	@Override
	public ProductsDetails readProductsImages(String id) {
		return this.productsDAO.readProductsImages(id);
	}

	@Override
	public boolean saveIndexs(String id1, String groupId1, String indexs1) {
		ProductsDetails details = this.readProductsImages(id1);
		if (details != null) {
			details.setIndexs(Integer.valueOf(indexs1));
			this.productsDAO.saveProductsDetails(details);
			return true;
		}
		return false;
	}

	@Override
	public boolean updateProducts(String id1, String indexs1) {
		Products products = this.readProducts(id1);
		if (products != null) {
			products.setIndexs(Integer.valueOf(indexs1));
			this.productsDAO.saveProducts(products);
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteProducts(String ids) {
		if (ids != null && ids.trim().length() > 0) {
			for (String id : ids.split(",")) {
				Products products = this.readProducts(id);
				if (products != null) {
					this.productsDAO.saveProductsHql("update Products set status = 0 where id = '" + products.getId() + "'");
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * 功能：导入数据
	 * */
	@Override
	public boolean addImportProducts(HashMap<String, List<String>> xlsFile, HashMap<String, String> picFile, Operator operator) {
		try {
			if (xlsFile != null && xlsFile.size() > 0 && picFile != null && picFile.size() > 0) {
				for (Iterator<String> it = xlsFile.keySet().iterator(); it.hasNext();) {
					String key = it.next();
					List<String> valuesList = xlsFile.get(key);

					Products products = new Products();
					String catory = valuesList.get(0);
					ProductsCategory cat = this.productsCategoryDAO.readProductsCategoryByName(catory);
					if (cat != null) {
						products.setCategory(cat);
						products.setName(valuesList.get(1));
						products.setSellPrice(Float.valueOf(valuesList.get(2)));
						products.setBazaarPrice(Float.valueOf(valuesList.get(2)));
						products.setImageDetails(valuesList.get(3));
						products.setOperator(operator);
						products.setOnline(Integer.valueOf(0));
						products.setIndexs(Integer.valueOf(0));
						this.productsDAO.saveProducts(products);

						for (int i = 4; i < 100; i++) {
							if (i <= (valuesList.size() - 1)) {
								String keys = valuesList.get(i);
								ProductsDetails details = new ProductsDetails();
								details.setProducts(products);
								details.setOperator(operator);
								details.setIndexs(Integer.valueOf(i) - 4);
								String image = picFile.get(keys);
								details.setImage(image);
								details.setImageSha1(image.substring(0, image.lastIndexOf(".")));
								this.productsDAO.saveProductsDetails(details);
							} else {
								break;
							}
						}
					}
				}
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateDisCount(String disIds, String discount) {
		String[] ids = disIds.split(",");
		if (ids != null && ids.length > 0) {
			for (String id : ids) {
				Products products = this.readProducts(id);
				if (products != null) {
					Float sellPrice = (products.getBazaarPrice().floatValue() * Float.valueOf(discount).floatValue()) / 100;
					products.setSellPrice(sellPrice.floatValue());
					this.productsDAO.saveProducts(products);
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * 功能：修改状态
	 * */
	@Override
	public boolean updateStatus(String id, String status) {
		if (id != null && id.trim().length() > 0) {
			ProductsOrder orders = this.productsDAO.readProductsOrders(id);
			if (orders != null) {
				orders.setTranStatus(Integer.valueOf(status));
				return this.productsDAO.saveProductsOrders(orders);
			}
		}
		return false;
	}

	/**
	 * 功能：删除订单中的商品
	 * */
	@Override
	public boolean deleteOrderDetails(String id) {
		if (id != null && id.trim().length() > 0) {
			ProductsOrderDetails details = this.productsDAO.readProductsOrdersDetails(id);
			if (details != null) {
				details.setStatus(EntityStatus.DELETED);
				return this.productsDAO.saveProductsOrdersDetails(details);
			}
		}
		return false;
	}

	/**
	 * 功能：读取订单商品
	 * */
	@Override
	public ProductsOrderDetails readProductsOrderDetails(String id) {
		return this.productsDAO.readProductsOrdersDetails(id);
	}

	@Override
	public boolean updateProductsOrderDetails(String id, String nums) {
		if (id != null && id.trim().length() > 0) {
			ProductsOrderDetails details = this.readProductsOrderDetails(id);
			if (details != null) {
				details.setNumber(Integer.valueOf(nums));
				return this.productsDAO.saveProductsOrdersDetails(details);
			}
		}
		return false;
	}

	/**
	 * 功能：添加商品到订单
	 * */
	@Override
	public boolean saveProductsOrdersDetails(String parentIds, String detailIds, String numbers) {
		try {
			ProductsOrderDetails details = this.productsDAO.readProductsOrdersDetails(parentIds, detailIds);
			if (details == null) {
				details = new ProductsOrderDetails();
				ProductsOrder orders = this.productsDAO.readProductsOrders(parentIds);
				Products products = this.productsDAO.readProducts(detailIds);
				details.setOrders(orders);
				details.setProducts(products);
			} else {
				details.setStatus(EntityStatus.NORMAL);
				details.setCreateDT(new Date());
			}
			details.setNumber(Integer.valueOf(numbers));
			return this.productsDAO.saveProductsOrdersDetails(details);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 功能：定时任务
	 * */
	@Override
	public boolean saveOnLineTask(String ids, String onlineDT, String offlineDT) {
		try {
			if (ids != null && ids.trim().length() > 0) {
				String[] $ids = ids.split(",");
				for (String id : $ids) {
					if (id != null && id.trim().length() > 0) {
						Products products = this.productsDAO.readProducts(id);
						if (products != null) {
							String taskDt = String.valueOf("|");
							if (onlineDT != null && onlineDT.trim().length() > 0) {
								taskDt = onlineDT + taskDt;
							}
							if (offlineDT != null && offlineDT.trim().length() > 0) {
								taskDt = taskDt + offlineDT;
							}
							products.setTaskDT(taskDt);
							this.productsDAO.saveProducts(products);
						}
					}
				}
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * 功能：导出订单
	 * */
	@Override
	public List<ProductsOrder> readProductsOrders(SearchFilter filter, String orderNo, String userId, String tranStatus, String chambers, String beginDT, String endDT) {
		List<ProductsOrder> rows = this.productsDAO.readPagesOrders(filter, orderNo, userId, tranStatus, chambers, beginDT, endDT);
		List<ProductsOrder> list = new ArrayList<ProductsOrder>();
		for (Object o : rows) {
			ProductsOrder details = (ProductsOrder) o;
			String cid = details.getUser().getChamber();
			Chamber chamber = this.chamberDAO.readChamber(cid);
			if (chamber != null)
				details.setChamber(chamber.getName());
			Float sumDetails = this.productsDAO.sumProductsOrdersDetails(details.getId());
			details.setTotalPrice(sumDetails.floatValue());
			List<ProductsOrderDetails> detailsList = this.readOrdersDetails(details.getId());
			details.setDetailsList(new HashSet<ProductsOrderDetails>(detailsList == null ? new ArrayList<ProductsOrderDetails>() : detailsList));
			list.add(details);
		}
		return list;
	}

}
