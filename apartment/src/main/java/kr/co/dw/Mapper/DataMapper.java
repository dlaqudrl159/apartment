package kr.co.dw.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.dw.Domain.NameCountDto;

@Mapper
public interface DataMapper {

	List<NameCountDto> get(String tableName);

	void insert(NameCountDto nameCountDto);

}
