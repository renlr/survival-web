package com.baofeng.carebay.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.CareBayUserDAO;
import com.baofeng.carebay.entity.CareBayUser;
import com.baofeng.carebay.entity.Chamber;
import com.baofeng.carebay.service.ICareBayUserService;
import com.baofeng.commons.dao.ChamberDAO;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.commons.entity.Operator;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("careBayUserService")
public class CareBayUserServiceImpl implements ICareBayUserService {

	@Autowired
	private ChamberDAO chamberDAO;

	@Autowired
	private CareBayUserDAO careBayUserDAO;
	

	public CareBayUserDAO getCareBayUserDAO() {
		return careBayUserDAO;
	}

	public void setCareBayUserDAO(CareBayUserDAO careBayUserDAO) {
		this.careBayUserDAO = careBayUserDAO;
	}

	public ChamberDAO getChamberDAO() {
		return chamberDAO;
	}

	public void setChamberDAO(ChamberDAO chamberDAO) {
		this.chamberDAO = chamberDAO;
	}

	@Override
	public PageResult readAllPages(Integer pageSize, Integer currentPage, SearchFilter filter) {
		return this.careBayUserDAO.readAllPages(pageSize, currentPage, filter);
	}

	@Override
	public boolean addChamberUser(String id, String room, String name, String birthDate, String homeAddress, String phone, String emContact, String emPhone, String checkInDT,
			String checkOutDT, String chamber, Operator operator) {
		try {
			CareBayUser careUser = new CareBayUser();
			careUser.setId(id);
			careUser.setRoom(room);
			careUser.setName(name);
			if (birthDate != null)
				careUser.setBirthDate(DateUtils.parseDate(birthDate, new String[] { "yyyy-MM-dd" }));
			careUser.setHomeAddress(homeAddress);
			careUser.setPhone(phone);
			careUser.setEmContact(emContact);
			careUser.setEmPhone(emPhone);
			if (checkInDT != null && checkInDT.trim().length() > 0)
				careUser.setCheckInDT(DateUtils.parseDate(checkInDT, new String[] { "yyyy-MM-dd HH:mm" }));
			else
				careUser.setCheckInDT(new Date());
			if (checkOutDT != null && checkOutDT.trim().length() > 0)
				careUser.setCheckOutDT(DateUtils.parseDate(checkOutDT, new String[] { "yyyy-MM-dd HH:mm" }));
			careUser.setAccounts(phone);
			careUser.setAccountsType(Integer.valueOf(1));
			careUser.setVip(Integer.valueOf(0));
			careUser.setOpenfire(Integer.valueOf(0));
			careUser.setInvitation(Integer.valueOf(0));
			careUser.setInquiry(Integer.valueOf(0));
			careUser.setOperator(operator);
			careUser.setChamber(chamber);
			if (operator.getChamber() != null)
				careUser.setChamber(operator.getChamber());

			if (id != null && id.trim().length() > 0) {
				CareBayUser $careUser = this.readChamberUser(id);
				if ($careUser != null) {
					$careUser.setRoom(careUser.getRoom());
					$careUser.setName(careUser.getName());
					$careUser.setBirthDate(careUser.getBirthDate());
					$careUser.setHomeAddress(careUser.getHomeAddress());
					$careUser.setPhone(careUser.getPhone());
					$careUser.setEmContact(careUser.getEmContact());
					$careUser.setEmPhone(careUser.getEmPhone());
					$careUser.setCheckInDT(careUser.getCheckInDT());
					$careUser.setCheckOutDT(careUser.getCheckOutDT());
					$careUser.setChamber(careUser.getChamber());
					careUser = $careUser;
				}
			}
			return this.careBayUserDAO.saveChamberUser(careUser);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public CareBayUser readChamberUser(String id) {
		if (id != null && id.trim().length() > 0)
			return this.careBayUserDAO.readChamberUser(id);
		return null;
	}

	@Override
	public PageResult readChamberPages(int rows, int page, SearchFilter filter) {
		PageResult $rows = this.careBayUserDAO.readChamberPages(rows, page, filter);
		if ($rows != null && $rows.getRows().size() > 0) {
			List<CareBayUser> list = new ArrayList<CareBayUser>();
			for (Object o : $rows.getRows()) {
				CareBayUser details = (CareBayUser) o;
				String chamberId = details.getChamber();
				Chamber chamber = this.chamberDAO.readChamber(chamberId);
				if(chamber != null)
					details.setChamber(chamber.getName());
				list.add(details);
			}
			$rows.setRows(list);
		}
		return $rows;
	}

	@Override
	public PageResult readChamberPages(int rows, int page, String worlds, SearchFilter filter) {
		return this.careBayUserDAO.readChamberPages(rows, page, worlds, filter);
	}

	@Override
	public boolean deleteChamberUser(String id) {
		CareBayUser chamberUser = this.readChamberUser(id);
		if (chamberUser != null) {
			chamberUser.setStatus(EntityStatus.DELETED);
			this.careBayUserDAO.saveChamberUser(chamberUser);
			return true;
		}
		return false;
	}

}
