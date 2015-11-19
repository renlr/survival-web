package com.baofeng.carebay.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.CurseCaseDAO;
import com.baofeng.carebay.dao.CurseToSDAO;
import com.baofeng.carebay.dao.CustomServiceDAO;
import com.baofeng.carebay.entity.CurseCase;
import com.baofeng.carebay.entity.CurseToS;
import com.baofeng.carebay.entity.CustomService;
import com.baofeng.carebay.service.ICustomService;
import com.baofeng.carebay.util.Constants;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("customService")
public class CustomServiceImpl implements ICustomService {

	@Autowired
	private CurseToSDAO curseToSDAO;
	@Autowired
	private CurseCaseDAO curseCaseDAO;
	@Autowired
	private CustomServiceDAO customServiceDAO;

	public CurseToSDAO getCurseToSDAO() {
		return curseToSDAO;
	}

	public void setCurseToSDAO(CurseToSDAO curseToSDAO) {
		this.curseToSDAO = curseToSDAO;
	}

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
	public PageResult readAllPages(int rows, int page, String gid, SearchFilter filter) {
		PageResult $rows = this.customServiceDAO.readAllPages(rows, page, gid, filter);
		if ($rows != null && $rows.getRows().size() > 0) {
			List<CustomService> list = new ArrayList<CustomService>();
			for (Object o : $rows.getRows()) {
				CustomService details = (CustomService) o;
				details.setImage(Constants.DEFAULT_HTTPIMAGES + "/" + Constants.sha1ToPath(details.getImageSha1()).replace(File.separator, "/") + "/" + details.getImage());
				list.add(details);
			}
			$rows.setRows(list);
		}
		return $rows;
	}

	@Override
	public boolean addCustomService(CustomService custom, String gid) {
		if (custom.getId() != null && custom.getId().trim().length() > 0) {
			CustomService $custom = this.readCustomService(custom.getId());
			if ($custom != null) {
				$custom.setName(custom.getName());
				$custom.setIndexs(custom.getIndexs());
				if (custom.getImage() != null && custom.getImageSha1() != null && custom.getImage().trim().length() > 0 && custom.getImageSha1().trim().length() > 0) {
					$custom.setImage(custom.getImage());
					$custom.setImageSha1(custom.getImageSha1());
				}
				custom = $custom;
			}
		} else {
			CurseToS tos = this.curseToSDAO.readCurseToS(gid);
			custom.setTos(tos);
			custom.setOnline(Integer.valueOf(0));
		}
		return this.customServiceDAO.saveCustomService(custom);
	}

	@Override
	public CustomService readCustomService(String id) {
		if (id != null && id.trim().length() > 0) {
			return this.customServiceDAO.readCustomService(id);
		}
		return null;
	}

	@Override
	public boolean deleteCustomService(String id) {
		if (id != null && id.trim().length() > 0) {
			CustomService custom = this.readCustomService(id);
			if (custom != null) {
				List<CurseCase> curseList = this.curseCaseDAO.readCurseList(id);
				if (curseList != null && curseList.size() == 0) {
					custom.setTos(null);
					this.customServiceDAO.saveCustomService(custom);
					this.customServiceDAO.deleteCustomService(custom);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean onLineCustomService(String id) {
		if (id != null && id.trim().length() > 0) {
			CustomService custom = this.readCustomService(id);
			if (custom != null) {
				List<CurseCase> curseList = this.curseCaseDAO.readCurseList(id);
				if (custom.getOnline() == null || custom.getOnline().intValue() == Integer.valueOf(0).intValue() && curseList.size() > 0) {
					this.customServiceDAO.saveCustomServiceHql("update CustomService set online = 1 where id = '" + custom.getId() + "'");
				} else {
					this.customServiceDAO.saveCustomServiceHql("update CustomService set online = 0 where id = '" + custom.getId() + "'");
				}
				return true;
			}
		}
		return false;
	}

}
