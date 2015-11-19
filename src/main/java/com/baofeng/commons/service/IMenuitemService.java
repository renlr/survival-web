package com.baofeng.commons.service;

import com.baofeng.commons.entity.MenuItem;
import com.baofeng.commons.entity.Operator;
import com.baofeng.utils.PageResult;

public interface IMenuitemService {

	PageResult readAllPages(int rows, int page, Operator user);

	void createMenuItem(MenuItem menuItem);

	boolean init();

}
