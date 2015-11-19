package com.baofeng.carebay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.DoctorDAO;
import com.baofeng.carebay.entity.Doctor;
import com.baofeng.carebay.service.IDoctorService;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("doctorService")
public class DoctorServiceImpl implements IDoctorService {

	@Autowired
	private DoctorDAO doctorDAO;

	public DoctorDAO getDoctorDAO() {
		return doctorDAO;
	}

	public void setDoctorDAO(DoctorDAO doctorDAO) {
		this.doctorDAO = doctorDAO;
	}

	@Override
	public PageResult readAllPages(Integer pageSize, Integer currentPage, SearchFilter filter) {
		return this.doctorDAO.readAllPages(pageSize, currentPage, filter);
	}

	@Override
	public boolean addDoctor(Doctor doctor) {
		doctor.setCounts(Integer.valueOf(0));
		doctor.setOnline(Integer.valueOf(0));
		if (this.doctorDAO.saveDoctor(doctor)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean editFiles(String id, String field, String value) {
		return this.doctorDAO.editFiles(id, field, value);
	}

	@Override
	public boolean deleteDoctor(String id) {
		if (id != null && id.trim().length() > 0) {
			Doctor doctor = this.readDoctor(id);
			if (doctor != null) {
				doctor.setStatus(EntityStatus.DELETED);
				this.doctorDAO.saveDoctor(doctor);
				return true;
			}
		}

		return false;
	}

	@Override
	public Doctor readDoctor(String id) {
		return this.doctorDAO.readDoctor(id);
	}

	@Override
	public boolean onLineDoctor(String id) {
		if (id != null && id.trim().length() > 0) {
			Doctor doctor = this.readDoctor(id);
			if (doctor != null) {
				if (doctor.getOnline() == null)
					doctor.setOnline(Integer.valueOf(0));
				if (doctor.getOnline().intValue() == Integer.valueOf(0).intValue()) {
					doctor.setOnline(Integer.valueOf(1));
				} else {
					doctor.setOnline(Integer.valueOf(0));
				}
				this.doctorDAO.saveDoctor(doctor);
				return true;
			}
		}
		return false;
	}
}
