package com.baofeng.carebay.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.UPloadServerDAO;
import com.baofeng.carebay.entity.UPloadServer;
import com.baofeng.carebay.service.IUPloadServerService;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("uploadServerService")
public class UPloadServerServiceImpl implements IUPloadServerService {

	@Autowired
	private UPloadServerDAO uploadServerDAO;

	public UPloadServerDAO getUploadServerDAO() {
		return uploadServerDAO;
	}

	public void setUploadServerDAO(UPloadServerDAO uploadServerDAO) {
		this.uploadServerDAO = uploadServerDAO;
	}

	@Override
	public PageResult readAllPages(Integer pageSize, Integer currentPage, SearchFilter filter) {
		return this.uploadServerDAO.readAllPages(pageSize, currentPage, filter);
	}

	@Override
	public boolean addUPloadServer(UPloadServer upserver) {
		if (upserver != null && upserver.getId() != null && upserver.getId().trim().length() > 0) {
			UPloadServer $upserver = this.readUPloadServer(upserver.getId());
			if ($upserver != null) {
				upserver.setCreateDT($upserver.getCreateDT());
				upserver.setTakeDT($upserver.getTakeDT());
			}
		} else {
			upserver.setTakeDT(new Date());
		}
		return this.uploadServerDAO.saveUPloadServer(upserver);
	}

	@Override
	public UPloadServer readUPloadServer(String id) {
		return this.uploadServerDAO.readUPloadServer(id);
	}

	@Override
	public UPloadServer editUPloadServer(String id) {
		if (id != null) {
			UPloadServer upserver = this.readUPloadServer(id);
			return upserver;
		}
		return null;
	}

	@Override
	public boolean delUPloadServer(String id) {
		if (id != null) {
			UPloadServer upserver = this.readUPloadServer(id);
			if (upserver != null) {
				upserver.setStatus(EntityStatus.DELETED);
				this.uploadServerDAO.saveUPloadServer(upserver);
				return true;
			}
		}
		return false;
	}
}
