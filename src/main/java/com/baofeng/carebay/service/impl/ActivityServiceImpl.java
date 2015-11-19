package com.baofeng.carebay.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.ActivityDAO;
import com.baofeng.carebay.entity.Activity;
import com.baofeng.carebay.entity.Chamber;
import com.baofeng.carebay.service.IActivityService;
import com.baofeng.commons.dao.ChamberDAO;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("activityService")
public class ActivityServiceImpl implements IActivityService {

	@Autowired
	private ChamberDAO chamberDAO;
	@Autowired
	private ActivityDAO activityDAO;

	public ChamberDAO getChamberDAO() {
		return chamberDAO;
	}

	public void setChamberDAO(ChamberDAO chamberDAO) {
		this.chamberDAO = chamberDAO;
	}

	public ActivityDAO getActivityDAO() {
		return activityDAO;
	}

	public void setActivityDAO(ActivityDAO activityDAO) {
		this.activityDAO = activityDAO;
	}

	@Override
	public PageResult readAllPages(Integer pageSize, Integer currentPage, SearchFilter filter) {
		PageResult $rows = this.activityDAO.readAllPages(pageSize, currentPage, filter);
		if ($rows != null && $rows.getRows().size() > 0) {
			List<Activity> list = new ArrayList<Activity>();
			for (Object o : $rows.getRows()) {
				Activity details = (Activity) o;
				String cid = details.getOperator().getChamber();
				Chamber chamber = this.chamberDAO.readChamber(cid);
				if (chamber != null)
					details.setChamber(chamber.getName());
				list.add(details);
			}
			$rows.setRows(list);
		}
		return $rows;
	}

	@Override
	public PageResult readAllPagesDetails(int rows, int page, String gid) {
		return this.activityDAO.readAllPagesDetails(rows, page, gid);
	}

	@Override
	public boolean addActivity(Activity act) {
		if (act.getId() != null && act.getId().trim().length() > 0) {
			Activity $act = this.readActivity(act.getId());
			if ($act != null) {
				$act.setName(act.getName());
				$act.setContent(act.getContent());
				$act.setDetails(act.getDetails());
				$act.setBeginTime(act.getBeginTime());
				$act.setEndTime(act.getEndTime());
				act = $act;
			}
		} else {
			act.setOnline(Integer.valueOf(0));
		}
		return this.activityDAO.saveActivity(act);
	}

	@Override
	public Activity readActivity(String id) {
		if (id != null && id.trim().length() > 0) {
			return this.activityDAO.readActivity(id);
		}
		return null;
	}

	@Override
	public boolean deleteActivity(String id) {
		if (id != null && id.trim().length() > 0) {
			Activity act = this.readActivity(id);
			if (act != null) {
				this.activityDAO.deleteActivity(act);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onLineActivity(String id) {
		if (id != null && id.trim().length() > 0) {
			Activity act = this.readActivity(id);
			if (act != null) {
				if (act.getOnline() == null || act.getOnline().intValue() == Integer.valueOf(0))
					this.activityDAO.saveActivityHql("update Activity set online = 1 where id = '" + act.getId() + "'");
				else
					this.activityDAO.saveActivityHql("update Activity set online = 0 where id = '" + act.getId() + "'");
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean saveOnLineTask(String ids, String onlineDT, String offlineDT) {
		try {
			if (ids != null && ids.trim().length() > 0) {
				String[] $ids = ids.split(",");
				for (String id : $ids) {
					if (id != null && id.trim().length() > 0) {
						Activity act = this.activityDAO.readActivity(id);
						if (act != null) {
							String taskDt = String.valueOf("|");
							if (onlineDT != null && onlineDT.trim().length() > 0) {
								taskDt = onlineDT + taskDt;
							}
							if (offlineDT != null && offlineDT.trim().length() > 0) {
								taskDt = taskDt + offlineDT;
							}
							act.setTaskDT(taskDt);
							this.activityDAO.saveActivity(act);
						}
					}
				}
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
}
