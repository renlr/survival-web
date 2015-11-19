package com.baofeng.carebay.dao;

import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baofeng.carebay.entity.Products;
import com.baofeng.carebay.entity.ProductsDetails;
import com.baofeng.carebay.entity.ProductsOrder;
import com.baofeng.carebay.entity.ProductsOrderDetails;
import com.baofeng.carebay.entity.ProductsParams;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.DetachedCriteriaUtil;
import com.baofeng.utils.IBaseDAO;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Repository("productsDAO")
public class ProductsDAO {

	@Autowired
	private IBaseDAO baseDAO;

	public IBaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public Products readProducts(String id) {
		if (id != null && id.trim().length() > 0) {
			Products products = (Products) this.baseDAO.get(Products.class, id);
			if (products != null)
				return products;
		}
		return null;
	}

	public boolean saveProducts(Products products) {
		try {
			if (products != null && products.getId() != null && products.getId().length() > 0)
				this.baseDAO.mrege(products);
			else {
				this.baseDAO.save(products);
			}
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public boolean saveProductsParams(ProductsParams params) {
		try {
			if (params != null && params.getId() != null && params.getId().length() > 0)
				this.baseDAO.mrege(params);
			else
				this.baseDAO.save(params);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public PageResult readAllPages(Integer pageSize, Integer currentPage, String gid, SearchFilter filter) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Products.class);
			detachedCriteria.add(Restrictions.eq("category.id", gid));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("online"));
			detachedCriteria.addOrder(Order.desc("createDT"));
			DetachedCriteriaUtil.addSearchFilter(detachedCriteria, filter);
			return baseDAO.findByPages(detachedCriteria, pageSize, currentPage);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public PageResult readAllPagesSkip(int rows, int page, SearchFilter filter, String queryFilter) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Products.class);
			if (queryFilter != null && queryFilter.trim().length() > 0) {
				detachedCriteria.add(Restrictions.like("name", "%" + queryFilter + "%"));
			}
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("online"));
			detachedCriteria.addOrder(Order.desc("createDT"));
			DetachedCriteriaUtil.addSearchFilter(detachedCriteria, filter);
			return baseDAO.findByPages(detachedCriteria, rows, page);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public PageResult readPagesOrders(int rows, int page, SearchFilter filter, String orderNo, String userId, String tranStatus, String chambers, String beginDT, String endDT) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ProductsOrder.class);
			if (orderNo != null && orderNo.trim().length() > 0) {
				detachedCriteria.add(Restrictions.eq("orderNo", orderNo));
			}
			if (userId != null && userId.trim().length() > 0) {
				detachedCriteria.createAlias("user", "user").add(Restrictions.eq("user.id", userId));
			}
			if (chambers != null && chambers.trim().length() > 0) {
				detachedCriteria.createAlias("user", "user").add(Restrictions.eq("user.chamber", chambers));
			}
			if (tranStatus != null && !tranStatus.equals("-1")) {
				detachedCriteria.add(Restrictions.eq("tranStatus", Integer.valueOf(tranStatus)));
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (beginDT != null && beginDT.trim().length() > 0) {
				detachedCriteria.add(Restrictions.ge("createDT", format.parse(beginDT + ":00")));
			}
			if (endDT != null && endDT.trim().length() > 0) {
				detachedCriteria.add(Restrictions.le("createDT", format.parse(endDT + ":59")));
			}
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("createDT"));
			DetachedCriteriaUtil.addSearchFilter(detachedCriteria, filter);
			return baseDAO.findByPages(detachedCriteria, rows, page);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public PageResult readAllPagesOnLine(int rows, int page, Object object) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Products.class);
			detachedCriteria.add(Restrictions.eq("online", Integer.valueOf(1)));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("createDT"));
			return baseDAO.findByPages(detachedCriteria, rows, page);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public PageResult readPagesDetails(int rows, int page, String groupId) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ProductsDetails.class);
			detachedCriteria.createAlias("products", "products").add(Restrictions.eq("products.id", groupId));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("createDT"));
			return baseDAO.findByPages(detachedCriteria, rows, page);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public ProductsDetails readProductsDetails(String gid) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ProductsDetails.class);
			detachedCriteria.createAlias("products", "products").add(Restrictions.eq("products.id", gid));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.asc("indexs"));
			PageResult page = this.baseDAO.findByPages(detachedCriteria, Integer.valueOf(1), Integer.valueOf(1));
			if (page.getRows() != null && page.getRows().size() > 0) {
				ProductsDetails details = (ProductsDetails) page.getRows().get(0);
				return details;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return null;
	}

	public PageResult readPagesParams(int rows, int page, String groupId) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ProductsParams.class);
			detachedCriteria.createAlias("products", "products").add(Restrictions.eq("products.id", groupId));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.asc("name1"));
			detachedCriteria.addOrder(Order.asc("auts1"));
			detachedCriteria.addOrder(Order.desc("createDT"));
			return baseDAO.findByPages(detachedCriteria, rows, page);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public boolean saveProductsDetails(ProductsDetails details) {
		try {
			if (details != null && details.getId() != null && details.getId().length() > 0)
				this.baseDAO.mrege(details);
			else
				this.baseDAO.save(details);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public List<Products> listProducts() {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Products.class);
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("createDT"));
			List<Products> list = this.baseDAO.findAllByCriteria(detachedCriteria);
			if (list != null && list.size() > 0) {
				return list;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return null;
	}

	public boolean editFiles(String id, String field, String value) {
		String hql = "update Products set " + field + "='" + value + "' where id='" + id + "'";
		int count = this.baseDAO.execute(hql);
		if (count > 0)
			return true;
		return false;
	}

	public boolean editParamsFiles(String id, String field, String value) {
		String hql = "update ProductsParams set " + field + "='" + value + "' where id='" + id + "'";
		int count = this.baseDAO.execute(hql);
		if (count > 0)
			return true;
		return false;
	}

	public ProductsParams readProductsParams(String id) {
		if (id != null && id.trim().length() > 0) {
			ProductsParams params = (ProductsParams) this.baseDAO.get(ProductsParams.class, id);
			return params;
		}
		return null;
	}

	public void deleteProductsParams(ProductsParams params) {
		this.baseDAO.delete(params);
	}

	public ProductsDetails readProductsImages(String id) {
		if (id != null && id.trim().length() > 0) {
			ProductsDetails details = (ProductsDetails) this.baseDAO.get(ProductsDetails.class, id);
			return details;
		}
		return null;
	}

	public void deleteProductsImages(ProductsDetails details) {
		this.baseDAO.delete(details);
	}

	public void saveProductsHql(String hql) {
		this.baseDAO.execute(hql);
	}

	public List<Products> readProductsList(String id) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Products.class);
			detachedCriteria.add(Restrictions.eq("category.id", id));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("online"));
			detachedCriteria.addOrder(Order.desc("createDT"));
			return baseDAO.findAllByCriteria(detachedCriteria);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 功能：读取图片裁剪
	 * */
	public List<ProductsDetails> readProductsDetails() {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ProductsDetails.class);
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.add(Restrictions.or(Restrictions.eq("tailor", Integer.valueOf(0)), Restrictions.isNull("tailor")));
			List<ProductsDetails> detailsList = (List<ProductsDetails>) this.baseDAO.QueryDetachedCriteria(detachedCriteria);
			if (detailsList != null && detailsList.size() > 0) {
				return detailsList;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * 功能：修改图片裁剪状态
	 * */
	public void updateProductsDetails(String id, Integer tailor) {
		try {
			String hql = "update ProductsDetails set tailor=" + tailor.intValue() + " where id = '" + id + "'";
			this.baseDAO.executeOpenSession(hql);
		} catch (Exception ex) {
		}
	}

	/**
	 * 功能：订单详细
	 * */
	public List<ProductsOrderDetails> readOrdersDetails(String gid) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ProductsOrderDetails.class);
			detachedCriteria.createAlias("orders", "orders").add(Restrictions.eq("orders.id", gid));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("createDT"));
			List<ProductsOrderDetails> detailsList = (List<ProductsOrderDetails>) this.baseDAO.QueryDetachedCriteria(detachedCriteria);
			if (detailsList != null && detailsList.size() > 0) {
				return detailsList;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * 功能：读取订单
	 * */
	public ProductsOrder readProductsOrders(String id) {
		if (id != null && id.trim().length() > 0) {
			ProductsOrder orders = (ProductsOrder) this.baseDAO.get(ProductsOrder.class, id);
			if (orders != null)
				return orders;
		}
		return null;
	}

	/**
	 * 功能：保存高订单
	 * */
	public boolean saveProductsOrders(ProductsOrder orders) {
		try {
			if (orders != null && orders.getId() != null && orders.getId().length() > 0)
				this.baseDAO.mrege(orders);
			else
				this.baseDAO.save(orders);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public ProductsOrderDetails readProductsOrdersDetails(String id) {
		if (id != null && id.trim().length() > 0) {
			ProductsOrderDetails details = (ProductsOrderDetails) this.baseDAO.get(ProductsOrderDetails.class, id);
			if (details != null)
				return details;
		}
		return null;
	}

	public boolean saveProductsOrdersDetails(ProductsOrderDetails details) {
		try {
			if (details != null && details.getId() != null && details.getId().length() > 0)
				this.baseDAO.mrege(details);
			else
				this.baseDAO.save(details);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * 功能：查询是否已存在
	 * */
	public ProductsOrderDetails readProductsOrdersDetails(String parentIds, String detailIds) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ProductsOrderDetails.class);
			detachedCriteria.createAlias("orders", "orders").add(Restrictions.eq("orders.id", parentIds));
			detachedCriteria.createAlias("products", "products").add(Restrictions.eq("products.id", detailIds));
			List<ProductsOrderDetails> detailsList = (List<ProductsOrderDetails>) this.baseDAO.QueryDetachedCriteria(detachedCriteria);
			if (detailsList != null && detailsList.size() > 0) {
				return detailsList.get(0);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * 功能：订单总计
	 * */
	public Float sumProductsOrdersDetails(String id) {
		Float sumDetails = Float.valueOf(0);
		try {
			String hql = "select (products.sellPrice * number) as totalPrice from ProductsOrderDetails where orders.id = '" + id + "' and status = "
					+ EntityStatus.NORMAL.ordinal();
			List<Object> quList = this.baseDAO.executeQuery(hql);
			if (quList != null && quList.size() > 0) {
				for (Object sum : quList) {
					if (sum != null) {
						Float ids = (Float) sum;
						sumDetails += ids.floatValue();
					}
				}
			}
			return sumDetails;
		} catch (Exception e) {
			e.printStackTrace();
			return Float.valueOf(0);
		}
	}

	public List<ProductsOrder> readPagesOrders(SearchFilter filter, String orderNo, String userId, String tranStatus, String chambers, String beginDT, String endDT) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ProductsOrder.class);
			if (orderNo != null && orderNo.trim().length() > 0) {
				detachedCriteria.add(Restrictions.eq("orderNo", orderNo));
			}
			if (userId != null && userId.trim().length() > 0) {
				detachedCriteria.createAlias("user", "user").add(Restrictions.eq("user.id", userId));
			}
			if (chambers != null && chambers.trim().length() > 0) {
				detachedCriteria.createAlias("user", "user").add(Restrictions.eq("user.chamber", chambers));
			}
			if (tranStatus != null && !tranStatus.equals("-1")) {
				detachedCriteria.add(Restrictions.eq("tranStatus", Integer.valueOf(tranStatus)));
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (beginDT != null && beginDT.trim().length() > 0) {
				detachedCriteria.add(Restrictions.ge("createDT", format.parse(beginDT + ":00")));
			}
			if (endDT != null && endDT.trim().length() > 0) {
				detachedCriteria.add(Restrictions.le("createDT", format.parse(endDT + ":59")));
			}
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("createDT"));
			DetachedCriteriaUtil.addSearchFilter(detachedCriteria, filter);
			List<ProductsOrder> ordersList = this.baseDAO.findAllByCriteria(detachedCriteria);
			return ordersList;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
