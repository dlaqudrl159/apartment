package kr.co.dw.Service.AutoData.Apt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import kr.co.dw.Domain.ParentRegionName;
import kr.co.dw.Domain.Region;
import kr.co.dw.Domain.RegionManager;
import kr.co.dw.Dto.Common.AptTransactionDto;
import kr.co.dw.Dto.Common.RegionYearDto;
import kr.co.dw.Dto.Response.DataAutoInsertResponseDto;
import kr.co.dw.Exception.ApiException;
import kr.co.dw.Mapper.AutoAptDataMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutoAptDataServiceImpl implements AutoAptDataService{

	private final Logger logger = LoggerFactory.getLogger(AutoAptDataServiceImpl.class);
	
	private final AutoAptDataMapper autoAptDataMapper;
	private final SqlSessionFactory sqlSessionFactory;
	
	@Value("${api.apt.url}")
	private String apturl;
	
	@Value("${api.apt.service-key}")
	private String aptserviceKey;
	
	private final Integer DELETEYEAR = 12;
	private final String PAGENO = "1";
	private final String NUMOFROWS = "10000";
	
	@Override
	public DataAutoInsertResponseDto autoDataInsert(String parentRegionName) {
		// TODO Auto-generated method stub
		if(parentRegionName == null) {
			return allex1(RegionManager.getInstance().getParentRegionNameList());
		}
		
		parentRegionName = parentRegionName.toUpperCase();
		
		List<Region> RegionList = RegionManager.getInstance().getListRegion(parentRegionName);
		if(RegionList == null) {
			throw new IllegalArgumentException("RegionList null 발생      유효하지 않은 지역명: " + parentRegionName);
		}
		return ex1(RegionList,RegionManager.getInstance().getParentNameByEng(parentRegionName));
	}
	@Override
	public DataAutoInsertResponseDto allex1(List<ParentRegionName> parentRegionList) {
		// TODO Auto-generated method stub
		List<DataAutoInsertResponseDto> totalResponse = new ArrayList<>();
		int totalCount = 0;
		for (ParentRegionName parentRegionName : parentRegionList) {
			try {
				List<Region> RegionList = RegionManager.getInstance().getListRegion(parentRegionName.getEngParentName());
				DataAutoInsertResponseDto response = ex1(RegionList,parentRegionName);
				
				if ("ERROR".equals(response.getStatus())) {
		               logger.error("{} 처리 실패", parentRegionName.getKorParentName());
		               return new DataAutoInsertResponseDto("ERROR", response.getRegionYearDto(), "전체 처리 중 " + parentRegionName.getKorParentName() + " 처리 실패", totalCount, LocalDateTime.now(), totalResponse);
		           }
				totalCount += response.getTotalCount();
				totalResponse.add(response);
				logger.info("{} 처리 완료: {}건", parentRegionName.getKorParentName(), response.getTotalCount());
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("{} 처리 중 예외 발생: {}", parentRegionName.getKorParentName(), e.getMessage());
				return new DataAutoInsertResponseDto("ERROR",null,"예상치 못한 오류 발생: " + e.getMessage(),totalCount,LocalDateTime.now(),totalResponse);
			}
			
		}
		
		return new DataAutoInsertResponseDto("SUCCESS", null, "전체 지역 처리 완료", totalCount, LocalDateTime.now(), totalResponse);
	}
	@Override
	public DataAutoInsertResponseDto ex1(List<Region> regionList, ParentRegionName parentRegionName) {
		// TODO Auto-generated method stub
		
		List<String> dealYearMonthList = makeDealYearMonthList(DELETEYEAR); 
		
		List<DataAutoInsertResponseDto> response = new ArrayList<>();
		
		List<AptTransactionDto> insertaptTransactionDtoList = new ArrayList<>();
		int totalCount = 0;
		
		for (String dealYearMonth : dealYearMonthList) {
				
			for (Region region : regionList) {
						RegionYearDto regionYearDto = new RegionYearDto(region, dealYearMonth, PAGENO, parentRegionName);
				try {
					//logger.info(dealYearMonth + " " + region.getRegionName() + "(" + region.getCode() + ")       시작");	
					StringBuilder sb = getRTMSDataSvcAptTradeDev(regionYearDto);
					
					Element eElement = makeNodeList(sb);
					if(isResultMsg(eElement)) {
						String resultMsg = eElement.getElementsByTagName("resultMsg").item(0) == null ? "-"
								: eElement.getElementsByTagName("resultMsg").item(0).getTextContent();
						String resultCode = eElement.getElementsByTagName("resultCode").item(0) == null ? "-"
								: eElement.getElementsByTagName("resultCode").item(0).getTextContent();
						NodeList nList = null;
						nList = eElement.getElementsByTagName("item");
						List<AptTransactionDto> aptTransactionDtoList = makeAptTransactionDto(nList, regionYearDto);
						insertaptTransactionDtoList.addAll(aptTransactionDtoList);
						response.add(new DataAutoInsertResponseDto(resultMsg, regionYearDto, resultCode , aptTransactionDtoList.size(), LocalDateTime.now()));
						logger.info(new DataAutoInsertResponseDto(resultMsg, regionYearDto, resultCode , aptTransactionDtoList.size(), LocalDateTime.now()).toString());
						totalCount += aptTransactionDtoList.size();
					} else {
						String errMsg = eElement.getElementsByTagName("errMsg").item(0) == null ? "-"
								: eElement.getElementsByTagName("errMsg").item(0).getTextContent();
						String returnAuthMsg = eElement.getElementsByTagName("returnAuthMsg").item(0) == null ? "-"
								: eElement.getElementsByTagName("returnAuthMsg").item(0).getTextContent();
						String returnReasonCode = eElement.getElementsByTagName("returnReasonCode").item(0) == null ? "-"
								: eElement.getElementsByTagName("returnReasonCode").item(0).getTextContent();
						System.out.println("errMsg = " + errMsg + " returnAuthMsg = " + returnAuthMsg + " returnReasonCode = " + returnReasonCode);
						response.add(new DataAutoInsertResponseDto(returnAuthMsg, regionYearDto,returnAuthMsg + " " + returnReasonCode, 0, LocalDateTime.now()));
						return new DataAutoInsertResponseDto("ERROR", regionYearDto, "처리 중 오류 발생", totalCount, LocalDateTime.now(), response);
					}
					
				} catch (Exception e) {
					// TODO: handle exception
	                   logger.error("처리 실패: region={}, yearMonth={}, error={}", region.getRegionName(), dealYearMonth, e.getMessage());						
	                   response.add(new DataAutoInsertResponseDto("ERROR", regionYearDto, e.getMessage(), 0, LocalDateTime.now()));
						
					   return new DataAutoInsertResponseDto("ERROR", regionYearDto, "처리 중 오류 발생", totalCount, LocalDateTime.now(), response);
				}

			}
				
		}
		
		String deleteYearMonth = makeDealYearMonth(DELETEYEAR);
		try {
			deleteByRegionYear(parentRegionName, deleteYearMonth);
			
			aptTransactionDtoInsert(insertaptTransactionDtoList, parentRegionName);
			
		} catch (Exception e) {
			// TODO: handle exception
			return new DataAutoInsertResponseDto("SQL ERROR", null, parentRegionName.getEngParentName() + " 테이블 작업중 오류 발생", totalCount, LocalDateTime.now(), response);
		}
		
		return new DataAutoInsertResponseDto("SUCCESS", null, parentRegionName.getEngParentName() + " 테이블 입력 완료", totalCount, LocalDateTime.now(), response);
	}
	
	@Override
	public boolean isResultMsg(Element eElement) {
		
		String resultMsg = eElement.getElementsByTagName("resultMsg").item(0) == null ? "-"
				: eElement.getElementsByTagName("resultMsg").item(0).getTextContent();
		
		return !resultMsg.equals("-");
	}
	
	@Transactional
	@Override
	public void deleteByRegionYear(ParentRegionName parentRegionName, String deleteYearMonth) {
		
		try {
			autoAptDataMapper.deleteByRegionYear(parentRegionName.getEngParentName(), deleteYearMonth);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e.getMessage());
		}
		
	}
	
	@Override
	public String makeDealYearMonth(int num) {
		// TODO Auto-generated method stub
		LocalDate now = LocalDate.now();
		String yearMonth = now.minusMonths(num).format(DateTimeFormatter.ofPattern("yyyyMM"));
		return yearMonth;
	}
	@Override
	public List<String> makeDealYearMonthList(int num) {
		// TODO Auto-generated method stub
	    List<String> dealYearMonthList = new ArrayList<>();
	    
	    for(int i = 0; i <= num; i++) {
	        String yearMonth = makeDealYearMonth(i);
	        dealYearMonthList.add(yearMonth);
	    }
	    
	    return dealYearMonthList;
	}
	@Override
	public StringBuilder getRTMSDataSvcAptTradeDev(RegionYearDto regionYearDto) throws IOException {
		// TODO Auto-generated method stub
		
		StringBuilder sb = null;
		StringBuilder urlBuilder = new StringBuilder(this.apturl); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + this.aptserviceKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("LAWD_CD","UTF-8") + "=" + URLEncoder.encode(regionYearDto.getRegion().getCode(), "UTF-8")); /*각 지역별 코드*/
        urlBuilder.append("&" + URLEncoder.encode("DEAL_YMD","UTF-8") + "=" + URLEncoder.encode(regionYearDto.getYear()/*DEAL_YMD*/, "UTF-8")); /*월 단위 신고자료*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(PAGENO, "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(NUMOFROWS, "UTF-8")); /*한 페이지 결과 수*/
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
	@Override
	public Element makeNodeList(StringBuilder sb) throws SAXException, IOException, ParserConfigurationException {
		// TODO Auto-generated method stub
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
	public List<AptTransactionDto> makeAptTransactionDto(NodeList nList, RegionYearDto regionYearDto) {
		// TODO Auto-generated method stub
		List<AptTransactionDto> AptTransactionDtoList = new ArrayList<>();
		
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
				
				AptTransactionDtoList.add(new AptTransactionDto(regionYearDto.getParentRegionName().getKorParentName() + " " + regionYearDto.getRegion().getRegionName() + " " +  Dong,
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
		
		return AptTransactionDtoList;
	}
	@Override
	public String getElementContent(Element element, String tagName) {
		// TODO Auto-generated method stub
	    Node node = element.getElementsByTagName(tagName).item(0);
	    return node == null ? "-" : 
	           node.getTextContent().replaceAll("\"", "").trim().isEmpty() ? "-" : 
	           node.getTextContent().replaceAll("\"", "").trim();
	}
	@Override
	public String makeRoadName(String roadName, String roadNameBonbun, String roadNameBubun) {
		// TODO Auto-generated method stub
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
	@Override
	public String aptTransactionDtoInsert(List<AptTransactionDto> list, ParentRegionName parentRegionName) {
		// TODO Auto-generated method stub
		if (list.isEmpty()) {
	        return "LIST IS EMPTY";
	    }
		final int BATCH_SIZE = 1000; // 적절한 배치 사이즈 설정
		SqlSession sqlSession = null;
		
		try {
			sqlSession = this.sqlSessionFactory.openSession(ExecutorType.BATCH);
			int count = 0;
			for (AptTransactionDto AptTransactionDto : list) {
				
				Map<String, Object> map = new HashMap<>();
				map.put("AptTransactionDto", AptTransactionDto);
				map.put("RegionName", parentRegionName.getEngParentName());
				sqlSession.insert("kr.co.dw.Mapper.AutoAptDataMapper.dataInsert", map);
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
