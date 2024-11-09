package kr.co.dw.Service;

import java.util.List;

import kr.co.dw.Domain.AptTransactionDto;
import kr.co.dw.Domain.AptTransactionResponseDto;
import kr.co.dw.Domain.AptLatLngDto;

public interface AptService {

	List<AptLatLngDto> getMarkers(List<String> addressnameArr);

	List<AptTransactionResponseDto> getAptTransactionResponseDtolist(AptLatLngDto AptLatLngDto);

	List<Integer> getTransactionYears(List<AptTransactionDto> getAptTrancsactionHistory);

	

	

	

}
