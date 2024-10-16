package kr.co.dw.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.json.simple.parser.ParseException;

import kr.co.dw.Domain.ApiDto;
import kr.co.dw.Domain.NameCountDto;

public interface AptService {

	List<NameCountDto> get(List<String> arr, String year);

	List<NameCountDto> getLatLngNameCountDto(String lat, String lng);

	List<ApiDto> getAptTrancsactionHistory(String apartmentname, String bungi, String sigungu, String roadname);

	List<NameCountDto> get(List<String> addressnameArr);

	

	

}
