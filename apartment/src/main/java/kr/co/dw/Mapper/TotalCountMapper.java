package kr.co.dw.Mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TotalCountMapper {

	void insert(Map<String, String> makeMap);


	
	
}
