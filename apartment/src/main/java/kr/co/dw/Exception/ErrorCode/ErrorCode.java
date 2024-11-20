package kr.co.dw.Exception.ErrorCode;

public enum ErrorCode {

	//AutoAptDataController
	EMPTY_OR_NULL_Parameter("A001","empty_or_null_region", 400);


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
