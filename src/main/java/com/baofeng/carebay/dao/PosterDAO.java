package com.baofeng.carebay.dao;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baofeng.carebay.entity.Poster;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.BaseException;
import com.baofeng.utils.IBaseDAO;
import com.baofeng.utils.PageResult;

@Repository("posterDAO")
public class PosterDAO {

	@Autowired
	private IBaseDAO baseDAO;

	public IBaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public PageResult readAllPages(Integer pageSize, Integer currentPage, String gid) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Poster.class);
			detachedCriteria.createAlias("week", "week").add(Restrictions.eq("week.id", gid));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.asc("online"));
			detachedCriteria.addOrder(Order.desc("indexs"));
			detachedCriteria.addOrder(Order.desc("createDT"));
			return baseDAO.findByPages(detachedCriteria, pageSize, currentPage);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BaseException("查询所有分页异常");
		}
	}

	public boolean savePoster(Poster post) {
		try {
			if (post != null && post.getId() != null && post.getId().length() > 0)
				this.baseDAO.mrege(post);
			else
				this.baseDAO.save(post);
			return true;
		} catch (Exception ex) {
			throw new BaseException("添加异常");
		}
	}

	public Poster readPoster(String id) {
		Poster post = (Poster) this.baseDAO.get(Poster.class, id);
		if (post != null)
			return post;
		return null;
	}

	public void delPoster(Poster post) {
		this.baseDAO.delete(post);
	}
}
