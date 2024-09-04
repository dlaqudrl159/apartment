package kr.co.dw.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.dw.Domain.ApiDto;
import kr.co.dw.Domain.NameCountDto;

@Mapper
public interface DataMapper {

	void insert(NameCountDto nameCountDto);

	List<NameCountDto> getList(String tableName);

	NameCountDto get(NameCountDto nameCountDto);

	void DataInsert(List<ApiDto> list);

	void DataInsert(@Param("list") List<ApiDto> list, @Param("tableName") String tableName);

	int getTotalCount(Map<String, String> map);

	void updateTotalCount(@Param("REGION")String RegionName, @Param("REGIONCODE")String LAWD_CD, @Param("YEAR")String DEAL_YMD, @Param("EnglishMonth")String EnglishMonth,
			@Param("totalCount")String totalCount);

	//void DeleteData(@Param("REGION") String RegionName, @Param("YEAR") String DEAL_YMD,@Param("LAWD_CD") String LAWD_CD);

	List<ApiDto> getOldApiDto(@Param("REGION") String RegionName,@Param("YEAR") String DEAL_YMD,@Param("LAWD_CD") String LAWD_CD);

	void DeleteData(@Param("ApiDto") ApiDto ApiDto, @Param("REGION") String RegionName,@Param("YEAR") String DEAL_YMD,@Param("LAWD_CD") String LAWD_CD);

	ApiDto SelectData(@Param("ApiDto") ApiDto ApiDto, @Param("REGION") String RegionName,@Param("YEAR") String DEAL_YMD,@Param("LAWD_CD") String LAWD_CD);

	void DeleteData2(@Param("ApiDto") ApiDto ApiDto, @Param("REGION") String RegionName);

	void deleteRegionYear(@Param("REGION") String RegionName, @Param("YEAR") String DbYear);

	
	
}
