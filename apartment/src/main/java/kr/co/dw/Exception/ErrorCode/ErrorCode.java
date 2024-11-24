package kr.co.dw.Exception.ErrorCode;

public enum ErrorCode {

	EMPTY_OR_NULL_Parameter("D001","empty_or_null_region", 400),
	
	
	INTERNAL_SERVER_ERROR("S001", "내부 서버 오류가 발생했습니다", 500),
	EXTERNAL_API_ERROR("S002", "외부 API 서비스가 일시적으로 불가능합니다", 503);
	
	
	private final String code;
	private final String message;
	private final int status;
	
	ErrorCode(String code, String message, int status) {
		this.code = code;
		this.message = message;
		this.status = status;
	}
	
    public String getCode() { return code; }
    public String getMessage() { return message; }
    public int getStatus() { return status; }
	
	
}
