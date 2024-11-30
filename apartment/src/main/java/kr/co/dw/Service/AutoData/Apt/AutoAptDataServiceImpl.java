package kr.co.dw.Service.AutoData.Apt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import kr.co.dw.Domain.Sido;
import kr.co.dw.Domain.Sigungu;
import kr.co.dw.Constant.Constant;
import kr.co.dw.Domain.RegionManager;
import kr.co.dw.Dto.Common.AptTransactionDto;
import kr.co.dw.Dto.Response.AutoAptDataResponse;
import kr.co.dw.Dto.Response.ProcessedRes;
import kr.co.dw.Exception.CustomException;
import kr.co.dw.Exception.ErrorCode.ErrorCode;
import kr.co.dw.Mapper.AutoAptDataMapper;
import kr.co.dw.Repository.Auto.AutoAptDataRepository;
import kr.co.dw.Service.AutoData.Apt.AptDataPaser.AptDataParserService;
import kr.co.dw.Service.AutoData.Apt.OpenApi.OpenApiService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutoAptDataServiceImpl implements AutoAptDataService {

	private final Logger logger = LoggerFactory.getLogger(AutoAptDataServiceImpl.class);

	private final AutoAptDataMapper autoAptDataMapper;
	private final AptDataParserService aptDataParserService;
	private final OpenApiService openApiService;
	private final SqlSessionFactory sqlSessionFactory;
	private final AutoAptDataRepository autoAptDataRepository;
	
	@Value("${api.apt.url}")
	private String API_APT_URL;

	@Value("${api.apt.service-key}")
	private String API_APT_SERVICE_KEY;

	@Override
	public List<AutoAptDataResponse> allAutoAptDataInsert() {

		return null;// new DataAutoInsertResponseDto("SUCCESS", null, "전체 지역 처리 완료", totalCount,
					// LocalDateTime.now(), totalResponse);
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
		
		return new AutoAptDataResponse(200, korSido + "지역 데이터 처리(INSERT, DELETE) 성공", 
				new Sido(korSido, RegionManager.toEngSido(korSido)), 
				failProcesseds, successProcesseds);
	}
	
	@Override
	public List<ProcessedRes> processedAptData(String korSido) {
		List<ProcessedRes> processeds = new ArrayList<>();
		Sido sido = RegionManager.getSido(korSido);
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
