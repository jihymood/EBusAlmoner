package com.xpro.ebusalmoner.entity;

public class NewVersionRoot {
	private boolean success;

	private String errorCode;

	private String msg;

	private NewVersionBody body;

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean getSuccess() {
		return this.success;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setBody(NewVersionBody body) {
		this.body = body;
	}

	public NewVersionBody getBody() {
		return this.body;
	}

}
