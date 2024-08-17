package kr.co.dw.Mapper;

import java.util.List;

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

}
