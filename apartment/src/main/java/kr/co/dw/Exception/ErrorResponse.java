package kr.co.dw.Exception;

import kr.co.dw.Exception.ErrorCode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {

    private final String code;
    private final String message;
    private final int status;
	
    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(
            errorCode.getCode(),
            errorCode.getMessage(),
            errorCode.getStatus()
        );
    }

    public static ErrorResponse of(ErrorCode errorCode, String message) {
        return new ErrorResponse(
            errorCode.getCode(),
            message,
            errorCode.getStatus()
        );
    }
    
}
