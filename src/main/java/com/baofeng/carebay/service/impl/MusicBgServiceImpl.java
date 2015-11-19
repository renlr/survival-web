package com.baofeng.carebay.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.MusicBgDAO;
import com.baofeng.carebay.dao.WeekServiceDAO;
import com.baofeng.carebay.entity.MusicBg;
import com.baofeng.carebay.entity.WeekService;
import com.baofeng.carebay.service.IMusicBgService;
import com.baofeng.carebay.util.Constants;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.PageResult;

@Service("musicBgService")
public class MusicBgServiceImpl implements IMusicBgService {

	@Autowired
	private MusicBgDAO musicBgDAO;
	@Autowired
	private WeekServiceDAO weekServiceDAO;

	public MusicBgDAO getMusicBgDAO() {
		return musicBgDAO;
	}

	public void setMusicBgDAO(MusicBgDAO musicBgDAO) {
		this.musicBgDAO = musicBgDAO;
	}

	public WeekServiceDAO getWeekServiceDAO() {
		return weekServiceDAO;
	}

	public void setWeekServiceDAO(WeekServiceDAO weekServiceDAO) {
		this.weekServiceDAO = weekServiceDAO;
	}

	@Override
	public PageResult readAllPages(int $rows, int page, String gid) {
		PageResult rows = this.musicBgDAO.readAllPages($rows, page, gid);
		if (rows != null && rows.getRows().size() > 0) {
			List<MusicBg> list = new ArrayList<MusicBg>();
			for (Object o : rows.getRows()) {
				MusicBg music = (MusicBg) o;
				music.setImage(Constants.DEFAULT_HTTPIMAGES + "/" + Constants.sha1ToPath(music.getImageSha1()).replace(File.separator, "/") + "/" + music.getImage());
				list.add(music);
			}
			rows.setRows(list);
		}
		return rows;
	}

	@Override
	public boolean addMusicBg(MusicBg music, String gid) {
		if (music.getId() != null && music.getId().trim().length() > 0) {
			MusicBg $music = this.musicBgDAO.readMusicBg(music.getId());
			if ($music != null) {
				$music.setImage(music.getImage());
				$music.setImageSha1(music.getImageSha1());
				music = $music;
			}
		} else {
			WeekService week = this.weekServiceDAO.readWeekService(gid);
			music.setOnline(Integer.valueOf(0));
			music.setWeek(week);
		}
		return this.musicBgDAO.saveMusicBg(music);
	}

	@Override
	public boolean onLineMusicBg(String id) {
		MusicBg music = this.readMusicBg(id);
		if (music != null) {
			if (music.getOnline() == null) {
				music.setOnline(Integer.valueOf(0));
			}
			if (music.getOnline().intValue() == Integer.valueOf(1).intValue()) {
				music.setOnline(Integer.valueOf(0));
			} else {
				music.setOnline(Integer.valueOf(1));
			}
			this.musicBgDAO.saveMusicBg(music);
			return true;
		}
		return false;
	}

	@Override
	public MusicBg readMusicBg(String id) {
		return this.musicBgDAO.readMusicBg(id);
	}

	@Override
	public boolean deleteMusicBg(String id) {
		MusicBg music = this.readMusicBg(id);
		if (music != null) {
			music.setStatus(EntityStatus.DELETED);
			this.musicBgDAO.saveMusicBg(music);
			return true;
		}
		return false;
	}
}
