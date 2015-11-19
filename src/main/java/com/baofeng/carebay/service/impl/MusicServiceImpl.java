package com.baofeng.carebay.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.MusicDAO;
import com.baofeng.carebay.dao.WeekServiceDAO;
import com.baofeng.carebay.entity.Music;
import com.baofeng.carebay.entity.WeekService;
import com.baofeng.carebay.service.IMusicService;
import com.baofeng.carebay.util.Constants;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("musicService")
public class MusicServiceImpl implements IMusicService {

	@Autowired
	private MusicDAO musicDAO;

	@Autowired
	private WeekServiceDAO weekServiceDAO;

	public MusicDAO getMusicDAO() {
		return musicDAO;
	}

	public void setMusicDAO(MusicDAO musicDAO) {
		this.musicDAO = musicDAO;
	}

	public WeekServiceDAO getWeekServiceDAO() {
		return weekServiceDAO;
	}

	public void setWeekServiceDAO(WeekServiceDAO weekServiceDAO) {
		this.weekServiceDAO = weekServiceDAO;
	}

	@Override
	public PageResult readAllPages(Integer pageSize, Integer currentPage, SearchFilter filter) {
		return null;
	}

	@Override
	public boolean addMusic(Music music, String gid) {
		if (music.getId() != null && music.getId().trim().length() > 0) {
			Music $music = this.readMusic(music.getId());
			if ($music != null) {
				$music.setImage(music.getImage());
				$music.setImageSha1(music.getImageSha1());
				music = $music;
			}
		}
		WeekService week = this.weekServiceDAO.readWeekService(gid);
		if (week != null) {
			music.setWeek(week);
		}
		return this.musicDAO.saveMusic(music);
	}

	@Override
	public PageResult readAllPages(int pageSize, int page, String gid) {
		PageResult rows = this.musicDAO.readAllPages(pageSize, page, gid);
		if (rows != null && rows.getRows().size() > 0) {
			List<Music> list = new ArrayList<Music>();
			for (Object o : rows.getRows()) {
				Music music = (Music) o;
				music.setImage(Constants.DEFAULT_HTTPIMAGES + "/" + Constants.sha1ToPath(music.getImageSha1()).replace(File.separator, "/") + "/" + music.getImage());
				music.setFile(Constants.DEFAULT_HTTPMUSIC + "/" + Constants.sha1ToPath(music.getFileSha1()).replace(File.separator, "/") + "/" + music.getFile());
				list.add(music);
			}
			rows.setRows(list);
		}
		return rows;
	}

	@Override
	public boolean deleteMusic(String id) {
		Music music = this.readMusic(id);
		if (music != null) {
			music.setStatus(EntityStatus.DELETED);
			this.musicDAO.saveMusic(music);
			return true;
		}
		return false;
	}

	public Music readMusic(String id) {
		return this.musicDAO.readMusic(id);
	}

	@Override
	public boolean editFiles(String id, String field, String value) {
		return this.musicDAO.editFiles(id, field, value);
	}
}
