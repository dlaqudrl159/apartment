package kr.co.dw.Exception.ErrorCode;

public enum ErrorCode {
	
	INTERNAL_SERVER_ERROR("SERVICE-1", "내부 서버 오류가 발생했습니다", 500),
	EXTERNAL_API_ERROR("SERVICE-2", "외부 API 서비스가 일시적으로 불가능합니다", 503),
	EMPTY_OR_NULL_Parameter("SERVICE-3","파라미터 요청이 적절하지 않습니다", 400),
	PARSER_AND_CONVERTER_ERROR("SERVICE-4","객체에 대한 파싱 또는 변환이 실패했습니다.", 500),
	
	DATABASE_ERROR("DB-001", "DB 처리 중 에러가 발생했습니다", 500);
	
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
