package com.baofeng.carebay.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baofeng.carebay.entity.Music;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.BaseException;
import com.baofeng.utils.DetachedCriteriaUtil;
import com.baofeng.utils.IBaseDAO;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Repository("musicGroupDAO")
public class MusicGroupDAO {

	@Autowired
	private IBaseDAO baseDAO;

	public IBaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 * 功能：读取分页数据
	 * */
	public PageResult readAllPages(int pageSize, int currentPage, SearchFilter filter) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Music.class);
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("name"));
			detachedCriteria.addOrder(Order.desc("createDT"));
			DetachedCriteriaUtil.addSearchFilter(detachedCriteria, filter);
			return baseDAO.findByPages(detachedCriteria, pageSize, currentPage);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BaseException("查询所有分页异常");
		}
	}

	public boolean saveBabyMusicGroup(Music group) {
		try {
			if (group != null && group.getId() != null && group.getId().length() > 0)
				this.baseDAO.mrege(group);
			else
				this.baseDAO.save(group);
			return true;
		} catch (Exception ex) {
			throw new BaseException("添加异常");
		}
	}

	public Music readBabyMusicGroup(String id) {
		Music group = (Music) this.baseDAO.get(Music.class, id);
		return group;
	}

	public boolean deleteBabyMusicGroup(String id) {
		Music group = this.readBabyMusicGroup(id);
		if (group != null) {
			group.setStatus(EntityStatus.DELETED);
			this.baseDAO.mrege(group);
			return true;
		}
		return false;
	}

	public Music readBabyMusicGroupByName(String name) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Music.class);
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.add(Restrictions.eq("name", Integer.valueOf(name)));
			List<Music> list = this.baseDAO.findAllByCriteria(detachedCriteria);
			if (list != null && list.size() > 0) {
				Music group = list.get(0);
				return group;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return null;
	}

}
