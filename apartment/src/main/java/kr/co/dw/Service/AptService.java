package kr.co.dw.Service;

import java.util.List;

import kr.co.dw.Domain.NameCountDto;

public interface AptService {

	List<NameCountDto> get(List<String> arr, String year);

	

}
