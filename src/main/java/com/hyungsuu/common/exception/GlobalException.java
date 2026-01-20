package com.hyungsuu.common.exception;



@SuppressWarnings("serial")
public class GlobalException extends Exception {
	private String code;
	private String message;
	private Exception javaClass;

	public GlobalException(String code, String message, Exception e) {
		this.code = code;
		if(e == null) {
			this.message = message;
		} else {
			this.message = (message == null ? "" : message) + " (" + e.getMessage() + ")";
		}
		this.javaClass = e;
	}

	public GlobalException(String code, String message) {
		this.code =code;
		this.message = message;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
		if(javaClass != null)
			this.message += " (" + javaClass.getMessage() + ")";
	}

	public Exception getJavaClass() {
		return this.javaClass;
	}

	public void setJavaClass(Exception javaClass) {
		this.javaClass = javaClass;
	}


}