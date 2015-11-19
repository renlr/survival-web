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

import com.baofeng.commons.entity.BaseEntity;
import com.baofeng.utils.AccountStatus;

/**
 * 描述：馨月汇会员用户
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "carebayUser")
@SuppressWarnings("serial")
public class CareBayUser extends BaseEntity implements Serializable {

	/** 会员帐号,邮箱或手机号码 */
	@Column(length = 32)
	private String accounts;
	/** 登录密码,由大小写字母、数字组成，最少8 位、最长20 位 */
	@Column(length = 32)
	private String password;
	/** 昵称 */
	private String nickname;
	/** 会所房间 */
	private String room;
	/** 姓名 */
	private String name;
	/** 出生日期 */
	private Date birthDate;
	/** 家庭住址 */
	private String homeAddress;
	/** 邮箱 */
	private String email;
	/** 手机号码 */
	private String phone;
	/** 紧急联系人 */
	private String emContact;
	/** 紧急电话 */
	private String emPhone;
	/** 入所时间 */
	private Date checkInDT;
	/** 离所时间 */
	private Date checkOutDT;
	/** 帐号类型（0:邮箱,1:手机号码） */
	private Integer accountsType;
	/** 账号状态 */
	private AccountStatus accountStatus = AccountStatus.ENABLED;
	/** 亲属关系 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "family_id")
	private CareBayFamily family;
	/** 宝宝 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "baby_id")
	private CareBay carebay;
	/** 第三方帐号验证登录 */
	private Boolean third = false;
	/** 是否宝宝创建者(0:否，1：是) */
	private Integer isCreator = 0;
	/** 第三方验证key */
	private String thirdToken;
	/** 第三方登录（0：QQ,1：新浪微博） */
	private Integer thirdServ;
	/** 终端类型(IOS,ANDROID) */
	private String terminal;
	/** 移动设备Token */
	private String deviceToken;
	/** 当前用户token */
	private String token;
	/** 是否是会员(0:否，1：是) */
	private Integer vip;
	/** 预约帐号（0：否，1：是） */
	private Integer openfire;
	/** 会员开始时间 */
	private Date vipDt;
	/** 邀请(0:普通用户，1:邀请发起人,2:被邀请人) */
	private Integer invitation;
	/** 邀请码 */
	private String invitationCode;
	/** 所在会所 */
	private String chamber;
	/** 参与调查(0:未参，1：已参) */
	private Integer inquiry;

	public String getAccounts() {
		return accounts;
	}

	public void setAccounts(String accounts) {
		this.accounts = accounts;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getAccountsType() {
		return accountsType;
	}

	public void setAccountsType(Integer accountsType) {
		this.accountsType = accountsType;
	}

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

	public CareBayFamily getFamily() {
		return family;
	}

	public void setFamily(CareBayFamily family) {
		this.family = family;
	}

	public CareBay getCarebay() {
		return carebay;
	}

	public void setCarebay(CareBay carebay) {
		this.carebay = carebay;
	}

	public Boolean getThird() {
		return third;
	}

	public void setThird(Boolean third) {
		this.third = third;
	}

	public Integer getIsCreator() {
		return isCreator;
	}

	public void setIsCreator(Integer isCreator) {
		this.isCreator = isCreator;
	}

	public String getThirdToken() {
		return thirdToken;
	}

	public void setThirdToken(String thirdToken) {
		this.thirdToken = thirdToken;
	}

	public Integer getThirdServ() {
		return thirdServ;
	}

	public void setThirdServ(Integer thirdServ) {
		this.thirdServ = thirdServ;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getVip() {
		return vip;
	}

	public void setVip(Integer vip) {
		this.vip = vip;
	}

	public Integer getOpenfire() {
		return openfire;
	}

	public void setOpenfire(Integer openfire) {
		this.openfire = openfire;
	}

	public Date getVipDt() {
		return vipDt;
	}

	public void setVipDt(Date vipDt) {
		this.vipDt = vipDt;
	}

	public Integer getInvitation() {
		return invitation;
	}

	public void setInvitation(Integer invitation) {
		this.invitation = invitation;
	}

	public String getInvitationCode() {
		return invitationCode;
	}

	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}

	public String getChamber() {
		return chamber;
	}

	public void setChamber(String chamber) {
		this.chamber = chamber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getEmContact() {
		return emContact;
	}

	public void setEmContact(String emContact) {
		this.emContact = emContact;
	}

	public String getEmPhone() {
		return emPhone;
	}

	public void setEmPhone(String emPhone) {
		this.emPhone = emPhone;
	}

	public Date getCheckInDT() {
		return checkInDT;
	}

	public void setCheckInDT(Date checkInDT) {
		this.checkInDT = checkInDT;
	}

	public Date getCheckOutDT() {
		return checkOutDT;
	}

	public void setCheckOutDT(Date checkOutDT) {
		this.checkOutDT = checkOutDT;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public Integer getInquiry() {
		return inquiry;
	}

	public void setInquiry(Integer inquiry) {
		this.inquiry = inquiry;
	}
}
