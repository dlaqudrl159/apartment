package kr.co.dw.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.dw.Domain.ParentRegionName;
import kr.co.dw.Dto.Common.AptCoordsDto;

@Mapper
public interface AutoCoordsDataMapper {

	void insertCoords(AptCoordsDto aptCoordsDto);

	List<AptCoordsDto> getList(String tableName);

	AptCoordsDto getCoords(AptCoordsDto aptCoordsDto);
//--------------------------------------------------------------

	List<AptCoordsDto> getParentRegionAptCoordsDtoList(ParentRegionName parentRegionName);

	AptCoordsDto getCoordsDto(AptCoordsDto aptCoordsDto);



	
}
