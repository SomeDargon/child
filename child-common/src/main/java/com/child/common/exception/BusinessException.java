package com.child.common.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 8372652214797382977L;

	private ErrorCode errorCode;

	public BusinessException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public BusinessException(ErrorCode errorCode, Throwable ex) {
		super(errorCode.getMessage(), ex);
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
