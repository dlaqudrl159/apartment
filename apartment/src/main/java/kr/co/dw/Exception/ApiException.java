package kr.co.dw.Exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException{

    private int errorCode;
    
    public ApiException(String message) {
        super(message);
        this.errorCode = 500;
    }
    
    public ApiException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
	
}
