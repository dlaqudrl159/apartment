package kr.co.dw.Repository.Auto;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.co.dw.Constant.Constant;
import kr.co.dw.Dto.Common.AptTransactionDto;
import kr.co.dw.Dto.Response.ProcessedRes;
import kr.co.dw.Exception.CustomException;
import kr.co.dw.Exception.CustomExceptions.DatabaseException;
import kr.co.dw.Exception.ErrorCode.ErrorCode;
import kr.co.dw.Mapper.AutoAptDataMapper;
import kr.co.dw.Service.ParserAndConverter.ParserAndConverter;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AutoAptDataRepository {
	
	private final Logger logger = LoggerFactory.getLogger(AutoAptDataRepository.class);
	
	private final AutoAptDataMapper autoAptDataMapper;
	private final SqlSessionTemplate batchSqlSessionTemplate;
	
	public void deleteAptData(List<ProcessedRes> failProcesseds, String korSido, String deleteDealYearMonth) {
		try {
			autoAptDataMapper.deleteAptData(failProcesseds, korSido, deleteDealYearMonth);
		} catch (Exception e) {
			logger.error("아파트 거래 내역 삭제(delete) 중 데이터베이스 오류 발생 korSido={} deleteDealYearMonth={}", korSido, deleteDealYearMonth, e);
			throw new DatabaseException(ErrorCode.DATABASE_ERROR);
		}
	}
	
	public void insertAptData(AptTransactionDto aptTransactionDto, String korSido) {
		try {
			autoAptDataMapper.insertAptData(aptTransactionDto, korSido);
		} catch (Exception e) {
			logger.error("아파트 거래 내역 삽입(insert) 중 데이터베이스 오류 발생 korSido={}", korSido, e);
			throw new DatabaseException(ErrorCode.DATABASE_ERROR);
		}
	}
	
	public void flushBatch() {
		try {
			batchSqlSessionTemplate.flushStatements();
		} catch (Exception e) {
			logger.error("아파트 거래 내역 삽입(insert) 배치 처리 중 오류 발생", e);
			throw new DatabaseException(ErrorCode.DATABASE_ERROR);
		}
	}
}
