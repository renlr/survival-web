package com.baofeng.carebay.dao;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baofeng.carebay.entity.Photos;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.BaseException;
import com.baofeng.utils.DetachedCriteriaUtil;
import com.baofeng.utils.IBaseDAO;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Repository("photosDAO")
public class PhotosDAO {

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
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Photos.class);
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("createDT"));
			DetachedCriteriaUtil.addSearchFilter(detachedCriteria, filter);
			return baseDAO.findByPages(detachedCriteria, pageSize, currentPage);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BaseException("查询所有分页异常");
		}
	}

	public boolean savePhotos(Photos $photos) {
		try {
			if ($photos != null && $photos.getId() != null && $photos.getId().length() > 0)
				this.baseDAO.mrege($photos);
			else
				this.baseDAO.save($photos);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}