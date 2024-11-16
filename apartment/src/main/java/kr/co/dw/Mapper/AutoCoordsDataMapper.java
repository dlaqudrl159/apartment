package kr.co.dw.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.dw.Domain.ParentRegionName;
import kr.co.dw.Dto.Common.AptCoordsDto;

@Mapper
public interface AutoCoordsDataMapper {

	void insertCoodrs(AptCoordsDto aptCoordsDto);

	List<AptCoordsDto> getList(String tableName);

	AptCoordsDto getCoodrs(AptCoordsDto aptCoordsDto);
//--------------------------------------------------------------
	AptCoordsDto getCoodrsDto(AptCoordsDto aptCoordsDto);

	List<AptCoordsDto> getParentRegionAptCoodrsDtoList(ParentRegionName parentRegionName);

	List<AptCoordsDto> getParentRegionAptCoordsDtoList(ParentRegionName parentRegionName);
	
}
