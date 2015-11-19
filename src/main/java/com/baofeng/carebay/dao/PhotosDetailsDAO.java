package com.baofeng.carebay.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baofeng.carebay.entity.Photos;
import com.baofeng.carebay.entity.PhotosDetails;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.BaseException;
import com.baofeng.utils.DetachedCriteriaUtil;
import com.baofeng.utils.IBaseDAO;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Repository("photosDetailsDAO")
public class PhotosDetailsDAO {

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
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PhotosDetails.class);
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("createDT"));
			DetachedCriteriaUtil.addSearchFilter(detachedCriteria, filter);
			return baseDAO.findByPages(detachedCriteria, pageSize, currentPage);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BaseException("查询所有分页异常");
		}
	}

	public List<PhotosDetails> readListPhotosDetails(Integer type) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PhotosDetails.class);
			detachedCriteria.add(Restrictions.eq("type", type.intValue()));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.add(Restrictions.or(Restrictions.eq("cutter", Integer.valueOf(1)), Restrictions.eq("isConvert", Integer.valueOf(1))));
			detachedCriteria.addOrder(Order.desc("createDT"));
			List<PhotosDetails> list = this.baseDAO.findAllByCriteria(detachedCriteria);
			if (list != null && list.size() > 0)
				return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	public PhotosDetails readPhotosDetails(String id) {
		PhotosDetails details = (PhotosDetails) this.baseDAO.get(PhotosDetails.class, id);
		return details;
	}
	
	public boolean savePhotosDetails(PhotosDetails details) {
		try {
			if (details != null && details.getId() != null && details.getId().length() > 0)
				this.baseDAO.mrege(details);
			else
				this.baseDAO.save(details);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<Photos> readPhotosList() {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Photos.class);
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.add(Restrictions.ne("cutter", Integer.valueOf(2)));
			detachedCriteria.addOrder(Order.desc("createDT"));
			List<Photos> list = this.baseDAO.findAllByCriteria(detachedCriteria);
			if (list != null && list.size() > 0)
				return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	public int totalCount(String id) {
		int count = 0;
		try {
			String hql = "select count(id) from PhotosDetails where photos.id = '" + id + "'";
			List<Object> list = this.baseDAO.find(hql);
			if (list != null) {
				Object o = list.get(0);
				count = Integer.valueOf(o.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public int cutteCount(String id) {
		int count = 0;
		try {
			String hql = "select count(id) from PhotosDetails where photos.id = '" + id + "' and cutter = 2";
			List<Object> list = this.baseDAO.find(hql);
			if (list != null) {
				Object o = list.get(0);
				count = Integer.valueOf(o.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public int ConvertCount(String id) {
		int count = 0;
		try {
			String hql = "select count(id) from PhotosDetails where photos.id = '" + id + "' and isConvert = 2";
			List<Object> list = this.baseDAO.find(hql);
			if (list != null) {
				Object o = list.get(0);
				count = Integer.valueOf(o.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
}
