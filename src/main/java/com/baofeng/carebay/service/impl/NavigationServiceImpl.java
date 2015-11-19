package com.baofeng.carebay.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.NavigationDAO;
import com.baofeng.carebay.entity.Navigation;
import com.baofeng.carebay.service.INavigationService;
import com.baofeng.carebay.util.Constants;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("navigationService")
public class NavigationServiceImpl implements INavigationService {

	@Autowired
	private NavigationDAO navigationDAO;

	public NavigationDAO getNavigationDAO() {
		return navigationDAO;
	}

	public void setNavigationDAO(NavigationDAO navigationDAO) {
		this.navigationDAO = navigationDAO;
	}

	@Override
	public PageResult readAllPages(int rows, int page, String gid, SearchFilter filter) {
		PageResult $rows = this.navigationDAO.readAllPages(rows, page, gid, filter);
		if ($rows != null && $rows.getRows().size() > 0) {
			List<Navigation> list = new ArrayList<Navigation>();
			for (Object o : $rows.getRows()) {
				Navigation details = (Navigation) o;
				details.setImage(Constants.DEFAULT_HTTPIMAGES + "/" + Constants.sha1ToPath(details.getImageSha1()).replace(File.separator, "/") + "/" + details.getImage());
				list.add(details);
			}
			$rows.setRows(list);
		}
		return $rows;
	}

	@Override
	public boolean addNavigation(String gid, Navigation nav) {
		if (nav.getId() != null && nav.getId().trim().length() > 0) {
			Navigation $nav = this.readNavigation(nav.getId());
			if ($nav != null) {
				$nav.setName(nav.getName());
				$nav.setNavId(nav.getNavId());
				$nav.setNkey(nav.getNkey());
				$nav.setIndexs(nav.getIndexs());
				if (nav.getImage() != null && nav.getImageSha1() != null && nav.getImage().trim().length() > 0 && nav.getImageSha1().trim().length() > 0) {
					$nav.setImage(nav.getImage());
					$nav.setImageSha1(nav.getImageSha1());
				}
				nav = $nav;
			}
		} else {
			if (gid != null && gid.trim().length() > 0) {
				Navigation $navgation = this.readNavigation(gid);
				if ($navgation != null)
					nav.setNav($navgation);
			}
		}
		return this.navigationDAO.saveNavigation(nav);
	}

	@Override
	public Navigation readNavigation(String id) {
		if (id != null && id.trim().length() > 0) {
			return this.navigationDAO.readNavigation(id);
		}
		return null;
	}

	@Override
	public boolean deleteNavigation(String id) {
		if (id != null && id.trim().length() > 0) {
			Navigation nav = this.readNavigation(id);
			if (nav != null && this.isChild(id)) {
				nav.setStatus(EntityStatus.DELETED);
				this.navigationDAO.saveNavigation(nav);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isChild(String id) {
		List<Navigation> list = this.navigationDAO.readGidList(id);
		if (list != null && list.size() > 0) {
			return false;
		}
		return true;
	}

	@Override
	public List<Navigation> readNavigtorList(String keys) {
		return this.navigationDAO.readNavigtorList(keys);
	}
}
