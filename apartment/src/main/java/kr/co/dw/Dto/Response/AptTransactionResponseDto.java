package kr.co.dw.Dto.Response;

import java.util.List;

import kr.co.dw.Dto.Common.AptLatLngDto;
import kr.co.dw.Dto.Common.AptTransactionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AptTransactionResponseDto {

	private List<Integer> years;
	private List<AptTransactionDto> AptTransactionDtoList;
	private AptLatLngDto AptLatLngDto;
	
}
