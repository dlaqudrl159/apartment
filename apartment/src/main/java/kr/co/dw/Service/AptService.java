package kr.co.dw.Service;

import java.util.List;

import kr.co.dw.Domain.ApiDto;
import kr.co.dw.Domain.AptTransactionResponseDto;
import kr.co.dw.Domain.NameCountDto;

public interface AptService {

	List<NameCountDto> get(List<String> addressnameArr);

	List<NameCountDto> getLatLngNameCountDto(String lat, String lng);

	AptTransactionResponseDto getAptTrancsactionHistory(String apartmentname, String bungi, String sigungu, String roadname);

	

	

	

}
