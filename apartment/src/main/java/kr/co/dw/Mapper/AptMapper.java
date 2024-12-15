package kr.co.dw.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.dw.Dto.Common.AptCoordsDto;
import kr.co.dw.Dto.Common.AptTransactionDto;
import kr.co.dw.Dto.Common.SearchRoadNamesDto;

@Mapper
public interface AptMapper {

	List<AptCoordsDto> getMarkers(Map<String, String> map);

	List<AptTransactionDto> getAptTransactionHistory(@Param("aptCoordsDto") AptCoordsDto aptCoordsDto,@Param("korSido") String korSido);
	
	List<String> getRoadName(@Param("aptCoordsDto") AptCoordsDto aptCoordsDto);

	List<AptCoordsDto> getRoadNames(@Param("searchRoadNamesDto") SearchRoadNamesDto searchRoadNamesDto);
	
}
