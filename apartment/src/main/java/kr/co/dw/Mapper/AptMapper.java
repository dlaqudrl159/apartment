package kr.co.dw.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.dw.Domain.ApiDto;
import kr.co.dw.Domain.NameCountDto;

@Mapper
public interface AptMapper {

	
	ApiDto get();

	List<NameCountDto> get2();
	
}
