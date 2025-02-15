package kr.co.dw.Dto.Common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProcessedAutoCoordsDataDto {

	private String message;
	private AptCoordsDto aptCoordsDto;
	private boolean isSuccess = false;
	
	public ProcessedAutoCoordsDataDto(String message, AptCoordsDto aptCoordsDto) {
		this.message = message;
		this.aptCoordsDto = aptCoordsDto;
	}
	
	
	
}
