package kr.co.dw.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.dw.Domain.ApiDto;
import kr.co.dw.Domain.NameCountDto;

@Mapper
public interface DataMapper {

	void InsertLatLng(NameCountDto nameCountDto);

	List<NameCountDto> getList(String tableName);

	NameCountDto get(NameCountDto nameCountDto);

	void deleteRegionYear(@Param("REGION") String RegionName, @Param("YEAR") String DbYear);

	void DataInsert(Map<String, Object> map);

	
	
}
