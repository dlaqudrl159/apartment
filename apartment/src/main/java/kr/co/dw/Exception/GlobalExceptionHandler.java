package kr.co.dw.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kr.co.dw.Exception.ErrorCode.ErrorCode;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(CustomException.class)
	protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
		
		logger.error("HandleCustomException: {}", e.getMessage(), e);
		ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
            .status(errorCode.getStatus())
            .body(ErrorResponse.of(errorCode, e.getMessage()));
		
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	protected ResponseEntity<ErrorResponse> handleMissingServletRequestParameter(MissingServletRequestParameterException e) {
		ErrorResponse error = new ErrorResponse("400", "필수 파라미터가 누락되었습니다 " + e.getParameterName(), 400);
		logger.error("Required parameter is missing: {}", e.getParameterName());
        return ResponseEntity
            .status(error.getStatus())
            .body(error);
		
	}
	
}
