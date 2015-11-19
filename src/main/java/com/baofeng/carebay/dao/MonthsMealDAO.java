package com.baofeng.carebay.dao;

import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baofeng.carebay.entity.MonthsMeal;
import com.baofeng.carebay.entity.MonthsMealCarousel;
import com.baofeng.carebay.entity.MonthsMealDetails;
import com.baofeng.carebay.entity.MonthsMealOrder;
import com.baofeng.carebay.entity.MonthsMealOrderDetails;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.DetachedCriteriaUtil;
import com.baofeng.utils.IBaseDAO;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Repository("monthsMealDAO")
public class MonthsMealDAO {

	@Autowired
	private IBaseDAO baseDAO;

	public IBaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public PageResult readAllPages(int rows, int page, SearchFilter filter) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MonthsMeal.class);
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

	public PageResult readAllPagesDetails(int rows, int page, String gid, SearchFilter filter) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MonthsMealDetails.class);
			detachedCriteria.createAlias("meal", "meal").add(Restrictions.eq("meal.id", gid));
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

	public PageResult readAllPagesCarousel(int rows, int page, SearchFilter filter) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MonthsMealCarousel.class);
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("online"));
			detachedCriteria.addOrder(Order.desc("indexs"));
			detachedCriteria.addOrder(Order.desc("createDT"));
			DetachedCriteriaUtil.addSearchFilter(detachedCriteria, filter);
			return baseDAO.findByPages(detachedCriteria, rows, page);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public PageResult readAllPagesOrders(int rows, int page, SearchFilter filter, String orderNo, String userId, String tranStatus, String chambers, String beginDT, String endDT) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MonthsMealOrder.class);
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

	public boolean saveMonthsMeal(MonthsMeal meal) {
		try {
			if (meal != null && meal.getId() != null && meal.getId().length() > 0)
				this.baseDAO.mrege(meal);
			else
				this.baseDAO.save(meal);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public MonthsMeal readMonthsMeal(String id) {
		if (id != null && id.trim().length() > 0) {
			MonthsMeal meal = (MonthsMeal) this.baseDAO.get(MonthsMeal.class, id);
			if (meal != null)
				return meal;
		}
		return null;
	}

	public boolean saveMonthsMealDetails(MonthsMealDetails details) {
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

	public MonthsMealDetails readMonthsMealDetails(String id) {
		if (id != null && id.trim().length() > 0) {
			MonthsMealDetails details = (MonthsMealDetails) this.baseDAO.get(MonthsMealDetails.class, id);
			if (details != null)
				return details;
		}
		return null;
	}

	public void deleteMonthsMealDetails(MonthsMealDetails details) {
		if (details != null)
			this.baseDAO.delete(details);
	}

	public void deleteMonthsMeal(MonthsMeal meal) {
		if (meal != null)
			this.baseDAO.delete(meal);
	}

	public List<MonthsMealDetails> readDetailsList(String id) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MonthsMealDetails.class);
			detachedCriteria.createAlias("meal", "meal").add(Restrictions.eq("meal.id", id));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			return this.baseDAO.findAllByCriteria(detachedCriteria);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public void saveMonthsMealDetailsHql(String hql) {
		if (hql != null)
			this.baseDAO.execute(hql);
	}

	public void saveMonthsMealHql(String hql) {
		if (hql != null)
			this.baseDAO.execute(hql);

	}

	public boolean saveMonthsMealCarousel(MonthsMealCarousel carousel) {
		try {
			if (carousel != null && carousel.getId() != null && carousel.getId().length() > 0)
				this.baseDAO.mrege(carousel);
			else
				this.baseDAO.save(carousel);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public MonthsMealCarousel readMonthsMealCarousel(String id) {
		if (id != null && id.trim().length() > 0) {
			MonthsMealCarousel carousel = (MonthsMealCarousel) this.baseDAO.get(MonthsMealCarousel.class, id);
			if (carousel != null)
				return carousel;
		}
		return null;
	}

	public void deleteMonthsMealCarousel(MonthsMealCarousel carousel) {
		if (carousel != null)
			this.baseDAO.delete(carousel);
	}

	/**
	 * 功能：读取图片裁剪
	 * */
	public List<MonthsMealDetails> readMonthsMealDetails() {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MonthsMealDetails.class);
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.add(Restrictions.or(Restrictions.eq("tailor", Integer.valueOf(0)), Restrictions.isNull("tailor")));
			List<MonthsMealDetails> detailsList = (List<MonthsMealDetails>) this.baseDAO.QueryDetachedCriteria(detachedCriteria);
			if (detailsList != null && detailsList.size() > 0) {
				return detailsList;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return null;
	}

	public void updateMonthsMealDetails(String id, Integer tailor) {
		try {
			String hql = "update MonthsMealDetails set tailor=" + tailor.intValue() + " where id = '" + id + "'";
			this.baseDAO.executeOpenSession(hql);
		} catch (Exception ex) {
		}
	}

	public List<MonthsMealOrderDetails> readOrdersDetails(String gid) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MonthsMealOrderDetails.class);
			detachedCriteria.createAlias("orders", "orders").add(Restrictions.eq("orders.id", gid));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("createDT"));
			List<MonthsMealOrderDetails> detailsList = (List<MonthsMealOrderDetails>) this.baseDAO.QueryDetachedCriteria(detachedCriteria);
			if (detailsList != null && detailsList.size() > 0) {
				return detailsList;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return null;
	}

	public PageResult readAllPagesSkip(int rows, int page, SearchFilter $filter, String qfilter) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MonthsMeal.class);
			if (qfilter != null && qfilter.trim().length() > 0) {
				detachedCriteria.add(Restrictions.like("name", "%" + qfilter + "%"));
			}
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("online"));
			detachedCriteria.addOrder(Order.desc("createDT"));
			DetachedCriteriaUtil.addSearchFilter(detachedCriteria, $filter);
			return baseDAO.findByPages(detachedCriteria, rows, page);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public MonthsMealOrderDetails readMonthsMealOrdersDetails(String parentIds, String detailIds) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MonthsMealOrderDetails.class);
			detachedCriteria.createAlias("orders", "orders").add(Restrictions.eq("orders.id", parentIds));
			detachedCriteria.createAlias("meal", "meal").add(Restrictions.eq("meal.id", detailIds));
			List<MonthsMealOrderDetails> detailsList = (List<MonthsMealOrderDetails>) this.baseDAO.QueryDetachedCriteria(detachedCriteria);
			if (detailsList != null && detailsList.size() > 0) {
				return detailsList.get(0);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return null;
	}

	public MonthsMealOrder readMonthsMealOrders(String parentIds) {
		if (parentIds != null && parentIds.trim().length() > 0) {
			MonthsMealOrder orders = (MonthsMealOrder) this.baseDAO.get(MonthsMealOrder.class, parentIds);
			if (orders != null) {
				return orders;
			}
		}
		return null;
	}

	public boolean saveMonthsMealOrdersDetails(MonthsMealOrderDetails details) {
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

	public MonthsMealOrderDetails readMonthsMealOrdersDetails(String id) {
		if (id != null && id.trim().length() > 0) {
			MonthsMealOrderDetails details = (MonthsMealOrderDetails) this.getBaseDAO().get(MonthsMealOrderDetails.class, id);
			return details;
		}
		return null;
	}

	public boolean saveMonthsMealOrders(MonthsMealOrder orders) {
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

	public Float sumMonthsMealOrdersDetails(String id) {
		Float sumDetails = Float.valueOf(0);
		try {
			String hql = "select (meal.price * number) as totalPrice from MonthsMealOrderDetails where orders.id = '" + id + "' and status = " + EntityStatus.NORMAL.ordinal();
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

	public List<MonthsMealOrder> readMonthsMealsOrders(SearchFilter filter, String orderNo, String userId, String tranStatus, String chambers, String beginDT, String endDT) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MonthsMealOrder.class);
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
			return baseDAO.findAllByCriteria(detachedCriteria);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
