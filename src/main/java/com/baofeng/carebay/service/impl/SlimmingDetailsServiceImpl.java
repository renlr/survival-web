package com.baofeng.carebay.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.SlimmingDAO;
import com.baofeng.carebay.dao.SlimmingDetailsDAO;
import com.baofeng.carebay.entity.Slimming;
import com.baofeng.carebay.entity.SlimmingDetails;
import com.baofeng.carebay.service.ISlimmingDetailsService;
import com.baofeng.carebay.util.Constants;
import com.baofeng.utils.PageResult;

@Service("slimmingDetailsService")
public class SlimmingDetailsServiceImpl implements ISlimmingDetailsService {

	@Autowired
	private SlimmingDAO slimmingDAO;
	@Autowired
	private SlimmingDetailsDAO slimmingDetailsDAO;

	public SlimmingDAO getSlimmingDAO() {
		return slimmingDAO;
	}

	public void setSlimmingDAO(SlimmingDAO slimmingDAO) {
		this.slimmingDAO = slimmingDAO;
	}

	public SlimmingDetailsDAO getSlimmingDetailsDAO() {
		return slimmingDetailsDAO;
	}

	public void setSlimmingDetailsDAO(SlimmingDetailsDAO slimmingDetailsDAO) {
		this.slimmingDetailsDAO = slimmingDetailsDAO;
	}

	@Override
	public PageResult readAllPages(int rows, int page, String groupId) {
		PageResult $rows = this.slimmingDetailsDAO.readAllPages(rows, page, groupId);
		if ($rows != null && $rows.getRows().size() > 0) {
			List<SlimmingDetails> list = new ArrayList<SlimmingDetails>();
			for (Object o : $rows.getRows()) {
				SlimmingDetails details = (SlimmingDetails) o;
				details.setImage(Constants.DEFAULT_HTTPIMAGES + "/" + Constants.sha1ToPath(details.getImageSha1()).replace(File.separator, "/") + "/" + details.getImage());
				list.add(details);
			}
			$rows.setRows(list);
		}
		return $rows;
	}

	@Override
	public boolean addSlimmingDetails(SlimmingDetails details, String groupId) {
		if (details != null && details.getId() != null && details.getId().trim().length() > 0) {
			SlimmingDetails $details = this.readSlimmingDetails(details.getId());
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
			Slimming slim = this.slimmingDAO.readSlimming(groupId);
			if (slim != null) {
				details.setSlim(slim);
			}
			details.setOnline(Integer.valueOf(0));
		}
		return this.slimmingDetailsDAO.saveSlimmingDetails(details);
	}

	@Override
	public SlimmingDetails readSlimmingDetails(String id) {
		return this.slimmingDetailsDAO.readSlimmingDetails(id);
	}

	@Override
	public boolean onLineSlimmingDetails(String id) {
		SlimmingDetails details = this.readSlimmingDetails(id);
		if (details != null) {
			if (details.getOnline() == null) {
				details.setOnline(Integer.valueOf(0));
			}
			if (details.getOnline().intValue() == 0) {
				details.setOnline(Integer.valueOf(1));
			} else {
				details.setOnline(Integer.valueOf(0));
			}
			this.slimmingDetailsDAO.saveSlimmingDetails(details);
			return true;
		}
		return false;
	}

	@Override
	public boolean delSlimmingDetails(String id) {
		SlimmingDetails details = this.readSlimmingDetails(id);
		if (details != null) {
			details.setSlim(null);
			this.slimmingDetailsDAO.saveSlimmingDetails(details);
			this.slimmingDetailsDAO.delSlimmingDetails(details);
			return true;
		}
		return false;
	}

	@Override
	public boolean saveIndexs(String id1, String gid1, String indexs1) {
		SlimmingDetails details = this.readSlimmingDetails(id1);
		if (details != null) {
			details.setIndexs(Integer.valueOf(indexs1));
			this.slimmingDetailsDAO.saveSlimmingDetails(details);
			return true;
		}
		return false;
	}

	@Override
	public boolean addSlimmingContent(String id, String gid, String content) {
		SlimmingDetails details = this.readSlimmingDetails(id);
		if (details != null) {
			details.setContent(id + ".html");
			this.slimmingDetailsDAO.saveSlimmingDetails(details);
			return true;
		}
		return false;
	}

}
