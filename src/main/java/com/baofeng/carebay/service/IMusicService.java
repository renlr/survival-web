package com.baofeng.carebay.service;

import com.baofeng.carebay.entity.Music;
import com.baofeng.utils.IBaseService;
import com.baofeng.utils.PageResult;

public interface IMusicService extends IBaseService {

	PageResult readAllPages(int rows, int page, String gid);

	boolean addMusic(Music music, String gid);

	boolean deleteMusic(String id);

	boolean editFiles(String id, String field, String value);

}
