package kr.co.dw.Service.Apt;

import java.util.List;

import kr.co.dw.Dto.Common.AptCoordsDto;
import kr.co.dw.Dto.Common.AptTransactionDto;
import kr.co.dw.Dto.Response.AptTransactionResponse;

public interface AptService {

	List<AptCoordsDto> getMarkers(List<String> addresses);

	List<AptTransactionResponse> getAptTransactionResponseDtolist(AptCoordsDto aptCoordsDto);

	List<Integer> getTransactionYears(List<AptTransactionDto> getAptTrancsactionHistory);

	

	

	

}
