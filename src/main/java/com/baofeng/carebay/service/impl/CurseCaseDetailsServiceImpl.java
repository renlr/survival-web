package com.baofeng.carebay.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.CurseCaseDAO;
import com.baofeng.carebay.dao.CurseCaseDetailsDAO;
import com.baofeng.carebay.entity.CurseCase;
import com.baofeng.carebay.entity.CurseCaseDetails;
import com.baofeng.carebay.service.ICurseCaseDetailsService;
import com.baofeng.carebay.util.Constants;
import com.baofeng.utils.PageResult;

@Service("curseCaseDetailsService")
public class CurseCaseDetailsServiceImpl implements ICurseCaseDetailsService {

	@Autowired
	private CurseCaseDAO curseCaseDAO;

	@Autowired
	private CurseCaseDetailsDAO curseCaseDetailsDAO;

	public CurseCaseDetailsDAO getCurseCaseDetailsDAO() {
		return curseCaseDetailsDAO;
	}

	public void setCurseCaseDetailsDAO(CurseCaseDetailsDAO curseCaseDetailsDAO) {
		this.curseCaseDetailsDAO = curseCaseDetailsDAO;
	}

	@Override
	public PageResult readAllPages(Integer pageSize, Integer currentPage, String groupid) {
		PageResult rows = this.curseCaseDetailsDAO.readAllPages(pageSize, currentPage, groupid);
		if (rows != null && rows.getRows().size() > 0) {
			List<CurseCaseDetails> list = new ArrayList<CurseCaseDetails>();
			for (Object o : rows.getRows()) {
				CurseCaseDetails details = (CurseCaseDetails) o;
				details.setImage(Constants.DEFAULT_HTTPIMAGES + "/" + Constants.sha1ToPath(details.getImageSha1()).replace(File.separator, "/") + "/" + details.getImage());
				list.add(details);
			}
			rows.setRows(list);
		}
		return rows;
	}

	@Override
	public boolean addCurseCaseDetails(CurseCaseDetails details, String groupId) {
		if (details != null && details.getId() != null && details.getId().trim().length() > 0) {
			CurseCaseDetails $details = this.readCurseCaseDetails(details.getId());
			if ($details != null) {
				$details.setName(details.getName());
				$details.setIndexs(details.getIndexs());
				if (details.getImage() != null && details.getImageSha1() != null && details.getImage().trim().length() > 0 && details.getImageSha1().trim().length() > 0) {
					$details.setImage(details.getImage());
					$details.setImageSha1(details.getImageSha1());
				}
				details = $details;
			}
		} else {
			CurseCase curse = this.curseCaseDAO.readCurseCase(groupId);
			if (curse != null) {
				details.setCurse(curse);
			}
			details.setOnline(Integer.valueOf(0));
		}
		return this.curseCaseDetailsDAO.saveCurseCaseDetails(details);
	}

	@Override
	public CurseCaseDetails readCurseCaseDetails(String id) {
		return this.curseCaseDetailsDAO.readCurseCaseDetails(id);
	}

	public CurseCaseDAO getCurseCaseDAO() {
		return curseCaseDAO;
	}

	public void setCurseCaseDAO(CurseCaseDAO curseCaseDAO) {
		this.curseCaseDAO = curseCaseDAO;
	}

	@Override
	public boolean onLineCurseCaseDetails(String id) {
		CurseCaseDetails details = this.readCurseCaseDetails(id);
		if (details != null) {
			if (details.getOnline() == null) {
				details.setOnline(Integer.valueOf(0));
			}
			if (details.getOnline().intValue() == 0) {
				details.setOnline(Integer.valueOf(1));
			} else {
				details.setOnline(Integer.valueOf(0));
			}
			this.curseCaseDetailsDAO.saveCurseCaseDetails(details);
			return true;
		}
		return false;
	}

	@Override
	public boolean delCurseCaseDetails(String id) {
		CurseCaseDetails details = this.readCurseCaseDetails(id);
		if (details != null) {
			details.setCurse(null);
			this.curseCaseDetailsDAO.saveCurseCaseDetails(details);
			this.curseCaseDetailsDAO.deleteCurseCaseDetails(details);
			return true;
		}
		return false;
	}

	@Override
	public boolean saveIndexs(String id1, String gid1, String indexs1) {
		CurseCaseDetails details = this.readCurseCaseDetails(id1);
		if (details != null) {
			details.setIndexs(Integer.valueOf(indexs1));
			this.curseCaseDetailsDAO.saveCurseCaseDetails(details);
			return true;
		}
		return false;
	}

	@Override
	public boolean addDetails(String id, String gid, String content, CurseCaseDetails details) {
		CurseCaseDetails $details = this.readCurseCaseDetails(id);
		if ($details != null) {
			$details.setContent(id + ".html");
			this.curseCaseDetailsDAO.saveCurseCaseDetails($details);
			return true;
		}
		return false;
	}

}
