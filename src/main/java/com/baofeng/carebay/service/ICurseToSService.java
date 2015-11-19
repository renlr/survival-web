package com.baofeng.carebay.service;

import com.baofeng.carebay.entity.CurseToS;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

public interface ICurseToSService {

	boolean addCurseTos(CurseToS curseTos);

	CurseToS readCurseToS(String id);

	boolean onLineCurseToS(String id);

	boolean delCurseToS(String id);

	PageResult readAllPages(int rows, int page, SearchFilter filter);

}
