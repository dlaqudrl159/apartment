package kr.co.dw.Exception;

import kr.co.dw.Exception.ErrorCode.ErrorCode;

public class EmptyOrNullRegion extends CustomException{

	private String param;
	
	public EmptyOrNullRegion(ErrorCode errorCode) {
		super(errorCode);
	}

	public EmptyOrNullRegion(ErrorCode errorCode , String message) {
		super(errorCode, message);
	}
	
	public EmptyOrNullRegion(ErrorCode errorCode , String message , String param) {
		super(errorCode, message);
		this.param = param;
	}
}
