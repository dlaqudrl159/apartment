package kr.co.dw.Repository.Auto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.co.dw.Constant.Constant;
import kr.co.dw.Dto.Common.AptTransactionDto;
import kr.co.dw.Dto.Response.ProcessedRes;
import kr.co.dw.Exception.CustomException;
import kr.co.dw.Exception.ErrorCode.ErrorCode;
import kr.co.dw.Mapper.AutoAptDataMapper;
import kr.co.dw.Service.ParserAndConverter.ParserAndConverter;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AutoAptDataRepository {

	
	private final Logger logger = LoggerFactory.getLogger(AutoAptDataRepository.class);

	
	private final AutoAptDataMapper autoAptDataMapper;
	private final ParserAndConverter aptDataParserSerivce;
	private final SqlSessionTemplate batchSqlSessionTemplate;
	
	@Transactional
	public void deleteAndInsertData(List<ProcessedRes> successProcesseds, List<ProcessedRes> failProcesseds, String korSido) {
		try {
			String deleteDealYearMonth = aptDataParserSerivce.createDealYearMonth(Constant.DELETE_YEAR);
			autoAptDataMapper.deleteAptData(failProcesseds, korSido, deleteDealYearMonth);
			List<AptTransactionDto> AptTransactionDtos = aptDataParserSerivce.createSuccessedAptTransactionDtos(successProcesseds);
			aptTransactionDtoInsert(AptTransactionDtos,korSido);
		} catch (Exception e) {
			logger.error("DB 처리 중 오류 발생: {}", e.getMessage());
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
	}
	
	public void aptTransactionDtoInsert(List<AptTransactionDto> list, String korSido) {
		if (list.isEmpty()) {
			
	    }
		try {
			int count = 0;
			for (AptTransactionDto aptTransactionDto : list) {
				autoAptDataMapper.AptTransactionDtosInsert(aptTransactionDto, korSido);
				if (++count % Constant.BATCH_SIZE == 0) {
					batchSqlSessionTemplate.flushStatements();
	                logger.debug("Batch processed: {}/{}", count, list.size());
	            }
			}
			if (count % Constant.BATCH_SIZE != 0) {
				batchSqlSessionTemplate.flushStatements();
			}
				logger.info("Total processed: {}", count);
			} catch (Exception e) {
		        logger.error("데이터 입력 실패: {}", e.getMessage());
		        new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
			}
	}
	
}
