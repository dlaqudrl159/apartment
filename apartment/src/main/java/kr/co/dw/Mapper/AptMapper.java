package kr.co.dw.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.dw.Domain.AptTransactionDto;
import kr.co.dw.Domain.AptLatLngDto;

@Mapper
public interface AptMapper {

	List<AptLatLngDto> getMarkers(Map<String, String> map);

	List<AptTransactionDto> getAptTrancsactionHistory(@Param("AptLatLngDto") AptLatLngDto AptLatLngDto,@Param("tableName") String tableName);
	
	List<String> getRoadName(@Param("AptLatLngDto") AptLatLngDto AptLatLngDto);

	
	
}
