package com.baofeng.carebay.service;

import java.util.HashMap;
import java.util.List;

import com.baofeng.carebay.entity.Products;
import com.baofeng.carebay.entity.ProductsDetails;
import com.baofeng.carebay.entity.ProductsOrder;
import com.baofeng.carebay.entity.ProductsOrderDetails;
import com.baofeng.commons.entity.Operator;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

public interface IProductsService {

	Products readProducts(String id);

	PageResult readPagesDetails(int rows, int page, String groupId);

	boolean addProductsDetails(ProductsDetails details, String groupId);

	List<Products> listProducts();

	boolean editFiles(String id, String field, String value);

	boolean addProducts(Products products, String category, String gid);

	boolean onLineProducts(String id, Integer online);

	PageResult readAllPagesOnline(int rows, int page, Object object);

	ProductsDetails readProductsDetails(String id);

	boolean addProductsContent(String id, String content);

	PageResult readPagesParams(int rows, int page, String groupId);

	boolean editParamsFiles(String id, String field, String value);

	boolean onLineProductsParams(String id);

	boolean addProductsParams(String groupId, String name1, String name2, String nameVal1, String nameVal2, String costPrice, String bazaarPrice, String sellPrice, String inventory);

	boolean deleteProductsImages(String id);

	ProductsDetails readProductsImages(String id);

	boolean saveIndexs(String id1, String groupId1, String indexs1);

	PageResult readAllPagesSkip(int rows, int page, SearchFilter filter, String queryFilter);

	boolean updateProducts(String id1, String indexs1);

	PageResult readAllPages(int rows, int page, String gid, SearchFilter filter);

	boolean deleteProducts(String id);

	boolean updateDisCount(String disIds, String discount);

	boolean addImportProducts(HashMap<String, List<String>> xlsFile, HashMap<String, String> picFile, Operator operator);

	PageResult readPagesOrders(int rows, int page, SearchFilter filter, String orderNo, String userId, String tranStatus,String chamber, String beginDT, String endDT);

	List<ProductsOrderDetails> readOrdersDetails(String gid);

	boolean updateStatus(String id, String status);

	boolean deleteOrderDetails(String id);

	ProductsOrderDetails readProductsOrderDetails(String id);

	boolean updateProductsOrderDetails(String id, String nums);

	boolean saveProductsOrdersDetails(String parentIds, String detailIds, String numbers);

	boolean saveOnLineTask(String ids, String onlineDT, String offlineDT);

	List<ProductsOrder> readProductsOrders(SearchFilter filter, String orderNo, String userId, String tranStatus,String chamber, String beginDT, String endDT);

}
