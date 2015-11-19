package com.baofeng.carebay.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.MonthsMealDAO;
import com.baofeng.carebay.entity.Chamber;
import com.baofeng.carebay.entity.MonthsMeal;
import com.baofeng.carebay.entity.MonthsMealCarousel;
import com.baofeng.carebay.entity.MonthsMealDetails;
import com.baofeng.carebay.entity.MonthsMealOrder;
import com.baofeng.carebay.entity.MonthsMealOrderDetails;
import com.baofeng.carebay.service.IMonthsMealService;
import com.baofeng.carebay.util.Constants;
import com.baofeng.commons.dao.ChamberDAO;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("monthsMealService")
public class MonthsMealServiceImpl implements IMonthsMealService {

	@Autowired
	private ChamberDAO chamberDAO;
	@Autowired
	private MonthsMealDAO monthsMealDAO;

	public ChamberDAO getChamberDAO() {
		return chamberDAO;
	}

	public void setChamberDAO(ChamberDAO chamberDAO) {
		this.chamberDAO = chamberDAO;
	}

	public MonthsMealDAO getMonthsMealDAO() {
		return monthsMealDAO;
	}

	public void setMonthsMealDAO(MonthsMealDAO monthsMealDAO) {
		this.monthsMealDAO = monthsMealDAO;
	}

	@Override
	public PageResult readAllPages(int rows, int page, SearchFilter filter) {
		PageResult $rows = this.monthsMealDAO.readAllPages(rows, page, filter);
		if ($rows != null && $rows.getRows().size() > 0) {
			List<MonthsMeal> list = new ArrayList<MonthsMeal>();
			for (Object o : $rows.getRows()) {
				MonthsMeal meal = (MonthsMeal) o;
				String cid = meal.getOperator().getChamber();
				Chamber chamber = this.chamberDAO.readChamber(cid);
				if (chamber != null)
					meal.setChamber(chamber.getName());
				List<MonthsMealDetails> detailsList = this.readMonthsMealDetailsList(meal.getId());
				if (detailsList != null && detailsList.size() > 0) {
					MonthsMealDetails details = detailsList.get(0);
					meal.setImage(Constants.DEFAULT_HTTPIMAGES + "/" + Constants.sha1ToPath(details.getImageSha1()).replace(File.separator, "/") + "/" + details.getImage());
				}
				list.add(meal);
			}
			$rows.setRows(list);
		}
		return $rows;
	}

	@Override
	public List<MonthsMealDetails> readMonthsMealDetailsList(String id) {
		if (id != null && id.trim().length() > 0) {
			return this.monthsMealDAO.readDetailsList(id);
		}
		return null;
	}

	@Override
	public PageResult readAllPagesDetails(int rows, int page, String gid, SearchFilter filter) {
		PageResult $rows = this.monthsMealDAO.readAllPagesDetails(rows, page, gid, filter);
		if ($rows != null && $rows.getRows().size() > 0) {
			List<MonthsMealDetails> list = new ArrayList<MonthsMealDetails>();
			for (Object o : $rows.getRows()) {
				MonthsMealDetails details = (MonthsMealDetails) o;
				details.setImage(Constants.DEFAULT_HTTPIMAGES + "/" + Constants.sha1ToPath(details.getImageSha1()).replace(File.separator, "/") + "/" + details.getImage());
				list.add(details);
			}
			$rows.setRows(list);
		}
		return $rows;
	}

	@Override
	public PageResult readAllPagesCarousel(int rows, int page, SearchFilter filter) {
		PageResult $rows = this.monthsMealDAO.readAllPagesCarousel(rows, page, filter);
		if ($rows != null && $rows.getRows().size() > 0) {
			List<MonthsMealCarousel> list = new ArrayList<MonthsMealCarousel>();
			for (Object o : $rows.getRows()) {
				MonthsMealCarousel details = (MonthsMealCarousel) o;
				String cid = details.getOperator().getChamber();
				Chamber chamber = this.chamberDAO.readChamber(cid);
				if (chamber != null)
					details.setChamber(chamber.getName());
				details.setImage(Constants.DEFAULT_HTTPIMAGES + "/" + Constants.sha1ToPath(details.getImageSha1()).replace(File.separator, "/") + "/" + details.getImage());
				list.add(details);
			}
			$rows.setRows(list);
		}
		return $rows;
	}

