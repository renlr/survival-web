package com.baofeng.carebay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.ProductsDAO;
import com.baofeng.carebay.dao.RecductListDAO;
import com.baofeng.carebay.entity.Products;
import com.baofeng.carebay.entity.RecductList;
import com.baofeng.carebay.entity.RecductListDetails;
import com.baofeng.carebay.service.IRecductListService;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("recductListService")
public class RecductListServiceImpl implements IRecductListService {

	@Autowired
	private ProductsDAO productsDAO;
	@Autowired
	private RecductListDAO recductListDAO;

	public ProductsDAO getProductsDAO() {
		return productsDAO;
	}

	public void setProductsDAO(ProductsDAO productsDAO) {
		this.productsDAO = productsDAO;
	}

	public RecductListDAO getRecductListDAO() {
		return recductListDAO;
	}

	public void setRecductListDAO(RecductListDAO recductListDAO) {
		this.recductListDAO = recductListDAO;
	}

	@Override
	public PageResult readAllPages(Integer pageSize, Integer currentPage, SearchFilter filter) {
		return this.recductListDAO.readAllPages(pageSize, currentPage, filter);
	}

	@Override
	public PageResult readPagesDetails(int rows, int page, String gid) {
		return this.recductListDAO.readPagesDetails(rows, page, gid);
	}

	@Override
	public boolean addProductCat(RecductList category) {
		if (category != null && category.getId() != null && category.getId().trim().length() > 0) {
			RecductList $category = this.readProductCat(category.getId());
			if ($category != null) {
				$category.setName(category.getName());
			}
		}
		return this.recductListDAO.saveProductCat(category);
	}

	@Override
	public RecductList readProductCat(String id) {
		return this.recductListDAO.readProductCat(id);
	}

	@Override
	public boolean deleteProductCat(String id) {
		RecductList category = this.readProductCat(id);
		if (category != null) {
			category.setStatus(EntityStatus.DELETED);
			this.recductListDAO.saveProductCat(category);
			return true;
		}
		return false;
	}

	@Override
	public boolean readProductCatByName(String name) {
		return this.recductListDAO.readProductCatByName(name);
	}

	@Override
	public boolean addProductCatDetails(String gid, String ids) {
		if (gid != null && ids != null && gid.trim().length() > 0 && ids.trim().length() > 0) {
			RecductList category = this.readProductCat(gid);
			if (category != null) {
				for (String id : ids.split(",")) {
					if (id != null && id.trim().length() > 0) {
						Products products = this.productsDAO.readProducts(id);
						RecductListDetails details = new RecductListDetails();
						details.setDuctCat(category);
						details.setProducts(products);
						this.recductListDAO.saveProductCatDetails(details);
					}
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteProductCatDetails(String gid, String ids) {
		if (gid != null && ids != null && gid.trim().length() > 0 && ids.trim().length() > 0) {
			RecductList category = this.readProductCat(gid);
			if (category != null) {
				for (String id : ids.split(",")) {
					if (id != null && id.trim().length() > 0) {
						RecductListDetails details = this.readProductCatDetails(id);
						details.setDuctCat(null);
						details.setProducts(null);
						this.deleteProductCatDetails(details);
					}
				}
			}
			return true;
		}
		return false;
	}

	private void deleteProductCatDetails(RecductListDetails details) {
		this.recductListDAO.deleteProductCatDetails(details);
	}

	private RecductListDetails readProductCatDetails(String id) {
		return this.recductListDAO.readProductCatDetails(id);
	}
}
