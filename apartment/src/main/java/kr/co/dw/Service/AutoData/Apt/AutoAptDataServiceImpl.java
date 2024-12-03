package kr.co.dw.Service.AutoData.Apt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.co.dw.Domain.Sido;
import kr.co.dw.Domain.Sigungu;
import kr.co.dw.Constant.Constant;
import kr.co.dw.Domain.RegionManager;
import kr.co.dw.Dto.Response.AutoAptDataResponse;
import kr.co.dw.Dto.Response.ProcessedRes;
import kr.co.dw.Exception.CustomException;
import kr.co.dw.Exception.ErrorCode.ErrorCode;
import kr.co.dw.Mapper.AutoAptDataMapper;
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
	
	@Value("${api.apt.url}")
	private String API_APT_URL;

	@Value("${api.apt.service-key}")
	private String API_APT_SERVICE_KEY;

	@Override
	public List<AutoAptDataResponse> allAutoAptDataInsert() {
		List<Sido> sidos = RegionManager.getSidos();
		List<AutoAptDataResponse> AutoAptDataResponses = new ArrayList<>();
		
		sidos.forEach(sido -> {
			AutoAptDataResponses.add(autoAptDataInsert(sido.getKorSido()));
		});
		
		return AutoAptDataResponses;
	}

	@Override
	public AutoAptDataResponse autoAptDataInsert(String korSido) {
		
		
		return syncAptTransactionData(korSido);
	}

	@Override
	public AutoAptDataResponse syncAptTransactionData(String korSido) {
		
		List<ProcessedRes> processeds = processedAptData(korSido);
		Map<Boolean, List<ProcessedRes>> processedsMap = aptDataParserService.createProcessedsMap(processeds);
		List<ProcessedRes> successProcesseds = processedsMap.get(true); 
		List<ProcessedRes> failProcesseds = processedsMap.get(false); 
		
		autoAptDataRepository.deleteAndInsertData(successProcesseds,failProcesseds,korSido);
		logger.info("korSido={} 전체 행정구역 거래내역 데이터 삭제 입력 완료",korSido);
		return new AutoAptDataResponse(200, korSido + "지역 데이터 처리(INSERT, DELETE) 성공", 
				new Sido(korSido, RegionManager.toEngSido(korSido)), 
				failProcesseds, successProcesseds);
	}
	
	@Override
	public List<ProcessedRes> processedAptData(String korSido) {
		List<ProcessedRes> processeds = new ArrayList<>();
		Sido sido = RegionManager.getSido(korSido);
		if(sido == null) {
			logger.error("korSido 파라미터 시도 객체 변환 실패 파라미터 확인 요망 korSido={}", korSido);
			throw new CustomException(ErrorCode.EMPTY_OR_NULL_Parameter);
		}
		List<Sigungu> sigungus = RegionManager.getSigungus(korSido);
		List<String> dealYearMonths = aptDataParserService.createDealYearMonths(Constant.DELETE_YEAR);

		for (String dealYearMonth : dealYearMonths) {
			sigungus.forEach(sigungu -> {
				ProcessedRes processedRes = new ProcessedRes(null, sigungu, dealYearMonth, sido);
				processeds.add(openApiService.callRTMSDataSvcAptTradeDev(processedRes)); 
			});
		}
		return processeds;
	}

}