	@Override
	public PageResult readAllPagesOrders(int rows, int page, SearchFilter filter, String orderNo, String userId, String tranStatus, String chambers, String beginDT, String endDT) {
		PageResult $page = this.monthsMealDAO.readAllPagesOrders(rows, page, filter, orderNo, userId, tranStatus, chambers, beginDT, endDT);
		if ($page != null && $page.getRows().size() > 0) {
			List<MonthsMealOrder> list = new ArrayList<MonthsMealOrder>();
			for (Object o : $page.getRows()) {
				MonthsMealOrder details = (MonthsMealOrder) o;
				String cid = details.getUser().getChamber();
				Chamber chamber = this.chamberDAO.readChamber(cid);
				if (chamber != null)
					details.setChamber(chamber.getName());
				Float sumDetails = this.monthsMealDAO.sumMonthsMealOrdersDetails(details.getId());
				details.setTotalPrice(sumDetails.floatValue());
				list.add(details);
			}
			$page.setRows(list);
		}
		return $page;
	}

	@Override
	public List<MonthsMealOrderDetails> readOrdersDetails(String gid) {
		List<MonthsMealOrderDetails> detailsList = this.monthsMealDAO.readOrdersDetails(gid);
		return detailsList;
	}

	@Override
	public PageResult readAllPagesSkip(int rows, int page, SearchFilter $filter, String Qfilter) {
		return this.monthsMealDAO.readAllPagesSkip(rows, page, $filter, Qfilter);
	}

	@Override
	public boolean addMonthsMeal(MonthsMeal meal) {
		if (meal.getId() != null && meal.getId().trim().length() > 0) {
			MonthsMeal $meal = this.readMonthsMeal(meal.getId());
			if ($meal != null) {
				$meal.setName(meal.getName());
				$meal.setAuthor(meal.getAuthor());
				$meal.setProductionMethods(meal.getProductionMethods());
				$meal.setFlavor(meal.getFlavor());
				$meal.setHeat(meal.getHeat());
				$meal.setDescribes(meal.getDescribes());
				$meal.setMethodDescribes(meal.getMethodDescribes());
				$meal.setPrice(meal.getPrice());
				$meal.setIndexs(meal.getIndexs());
				meal = $meal;
			}
		} else {
			meal.setOnline(Integer.valueOf(0));
		}
		return this.monthsMealDAO.saveMonthsMeal(meal);
	}

	@Override
	public MonthsMeal readMonthsMeal(String id) {
		if (id != null && id.trim().length() > 0) {
			return this.monthsMealDAO.readMonthsMeal(id);
		}
		return null;
	}

	@Override
	public boolean addMonthsMealDetails(MonthsMealDetails details, String gid) {
		if (details.getId() != null && details.getId().trim().length() > 0) {
			MonthsMealDetails $details = this.readMonthsMealDetails(details.getId());
			if ($details != null) {
				if (details.getImage() != null && details.getImageSha1() != null && details.getImageSha1().trim().length() > 0) {
					$details.setImage(details.getImage());
					$details.setImageSha1(details.getImageSha1());
				}
				$details.setIndexs(details.getIndexs());
				details = $details;
			}
		} else {
			MonthsMeal $meal = this.readMonthsMeal(gid);
			if ($meal != null) {
				details.setMeal($meal);
			}
		}
		return this.monthsMealDAO.saveMonthsMealDetails(details);
	}

