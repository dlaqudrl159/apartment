package kr.co.dw.Service.AutoData.Apt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import kr.co.dw.Domain.Sido;
import kr.co.dw.Dto.Common.AptTransactionDto;
import kr.co.dw.Dto.Common.RegionYearDto;
import kr.co.dw.Dto.Response.AutoAptDataRes;
import kr.co.dw.Dto.Response.DataAutoInsertResponseDto;
import kr.co.dw.Dto.Response.ProcessedRes;
import kr.co.dw.Mapper.AutoAptDataMapper;
import kr.co.dw.Utils.DateUtils;
import kr.co.dw.sidosigungu.Constant;
import kr.co.dw.sidosigungu.AutoAptDto;
import kr.co.dw.sidosigungu.RegionManager;
import kr.co.dw.sidosigungu.Sigungu2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
public class AutoAptDataServiceImpl2 implements AutoAptDataService2{

	private final AutoAptDataMapper autoAptDataMapper;
	private final SqlSessionFactory sqlSessionFactory;
	
	private final Logger logger = LoggerFactory.getLogger(AutoAptDataServiceImpl2.class);
	
	@Value("${api.apt.url}")
	private  String API_APT_URL;
	
	@Value("${api.apt.service-key}")
	private  String API_APT_SERVICE_KEY;
	
	@Override
	public List<AutoAptDataRes> allAutoAptDataInsert() {
		return null;
	}

	@Override
	public AutoAptDataRes autoAptDataInsert(String korSido) {
		AutoAptDto autoAptDto = RegionManager.getAutoAptDto(korSido);
		List<String> dealYearMonths = DateUtils.makeDealYearMonthList(Constant.DELETE_YEAR); 
		List<ProcessedRes> list = processedAptData(autoAptDto,dealYearMonths);
		Map<Boolean, List<ProcessedRes>> processedsMap = createProcessedsMap(list);
		List<ProcessedRes> successProcesseds = processedsMap.get(true); 
		List<ProcessedRes> failProcesseds = processedsMap.get(false); 
		test(successProcesseds,failProcesseds,korSido);
		
		return null;//new AutoAptDataRes(200, "성공", failProcesseds, successProcesseds);
	}

	public void test(List<ProcessedRes> successProcesseds, List<ProcessedRes> failProcesseds, String korSido) {
		String deleteDealYearMonth = DateUtils.makeDealYearMonth(Constant.DELETE_YEAR);
		autoAptDataMapper.deleteAptData(failProcesseds, korSido, deleteDealYearMonth);
		List<AptTransactionDto> list = createAptTransactionDtoList(successProcesseds);
		aptTransactionDtoInsert(list,korSido);
		
	}
	
	public List<AptTransactionDto> createAptTransactionDtoList(List<ProcessedRes> Processeds) {
		return Processeds.stream().flatMap(processed -> processed.getProcesedResData().stream()).collect(Collectors.toList());
	}
	
	public Map<Boolean, List<ProcessedRes>> createProcessedsMap(List<ProcessedRes> processeds) {
		return processeds.stream().collect(Collectors.groupingBy(processedDto -> processedDto.isSuccess()));
	}
	
	public List<ProcessedRes> processedAptData(AutoAptDto autoAptDto, List<String> dealYearMonths) {
		List<ProcessedRes> processeds = new ArrayList<>();
		for (String dealYearMonth : dealYearMonths) {
			autoAptDto.getSigungus().stream().forEach(sigungu -> {
				ProcessedRes processedDto = null;//new ProcessedRes(null, sigungu, dealYearMonth, autoAptDto.getSiDo());
				processeds.add(callApi(processedDto));
			});
		}
		return processeds;
	}
	
