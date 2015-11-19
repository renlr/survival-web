package com.baofeng.carebay.service;

import com.baofeng.carebay.entity.CurseCase;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

public interface ICurseCaseService {

	boolean addCurseCase(CurseCase curse, String gid);

	CurseCase readCurseCase(String id);

	boolean delCurseCase(String id);

	boolean onLineCurseCase(String id);

	PageResult readPagesSkip(int rows, int page, SearchFilter filter);

	PageResult readAllPages(int rows, int page, String gid, SearchFilter filter);

}
