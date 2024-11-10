package kr.co.dw.Service;

import java.util.List;

import kr.co.dw.Dto.Common.AptLatLngDto;
import kr.co.dw.Dto.Common.AptTransactionDto;
import kr.co.dw.Dto.Response.AptTransactionResponseDto;

public interface AptService {

	List<AptLatLngDto> getMarkers(List<String> addressnameArr);

	List<AptTransactionResponseDto> getAptTransactionResponseDtolist(AptLatLngDto AptLatLngDto);

	List<Integer> getTransactionYears(List<AptTransactionDto> getAptTrancsactionHistory);

	

	

	

}
