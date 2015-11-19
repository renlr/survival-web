package com.baofeng.carebay.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.CurseCaseDAO;
import com.baofeng.carebay.dao.CustomServiceDAO;
import com.baofeng.carebay.entity.CurseCase;
import com.baofeng.carebay.entity.CustomService;
import com.baofeng.carebay.service.ICurseCaseService;
import com.baofeng.carebay.util.Constants;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("curseCaseService")
public class CurseCaseServiceImpl implements ICurseCaseService {

	@Autowired
	private CurseCaseDAO curseCaseDAO;

	@Autowired
	private CustomServiceDAO customServiceDAO;

	public CurseCaseDAO getCurseCaseDAO() {
		return curseCaseDAO;
	}

	public void setCurseCaseDAO(CurseCaseDAO curseCaseDAO) {
		this.curseCaseDAO = curseCaseDAO;
	}

	public CustomServiceDAO getCustomServiceDAO() {
		return customServiceDAO;
	}

	public void setCustomServiceDAO(CustomServiceDAO customServiceDAO) {
		this.customServiceDAO = customServiceDAO;
	}

	@Override
	public PageResult readAllPages(int pageSize, int page, String gid, SearchFilter filter) {
		PageResult rows = this.curseCaseDAO.readAllPages(pageSize, page, gid, filter);
		if (rows != null && rows.getRows().size() > 0) {
			List<CurseCase> list = new ArrayList<CurseCase>();
			for (Object o : rows.getRows()) {
				CurseCase curse = (CurseCase) o;
				curse.setImage(Constants.DEFAULT_HTTPIMAGES + "/" + Constants.sha1ToPath(curse.getImageSha1()).replace(File.separator, "/") + "/" + curse.getImage());
				list.add(curse);
			}
			rows.setRows(list);
		}
		return rows;
	}

	@Override
	public PageResult readPagesSkip(int rows, int page, SearchFilter filter) {
		return this.curseCaseDAO.readPagesSkip(rows, page, filter);
	}

	@Override
	public boolean addCurseCase(CurseCase curse, String gid) {
		if (curse != null && curse.getId() != null && curse.getId().trim().length() > 0) {
			CurseCase $curse = this.readCurseCase(curse.getId());
			if ($curse != null) {
				$curse.setName(curse.getName());
				$curse.setIndexs(curse.getIndexs());
				if (curse.getImage() != null && curse.getImageSha1() != null && curse.getImage().trim().length() > 0 && curse.getImageSha1().trim().length() > 0) {
					$curse.setImage(curse.getImage());
					$curse.setImageSha1(curse.getImageSha1());
				}
				curse = $curse;
			}
		} else {
			CustomService custom = this.customServiceDAO.readCustomService(gid);
			if (custom != null)
				curse.setCustom(custom);
			curse.setOnline(Integer.valueOf(0));
		}
		return this.curseCaseDAO.saveCurseCase(curse);
	}

	@Override
	public CurseCase readCurseCase(String id) {
		return this.curseCaseDAO.readCurseCase(id);
	}

	@Override
	public boolean delCurseCase(String id) {
		return this.curseCaseDAO.delCurseCase(id);
	}

	@Override
	public boolean onLineCurseCase(String id) {
		CurseCase curse = this.readCurseCase(id);
		if (curse != null) {
			if (curse.getOnline().intValue() == 0) {
				this.curseCaseDAO.saveCurseCase("update CurseCase set online=1 where id = '" + id + "'");
			} else {
				this.curseCaseDAO.saveCurseCase("update CurseCase set online=0 where id = '" + id + "'");
			}
			return true;
		}
		return false;
	}

}
