package kr.co.dw.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.dw.Domain.Sido;
import kr.co.dw.Dto.Common.AptCoordsDto;

@Mapper
public interface AutoCoordsDataMapper {

	void insertCoords(AptCoordsDto aptCoordsDto);

	AptCoordsDto getCoordsDto(AptCoordsDto aptCoordsDto);

	List<AptCoordsDto> getAptCoordsDtosBySido(@Param("korSido") String korSido);

	void notExistTransactionCoordsDelete(String korSido);



	
}
