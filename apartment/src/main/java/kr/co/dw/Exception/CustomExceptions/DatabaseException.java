package kr.co.dw.Exception.CustomExceptions;

import kr.co.dw.Exception.CustomException;
import kr.co.dw.Exception.ErrorCode.ErrorCode;

public class DatabaseException extends CustomException{

	public DatabaseException(ErrorCode errorCode) {
		super(errorCode);
	}
	
	public DatabaseException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public DatabaseException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