	@Override
	public boolean addMonthsMealCarousel(MonthsMealCarousel carousel, String mealId) {
		if (carousel.getId() != null && carousel.getId().trim().length() > 0) {
			MonthsMealCarousel $carousel = this.readMonthsMealCarousel(carousel.getId());
			if ($carousel != null) {
				if (carousel.getImage() != null && carousel.getImageSha1() != null && carousel.getImageSha1().trim().length() > 0) {
					$carousel.setImage(carousel.getImage());
					$carousel.setImageSha1(carousel.getImageSha1());
				}
				if (!$carousel.getMeal().getId().equals(mealId)) {
					MonthsMeal meal = this.readMonthsMeal(mealId);
					$carousel.setMeal(meal);
				}
				$carousel.setIndexs(carousel.getIndexs());
				carousel = $carousel;
			}
		} else {
			MonthsMeal meal = this.readMonthsMeal(mealId);
			carousel.setMeal(meal);
			carousel.setOnline(Integer.valueOf(0));
		}
		return this.monthsMealDAO.saveMonthsMealCarousel(carousel);
	}

	@Override
	public MonthsMealCarousel readMonthsMealCarousel(String id) {
		if (id != null && id.trim().length() > 0) {
			return this.monthsMealDAO.readMonthsMealCarousel(id);
		}
		return null;
	}

	@Override
	public MonthsMealDetails readMonthsMealDetails(String id) {
		if (id != null && id.trim().length() > 0) {
			return this.monthsMealDAO.readMonthsMealDetails(id);
		}
		return null;
	}

