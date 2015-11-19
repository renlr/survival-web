package com.baofeng.carebay.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.ProductsDAO;
import com.baofeng.carebay.dao.RecductDAO;
import com.baofeng.carebay.dao.RecductListDAO;
import com.baofeng.carebay.entity.Products;
import com.baofeng.carebay.entity.Recduct;
import com.baofeng.carebay.entity.RecductDetails;
import com.baofeng.carebay.entity.RecductList;
import com.baofeng.carebay.service.IRecductService;
import com.baofeng.carebay.util.Constants;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("recductService")
public class RecductServiceImpl implements IRecductService {

	@Autowired
	private RecductDAO recductDAO;
	@Autowired
	private ProductsDAO productsDAO;
	@Autowired
	private RecductListDAO productCategoryDAO;

	public RecductDAO getRecductDAO() {
		return recductDAO;
	}

	public void setRecductDAO(RecductDAO recductDAO) {
		this.recductDAO = recductDAO;
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

	@Override
	public PageResult readAllPages(Integer pageSize, Integer currentPage, SearchFilter filter) {
		PageResult rows = this.recductDAO.readAllPages(pageSize, currentPage, filter);
		if (rows != null && rows.getRows().size() > 0) {
			List<Recduct> list = new ArrayList<Recduct>();
			for (Object o : rows.getRows()) {
				Recduct recduct = (Recduct) o;
				String $image = String.valueOf("");
				List<RecductDetails> images = this.readRecductDetailsByParentId(recduct.getId());
				if (images != null && images.size() > 0) {
					for (RecductDetails details : images) {
						$image += String.format("%s,%s", details.getIndexs(), details.getImage()) + "|";
					}
				}
				if ($image != null && $image.length() > 0)
					$image = $image.substring(0, $image.length() - 1);
				recduct.setImage($image);
				list.add(recduct);
			}
			rows.setRows(list);
		}
		return rows;
	}

	@Override
	public List<RecductDetails> readRecductDetailsByParentId(String id) {
		if (id != null && id.trim().length() > 0) {
			List<RecductDetails> $temp = new ArrayList<RecductDetails>();
			List<RecductDetails> images = this.recductDAO.readRecductDetailsByParentId(id);
			if (images != null && images.size() > 0) {
				for (RecductDetails details : images) {
					details.setImage(Constants.DEFAULT_HTTPIMAGES + "/" + Constants.sha1ToPath(details.getImageSha1()).replace(File.separator, "/") + "/" + details.getImage());
					$temp.add(details);
				}
				return $temp;
			}
		}
		return null;
	}

	@Override
	public boolean addRecduct(Recduct recduct, RecductDetails details) {
		try {
			this.recductDAO.saveRecduct(recduct);
			if (Integer.valueOf(2).intValue() == details.getType().intValue()) {
				Products products = this.productsDAO.readProducts(details.getParams());
				if (products != null) {
					details.setParamsName(products.getName());
				}
			}
			this.recductDAO.saveRecductDetails(details);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean onLinePoster(String id) {
		Recduct recduct = this.readRecduct(id);
		if (recduct != null) {
			if (recduct.getOnline() == null) {
				recduct.setOnline(Integer.valueOf(0));
			}
			if (recduct.getOnline().intValue() == 0) {
				recduct.setOnline(Integer.valueOf(1));
			} else {
				recduct.setOnline(Integer.valueOf(0));
			}
			this.recductDAO.saveRecduct(recduct);
			return true;
		}
		return false;
	}

	@Override
	public Recduct readRecduct(String id) {
		return this.recductDAO.readRecduct(id);
	}

	@Override
	public boolean delRecduct(String id) {
		Recduct recduct = this.recductDAO.readRecduct(id);
		if (recduct != null) {
			recduct.setStatus(EntityStatus.DELETED);
			this.recductDAO.saveRecduct(recduct);
			return true;
		}
		return false;
	}

	@Override
	public PageResult readPagesDetails(int rows, int page, String groupId) {
		PageResult $page = this.recductDAO.readPagesDetails(rows, page, groupId);
		if ($page != null && $page.getRows().size() > 0) {
			List<RecductDetails> list = new ArrayList<RecductDetails>();
			for (Object o : $page.getRows()) {
				RecductDetails details = (RecductDetails) o;
				details.setImage(Constants.DEFAULT_HTTPIMAGES + "/" + Constants.sha1ToPath(details.getImageSha1()).replace(File.separator, "/") + "/" + details.getImage());
				list.add(details);
			}
			$page.setRows(list);
		}
		return $page;
	}

	@Override
	public boolean addRecductDetails(RecductDetails details, String groupId) {
		Recduct recduct = this.readRecduct(groupId);
		if (Integer.valueOf(1).intValue() == details.getType().intValue()) {
			RecductList ductName = this.productCategoryDAO.readProductCat(details.getParams());
			if (ductName != null) {
				details.setParamsName(ductName.getName());
			}
		} else if (Integer.valueOf(2).intValue() == details.getType().intValue()) {
			Products products = this.productsDAO.readProducts(details.getParams());
			if (products != null) {
				details.setParamsName(products.getName());
			}
		}
		if (recduct != null) {
			details.setRecduct(recduct);
			return this.recductDAO.saveRecductDetails(details);
		}
		return false;
	}

	@Override
	public boolean deleteRecductDetails(String id) {
		RecductDetails details = this.readRecductDetails(id);
		if (details != null) {
			this.recductDAO.deleteRecductDetails(details);
			return true;
		}
		return false;
	}

	@Override
	public RecductDetails readRecductDetails(String id) {
		RecductDetails details = this.recductDAO.readRecductDetails(id);
		if (details != null) {
			return details;
		}
		return null;
	}

	@Override
	public boolean updateRecduct(String id1, String indexs1) {
		Recduct recduct = this.readRecduct(id1);
		if (recduct != null) {
			recduct.setIndexs(Integer.valueOf(indexs1));
			this.recductDAO.saveRecduct(recduct);
			return true;
		}
		return false;
	}

	@Override
	public boolean updateRecductDetails(String id1, String indexs1) {
		RecductDetails details = this.recductDAO.readRecductDetails(id1);
		if (details != null) {
			details.setIndexs(Integer.valueOf(indexs1));
			this.recductDAO.saveRecductDetails(details);
			return true;
		}
		return false;
	}

	@Override
	public boolean uploadImagesDetails(String id3, String model3, String type3, String ductCat3, String products3, String image, String imageSha1) {
		try {
			String endValue = model3;
			RecductDetails details = this.recductDAO.recductDetailsByIndexs(id3, endValue);
			if (details != null) {
				if(image != null && image.trim().length() > 0)
					details.setImage(image);
				if(imageSha1 != null && imageSha1.trim().length() > 0)
					details.setImageSha1(imageSha1);
				details.setType(Integer.valueOf(type3));
				if (Integer.valueOf(type3).intValue() == Integer.valueOf(1).intValue()) {
					RecductList ductName = this.productCategoryDAO.readProductCat(ductCat3);
					if (ductName != null) {
						details.setParams(ductName.getId());
						details.setParamsName(ductName.getName());
					}
				} else {
					details.setParams(products3);
					Products products = this.productsDAO.readProducts(products3);
					if (products != null) {
						details.setParams(products.getId());
						details.setParamsName(products.getName());
					}
				}
				this.recductDAO.saveRecductDetails(details);
			} else {
				Recduct recduct = this.readRecduct(id3);
				details = new RecductDetails();
				details.setImage(image);
				details.setRecduct(recduct);
				details.setImageSha1(imageSha1);
				details.setType(Integer.valueOf(type3));
				details.setIndexs(Integer.valueOf(endValue));
				if (Integer.valueOf(1).intValue() == details.getType().intValue()) {
					RecductList ductName = this.productCategoryDAO.readProductCat(ductCat3);
					if (ductName != null) {
						details.setParams(ductName.getId());
						details.setParamsName(ductName.getName());
					}
				} else if (Integer.valueOf(2).intValue() == details.getType().intValue()) {
					Products products = this.productsDAO.readProducts(products3);
					if (products != null) {
						details.setParams(products.getId());
						details.setParamsName(products.getName());
					}
				}
				this.recductDAO.saveRecductDetails(details);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
