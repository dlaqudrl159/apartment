package kr.co.dw.Dto.Response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.dw.Domain.Sido;
import kr.co.dw.Domain.Sigungu;
import kr.co.dw.Dto.Common.AptTransactionDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProcessedRes {
	/* 작업하면서 겪었던 고충 1. ProcessedDto가 메소드를 왔다갔다 하면서 전달해줘야 하는 데이터가 많아지니 (여러메소드의 return값 등) dto클래스가 늘어 나거나 ProcessedDto에 필드가 늘어남 
	 * dto파일이 너무 많아지는걸 고민하다 inner class활용을 생각도 해봄 실제로 해도 괜찮음
	 * ProcessedDto를 응답에도 사용할라 했는데 만약에 data도 응답하게 된다면 수만가지의 데이터도 같이 응답함 이건 아니라고 생각해서 inner클래스를 사용 할려했음
	 * 근데 @JsonIgnore가 있다는걸 알고 inner class대신 사용 응답시 @JsonIgnore가 붙으면 그건 응답(직렬화)하지 않음
	*/
	private String message;
	private Sigungu sigungu;
	private String dealYearMonth;
	private Sido sido;
	private int processedCount = 0;
	private boolean isSuccess = false;
	
	@JsonIgnore
	private final List<AptTransactionDto> procesedResData = new ArrayList<>();

	public ProcessedRes(String message, Sigungu sigungu, String dealYearMonth, Sido sido) {
		this.message = message;
		this.sigungu = sigungu;
		this.dealYearMonth = dealYearMonth;
		this.sido = sido;
	}
	
	public void addProcesedResData(List<AptTransactionDto> aptTransactionDto) {
		this.isSuccess = true;
		if(aptTransactionDto != null && !aptTransactionDto.isEmpty()) {
			this.procesedResData.addAll(aptTransactionDto);
			this.processedCount = aptTransactionDto.size();
		}else {
			this.processedCount = 0;
		}
		
	}


	
}
