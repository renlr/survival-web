package com.baofeng.carebay.service;

import java.util.List;

import com.baofeng.carebay.entity.MonthsMeal;
import com.baofeng.carebay.entity.MonthsMealCarousel;
import com.baofeng.carebay.entity.MonthsMealDetails;
import com.baofeng.carebay.entity.MonthsMealOrder;
import com.baofeng.carebay.entity.MonthsMealOrderDetails;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

public interface IMonthsMealService {

	boolean addMonthsMeal(MonthsMeal meal);

	MonthsMeal readMonthsMeal(String id);

	boolean addMonthsMealDetails(MonthsMealDetails details, String gid);

	MonthsMealDetails readMonthsMealDetails(String id);

	boolean deleteMonthsMealDetails(String id);

	boolean deleteMonthsMeal(String id);

	boolean addMonthsMealCarousel(MonthsMealCarousel carousel, String mealId);

	MonthsMealCarousel readMonthsMealCarousel(String id);

	boolean deleteMonthsMealCarousel(String id);

	boolean onlineMonthsMealCarousel(String id);

	boolean onlineMonthsMeal(String id);

	List<MonthsMealDetails> readMonthsMealDetailsList(String id);

	PageResult readAllPages(int rows, int page, SearchFilter filter);

	PageResult readAllPagesDetails(int rows, int page, String gid, SearchFilter filter);

	PageResult readAllPagesCarousel(int rows, int page, SearchFilter filter);

	boolean updateMonthsMeal(String id1, String indexs1);

	PageResult readAllPagesOrders(int rows, int page, SearchFilter filter, String orderNo, String userId, String tranStatus, String chambers, String beginDT, String endDT);

	List<MonthsMealOrderDetails> readOrdersDetails(String gid);

	PageResult readAllPagesSkip(int rows, int page, SearchFilter $filter, String filter);

	boolean saveMonthsMealOrders(String parentIds, String detailIds, String numbers);

	MonthsMealOrderDetails readMonthsMealOrdersDetails(String id);

	boolean updateMonthsMealOrderDetails(String id, String nums);

	boolean deleteOrderDetails(String id);

	boolean updateStatus(String id, String status);

	boolean saveOnLineTask(String ids, String onlineDT, String offlineDT);

	List<MonthsMealOrder> readMonthsMealsOrders(SearchFilter filter, String orderNo, String userId, String tranStatus, String chambers, String beginDT, String endDT);

}
