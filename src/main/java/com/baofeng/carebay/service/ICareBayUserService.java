package com.baofeng.carebay.service;

import com.baofeng.carebay.entity.CareBayUser;
import com.baofeng.commons.entity.Operator;
import com.baofeng.utils.IBaseService;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

public interface ICareBayUserService extends IBaseService {

	boolean addChamberUser(String id, String room, String name, String birthDate, String homeAddress, String phone, String emContact, String emPhone, String checkInDT,
			String checkOutDT, String chamber, Operator operator);

	CareBayUser readChamberUser(String id);

	boolean deleteChamberUser(String id);

	PageResult readChamberPages(int rows, int page, String worlds, SearchFilter filter);

	PageResult readChamberPages(int rows, int page, SearchFilter filter);

}
