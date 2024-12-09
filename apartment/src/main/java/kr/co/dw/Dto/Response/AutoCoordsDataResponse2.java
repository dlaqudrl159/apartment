package kr.co.dw.Dto.Response;

import java.util.List;

import kr.co.dw.Domain.Sido;
import kr.co.dw.Dto.Common.AptCoordsDto;
import kr.co.dw.Dto.Common.ProcessedAutoCoordsDataDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutoCoordsDataResponse2 {

	private int status;
	private String message;
	private Sido sido;
	private List<ProcessedAutoCoordsDataDto> failProcessedAutoCoordsDataDto;
	private List<ProcessedAutoCoordsDataDto> successProcessedAutoCoordsDataDto;
	
}
