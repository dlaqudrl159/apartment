package kr.co.dw.Domain;

import java.util.List;

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
