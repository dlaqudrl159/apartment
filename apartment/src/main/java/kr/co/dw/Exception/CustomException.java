package kr.co.dw.Exception;

import kr.co.dw.Exception.ErrorCode.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
	
	private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CustomException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public CustomException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }
	
}
