package com.baofeng.carebay.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.CurseCaseDetailsDAO;
import com.baofeng.carebay.dao.ReservationDAO;
import com.baofeng.carebay.dao.SlimmingDetailsDAO;
import com.baofeng.carebay.entity.CurseCaseDetails;
import com.baofeng.carebay.entity.Reservation;
import com.baofeng.carebay.entity.SlimmingDetails;
import com.baofeng.carebay.service.IReservationService;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("reservationService")
public class ReservationServiceImpl implements IReservationService {

	@Autowired
	private ReservationDAO reservationDAO;
	@Autowired
	private CurseCaseDetailsDAO curseCaseDetailsDAO;
	@Autowired
	private SlimmingDetailsDAO slimmingDetailsDAO;

	public ReservationDAO getReservationDAO() {
		return reservationDAO;
	}

	public void setReservationDAO(ReservationDAO reservationDAO) {
		this.reservationDAO = reservationDAO;
	}

	public SlimmingDetailsDAO getSlimmingDetailsDAO() {
		return slimmingDetailsDAO;
	}

	public void setSlimmingDetailsDAO(SlimmingDetailsDAO slimmingDetailsDAO) {
		this.slimmingDetailsDAO = slimmingDetailsDAO;
	}

	public CurseCaseDetailsDAO getCurseCaseDetailsDAO() {
		return curseCaseDetailsDAO;
	}

	public void setCurseCaseDetailsDAO(CurseCaseDetailsDAO curseCaseDetailsDAO) {
		this.curseCaseDetailsDAO = curseCaseDetailsDAO;
	}

	@Override
	public PageResult readAllPages(Integer pageSize, Integer currentPage, SearchFilter filter) {
		PageResult rows = this.reservationDAO.readAllPages(pageSize, currentPage, filter);
		if (rows != null && rows.getRows().size() > 0) {
			List<Reservation> list = new ArrayList<Reservation>();
			for (Object o : rows.getRows()) {
				Reservation details = (Reservation) o;
				if (details.getType().intValue() == Integer.valueOf(0).intValue()) {
					CurseCaseDetails $dateils = this.curseCaseDetailsDAO.readCurseCaseDetails(details.getParams());
					if ($dateils != null)
						details.setParamsName($dateils.getName());
				} else if (details.getType().intValue() == Integer.valueOf(1).intValue()) {
					SlimmingDetails $details = this.slimmingDetailsDAO.readSlimmingDetails(details.getParams());
					if ($details != null)
						details.setParamsName($details.getName());
				}
				list.add(details);
			}
			rows.setRows(list);
		}
		return rows;
	}
}
