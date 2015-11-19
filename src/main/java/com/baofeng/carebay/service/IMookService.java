package com.baofeng.carebay.service;

import com.baofeng.carebay.entity.Mook;
import com.baofeng.utils.IBaseService;

public interface IMookService extends IBaseService{

	boolean addMook(Mook mook);

	Mook readMook(String id);

	boolean deleteBabyJournal(String id);

	boolean onLineBabyJournal(String id);

	boolean updateMook(String id1, String indexs1);

}
