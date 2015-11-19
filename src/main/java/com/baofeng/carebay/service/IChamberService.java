package com.baofeng.carebay.service;

import java.util.List;

import com.baofeng.carebay.entity.Chamber;
import com.baofeng.carebay.entity.ChamberDetails;
import com.baofeng.utils.IBaseService;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

public interface IChamberService extends IBaseService {

	boolean addChamber(String id, String name, String address, String telPhone, String manager,String leavePWD,String resetPWD);

	Chamber readChamber(String id);

	boolean deleteChamber(String id);

	boolean addChamberDetails(ChamberDetails details, String gid, String inds,String indexTerms);

	ChamberDetails readChamberDetails(String id);

	PageResult readAllPagesDetails(int rows, int page, String gid, String inds);

	boolean onLineChamberDetails(String id);

	boolean deleteChamberDetails(String id);

	List<Chamber> readChamberList(SearchFilter filter);

}
