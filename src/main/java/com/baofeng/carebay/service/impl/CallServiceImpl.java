package com.baofeng.carebay.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.CallServiceDAO;
import com.baofeng.carebay.dao.MonthsMealDAO;
import com.baofeng.carebay.entity.ButlerService;
import com.baofeng.carebay.entity.Chamber;
import com.baofeng.carebay.entity.MonthsMealOrder;
import com.baofeng.carebay.service.ICallService;
import com.baofeng.commons.dao.ChamberDAO;
import com.baofeng.commons.entity.Operator;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("callService")
public class CallServiceImpl implements ICallService {

	@Autowired
	private ChamberDAO chamberDAO;
	@Autowired
	private MonthsMealDAO monthsMealDAO;
	@Autowired
	private CallServiceDAO callServiceDAO;

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

	public CallServiceDAO getCallServiceDAO() {
		return callServiceDAO;
	}

	public void setCallServiceDAO(CallServiceDAO callServiceDAO) {
		this.callServiceDAO = callServiceDAO;
	}

	@Override
	public PageResult readAllPages(Integer pageSize, Integer currentPage, SearchFilter filter) {
		PageResult $rows = this.callServiceDAO.readAllPages(pageSize, currentPage, filter);
		if ($rows != null && $rows.getRows().size() > 0) {
			List<ButlerService> list = new ArrayList<ButlerService>();
			for (Object o : $rows.getRows()) {
				ButlerService details = (ButlerService) o;
				String cid = details.getUser().getChamber();
				Chamber chamber = this.chamberDAO.readChamber(cid);
				if (chamber != null)
					details.setChamber(chamber.getName());
				list.add(details);
			}
			$rows.setRows(list);
		}
		return $rows;
	}

	@Override
	public ButlerService readButlerService(Operator operator) {
		List<ButlerService> butList = this.callServiceDAO.readButlerService(operator);
		if (butList != null && butList.size() > 0) {
			for (ButlerService butler : butList) {
				if (butler != null && butler.getUser() != null && butler.getUser().getChamber() != null) {
					String $uChamber = butler.getUser().getChamber();
					String $oChamber = operator.getChamber();
					if ($uChamber.equals($oChamber)) {
						return butler;
					}
				}
			}
		}
		return null;
	}

	@Override
	public boolean serviceing(String id, Operator operator) {
		if (id != null && id.trim().length() > 0) {
			ButlerService butler = this.readButlerService(id);
			if (butler != null && butler.getService().intValue() != Integer.valueOf(1).intValue()) {
				this.callServiceDAO.saveButlerServiceHql("update ButlerService set service = 1,operator.id = " + operator.getId() + ", servDT=now() where id = '" + butler.getId()
						+ "'");
				return true;
			}
		}
		return false;
	}

	@Override
	public ButlerService readButlerService(String id) {
		if (id != null && id.trim().length() > 0) {
			ButlerService butler = this.callServiceDAO.readButlerService(id);
			return butler;
		}
		return null;
	}

	@Override
	public MonthsMealOrder readServiceMonthsMealOrders(Operator user) {
		List<MonthsMealOrder> butList = this.callServiceDAO.readServiceMonthsMealOrders(user);
		if (butList != null && butList.size() > 0) {
			for (MonthsMealOrder butler : butList) {
				if (butler != null && butler.getUser() != null && butler.getUser().getChamber() != null) {
					String $uChamber = butler.getUser().getChamber();
					String $oChamber = user.getChamber();
					if ($uChamber.equals($oChamber)) {
						return butler;
					}
				}
			}
		}
		return null;
	}

	@Override
	public boolean updateServiceMeal(String id, Operator user) {
		if (id != null && id.trim().length() > 0) {
			MonthsMealOrder orders = this.monthsMealDAO.readMonthsMealOrders(id);
			if (orders != null && orders.getService().intValue() != Integer.valueOf(1).intValue()) {
				orders.setService(Integer.valueOf(1).intValue());
				return this.monthsMealDAO.saveMonthsMealOrders(orders);
			}
		}
		return false;
	}
}
