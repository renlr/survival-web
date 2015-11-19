package com.baofeng.carebay.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.SlimmingDAO;
import com.baofeng.carebay.entity.Slimming;
import com.baofeng.carebay.service.ISlimmingService;
import com.baofeng.carebay.util.Constants;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("slimmingService")
public class SlimmingServiceImpl implements ISlimmingService {

	@Autowired
	private SlimmingDAO slimmingDAO;

	public SlimmingDAO getSlimmingDAO() {
		return slimmingDAO;
	}

	public void setSlimmingDAO(SlimmingDAO slimmingDAO) {
		this.slimmingDAO = slimmingDAO;
	}

	@Override
	public PageResult readAllPages(Integer pageSize, Integer currentPage, SearchFilter filter) {
		PageResult rows = this.slimmingDAO.readAllPages(pageSize, currentPage, filter);
		if (rows != null && rows.getRows().size() > 0) {
			List<Slimming> list = new ArrayList<Slimming>();
			for (Object o : rows.getRows()) {
				Slimming slim = (Slimming) o;
				slim.setImage(Constants.DEFAULT_HTTPIMAGES + "/" + Constants.sha1ToPath(slim.getImageSha1()).replace(File.separator, "/") + "/" + slim.getImage());
				list.add(slim);
			}
			rows.setRows(list);
		}
		return rows;
	}

	@Override
	public boolean addSlimming(Slimming slim) {
		if (slim != null && slim.getId() != null && slim.getId().trim().length() > 0) {
			Slimming $slim = this.readSlimming(slim.getId());
			if ($slim != null) {
				$slim.setName(slim.getName());
				$slim.setIndexs(slim.getIndexs());
				if (slim.getImage() != null && slim.getImageSha1() != null && slim.getImage().trim().length() > 0 && slim.getImageSha1().trim().length() > 0) {
					$slim.setImage(slim.getImage());
					$slim.setImageSha1(slim.getImageSha1());
				}
				slim = $slim;
			}
		} else {
			slim.setOnline(Integer.valueOf(0));
		}
		return this.slimmingDAO.saveSlimming(slim);
	}

	@Override
	public Slimming readSlimming(String id) {
		return this.slimmingDAO.readSlimming(id);
	}

	@Override
	public boolean onLineSlimming(String id) {
		Slimming slim = this.readSlimming(id);
		if (slim != null) {
			if (slim.getOnline() == null) {
				slim.setOnline(Integer.valueOf(0));
			}
			if (slim.getOnline().intValue() == 0) {
				slim.setOnline(Integer.valueOf(1));
			} else {
				slim.setOnline(Integer.valueOf(0));
			}
			this.slimmingDAO.saveSlimming(slim);
			return true;
		}
		return false;
	}

	@Override
	public boolean delSlimming(String id) {
		Slimming slim = this.readSlimming(id);
		if (slim != null) {
			slim.setStatus(EntityStatus.DELETED);
			this.slimmingDAO.saveSlimming(slim);
			return true;
		}
		return false;
	}

}
