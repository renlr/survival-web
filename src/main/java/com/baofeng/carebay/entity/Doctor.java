package com.baofeng.carebay.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.baofeng.commons.entity.BaseEntity;

/**
 * 描述：服务医师
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "doctor")
public class Doctor extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 453301264928787224L;

	/** 医师帐号 */
	private String doctorUser;
	/** 登录密码 */
	private String doctorPWD;
	/** 医师昵称 */
	private String doctorNickname;
	/** 服务描述 */
	private String describes;
	/** 医师等级(0：普通医师，1：会员医师，2：主任医师) */
	private Integer level;
	/** 上线服务(0：下线，1：上线) */
	private Integer online;
	/** 房间名称 */
	private String roomName;
	/** 房间标识 */
	private String roomIdentifd;
	/** 房间主题 */
	private String theme;
	/** 最大人数 */
	private Integer counts;

	public String getDoctorUser() {
		return doctorUser;
	}

	public void setDoctorUser(String doctorUser) {
		this.doctorUser = doctorUser;
	}

	public String getDoctorPWD() {
		return doctorPWD;
	}

	public void setDoctorPWD(String doctorPWD) {
		this.doctorPWD = doctorPWD;
	}

	public String getDoctorNickname() {
		return doctorNickname;
	}

	public void setDoctorNickname(String doctorNickname) {
		this.doctorNickname = doctorNickname;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomIdentifd() {
		return roomIdentifd;
	}

	public void setRoomIdentifd(String roomIdentifd) {
		this.roomIdentifd = roomIdentifd;
	}

	public String getDescribes() {
		return describes;
	}

	public void setDescribes(String describes) {
		this.describes = describes;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}

	public Integer getCounts() {
		return counts;
	}

	public void setCounts(Integer counts) {
		this.counts = counts;
	}
}
