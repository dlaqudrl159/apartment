package kr.co.dw.Service;

import java.util.List;
import java.util.Map;

import kr.co.dw.Domain.ApiDto;
import kr.co.dw.Domain.NameCountDto;

public interface AptService {

	ApiDto get();

	List<NameCountDto> get2();

}
