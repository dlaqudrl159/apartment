package kr.co.dw.Dto.Response;

import java.util.List;

import kr.co.dw.Dto.Common.AptCoordsDto;
import kr.co.dw.Dto.Common.AptTransactionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AptTransactionResponse {

	private List<Integer> years;
	private List<AptTransactionDto> aptTransactionDtos;
	private AptCoordsDto aptCoordsDto;
	
}
