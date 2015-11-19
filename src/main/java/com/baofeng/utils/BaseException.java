package com.baofeng.utils;


@SuppressWarnings("serial")
public class BaseException extends RuntimeException {

	public BaseException() {
	}
	
	public BaseException(String msg) {
		super(msg);
	}
	
	public BaseException(Throwable tr) {
		super(tr);
	}

	public BaseException(String msg, Throwable tr) {
		super(msg, tr);
	}
}
