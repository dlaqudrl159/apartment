package kr.co.dw.Dto.Response;

import java.util.List;

import kr.co.dw.Dto.Common.AptCoordsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutoCoordsDataResponse {

	private String status;
	private String code;
	private String message;
	private AptCoordsDto aptCoordsDto;
	private List<AutoCoordsDataResponse> response;
	
	/**
	 * @param status
	 * @param code
	 * @param message
	 * @param aptCoordsDto
	 */
	public AutoCoordsDataResponse(String status, String code, String message, AptCoordsDto aptCoordsDto) {
		this.status = status;
		this.code = code;
		this.message = message;
		this.aptCoordsDto = aptCoordsDto;
	}
	
	
	
}
