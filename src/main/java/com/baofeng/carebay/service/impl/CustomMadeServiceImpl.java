package com.baofeng.carebay.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.CustomMadeDAO;
import com.baofeng.carebay.entity.Chamber;
import com.baofeng.carebay.entity.CustomMade;
import com.baofeng.carebay.entity.CustomMadeDetails;
import com.baofeng.carebay.service.ICustomMadeService;
import com.baofeng.carebay.util.Constants;
import com.baofeng.commons.dao.ChamberDAO;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("customMadeService")
public class CustomMadeServiceImpl implements ICustomMadeService {

	@Autowired
	private ChamberDAO chamberDAO;
	@Autowired
	private CustomMadeDAO customMadeDAO;

	public CustomMadeDAO getCustomMadeDAO() {
		return customMadeDAO;
	}

	public void setCustomMadeDAO(CustomMadeDAO customMadeDAO) {
		this.customMadeDAO = customMadeDAO;
	}

	@Override
	public PageResult readAllPages(int rows, int page, SearchFilter filter) {
		PageResult $rows = this.customMadeDAO.readAllPages(rows, page, filter);
		if ($rows != null && $rows.getRows().size() > 0) {
			List<CustomMade> list = new ArrayList<CustomMade>();
			for (Object o : $rows.getRows()) {
				CustomMade details = (CustomMade) o;
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
	public PageResult readPagesSkip(int rows, int page, SearchFilter filter) {
		return this.customMadeDAO.readPagesSkip(rows, page, filter);
	}

	@Override
	public PageResult readAllPagesDetails(int rows, int page, String gid, SearchFilter filter) {
		PageResult $rows = this.customMadeDAO.readAllPagesDetails(rows, page, gid, filter);
		if ($rows != null && $rows.getRows().size() > 0) {
			List<CustomMadeDetails> list = new ArrayList<CustomMadeDetails>();
			for (Object o : $rows.getRows()) {
				CustomMadeDetails details = (CustomMadeDetails) o;
				details.setImage(Constants.DEFAULT_HTTPIMAGES + "/" + Constants.sha1ToPath(details.getImageSha1()).replace(File.separator, "/") + "/" + details.getImage());
				list.add(details);
			}
			$rows.setRows(list);
		}
		return $rows;
	}

	@Override
	public boolean addCustomMade(CustomMade custom) {
		if (custom.getId() != null && custom.getId().trim().length() > 0) {
			CustomMade $custom = this.readCustomMade(custom.getId());
			if ($custom != null) {
				if (custom.getName() != null && custom.getName().trim().length() > 0)
					$custom.setName(custom.getName());
				if (custom.getIndexs() != null)
					$custom.setIndexs(custom.getIndexs());
				if (custom.getImage() != null && custom.getImageSha1() != null && custom.getImage().trim().length() > 0 && custom.getImageSha1().trim().length() > 0) {
					$custom.setImage(custom.getImage());
					$custom.setImageSha1(custom.getImageSha1());
				}
				custom = $custom;
			}
		} else {
			custom.setOnline(Integer.valueOf(0));
		}
		return this.customMadeDAO.saveCustomMade(custom);
	}

	@Override
	public boolean addCustomMadeDetails(CustomMadeDetails details, String gid) {
		CustomMade custom = this.readCustomMade(gid);
		if (details.getId() != null && details.getId().trim().length() > 0) {
			CustomMadeDetails $details = this.readCustomMadeDetails(details.getId());
			if ($details != null) {
				$details.setName(details.getName());
				$details.setContent(details.getContent());
				$details.setIndexs(details.getIndexs());
				if (details.getImage() != null && details.getImageSha1() != null && details.getImage().trim().length() > 0 && details.getImageSha1().trim().length() > 0) {
					$details.setImage(details.getImage());
					$details.setImageSha1(details.getImageSha1());
				}
				details = $details;
			}
		} else {
			details.setCustom(custom);
			details.setOnline(Integer.valueOf(0));
		}
		return this.customMadeDAO.saveCustomMadeDetails(details);
	}

	@Override
	public CustomMadeDetails readCustomMadeDetails(String id) {
		if (id != null && id.trim().length() > 0) {
			return this.customMadeDAO.readCustomMadeDetails(id);
		}
		return null;
	}

	@Override
	public CustomMade readCustomMade(String id) {
		if (id != null && id.trim().length() > 0) {
			return this.customMadeDAO.readCustomMade(id);
		}
		return null;
	}

	@Override
	public boolean deleteCustomMade(String id) {
		if (id != null && id.trim().length() > 0) {
			CustomMade custom = this.readCustomMade(id);
			if (custom != null) {
				this.customMadeDAO.saveCustomMadeHql("update CustomMade set status = 0 where id = '" + id + "'");
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onLineCustomMade(String id) {
		if (id != null && id.trim().length() > 0) {
			CustomMade made = this.readCustomMade(id);
			if (made != null && made.getImageSha1() != null && made.getImageSha1().trim().length() > 0) {
				List<CustomMadeDetails> detailsList = this.customMadeDAO.readCustomDetailsList(id);
				if (made.getOnline() == null || made.getOnline().intValue() == Integer.valueOf(0).intValue() && detailsList.size() > 0) {
					this.customMadeDAO.saveCustomMadeHql("update CustomMade set online = 1 where id = '" + made.getId() + "'");
				} else {
					this.customMadeDAO.saveCustomMadeHql("update CustomMade set online = 0 where id = '" + made.getId() + "'");
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onLineCustomMadeDetails(String id) {
		CustomMadeDetails details = this.readCustomMadeDetails(id);
		if (details != null && details.getImageSha1() != null && details.getImageSha1().trim().length() > 0) {
			if (details.getOnline() == null || details.getOnline().intValue() == 0) {
				this.customMadeDAO.saveCustomMadeHql("update CustomMadeDetails set online = 1 where id = '" + id + "'");
			} else {
				this.customMadeDAO.saveCustomMadeHql("update CustomMadeDetails set online = 0 where id = '" + id + "'");
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteCustomMadeDetails(String id) {
		if (id != null && id.trim().length() > 0) {
			CustomMadeDetails details = this.readCustomMadeDetails(id);
			if (details != null) {
				this.customMadeDAO.saveCustomMadeDetailsHql("update CustomMadeDetails set status = 0 where id = '" + id + "'");
			}
		}
		return false;
	}

	public ChamberDAO getChamberDAO() {
		return chamberDAO;
	}

	public void setChamberDAO(ChamberDAO chamberDAO) {
		this.chamberDAO = chamberDAO;
	}
}
