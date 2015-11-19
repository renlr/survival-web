package com.baofeng.carebay.service;

import com.baofeng.carebay.entity.MusicBg;
import com.baofeng.utils.PageResult;

public interface IMusicBgService {

	PageResult readAllPages(int rows, int page, String gid);

	boolean addMusicBg(MusicBg music, String gid);

	boolean onLineMusicBg(String id);

	MusicBg readMusicBg(String id);

	boolean deleteMusicBg(String id);

}
