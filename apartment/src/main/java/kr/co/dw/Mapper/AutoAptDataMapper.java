package kr.co.dw.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AutoAptDataMapper {

	void deleteByRegionYear(@Param("regionName") String regionName, @Param("year") String year);
	
	
}
