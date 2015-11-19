package com.baofeng.carebay.service;

import com.baofeng.carebay.entity.FunModule;
import com.baofeng.utils.IBaseService;
import com.baofeng.utils.PageResult;

public interface IFunModuleService extends IBaseService {

	boolean addFunModule(FunModule idx);

	FunModule readFunModule(String id);

	FunModule readFunModulebyName(String name);

	boolean deleteFunModule(String id);

	PageResult readPagesViews(int rows, int page);

	FunModule readFunModulebyId(String id);

	boolean addFunModuleViews(String module, String indexs);

	boolean deleteFunModuleViews(String id);

	boolean editIndexs(String id1, String indexs1);
}
