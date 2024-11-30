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
import kr.co.dw.Domain.RegionManager;
import kr.co.dw.Dto.Common.AptTransactionDto;
import kr.co.dw.Dto.Response.AutoAptDataResponse;
import kr.co.dw.Dto.Response.ProcessedRes;
import kr.co.dw.Exception.CustomException;
import kr.co.dw.Exception.ErrorCode.ErrorCode;
import kr.co.dw.Mapper.AutoAptDataMapper;
import kr.co.dw.Utils.AptUtils;
import kr.co.dw.Utils.DateUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutoAptDataServiceImpl implements AutoAptDataService {

	private final Logger logger = LoggerFactory.getLogger(AutoAptDataServiceImpl.class);

	private final AutoAptDataMapper autoAptDataMapper;
	private final SqlSessionFactory sqlSessionFactory;
	
	
	@Value("${api.apt.url}")
	private String API_APT_URL;

	@Value("${api.apt.service-key}")
	private String API_APT_SERVICE_KEY;

	private final Integer DELETE_YEAR = 12;

	@Override
	public List<AutoAptDataResponse> allAutoAptDataInsert() {

		return null;// new DataAutoInsertResponseDto("SUCCESS", null, "전체 지역 처리 완료", totalCount,
					// LocalDateTime.now(), totalResponse);
	}

	@Override
	public AutoAptDataResponse autoAptDataInsert(String korSido) {
		
		
		return syncAptTransactionData(korSido);
	}

	public AutoAptDataResponse syncAptTransactionData(String korSido) {
		
		List<ProcessedRes> processeds = processedAptData(korSido);
		Map<Boolean, List<ProcessedRes>> processedsMap = createProcessedsMap(processeds);
		List<ProcessedRes> successProcesseds = processedsMap.get(true); 
		List<ProcessedRes> failProcesseds = processedsMap.get(false); 
		
		deleteAndInsertData(successProcesseds,failProcesseds,korSido);
		
		return new AutoAptDataResponse(200, korSido + "지역 데이터 처리(INSERT, DELETE) 성공", 
				new Sido(korSido, AptUtils.toEngSido(korSido)), 
				failProcesseds, successProcesseds);
	}
	
	@Transactional
	public void deleteAndInsertData(List<ProcessedRes> successProcesseds, List<ProcessedRes> failProcesseds, String korSido) {
		try {
			String deleteDealYearMonth = DateUtils.makeDealYearMonth(DELETE_YEAR);
			autoAptDataMapper.deleteAptData(failProcesseds, korSido, deleteDealYearMonth);
			List<AptTransactionDto> AptTransactionDtos = createAptTransactionDtoList(successProcesseds);
			String result = aptTransactionDtoInsert(AptTransactionDtos,korSido);
		} catch (Exception e) {
			logger.error("DB 처리 중 오류 발생: {}", e.getMessage());
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	public List<AptTransactionDto> createAptTransactionDtoList(List<ProcessedRes> Processeds) {
		return Processeds.stream().flatMap(processed -> processed.getProcesedResData().stream()).collect(Collectors.toList());
	}
	
	public Map<Boolean, List<ProcessedRes>> createProcessedsMap(List<ProcessedRes> processeds) {
		return processeds.stream().collect(Collectors.groupingBy(processedDto -> processedDto.isSuccess()));
	}
	
	public List<ProcessedRes> processedAptData(String korSido) {
		List<ProcessedRes> processeds = new ArrayList<>();
		Sido sido = RegionManager.getSido(korSido);
		List<Sigungu> sigungus = RegionManager.getSigungus(korSido);
		List<String> dealYearMonths = DateUtils.makeDealYearMonthList(DELETE_YEAR);

		for (String dealYearMonth : dealYearMonths) {
			sigungus.forEach(sigungu -> {
				ProcessedRes processedRes = new ProcessedRes(null, sigungu, dealYearMonth, sido);
				processeds.add(callApi(processedRes)); 
			});
		}
		return processeds;
	}

	public ProcessedRes callApi(ProcessedRes processedRes) {

		int MAX_RETRIES = 3;
		int delayMs = 500;
		Element root = null;
		for (int tryCount = 1; tryCount <= MAX_RETRIES; tryCount++) {
			try {

				Thread.sleep(delayMs);
				StringBuilder sb = getRTMSDataSvcAptTradeDev(processedRes.getSigungu(), processedRes.getDealYearMonth());
				root = makeNodeList(sb);

				if (isResultMsg(root)) {
					NodeList nList = root.getElementsByTagName("item");
					logger.info("code={} name={} dealyearmonth={}" , processedRes.getSigungu().getCode(), processedRes.getSigungu().getName(), processedRes.getDealYearMonth());
					List<AptTransactionDto> list = makeAptTransactionDto(nList, processedRes.getSido().getKorSido(), processedRes.getSigungu().getName());
					processedRes.addProcesedResData(list);
					processedRes.setMessage("success");
					return processedRes;
				}

			} catch (SAXException | ParserConfigurationException | IOException e) {
				logger.error("국토교통부 Api호출 중 예외 발생 재시도 {}회 시군구:{} 코드:{} Error Message:{}", tryCount,
						processedRes.getSigungu().getName(), processedRes.getSigungu().getCode(), e.getMessage());
				processedRes.setMessage("fail");
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				logger.error("국토교통부 Api호출 중 Thread Interrupted 발생 재시도 {}회 시군구:{} 코드:{} Error Message:{}", tryCount,
						processedRes.getSigungu().getName(), processedRes.getSigungu().getCode(), e.getMessage());
				processedRes.setMessage("fail");
				return processedRes;
			} catch (Exception e) {  // 추가된 부분
	            logger.error("예상치 못한 예외 발생 재시도 {}회 시군구:{} 코드:{} Error Message:{}", tryCount,
	                    processedRes.getSigungu().getName(), processedRes.getSigungu().getCode(), e.getMessage());
	            processedRes.setMessage("fail");
	        }

			if (tryCount == MAX_RETRIES) {
				isErrorMsg(root);
				logger.error("국토교통부 Api호출 재시도 횟수 초과 재시도 {}회 시군구:{} 코드:{} 날짜:{}", tryCount,processedRes.getSigungu().getCode(), processedRes.getSigungu().getName(), processedRes.getDealYearMonth());
				processedRes.setMessage("fail");
			}
			delayMs = delayMs * tryCount;
		}
		return processedRes;
	}

	public void isErrorMsg(Element eElement) {
		String errMsg = eElement.getElementsByTagName("errMsg").item(0) == null ? "-" :
			eElement.getElementsByTagName("errMsg").item(0).getTextContent();
		
		String returnAuthMsg = eElement.getElementsByTagName("returnAuthMsg").item(0) == null ? "-" :
			eElement.getElementsByTagName("returnAuthMsg").item(0).getTextContent();
		
		String returnReasonCode = eElement.getElementsByTagName("returnReasonCode").item(0) == null ? "-" :
			eElement.getElementsByTagName("returnReasonCode").item(0).getTextContent();
		logger.error("errMsg={} returnAuthMsg={} returnReasonCode={}" , errMsg, returnAuthMsg, returnReasonCode);
	}
	
	@Override
	public boolean isResultMsg(Element eElement) {
		String resultMsg = eElement.getElementsByTagName("resultMsg").item(0) == null ? "-"
				: eElement.getElementsByTagName("resultMsg").item(0).getTextContent();
		/*
		 * String resultTotalCount = eElement.getElementsByTagName("totalCount").item(0)
		 * == null ? "-" :
		 * eElement.getElementsByTagName("totalCount").item(0).getTextContent(); String
		 * resultCode = eElement.getElementsByTagName("resultCode").item(0) == null ?
		 * "-" : eElement.getElementsByTagName("resultCode").item(0).getTextContent();
		 * logger.info("resultMsg={} resultTotalCount={} resultCode={}", resultMsg,
		 * resultTotalCount,resultCode); String errMsg =
		 * eElement.getElementsByTagName("errMsg").item(0) == null ? "-" :
		 * eElement.getElementsByTagName("errMsg").item(0).getTextContent(); String
		 * returnAuthMsg = eElement.getElementsByTagName("returnAuthMsg").item(0) ==
		 * null ? "-" :
		 * eElement.getElementsByTagName("returnAuthMsg").item(0).getTextContent();
		 * String returnReasonCode =
		 * eElement.getElementsByTagName("returnReasonCode").item(0) == null ? "-" :
		 * eElement.getElementsByTagName("returnReasonCode").item(0).getTextContent();
		 * logger.error("errMsg={} returnAuthMsg={} returnReasonCode={}" , errMsg,
		 * returnAuthMsg, returnReasonCode);
		 */

		return resultMsg != "-" ? true : false;
	}

	@Override
	public StringBuilder getRTMSDataSvcAptTradeDev(Sigungu sigungu, String dealYearMonth) throws IOException {

		StringBuilder sb = null;
		StringBuilder urlBuilder = new StringBuilder(this.API_APT_URL); /* URL */

		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + this.API_APT_SERVICE_KEY); /* Service Key */
		urlBuilder.append("&" + URLEncoder.encode("LAWD_CD", "UTF-8") + "="
				+ URLEncoder.encode(sigungu.getCode(), "UTF-8")); /* 각 지역별 코드 */
		urlBuilder.append("&" + URLEncoder.encode("DEAL_YMD", "UTF-8") + "="
				+ URLEncoder.encode(dealYearMonth/* DEAL_YMD */, "UTF-8")); /* 월 단위 신고자료 */
		urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "="
				+ URLEncoder.encode("1", "UTF-8")); /* 페이지번호 */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
				+ URLEncoder.encode("10000", "UTF-8")); /* 한 페이지 결과 수 */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");

		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		return sb;
	}

	@Override
	public Element makeNodeList(StringBuilder sb) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Document document = null;
		Element root = null;

		builder = factory.newDocumentBuilder();
		document = builder.parse(new InputSource(new StringReader(sb.toString())));
		document.getDocumentElement().normalize();
		root = document.getDocumentElement();

		return root;
	}

	@Override
	public List<AptTransactionDto> makeAptTransactionDto(NodeList nList, String sido, String sigungu) {
		List<AptTransactionDto> aptTransactionDtoList = new ArrayList<>();
		
		String apiDealAmount = "dealAmount";
		String apiDealingGbn = "dealingGbn";
		String apiBuildYear = "buildYear";
		String apiDealYear = "dealYear";
		String apiAptDong = "aptDong";
		String apiRgstDate = "rgstDate";
		String apiSlerGbn = "slerGbn";
		String apiBuyerGbn = "buyerGbn";
		String apiUmdNm = "umdNm";
		String apiAptNm = "aptNm";
		String apiDealMonth = "dealMonth";
		String apiDealDay = "dealDay";
		String apiExcluUseAr = "excluUseAr";
		String apiEstateAgentSggNm = "estateAgentSggNm";
		String apiJibun = "jibun";
		String apiLandCd = "landCd";
		String apiFloor = "floor";
		String apiCdealDay = "cdealDay";
		String apiCdealType = "cdealType";
		String apiRoadNm = "roadNm";
		String apiBonbun = "bonbun";
		String apiBubun = "bubun";
		String apiRoadNmBonbun = "roadNmBonbun";
		String apiRoadNmBubun = "roadNmBubun";
		String apiSggCd = "sggCd";
		
		
		for(int i = 0 ; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			if(nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String DealAmount = getElementContent(eElement,apiDealAmount);
				String ReqgbN = getElementContent(eElement,apiDealingGbn);
				String BuildYear = getElementContent(eElement,apiBuildYear);
				String DealYear = getElementContent(eElement,apiDealYear);
				String ApartmentDong = getElementContent(eElement,apiAptDong);
				String RegistartionDate = getElementContent(eElement,apiRgstDate);
				String SellerGBN = getElementContent(eElement,apiSlerGbn);
				String BuyerGBN = getElementContent(eElement,apiBuyerGbn);
				String Dong = getElementContent(eElement,apiUmdNm);
				String ApartmentName = getElementContent(eElement,apiAptNm);
				String DealMonth = getElementContent(eElement,apiDealMonth);
				String DealDay = getElementContent(eElement,apiDealDay);
				String AreaforExcusiveUse = getElementContent(eElement,apiExcluUseAr);
				String RdealerLawdnm = getElementContent(eElement,apiEstateAgentSggNm);
				String Jibun = getElementContent(eElement,apiJibun);
				String RegionalCode = getElementContent(eElement,apiLandCd);
				String Floor = getElementContent(eElement,apiFloor);
				String CancleDealDay = getElementContent(eElement,apiCdealDay);
				String CancleDealType = getElementContent(eElement,apiCdealType);
				String RoadName = getElementContent(eElement,apiRoadNm);
				String Bonbun = getElementContent(eElement,apiBonbun);
				String Bubun = getElementContent(eElement,apiBubun);
				String RoadNameBonbun = getElementContent(eElement,apiRoadNmBonbun);
				String RoadNameBubun = getElementContent(eElement,apiRoadNmBubun);
				String SggCd = getElementContent(eElement,apiSggCd);
				
				aptTransactionDtoList.add(new AptTransactionDto(sido + " " + sigungu + " " +  Dong,
						Jibun,
						Bonbun,
						Bubun,
						ApartmentName,
						AreaforExcusiveUse,
						DealYear + String.format("%02d", Integer.parseInt(DealMonth)),
						DealDay,
						DealAmount,
						ApartmentDong,
						Floor,
						BuyerGBN,
						SellerGBN,
						BuildYear,
						makeRoadName(RoadName, RoadNameBonbun, RoadNameBubun),
						CancleDealDay,
						ReqgbN,
						RdealerLawdnm,
						RegistartionDate,
						SggCd));			
				}
		}
		return aptTransactionDtoList;
	}

	@Override
	public String getElementContent(Element element, String tagName) {
		Node node = element.getElementsByTagName(tagName).item(0);
		return node == null ? "-"
				: node.getTextContent().replaceAll("\"", "").trim().isEmpty() ? "-"
						: node.getTextContent().replaceAll("\"", "").trim();
	}

	@Override
	public String makeRoadName(String roadName, String roadNameBonbun, String roadNameBubun) {
		roadName = roadName.trim();
		if (roadName.equals("-")) {
			roadName = "";
		}
		if (roadNameBonbun.equals("-")) {
			roadNameBonbun = "";
		}
		if (roadNameBubun.equals("-")) {
			roadNameBubun = "";
		}

		roadNameBonbun = roadNameBonbun + "!";
		roadNameBubun = roadNameBubun + "!";

		roadNameBonbun = roadNameBonbun.replace("0", " ").trim().replace(" ", "0").replace("!", "");
		roadNameBubun = roadNameBubun.replace("0", " ").trim().replace(" ", "0").replace("!", "");

		if (roadNameBonbun.length() != 0) {
			roadName = roadName + " " + roadNameBonbun;
			if (roadNameBubun.length() != 0) {
				roadName = roadName + "-" + roadNameBubun;
			}
		} else if (roadNameBonbun.length() == 0) {
			if (roadNameBubun.length() != 0) {
				roadName = roadName + " " + roadNameBubun;
			}
		}
		roadName = roadName.trim();
		if (roadName.equals("")) {
			roadName = "-";
		}
		return roadName;
	}

	@Override
	public String aptTransactionDtoInsert(List<AptTransactionDto> list, String korSido) {
		if (list.isEmpty()) {
	        return "LIST IS EMPTY";
	    }
		final int BATCH_SIZE = 1000; // 적절한 배치 사이즈 설정
		SqlSession sqlSession = null;
		
		try {
			sqlSession = this.sqlSessionFactory.openSession(ExecutorType.BATCH);
			int count = 0;
			for (AptTransactionDto aptTransactionDto : list) {
				
				Map<String, Object> map = new HashMap<>();
				map.put("aptTransactionDto", aptTransactionDto);
				map.put("korSido", korSido);
				sqlSession.insert("kr.co.dw.Mapper.AutoAptDataMapper.dataInsert2", map);
				if (++count % BATCH_SIZE == 0) {
	                sqlSession.flushStatements();
	                logger.debug("Batch processed: {}/{}", count, list.size());
	            }
			}
				
			if (count % BATCH_SIZE != 0) {
				sqlSession.flushStatements();
			}
				
				sqlSession.commit();
				logger.info("Total processed: {}", count);
				return "SUCCESS";
			} catch (Exception e) {
		        logger.error("데이터 입력 실패: {}", e.getMessage());
		        if (sqlSession != null) {
		            sqlSession.rollback();
		        }
		        return "FAIL";
			} finally {
		        if (sqlSession != null) {
		            sqlSession.close();
		        }
		    }
	}

}
