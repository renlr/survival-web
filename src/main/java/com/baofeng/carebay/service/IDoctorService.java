package com.baofeng.carebay.service;

import com.baofeng.carebay.entity.Doctor;
import com.baofeng.utils.IBaseService;

public interface IDoctorService extends IBaseService {

	boolean addDoctor(Doctor doctor);

	boolean editFiles(String id, String field, String value);

	boolean deleteDoctor(String id);

	Doctor readDoctor(String id);

	boolean onLineDoctor(String id);

}
