package kr.co.dw.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.dw.Dto.Common.AptTransactionDto;
import kr.co.dw.Dto.Response.ProcessedRes;

@Mapper
public interface AutoAptDataMapper {

	void deleteAptData(@Param("failProcesseds") List<ProcessedRes> failProcesseds, @Param("korSido") String korSido, @Param("deleteDealYearMonth") String deleteDealYearMonth);

	void insertAptData(@Param("aptTransactionDto") AptTransactionDto aptTransactionDto, @Param("korSido") String korSido);
	
	
}
