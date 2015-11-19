package com.baofeng.carebay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.MookDAO;
import com.baofeng.carebay.dao.MookDetailsDAO;
import com.baofeng.carebay.entity.Mook;
import com.baofeng.carebay.entity.MookDetails;
import com.baofeng.carebay.service.IMookDetailsService;
import com.baofeng.utils.PageResult;

@Service("mookDetailsService")
public class MookDetailsServiceImpl implements IMookDetailsService {

	@Autowired
	private MookDAO mookDAO;
	@Autowired
	private MookDetailsDAO mookDetailsDAO;

	public MookDAO getMookDAO() {
		return mookDAO;
	}

	public void setMookDAO(MookDAO mookDAO) {
		this.mookDAO = mookDAO;
	}

	public MookDetailsDAO getMookDetailsDAO() {
		return mookDetailsDAO;
	}

	public void setMookDetailsDAO(MookDetailsDAO mookDetailsDAO) {
		this.mookDetailsDAO = mookDetailsDAO;
	}

	@Override
	public PageResult readAllPages(int rows, int page, String gid) {
		return this.mookDetailsDAO.readAllPages(rows, page, gid);
	}

	@Override
	public boolean addMookDetails(MookDetails details, String gid) {
		if (details != null && details.getId() != null && details.getId().trim().length() > 0) {
			MookDetails $details = this.readMookDetails(details.getId());
			if ($details != null) {
				$details.setName(details.getName());
				details = $details;
			}
		}
		Mook mook = this.mookDAO.readMook(gid);
		if (mook != null) {
			details.setMook(mook);
		}
		return this.mookDetailsDAO.saveMookDetails(details);
	}

	@Override
	public MookDetails readMookDetails(String id) {
		return this.mookDetailsDAO.readMookDetails(id);
	}

	@Override
	public boolean deleteMookDetails(String id) {
		return this.mookDetailsDAO.deleteMookDetails(id);
	}

	@Override
	public boolean addMookDetailsContent(String id, String gid, String content) {
		MookDetails details = this.mookDetailsDAO.readMookDetails(id);
		if (details != null) {
			details.setContent(id + ".html");
			this.mookDetailsDAO.saveMookDetails(details);
			return true;
		}
		return false;
	}

}
