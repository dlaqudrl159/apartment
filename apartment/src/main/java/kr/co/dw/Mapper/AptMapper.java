package kr.co.dw.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.dw.Domain.NameCountDto;

@Mapper
public interface AptMapper {

	List<NameCountDto> get(Map<String, String> map);
	
	List<NameCountDto> get2(Map<String, String> map);
	
}
