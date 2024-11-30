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
import kr.co.dw.Service.AutoData.Apt.AptDataPaser.AptDataParserService;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AutoAptDataRepository {

	
	private final Logger logger = LoggerFactory.getLogger(AutoAptDataRepository.class);

	
	private final AutoAptDataMapper autoAptDataMapper;
	private final AptDataParserService aptDataParserSerivce;
	//private final SqlSessionFactory sqlSessionFactory;
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
		//SqlSession sqlSession = null;
		
		try {
			//sqlSession = this.sqlSessionFactory.openSession(ExecutorType.BATCH);
			int count = 0;
			for (AptTransactionDto aptTransactionDto : list) {
				
				Map<String, Object> map = new HashMap<>();
				map.put("aptTransactionDto", aptTransactionDto);
				map.put("korSido", korSido);
				autoAptDataMapper.AptTransactionDtosInsert(aptTransactionDto, korSido);
				//sqlSession.insert("kr.co.dw.Mapper.AutoAptDataMapper.dataInsert2", map);
				if (++count % Constant.BATCH_SIZE == 0) {
					batchSqlSessionTemplate.flushStatements();
	                //sqlSession.flushStatements();
	                logger.debug("Batch processed: {}/{}", count, list.size());
	            }
			}
				
			if (count % Constant.BATCH_SIZE != 0) {
				batchSqlSessionTemplate.flushStatements();
				//sqlSession.flushStatements();
			}
				//sqlSession.commit();
				logger.info("Total processed: {}", count);
			} catch (Exception e) {
		        logger.error("데이터 입력 실패: {}", e.getMessage());
		        /*if (sqlSession != null) {
		            sqlSession.rollback();
		        }*/
		        new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
			} /*finally {
		        if (sqlSession != null) {
		            sqlSession.close();
		        }
		    }*/
	}
	
}
