package kr.co.dw.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AutoAptDataMapper {

	void deleteRegionYear(@Param("REGION") String regionName, @Param("YEAR") String dbYear);
	
	
}
