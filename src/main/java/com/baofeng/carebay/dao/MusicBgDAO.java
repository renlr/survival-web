package com.baofeng.carebay.dao;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baofeng.carebay.entity.MusicBg;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.BaseException;
import com.baofeng.utils.IBaseDAO;
import com.baofeng.utils.PageResult;

@Repository("musicBgDAO")
public class MusicBgDAO {

	@Autowired
	private IBaseDAO baseDAO;

	public IBaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public PageResult readAllPages(int $rows, int page, String gid) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MusicBg.class);
			detachedCriteria.createAlias("week", "week").add(Restrictions.eq("week.id", gid));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("online"));
			detachedCriteria.addOrder(Order.desc("createDT"));
			return baseDAO.findByPages(detachedCriteria, $rows, page);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BaseException("查询所有分页异常");
		}
	}

	public boolean saveMusicBg(MusicBg music) {
		try {
			if (music != null && music.getId() != null && music.getId().length() > 0)
				this.baseDAO.mrege(music);
			else
				this.baseDAO.save(music);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public MusicBg readMusicBg(String id) {
		if (id != null && id.trim().length() > 0) {
			MusicBg music = (MusicBg) this.baseDAO.get(MusicBg.class, id);
			if (music != null) {
				return music;
			}
		}
		return null;
	}
}
