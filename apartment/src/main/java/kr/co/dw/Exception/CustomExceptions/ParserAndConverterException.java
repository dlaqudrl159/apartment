package kr.co.dw.Exception.CustomExceptions;

import kr.co.dw.Exception.CustomException;
import kr.co.dw.Exception.ErrorCode.ErrorCode;

public class ParserAndConverterException extends CustomException{

	public ParserAndConverterException(ErrorCode errorCode, String message) {
		super(errorCode, message);
	}

}
