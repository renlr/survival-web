package com.baofeng.carebay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.FunModuleDAO;
import com.baofeng.carebay.entity.FunModule;
import com.baofeng.carebay.service.IFunModuleService;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("funModuleService")
public class FunModuleServiceImpl implements IFunModuleService {

	@Autowired
	private FunModuleDAO funModuleDAO;

	public FunModuleDAO getFunModuleDAO() {
		return funModuleDAO;
	}

	public void setFunModuleDAO(FunModuleDAO funModuleDAO) {
		this.funModuleDAO = funModuleDAO;
	}

	@Override
	public PageResult readAllPages(Integer pageSize, Integer currentPage, SearchFilter filter) {
		return this.funModuleDAO.readAllPages(pageSize, currentPage, filter);
	}

	@Override
	public PageResult readPagesViews(int rows, int page) {
		return this.funModuleDAO.readPagesViews(rows, page);
	}

	@Override
	public boolean addFunModule(FunModule idx) {
		if (idx != null && idx.getId() != null && idx.getId().trim().length() > 0) {
			FunModule $idx = this.readFunModule(idx.getId());
			if ($idx != null) {
				$idx.setVals(idx.getVals());
				$idx.setName(idx.getName());
				idx = $idx;
			}
		}
		return this.funModuleDAO.saveFunModule(idx);
	}

	@Override
	public FunModule readFunModule(String id) {
		if (id != null && id.trim().length() > 0) {
			return this.funModuleDAO.readFunModule(id);
		}
		return null;
	}

	@Override
	public FunModule readFunModulebyName(String name) {
		return this.funModuleDAO.readFunModulebyName(name);
	}

	@Override
	public boolean deleteFunModule(String id) {
		return this.funModuleDAO.deleteFunModule(id);
	}

	@Override
	public FunModule readFunModulebyId(String id) {
		FunModule module = this.funModuleDAO.readFunModule(id);
		if (module != null && module.getViews() != null && module.getViews().intValue() == 1) {
			return module;
		}
		return null;
	}

	@Override
	public boolean addFunModuleViews(String module, String indexs) {
		FunModule $module = this.funModuleDAO.readFunModule(module);
		if ($module != null) {
			$module.setIndexs(Integer.valueOf(indexs));
			$module.setViews(Integer.valueOf(1));
			this.funModuleDAO.saveFunModule($module);
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteFunModuleViews(String id) {
		FunModule $module = this.funModuleDAO.readFunModule(id);
		if ($module != null) {
			$module.setIndexs(null);
			$module.setViews(null);
			this.funModuleDAO.saveFunModule($module);
			return true;
		}
		return false;
	}

	@Override
	public boolean editIndexs(String id, String indexs) {
		FunModule module = this.funModuleDAO.readFunModule(id);
		if (module != null) {
			module.setIndexs(Integer.valueOf(indexs));
			this.funModuleDAO.saveFunModule(module);
			return true;
		}
		return false;
	}
}
