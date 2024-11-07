package kr.co.dw.Service;

import java.util.List;

import kr.co.dw.Domain.ApiDto;
import kr.co.dw.Domain.AptTransactionResponseDto;
import kr.co.dw.Domain.LatLngDto;
import kr.co.dw.Domain.NameCountDto;

public interface AptService {

	List<NameCountDto> getMarkers(List<String> addressnameArr);

	List<NameCountDto> getLatLngNameCountDto(String lat, String lng);

	List<AptTransactionResponseDto> getAptTransactionResponseDtolist(String lat, String lng);

	

	

	

}
