package com.baofeng.carebay.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.baofeng.commons.entity.BaseEntity;

/**
 * 描述：商品订单表
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "productsOrder")
public class ProductsOrder extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 2533379324922475909L;

	/** 用户 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private CareBayUser user;
	/** 收货地址 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "address_id")
	private ShoppingAddress address;
	/** 配送方式 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "method_id")
	private ShoppingMethod method;
	/** 运费规则 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "ruled_id")
	private FreightRuled ruled;
	/** 订单号 */
	private String orderNo;
	/** 总金额 */
	private Float totalPrice;
	/** 总数量 */
	private Integer totalNumber;
	/** 总运费 */
	private Float freightPrice;
	/** 优惠金额 */
	private Float discountPrice;
	/** 实际付款 */
	private Float practicalPayment;
	/** 支付交易号 */
	private String transNo;
	/** 交易状态(0：等待处理，1：订单完成，2：订单失败) */
	private Integer tranStatus;
	/** 成交时间 */
	private Date TurnoverTime;
	/** 付款时间 */
	private Date paymentTime;
	/** 备注 */
	@Lob
	private String remarks;
	@Transient
	private String chamber;
	@Transient
	private Set<ProductsOrderDetails> detailsList;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public CareBayUser getUser() {
		return user;
	}

	public void setUser(CareBayUser user) {
		this.user = user;
	}

	public Float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Float getFreightPrice() {
		return freightPrice;
	}

	public void setFreightPrice(Float freightPrice) {
		this.freightPrice = freightPrice;
	}

	public Float getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(Float discountPrice) {
		this.discountPrice = discountPrice;
	}

	public Float getPracticalPayment() {
		return practicalPayment;
	}

	public void setPracticalPayment(Float practicalPayment) {
		this.practicalPayment = practicalPayment;
	}

	public ShoppingAddress getAddress() {
		return address;
	}

	public void setAddress(ShoppingAddress address) {
		this.address = address;
	}

	public ShoppingMethod getMethod() {
		return method;
	}

	public void setMethod(ShoppingMethod method) {
		this.method = method;
	}

	public FreightRuled getRuled() {
		return ruled;
	}

	public void setRuled(FreightRuled ruled) {
		this.ruled = ruled;
	}

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public Integer getTranStatus() {
		return tranStatus;
	}

	public void setTranStatus(Integer tranStatus) {
		this.tranStatus = tranStatus;
	}

	public Date getTurnoverTime() {
		return TurnoverTime;
	}

	public void setTurnoverTime(Date turnoverTime) {
		TurnoverTime = turnoverTime;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}

	public String getChamber() {
		return chamber;
	}

	public void setChamber(String chamber) {
		this.chamber = chamber;
	}

	public Set<ProductsOrderDetails> getDetailsList() {
		return detailsList;
	}

	public void setDetailsList(Set<ProductsOrderDetails> detailsList) {
		this.detailsList = detailsList;
	}
}
