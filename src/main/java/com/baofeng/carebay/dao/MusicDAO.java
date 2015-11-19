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
import com.baofeng.utils.IBaseDAO;
import com.baofeng.utils.PageResult;

@Repository("musicDAO")
public class MusicDAO {

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
	public PageResult readAllPages(int pageSize, int currentPage, String groupid) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Music.class);
			detachedCriteria.createAlias("week", "week").add(Restrictions.eq("week.id", groupid));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("createDT"));
			return baseDAO.findByPages(detachedCriteria, pageSize, currentPage);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BaseException("查询所有分页异常");
		}
	}

	public boolean saveMusic(Music music) {
		try {
			if (music != null && music.getId() != null && music.getId().length() > 0)
				this.baseDAO.mrege(music);
			else
				this.baseDAO.save(music);
			return true;
		} catch (Exception ex) {
			throw new BaseException("添加异常");
		}
	}

	public int readCount(String groupid) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Music.class);
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.createAlias("group", "group").add(Restrictions.eq("group.id", groupid));
			detachedCriteria.addOrder(Order.desc("createDT"));
			List<?> list = this.baseDAO.findAllByCriteria(detachedCriteria);
			if (list != null && list.size() > 0)
				return list.size();
			return 0;
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	public Music readMusic(String id) {
		Music music = (Music) this.baseDAO.get(Music.class, id);
		return music;
	}

	public boolean editFiles(String id, String field, String value) {
		String hql = "update Music set " + field + "='" + value + "' where id='" + id + "'";
		int count = this.baseDAO.execute(hql);
		if (count > 0)
			return true;
		return false;
	}
}
