package kr.co.dw.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.dw.Dto.Common.AptLatLngDto;

@Mapper
public interface AutoLatLngDataMapper {

	void insertLatLng(AptLatLngDto aptLatLngDto);

	List<AptLatLngDto> getList(String tableName);

	AptLatLngDto getLatLng(AptLatLngDto aptLatLngDto);
	
}
