package com.baofeng.commons.service;

import java.util.List;

import com.baofeng.commons.entity.MenuItem;
import com.baofeng.commons.entity.Operator;
import com.baofeng.commons.entity.UserPages;
import com.baofeng.utils.IBaseService;
import com.baofeng.utils.PageResult;

public interface IOperatorService extends IBaseService {

	/** 登录验证 */
	Operator validation(String loginName, String loginPwd);

	/** 添加管理用户 */
	boolean addUser(Operator user);

	/** 读取系统管理用户 */
	Operator readUser(Integer id);

	/** 删除系统管理用户 */
	boolean delUser(Integer id);

	Operator readOperator(Integer id);

	PageResult readAllPagesDetails(int rows, int page, int gid);

	boolean addUserPages(String gid, String ids, Operator operator);

	boolean deleteUserPages(String id, Operator operator);

	UserPages readUserPages(String id);

	boolean disableOperator(Integer id);

	boolean deleteOperator(Integer id);

	List<MenuItem> readMenuTopList(Operator user);

	List<MenuItem> readMenuLeftList(Operator user);
}