	@Override
	public boolean deleteMonthsMealDetails(String id) {
		if (id != null && id.trim().length() > 0) {
			MonthsMealDetails details = this.readMonthsMealDetails(id);
			if (details != null) {
				this.monthsMealDAO.saveMonthsMealDetailsHql("update MonthsMealDetails set status = 0 where id = '" + id + "'");
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean deleteMonthsMeal(String id) {
		if (id != null && id.trim().length() > 0) {
			MonthsMeal meal = this.readMonthsMeal(id);
			List<MonthsMealDetails> detailsList = this.monthsMealDAO.readDetailsList(id);
			if (meal != null && detailsList.size() == 0) {
				this.monthsMealDAO.saveMonthsMealHql("update MonthsMeal set status = 0 where id = '" + id + "'");
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean deleteMonthsMealCarousel(String id) {
		if (id != null && id.trim().length() > 0) {
			MonthsMealCarousel carousel = this.readMonthsMealCarousel(id);
			if (carousel != null) {
				carousel.setMeal(null);
				this.monthsMealDAO.saveMonthsMealCarousel(carousel);
				this.monthsMealDAO.deleteMonthsMealCarousel(carousel);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onlineMonthsMealCarousel(String id) {
		if (id != null && id.trim().length() > 0) {
			MonthsMealCarousel carousel = this.readMonthsMealCarousel(id);
			if (carousel != null) {
				if (carousel.getOnline() == null || carousel.getOnline().intValue() == Integer.valueOf(0).intValue()) {
					this.monthsMealDAO.saveMonthsMealHql("update MonthsMealCarousel set online = 1 where id = '" + carousel.getId() + "'");
				} else {
					this.monthsMealDAO.saveMonthsMealHql("update MonthsMealCarousel set online = 0 where id = '" + carousel.getId() + "'");
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onlineMonthsMeal(String id) {
		if (id != null && id.trim().length() > 0) {
			MonthsMeal meal = this.readMonthsMeal(id);
			if (meal != null) {
				if (meal.getOnline() == null || meal.getOnline().intValue() == Integer.valueOf(0).intValue()) {
					this.monthsMealDAO.saveMonthsMealHql("update MonthsMeal set online = 1 where id = '" + meal.getId() + "'");
				} else {
					this.monthsMealDAO.saveMonthsMealHql("update MonthsMeal set online = 0 where id = '" + meal.getId() + "'");
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean updateMonthsMeal(String id1, String indexs1) {
		if (id1 != null && id1.trim().length() > 0) {
			MonthsMeal meal = this.readMonthsMeal(id1);
			if (meal != null) {
				meal.setIndexs(Integer.valueOf(indexs1));
				this.monthsMealDAO.saveMonthsMeal(meal);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean saveMonthsMealOrders(String parentIds, String detailIds, String numbers) {
		try {
			MonthsMealOrderDetails details = this.monthsMealDAO.readMonthsMealOrdersDetails(parentIds, detailIds);
			if (details == null) {
				details = new MonthsMealOrderDetails();
				MonthsMealOrder orders = this.monthsMealDAO.readMonthsMealOrders(parentIds);
				MonthsMeal meal = this.monthsMealDAO.readMonthsMeal(detailIds);
				details.setOrders(orders);
				details.setMeal(meal);
			} else {
				details.setStatus(EntityStatus.NORMAL);
				details.setCreateDT(new Date());
			}
			if (details.getNumber() != null)
				details.setNumber(details.getNumber() + Integer.valueOf(numbers));
			else
				details.setNumber(Integer.valueOf(numbers));
			return this.monthsMealDAO.saveMonthsMealOrdersDetails(details);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public MonthsMealOrderDetails readMonthsMealOrdersDetails(String id) {
		return this.monthsMealDAO.readMonthsMealOrdersDetails(id);
	}

	@Override
	public boolean updateMonthsMealOrderDetails(String id, String nums) {
		if (id != null && id.trim().length() > 0) {
			MonthsMealOrderDetails details = this.readMonthsMealOrdersDetails(id);
			if (details != null) {
				details.setNumber(Integer.valueOf(nums));
				return this.monthsMealDAO.saveMonthsMealOrdersDetails(details);
			}
		}
		return false;
	}

	@Override
	public boolean deleteOrderDetails(String id) {
		if (id != null && id.trim().length() > 0) {
			MonthsMealOrderDetails details = this.monthsMealDAO.readMonthsMealOrdersDetails(id);
			if (details != null) {
				details.setStatus(EntityStatus.DELETED);
				return this.monthsMealDAO.saveMonthsMealOrdersDetails(details);
			}
		}
		return false;
	}

	@Override
	public boolean updateStatus(String id, String status) {
		if (id != null && id.trim().length() > 0) {
			MonthsMealOrder orders = this.monthsMealDAO.readMonthsMealOrders(id);
			if (orders != null) {
				orders.setTranStatus(Integer.valueOf(status));
				return this.monthsMealDAO.saveMonthsMealOrders(orders);
			}
		}
		return false;
	}

	@Override
	public boolean saveOnLineTask(String ids, String onlineDT, String offlineDT) {
		try {
			if (ids != null && ids.trim().length() > 0) {
				String[] $ids = ids.split(",");
				for (String id : $ids) {
					if (id != null && id.trim().length() > 0) {
						MonthsMeal meal = this.monthsMealDAO.readMonthsMeal(id);
						if (meal != null) {
							String taskDt = String.valueOf("|");
							if (onlineDT != null && onlineDT.trim().length() > 0) {
								taskDt = onlineDT + taskDt;
							}
							if (offlineDT != null && offlineDT.trim().length() > 0) {
								taskDt = taskDt + offlineDT;
							}
							meal.setTaskDT(taskDt);
							this.monthsMealDAO.saveMonthsMeal(meal);
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

	@Override
	public List<MonthsMealOrder> readMonthsMealsOrders(SearchFilter filter, String orderNo, String userId, String tranStatus, String chambers, String beginDT, String endDT) {
		List<MonthsMealOrder> rows = this.monthsMealDAO.readMonthsMealsOrders(filter, orderNo, userId, tranStatus, chambers, beginDT, endDT);
		List<MonthsMealOrder> list = new ArrayList<MonthsMealOrder>();
		for (Object o : rows) {
			MonthsMealOrder details = (MonthsMealOrder) o;
			String cid = details.getUser().getChamber();
			Chamber chamber = this.chamberDAO.readChamber(cid);
			if (chamber != null)
				details.setChamber(chamber.getName());
			Float sumDetails = this.monthsMealDAO.sumMonthsMealOrdersDetails(details.getId());
			details.setTotalPrice(sumDetails.floatValue());
			List<MonthsMealOrderDetails> detailsList = this.readOrdersDetails(details.getId());
			details.setDetailsList(detailsList == null ? new ArrayList<MonthsMealOrderDetails>() : detailsList);
			list.add(details);
		}
		return list;
	}
}
