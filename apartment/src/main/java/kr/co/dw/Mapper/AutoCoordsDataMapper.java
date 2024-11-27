package kr.co.dw.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.dw.Domain.Sido;
import kr.co.dw.Dto.Common.AptCoordsDto;

@Mapper
public interface AutoCoordsDataMapper {

	void insertCoords(AptCoordsDto aptCoordsDto);

	List<AptCoordsDto> getParentRegionAptCoordsDtoList(Sido parentRegionName);

	AptCoordsDto getCoordsDto(AptCoordsDto aptCoordsDto);



	
}
