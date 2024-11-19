package kr.co.dw.Exception;

import kr.co.dw.Exception.ErrorCode.ErrorCode;

public class EmptyOrNullParamException extends CustomException{

	private String param;
	
	public EmptyOrNullParamException(ErrorCode errorCode) {
		super(errorCode);
	}

	public EmptyOrNullParamException(ErrorCode errorCode , String message) {
		super(errorCode, message);
	}
	
	public EmptyOrNullParamException(ErrorCode errorCode , String message , String param) {
		super(errorCode, message);
		this.param = param;
	}
}
