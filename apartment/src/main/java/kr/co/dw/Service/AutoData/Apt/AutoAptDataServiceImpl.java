package kr.co.dw.Service.AutoData.Apt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.dw.Domain.Sido;
import kr.co.dw.Domain.Sigungu;
import kr.co.dw.Constant.Constant;
import kr.co.dw.Domain.RegionManager;
import kr.co.dw.Dto.Common.AptTransactionDto;
import kr.co.dw.Dto.Common.ProcessedAutoAptDataDto;
import kr.co.dw.Dto.Response.AutoAptDataResponse;
import kr.co.dw.Exception.CustomException;
import kr.co.dw.Exception.ErrorCode.ErrorCode;
import kr.co.dw.Repository.Auto.AutoAptDataRepository;
import kr.co.dw.Service.AutoData.Apt.OpenApi.OpenApiService;
import kr.co.dw.Service.ParserAndConverter.ParserAndConverter;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutoAptDataServiceImpl implements AutoAptDataService {

	private final Logger logger = LoggerFactory.getLogger(AutoAptDataServiceImpl.class);

	private final ParserAndConverter aptDataParserService;
	private final OpenApiService openApiService;
	private final AutoAptDataRepository autoAptDataRepository;

	@Override
	public List<AutoAptDataResponse> allAutoAptDataInsert() {
		List<Sido> sidos = RegionManager.getSidos();
		List<AutoAptDataResponse> AutoAptDataResponses = new ArrayList<>();
		
		for(int i = 0 ; i < sidos.size() ; i++) {
			String korSido = sidos.get(i).getKorSido();
			try {
				AutoAptDataResponses.add(syncAptTransactionData(korSido));
			} catch (Exception e) {
				logger.error("korSido: {} 지역 데이터 삭제 입력(delete, insert) 실패" , korSido, e);
				AutoAptDataResponses.add(new AutoAptDataResponse(500, korSido + " 지역 데이터 삭제 입력(delete, insert) 실패",
						new Sido(korSido, RegionManager.toEngSido(korSido)), null, null));
			}
		}
		return AutoAptDataResponses;
	}

	@Override
	public AutoAptDataResponse autoAptDataInsert(String korSido) {
		
		return syncAptTransactionData(korSido);
	}

	@Transactional
	@Override
	public AutoAptDataResponse syncAptTransactionData(String korSido) {
		try {
			List<ProcessedAutoAptDataDto> processedAutoAptDataDtos = processedAptData(korSido);
			Map<Boolean, List<ProcessedAutoAptDataDto>> processedAutoAptDataDtosMap = aptDataParserService.createProcessedsMap(processedAutoAptDataDtos);
			List<ProcessedAutoAptDataDto> successProcessedAutoAptDataDtos = processedAutoAptDataDtosMap.get(true); 
			List<ProcessedAutoAptDataDto> failProcessedAutoAptDataDtos = processedAutoAptDataDtosMap.get(false); 
			
			String deleteDealYearMonth = aptDataParserService.createDealYearMonth(Constant.DELETE_YEAR);
			
			autoAptDataRepository.deleteAptData(failProcessedAutoAptDataDtos, korSido, deleteDealYearMonth);
			
			List<AptTransactionDto> aptTransactionDtos = aptDataParserService.createSuccessedAptTransactionDtos(successProcessedAutoAptDataDtos);
			
			batchProcessAptTransactionDtos(aptTransactionDtos, korSido);
			
			logger.info("korSido: {} 전체 행정구역 거래내역 데이터 삭제 입력 완료", korSido);
			return new AutoAptDataResponse(200, korSido + "지역 데이터 삭제 입력(delete, insert) 성공", 
					new Sido(korSido, RegionManager.toEngSido(korSido)), 
					failProcessedAutoAptDataDtos, successProcessedAutoAptDataDtos);
		} catch (Exception e) {
			logger.error("korSido: {} 아파트 거래내역 자동 입력 중 오류 발생", korSido, e);
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, "아파트 거래내역 자동 입력 중 오류 발생");
		}
		
	}
	
	@Override
	public List<ProcessedAutoAptDataDto> processedAptData(String korSido) {
		List<ProcessedAutoAptDataDto> processedAutoAptDataDtos = new ArrayList<>();
		Sido sido = RegionManager.getSido(korSido);
		if(sido == null) {
			logger.error("korSido 파라미터 시도 객체 변환 실패 파라미터 확인 요망 korSido: {}", korSido);
			throw new CustomException(ErrorCode.EMPTY_OR_NULL_Parameter);
		}
		List<Sigungu> sigungus = RegionManager.getSigungus(korSido);
		List<String> dealYearMonths = aptDataParserService.createDealYearMonths(Constant.DELETE_YEAR);

		for (String dealYearMonth : dealYearMonths) {
			sigungus.forEach(sigungu -> {
				ProcessedAutoAptDataDto processedAutoAptDataDto = new ProcessedAutoAptDataDto(null, sigungu, dealYearMonth, sido);
				processedAutoAptDataDtos.add(openApiService.callRTMSDataSvcAptTradeDev(processedAutoAptDataDto)); 
			});
		}
		return processedAutoAptDataDtos;
	}

	public void batchProcessAptTransactionDtos(List<AptTransactionDto> aptTransactionDtos, String korSido) {
		if (aptTransactionDtos.isEmpty()) {
			return;
	    }
		try {
			int count = 0;
			for (AptTransactionDto aptTransactionDto : aptTransactionDtos) {
				autoAptDataRepository.insertAptData(aptTransactionDto, korSido);
				if (++count % Constant.BATCH_SIZE == 0) {
					autoAptDataRepository.flushBatch();
	                logger.debug("korSido: {} Batch processed: {}/{}", korSido, count, aptTransactionDtos.size());
	            }
			}
			if (count % Constant.BATCH_SIZE != 0) {
				autoAptDataRepository.flushBatch();
			}
				logger.info("korSido: {} Total processed: {}", korSido, count);
			} catch (Exception e) {
		        logger.error("아파트 거래내역 배치 처리 중 오류 발생: {}", e);
		        throw e;
			}
	}
	
}
