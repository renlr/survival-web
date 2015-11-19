package com.baofeng.carebay.service;

import com.baofeng.carebay.entity.Poster;
import com.baofeng.utils.PageResult;

public interface IPosterService {

	boolean addPoster(Poster post, String type, String ductCat, String products, String funModule, String activity,String gid);

	Poster readPoster(String id);

	boolean onLinePoster(String id);

	boolean delPoster(String id);

	boolean editIndexs(String id, String indexs);

	PageResult readAllPages(int rows, int page, String gid);
}
