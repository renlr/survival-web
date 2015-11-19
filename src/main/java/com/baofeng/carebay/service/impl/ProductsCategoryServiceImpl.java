package com.baofeng.carebay.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.ProductsCategoryDAO;
import com.baofeng.carebay.dao.ProductsDAO;
import com.baofeng.carebay.entity.Chamber;
import com.baofeng.carebay.entity.Products;
import com.baofeng.carebay.entity.ProductsCategory;
import com.baofeng.carebay.service.IProductsCategoryService;
import com.baofeng.commons.dao.ChamberDAO;
import com.baofeng.commons.entity.Operator;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("productsCategoryService")
public class ProductsCategoryServiceImpl implements IProductsCategoryService {

	@Autowired
	private ProductsDAO productsDAO;
	@Autowired
	private ChamberDAO chamberDAO;
	@Autowired
	private ProductsCategoryDAO productsCategoryDAO;

	public ChamberDAO getChamberDAO() {
		return chamberDAO;
	}

	public void setChamberDAO(ChamberDAO chamberDAO) {
		this.chamberDAO = chamberDAO;
	}

	public ProductsCategoryDAO getProductsCategoryDAO() {
		return productsCategoryDAO;
	}

	public void setProductsCategoryDAO(ProductsCategoryDAO productsCategoryDAO) {
		this.productsCategoryDAO = productsCategoryDAO;
	}

	@Override
	public PageResult readAllPages(Integer pageSize, Integer currentPage, SearchFilter filter) {
		PageResult rows = this.productsCategoryDAO.readAllPages(pageSize, currentPage, filter);
		if (rows != null && rows.getRows().size() > 0) {
			List<ProductsCategory> list = new ArrayList<ProductsCategory>();
			for (Object o : rows.getRows()) {
				ProductsCategory details = (ProductsCategory) o;
				String cid = details.getOperator().getChamber();
				Chamber chamber = this.chamberDAO.readChamber(cid);
				if (chamber != null)
					details.setChamber(chamber.getName());
				list.add(details);
			}
			rows.setRows(list);
		}
		return rows;
	}

	@Override
	public boolean addProductsCategory(String id, String name, String navigtor, Operator operator) {
		ProductsCategory category = new ProductsCategory();
		if (id != null && id.trim().length() > 0) {
			ProductsCategory $category = this.readProductsCategory(id);
			if ($category != null) {
				$category.setName(name);
				$category.setNavigtor(navigtor);
				category = $category;
			}
		} else {
			category.setOperator(operator);
			category.setNavigtor(navigtor);
			category.setName(name);
		}
		return this.productsCategoryDAO.saveProductCategory(category);
	}

	@Override
	public ProductsCategory readProductsCategory(String id) {
		return this.productsCategoryDAO.readProductsCategory(id);
	}

	@Override
	public boolean delProductsCategory(String id) {
		if (id != null && id.trim().length() > 0) {
			ProductsCategory category = this.readProductsCategory(id);
			List<Products> list = this.productsDAO.readProductsList(id);
			if (category != null && list.size() == 0) {
				this.productsCategoryDAO.saveProductCategoryHql("update ProductsCategory set status = 0 where id = '" + category.getId() + "'");
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean readProductsCategoryByName(String name) {
		if (name != null && name.trim().length() > 0) {
			ProductsCategory category = this.productsCategoryDAO.readProductsCategoryByName(name);
			if (category != null) {
				return true;
			}
		}
		return false;
	}

	public ProductsDAO getProductsDAO() {
		return productsDAO;
	}

	public void setProductsDAO(ProductsDAO productsDAO) {
		this.productsDAO = productsDAO;
	}

}
