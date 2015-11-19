package com.baofeng.carebay.dao;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baofeng.carebay.entity.Doctor;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.DetachedCriteriaUtil;
import com.baofeng.utils.IBaseDAO;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Repository("doctorDAO")
public class DoctorDAO {

	@Autowired
	private IBaseDAO baseDAO;

	public IBaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public PageResult readAllPages(Integer pageSize, Integer currentPage, SearchFilter filter) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Doctor.class);
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("createDT"));
			DetachedCriteriaUtil.addSearchFilter(detachedCriteria, filter);
			return baseDAO.findByPages(detachedCriteria, pageSize, currentPage);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public boolean saveDoctor(Doctor doctor) {
		try {
			if (doctor != null && doctor.getId() != null && doctor.getId().length() > 0)
				this.baseDAO.mrege(doctor);
			else
				this.baseDAO.save(doctor);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public boolean editFiles(String id, String field, String value) {
		String hql = "update Doctor set " + field + "='" + value + "' where id='" + id + "'";
		int count = this.baseDAO.execute(hql);
		if (count > 0)
			return true;
		return false;
	}

	public Doctor readDoctor(String id) {
		if (id != null && id.trim().length() > 0) {
			Doctor doctor = (Doctor) this.baseDAO.get(Doctor.class, id);
			return doctor;
		}
		return null;
	}
}
