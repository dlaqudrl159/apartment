package kr.co.dw.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.dw.Domain.ApiDto;
import kr.co.dw.Domain.LatLngDto;
import kr.co.dw.Domain.NameCountDto;

@Mapper
public interface AptMapper {

	List<LatLngDto> getMarkers(Map<String, String> map);
	
	List<NameCountDto> getLatLngNameCountDto(@Param("lat") String lat,@Param("lng") String lng);

	List<ApiDto> getAptTrancsactionHistory(Map<String, String> map);
	
}