	public boolean isResultMsg(Element eElement) {
		String resultMsg = eElement.getElementsByTagName("resultMsg").item(0) == null ? "-"
				: eElement.getElementsByTagName("resultMsg").item(0).getTextContent();
		/*String resultTotalCount = eElement.getElementsByTagName("totalCount").item(0) == null ? "-"
				: eElement.getElementsByTagName("totalCount").item(0).getTextContent();
		String resultCode = eElement.getElementsByTagName("resultCode").item(0) == null ? "-"
				: eElement.getElementsByTagName("resultCode").item(0).getTextContent();
		logger.info("resultMsg={} resultTotalCount={} resultCode={}", resultMsg, resultTotalCount,resultCode);
		String errMsg = eElement.getElementsByTagName("errMsg").item(0) == null ? "-"
				: eElement.getElementsByTagName("errMsg").item(0).getTextContent();
		String returnAuthMsg = eElement.getElementsByTagName("returnAuthMsg").item(0) == null ? "-"
				: eElement.getElementsByTagName("returnAuthMsg").item(0).getTextContent();
		String returnReasonCode = eElement.getElementsByTagName("returnReasonCode").item(0) == null ? "-"
				: eElement.getElementsByTagName("returnReasonCode").item(0).getTextContent();
		logger.error("errMsg={} returnAuthMsg={} returnReasonCode={}" , errMsg, returnAuthMsg, returnReasonCode);*/
		
		return resultMsg != "-" ? true : false;
	}
	
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
	
	public ProcessedRes callApi(ProcessedRes processedDto) {
		
		int MAX_RETRIES = 3;
		int delayMs = 500;
		
		for(int tryCount = 1 ; tryCount <= MAX_RETRIES ; tryCount++) {
			try {
				
				Thread.sleep(delayMs);
				StringBuilder sb = null;//getRTMSDataSvcAptTradeDev(processedDto.getSigungu(), processedDto.getDealYearMonth());
				Element root = makeNodeList(sb);
				
				if(isResultMsg(root)) { 
					NodeList nList = root.getElementsByTagName("item");
					logger.info("code={} name={}", processedDto.getSigungu().getCode(), processedDto.getSigungu().getName());
					List<AptTransactionDto> list = makeAptTransactionDto(nList, processedDto.getSido().getKorSido(), processedDto.getSigungu().getName());
					processedDto.addProcesedResData(list);
					processedDto.setMessage("success");
					return processedDto;
				}
				
			} catch (SAXException | ParserConfigurationException | IOException e) {
				logger.error("국토교통부 Api호출 중 예외 발생 재시도 {}회 시군구:{} 코드:{} Error Message:{}", tryCount,processedDto.getSigungu().getName(),processedDto.getSigungu().getCode(),e.getMessage());
				processedDto.setMessage("fail");
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				logger.error("국토교통부 Api호출 중 Thread Interrupted 발생 재시도 {}회 시군구:{} 코드:{} Error Message:{}", tryCount, processedDto.getSigungu().getName(),processedDto.getSigungu().getCode(),e.getMessage());
				processedDto.setMessage("fail");
				return processedDto;
			}
			
			if(tryCount == MAX_RETRIES) {
				logger.error("국토교통부 Api호출 재시도 횟수 초과 재시도 {}회" , tryCount);
				processedDto.setMessage("fail");
			}
			delayMs = delayMs * tryCount;
		}
		return processedDto;
	}
	
	public StringBuilder getRTMSDataSvcAptTradeDev(Sigungu2 sigungu , String dealYearMonth) throws IOException {
		
		StringBuilder sb = null;
		StringBuilder urlBuilder = new StringBuilder(this.API_APT_URL); /*URL*/
		
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + this.API_APT_SERVICE_KEY); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("LAWD_CD","UTF-8") + "=" + URLEncoder.encode(sigungu.getCode(), "UTF-8")); /*각 지역별 코드*/
        urlBuilder.append("&" + URLEncoder.encode("DEAL_YMD","UTF-8") + "=" + URLEncoder.encode(dealYearMonth/*DEAL_YMD*/, "UTF-8")); /*월 단위 신고자료*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(Constant.API_PAGE_NO, "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(Constant.API_NUM_OF_ROWS, "UTF-8")); /*한 페이지 결과 수*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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
	public String getElementContent(Element element, String tagName) {
	    Node node = element.getElementsByTagName(tagName).item(0);
	    return node == null ? "-" : 
	           node.getTextContent().replaceAll("\"", "").trim().isEmpty() ? "-" : 
	           node.getTextContent().replaceAll("\"", "").trim();
	}
	
	public String makeRoadName(String roadName, String roadNameBonbun, String roadNameBubun) {
		roadName = roadName.trim();
		if (roadName.equals("-")) {roadName = "";}
		if (roadNameBonbun.equals("-")) {roadNameBonbun = "";}
		if (roadNameBubun.equals("-")) {roadNameBubun = "";}
		
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
