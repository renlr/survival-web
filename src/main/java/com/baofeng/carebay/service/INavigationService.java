package com.baofeng.carebay.service;

import java.util.List;

import com.baofeng.carebay.entity.Navigation;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

public interface INavigationService {

	boolean addNavigation(String gid, Navigation nav);

	Navigation readNavigation(String id);

	boolean deleteNavigation(String id);

	boolean isChild(String id);

	PageResult readAllPages(int rows, int page, String gid, SearchFilter filter);

	List<Navigation> readNavigtorList(String keys);

}
