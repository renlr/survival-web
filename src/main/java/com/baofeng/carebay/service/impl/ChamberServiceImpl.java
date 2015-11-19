package com.baofeng.carebay.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.entity.Chamber;
import com.baofeng.carebay.entity.ChamberDetails;
import com.baofeng.carebay.service.IChamberService;
import com.baofeng.carebay.util.Constants;
import com.baofeng.commons.dao.ChamberDAO;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("chamberService")
public class ChamberServiceImpl implements IChamberService {

	@Autowired
	private ChamberDAO chamberDAO;

	public void setChamberDAO(ChamberDAO chamberDAO) {
		this.chamberDAO = chamberDAO;
	}

	public ChamberDAO getChamberDAO() {
		return chamberDAO;
	}

	@Override
	public PageResult readAllPages(Integer pageSize, Integer currentPage, SearchFilter filter) {
		return this.chamberDAO.readAllPages(pageSize, currentPage, filter);
	}

	@Override
	public PageResult readAllPagesDetails(int rows, int page, String gid, String inds) {
		PageResult $rows = this.chamberDAO.readAllPages(rows, page, gid, inds);
		if ($rows != null && $rows.getRows().size() > 0) {
			List<ChamberDetails> list = new ArrayList<ChamberDetails>();
			for (Object o : $rows.getRows()) {
				ChamberDetails details = (ChamberDetails) o;
				details.setImage(Constants.DEFAULT_HTTPIMAGES + "/" + Constants.sha1ToPath(details.getImageSha1()).replace(File.separator, "/") + "/" + details.getImage());
				list.add(details);
			}
			$rows.setRows(list);
		}
		return $rows;
	}

	@Override
	public boolean addChamber(String id, String name, String address, String telPhone, String manager, String leavePWD, String resetPWD) {
		Chamber chamber = new Chamber();
		if (id != null && id.trim().length() > 0) {
			Chamber $chamber = this.readChamber(id);
			if ($chamber != null) {
				$chamber.setName(name);
				$chamber.setTelPhone(telPhone);
				$chamber.setManager(manager);
				$chamber.setAddress(address);
				$chamber.setLeavePWD(leavePWD);
				$chamber.setResetPWD(resetPWD);
				chamber = $chamber;
			}
		} else {
			chamber.setName(name);
			chamber.setTelPhone(telPhone);
			chamber.setManager(manager);
			chamber.setAddress(address);
			chamber.setLeavePWD(leavePWD);
			chamber.setResetPWD(resetPWD);
		}
		return this.chamberDAO.saveChamber(chamber);
	}

	@Override
	public boolean addChamberDetails(ChamberDetails details, String gid, String inds, String indexTerms) {
		if (details != null && details.getId() != null && details.getId().trim().length() > 0) {
			ChamberDetails $details = this.readChamberDetails(details.getId());
			if ($details != null) {
				$details.setIndexTerms(indexTerms);
				$details.setIndexs(details.getIndexs());
				if (details.getImage() != null && details.getImageSha1() != null && details.getImage().trim().length() > 0 && details.getImageSha1().trim().length() > 0) {
					$details.setImage(details.getImage());
					$details.setImageSha1(details.getImageSha1());
				}
				details = $details;
			}
		} else {
			details.setIndexTerms(indexTerms);
			details.setOnline(Integer.valueOf(0));
			details.setType(Integer.valueOf(inds));
			Chamber chamber = this.readChamber(gid);
			details.setChamber(chamber);

		}
		return this.chamberDAO.saveChamberDetails(details);
	}

	@Override
	public ChamberDetails readChamberDetails(String id) {
		if (id != null && id.trim().length() > 0) {
			return this.chamberDAO.readChamberDetails(id);
		}
		return null;
	}

	@Override
	public Chamber readChamber(String id) {
		return this.chamberDAO.readChamber(id);
	}

	@Override
	public boolean deleteChamber(String id) {
		if (id != null && id.trim().length() > 0) {
			Chamber chamber = this.readChamber(id);
			if (chamber != null) {
				this.chamberDAO.deleteChamber(chamber);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onLineChamberDetails(String id) {
		if (id != null && id.trim().length() > 0) {
			ChamberDetails details = this.readChamberDetails(id);
			if (details != null) {
				if (details.getOnline() == null || details.getOnline().intValue() == Integer.valueOf(0).intValue()) {
					this.chamberDAO.saveChamberDetailsHql("update ChamberDetails set online = 1 where id = '" + details.getId() + "'");
				} else {
					this.chamberDAO.saveChamberDetailsHql("update ChamberDetails set online = 0 where id = '" + details.getId() + "'");
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean deleteChamberDetails(String id) {
		if (id != null && id.trim().length() > 0) {
			ChamberDetails details = this.readChamberDetails(id);
			if (details != null) {
				details.setChamber(null);
				this.chamberDAO.delChamberDetails(details);
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Chamber> readChamberList(SearchFilter filter) {
		return this.chamberDAO.readChamberList(filter);
	}

}
