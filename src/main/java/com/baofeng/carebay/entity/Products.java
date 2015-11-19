package com.baofeng.carebay.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.baofeng.commons.entity.BaseEntity;

/**
 * 描述：母婴商品
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "products")
public class Products extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 3453473792124631193L;
	/** 商品名称 */
	private String name;
	/** SEO名称 */
	private String subheading;
	/** 所属品牌 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "brand_id")
	private Brand brand;
	/** 供应商 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "supplier_id")
	private Supplier supplier;
	/** 销售属性 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "params_id")
	private ProductsParams params;
	/** 分类 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	private ProductsCategory category;
	/** 商品货号 */
	private String articleNumber;
	/** 销量 */
	private Long salesvolume;
	/** 库存数量 */
	private Long inventory;
	/** 限购数量 */
	private Long quotaNumber;
	/** 成本价格 */
	private Float costPrice;
	/** 市场价格 */
	private Float bazaarPrice;
	/** 销售价格 */
	private Float sellPrice;
	/** 生产日期 */
	private Date madeDate;
	/** 保质期(天) */
	private Integer expirationDate;
	/** 商品描述 */
	@Column(length = 2000)
	private String imageDetails;
	/** 上线（0:下线,1：上线） */
	private Integer online;
	/** 显示顺序 */
	private Integer indexs;
	/** 定时时间 */
	@Column(length = 200)
	private String taskDT;

	@Transient
	private String image;
	@Transient
	private String chamber;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public String getArticleNumber() {
		return articleNumber;
	}

	public void setArticleNumber(String articleNumber) {
		this.articleNumber = articleNumber;
	}

	public Long getInventory() {
		return inventory;
	}

	public void setInventory(Long inventory) {
		this.inventory = inventory;
	}

	public Long getQuotaNumber() {
		return quotaNumber;
	}

	public void setQuotaNumber(Long quotaNumber) {
		this.quotaNumber = quotaNumber;
	}

	public Float getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Float costPrice) {
		this.costPrice = costPrice;
	}

	public Float getBazaarPrice() {
		return bazaarPrice;
	}

	public void setBazaarPrice(Float bazaarPrice) {
		this.bazaarPrice = bazaarPrice;
	}

	public Float getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Float sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Date getMadeDate() {
		return madeDate;
	}

	public void setMadeDate(Date madeDate) {
		this.madeDate = madeDate;
	}

	public Integer getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Integer expirationDate) {
		this.expirationDate = expirationDate;
	}

	public ProductsParams getParams() {
		return params;
	}

	public void setParams(ProductsParams params) {
		this.params = params;
	}

	public String getImageDetails() {
		return imageDetails;
	}

	public void setImageDetails(String imageDetails) {
		this.imageDetails = imageDetails;
	}

	public String getSubheading() {
		return subheading;
	}

	public void setSubheading(String subheading) {
		this.subheading = subheading;
	}

	public ProductsCategory getCategory() {
		return category;
	}

	public void setCategory(ProductsCategory category) {
		this.category = category;
	}

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Long getSalesvolume() {
		return salesvolume;
	}

	public void setSalesvolume(Long salesvolume) {
		this.salesvolume = salesvolume;
	}

	public Integer getIndexs() {
		return indexs;
	}

	public void setIndexs(Integer indexs) {
		this.indexs = indexs;
	}

	public String getTaskDT() {
		return taskDT;
	}

	public void setTaskDT(String taskDT) {
		this.taskDT = taskDT;
	}

	public String getChamber() {
		return chamber;
	}

	public void setChamber(String chamber) {
		this.chamber = chamber;
	}


}
