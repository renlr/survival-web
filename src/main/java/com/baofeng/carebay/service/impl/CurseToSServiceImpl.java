package com.baofeng.carebay.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.CurseToSDAO;
import com.baofeng.carebay.dao.CustomServiceDAO;
import com.baofeng.carebay.entity.Chamber;
import com.baofeng.carebay.entity.CurseToS;
import com.baofeng.carebay.entity.CustomService;
import com.baofeng.carebay.service.ICurseToSService;
import com.baofeng.carebay.util.Constants;
import com.baofeng.commons.dao.ChamberDAO;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("curseToSService")
public class CurseToSServiceImpl implements ICurseToSService {

	@Autowired
	private ChamberDAO chamberDAO;
	@Autowired
	private CurseToSDAO curseToSDAO;

	@Autowired
	private CustomServiceDAO customServiceDAO;

	public CurseToSDAO getCurseToSDAO() {
		return curseToSDAO;
	}

	public void setCurseToSDAO(CurseToSDAO curseToSDAO) {
		this.curseToSDAO = curseToSDAO;
	}

	public CustomServiceDAO getCustomServiceDAO() {
		return customServiceDAO;
	}

	public void setCustomServiceDAO(CustomServiceDAO customServiceDAO) {
		this.customServiceDAO = customServiceDAO;
	}

	@Override
	public PageResult readAllPages(int rows, int page, SearchFilter filter) {
		PageResult $rows = this.curseToSDAO.readAllPages(rows, page, filter);
		if ($rows != null && $rows.getRows().size() > 0) {
			List<CurseToS> list = new ArrayList<CurseToS>();
			for (Object o : $rows.getRows()) {
				CurseToS details = (CurseToS) o;
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
	public boolean addCurseTos(CurseToS curseTos) {
		if (curseTos.getId() != null && curseTos.getId().trim().length() > 0) {
			CurseToS $tos = this.readCurseToS(curseTos.getId());
			if ($tos != null) {
				$tos.setName(curseTos.getName());
				$tos.setIndexs(curseTos.getIndexs());
				if (curseTos.getImage() != null && curseTos.getImageSha1() != null && curseTos.getImage().trim().length() > 0 && curseTos.getImageSha1().trim().length() > 0) {
					$tos.setImage(curseTos.getImage());
					$tos.setImageSha1(curseTos.getImageSha1());
				}
				curseTos = $tos;
			}
		} else {
			curseTos.setOnline(Integer.valueOf(0));
		}
		return this.curseToSDAO.saveCurseToS(curseTos);
	}

	@Override
	public CurseToS readCurseToS(String id) {
		if (id != null && id.trim().length() > 0) {
			return this.curseToSDAO.readCurseToS(id);
		}
		return null;
	}

	@Override
	public boolean onLineCurseToS(String id) {
		if (id != null && id.trim().length() > 0) {
			CurseToS tos = this.readCurseToS(id);
			if (tos != null) {
				List<CustomService> customList = this.customServiceDAO.readCustomServiceList(id);
				if (tos.getOnline() == null || tos.getOnline().intValue() == Integer.valueOf(0).intValue() && customList.size() > 0) {
					this.curseToSDAO.saveCurseTosHql("update CurseToS set online = 1 where id = '" + tos.getId() + "'");
				} else {
					this.curseToSDAO.saveCurseTosHql("update CurseToS set online = 0 where id = '" + tos.getId() + "'");
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean delCurseToS(String id) {
		if (id != null && id.trim().length() > 0) {
			CurseToS tos = this.readCurseToS(id);
			List<CustomService> customList = this.customServiceDAO.readCustomServiceList(id);
			if (tos != null && customList.size() == 0) {
				this.curseToSDAO.deleteCurseToS(tos);
				return true;
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
