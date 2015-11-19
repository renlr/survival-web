package com.baofeng.utils;

import java.io.Serializable;

public class ResultMsg implements Serializable {

	private static final long serialVersionUID = 4268580517239051068L;

	private Integer resultStatus;

	private String resultMessage;

	public Integer getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(Integer resultStatus) {
		this.resultStatus = resultStatus;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

}
