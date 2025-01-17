package kr.co.dw.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.dw.Dto.Common.AptTransactionDto;
import kr.co.dw.Dto.Common.ProcessedAutoAptDataDto;

@Mapper
public interface AutoAptDataMapper {

	void deleteAptData(@Param("failProcessedAutoAptDataDtos") List<ProcessedAutoAptDataDto> failProcessedAutoAptDataDtos, @Param("korSido") String korSido, @Param("deleteDealYearMonth") String deleteDealYearMonth);

	void insertAptData(@Param("aptTransactionDto") AptTransactionDto aptTransactionDto, @Param("korSido") String korSido);
	
}
