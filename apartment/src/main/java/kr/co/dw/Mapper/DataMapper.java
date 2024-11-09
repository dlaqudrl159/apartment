package kr.co.dw.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.dw.Domain.AptLatLngDto;

@Mapper
public interface DataMapper {

	void InsertLatLng(AptLatLngDto AptLatLngDto);

	List<AptLatLngDto> getList(String tableName);

	AptLatLngDto getLatLng(AptLatLngDto AptLatLngDto);

	void deleteRegionYear(@Param("REGION") String RegionName, @Param("YEAR") String DbYear);

	void DataInsert(Map<String, Object> map);

	
	
}
