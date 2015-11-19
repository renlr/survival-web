package com.baofeng.carebay.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.FunModuleDAO;
import com.baofeng.carebay.dao.PosterDAO;
import com.baofeng.carebay.dao.ProductsDAO;
import com.baofeng.carebay.dao.RecductListDAO;
import com.baofeng.carebay.dao.WeekServiceDAO;
import com.baofeng.carebay.entity.FunModule;
import com.baofeng.carebay.entity.Poster;
import com.baofeng.carebay.entity.Products;
import com.baofeng.carebay.entity.RecductList;
import com.baofeng.carebay.entity.WeekService;
import com.baofeng.carebay.service.IPosterService;
import com.baofeng.carebay.util.Constants;
import com.baofeng.utils.PageResult;

@Service("posterService")
public class PosterServiceImpl implements IPosterService {

	@Autowired
	private PosterDAO posterDAO;
	@Autowired
	private ProductsDAO productsDAO;
	@Autowired
	private FunModuleDAO funModuleDAO;
	@Autowired
	private RecductListDAO productCategoryDAO;
	@Autowired
	private WeekServiceDAO weekServiceDAO;

	public PosterDAO getPosterDAO() {
		return posterDAO;
	}

	public void setPosterDAO(PosterDAO posterDAO) {
		this.posterDAO = posterDAO;
	}

	public FunModuleDAO getFunModuleDAO() {
		return funModuleDAO;
	}

	public void setFunModuleDAO(FunModuleDAO funModuleDAO) {
		this.funModuleDAO = funModuleDAO;
	}

	public ProductsDAO getProductsDAO() {
		return productsDAO;
	}

	public void setProductsDAO(ProductsDAO productsDAO) {
		this.productsDAO = productsDAO;
	}

	public RecductListDAO getProductCategoryDAO() {
		return productCategoryDAO;
	}

	public void setProductCategoryDAO(RecductListDAO productCategoryDAO) {
		this.productCategoryDAO = productCategoryDAO;
	}

	public WeekServiceDAO getWeekServiceDAO() {
		return weekServiceDAO;
	}

	public void setWeekServiceDAO(WeekServiceDAO weekServiceDAO) {
		this.weekServiceDAO = weekServiceDAO;
	}

	@Override
	public PageResult readAllPages(int $rows, int page, String gid) {
		PageResult rows = this.posterDAO.readAllPages($rows, page, gid);
		if (rows != null && rows.getRows().size() > 0) {
			List<Poster> list = new ArrayList<Poster>();
			for (Object o : rows.getRows()) {
				Poster post = (Poster) o;
				post.setImage(Constants.DEFAULT_HTTPIMAGES + "/" + Constants.sha1ToPath(post.getImageSha1()).replace(File.separator, "/") + "/" + post.getImage());
				list.add(post);
			}
			rows.setRows(list);
		}
		return rows;
	}

	@Override
	public boolean addPoster(Poster post, String type, String ductCat, String products, String funModule, String activity, String gid) {
		if (post != null && post.getId() != null && post.getId().trim().length() > 0) {
			Poster $post = this.readPoster(post.getId());
			if ($post != null) {
				$post.setIndexs(post.getIndexs());
				$post.setType(post.getType());
				if (Integer.valueOf(type).intValue() == 1) {
					RecductList category = this.productCategoryDAO.readProductCat(ductCat);
					$post.setPrams(category.getId());
					$post.setPramsName(category.getName());
				} else if (Integer.valueOf(type) == 2) {
					Products ducts = this.productsDAO.readProducts(products);
					$post.setPrams(ducts.getId());
					$post.setPramsName(ducts.getName());
				} else if (Integer.valueOf(type) == 3) {
					FunModule module = this.funModuleDAO.readFunModule(funModule);
					$post.setPrams(module.getId());
					$post.setPramsName(module.getName());
				} else if (Integer.valueOf(type) == 4) {
					$post.setPrams(activity);
				}
				if (post.getImage() != null && post.getImageSha1() != null && post.getImage().trim().length() > 0 && post.getImageSha1().trim().length() > 0) {
					$post.setImage(post.getImage());
					$post.setImageSha1(post.getImageSha1());
				}
				post = $post;
			}
		} else {
			post.setType(Integer.valueOf(type));
			post.setOnline(Integer.valueOf(0));
			if (Integer.valueOf(type).intValue() == 1) {
				RecductList category = this.productCategoryDAO.readProductCat(ductCat);
				post.setPrams(category.getId());
				post.setPramsName(category.getName());
			} else if (Integer.valueOf(type) == 2) {
				Products ducts = this.productsDAO.readProducts(products);
				post.setPrams(ducts.getId());
				post.setPramsName(ducts.getName());
			} else if (Integer.valueOf(type) == 3) {
				FunModule module = this.funModuleDAO.readFunModule(funModule);
				post.setPrams(module.getId());
				post.setPramsName(module.getName());
			} else if (Integer.valueOf(type) == 4) {
				post.setPrams(activity);
			}
		}
		if (gid != null) {
			WeekService week = this.weekServiceDAO.readWeekService(gid);
			if (week != null) {
				post.setWeek(week);
				return this.posterDAO.savePoster(post);
			}
		}
		return false;
	}

	@Override
	public Poster readPoster(String id) {
		return this.posterDAO.readPoster(id);
	}

	@Override
	public boolean onLinePoster(String id) {
		Poster post = this.readPoster(id);
		if (post != null) {
			if (post.getOnline() == null) {
				post.setOnline(Integer.valueOf(0));
			}
			if (post.getOnline().intValue() == 0) {
				post.setOnline(Integer.valueOf(1));
			} else {
				post.setOnline(Integer.valueOf(0));
			}
			this.posterDAO.savePoster(post);
			return true;
		}
		return false;
	}

	@Override
	public boolean delPoster(String id) {
		Poster post = this.readPoster(id);
		if (post != null) {
			post.setWeek(null);
			this.posterDAO.savePoster(post);
			this.posterDAO.delPoster(post);
			return true;
		}
		return false;
	}

	@Override
	public boolean editIndexs(String id, String indexs) {
		Poster post = this.readPoster(id);
		if (post != null) {
			post.setIndexs(Integer.valueOf(indexs));
			this.posterDAO.savePoster(post);
			return true;
		}
		return false;
	}
}
