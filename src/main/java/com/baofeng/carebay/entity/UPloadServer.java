package com.baofeng.carebay.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.baofeng.commons.entity.BaseEntity;

/**
 * 描述：图片上传服务
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "upserver")
public class UPloadServer extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 9214310728201641325L;
	/** 主机 */
	private String host;
	/** 端口 */
	private String port;
	/** 上传文件服务器存储路径 */
	private String tusdata;
	/** 按时间分配法 */
	private Date takeDT;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getTusdata() {
		return tusdata;
	}

	public void setTusdata(String tusdata) {
		this.tusdata = tusdata;
	}

	public Date getTakeDT() {
		return takeDT;
	}

	public void setTakeDT(Date takeDT) {
		this.takeDT = takeDT;
	}
}
