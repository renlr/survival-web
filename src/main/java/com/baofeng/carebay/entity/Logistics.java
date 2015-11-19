package com.baofeng.carebay.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.baofeng.commons.entity.BaseEntity;

/**
 * 描述：物流信息
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "logistics")
public class Logistics extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -9019484974042781956L;
	/** 收货地址： 任梁荣 ，13601655231 ， ，上海 上海市 徐汇区 宜山路1388号民润大厦11楼 ，201203 */
	private String address;
	/** 运送方式 */
	private String method;
	/** 物流公司 */
	private String corp;
	/** 运单号 */
	private String trackingNumber;
	/** 买家留言 */
	private String remarks;
	/** 物流查询接口 */
	private String corpUrl;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getCorp() {
		return corp;
	}

	public void setCorp(String corp) {
		this.corp = corp;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCorpUrl() {
		return corpUrl;
	}

	public void setCorpUrl(String corpUrl) {
		this.corpUrl = corpUrl;
	}
}
