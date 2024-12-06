package kr.co.dw.Service.ParserAndConverter;

import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import kr.co.dw.Constant.Constant;
import kr.co.dw.Dto.Common.AptTransactionDto;
import kr.co.dw.Dto.Response.ProcessedRes;
import kr.co.dw.Exception.CustomExceptions.ParserAndConverterException;
import kr.co.dw.Exception.ErrorCode.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParserAndConverter {
	
	private final Logger logger = LoggerFactory.getLogger(ParserAndConverter.class);
	
	public void isErrorMsg(Element eElement) {
		try {
			String errMsg = eElement.getElementsByTagName("errMsg").item(0) == null ? "-" :
				eElement.getElementsByTagName("errMsg").item(0).getTextContent();
			
			String returnAuthMsg = eElement.getElementsByTagName("returnAuthMsg").item(0) == null ? "-" :
				eElement.getElementsByTagName("returnAuthMsg").item(0).getTextContent();
			
			String returnReasonCode = eElement.getElementsByTagName("returnReasonCode").item(0) == null ? "-" :
				eElement.getElementsByTagName("returnReasonCode").item(0).getTextContent();
			logger.error("errMsg={} returnAuthMsg={} returnReasonCode={}" , errMsg, returnAuthMsg, returnReasonCode);
		} catch (Exception e) {
			logger.error("국토교통부 API 에러 응답 출력 오류 발생" , e);
			throw new ParserAndConverterException(ErrorCode.PARSER_AND_CONVERTER_ERROR);
		}
		
	}
	
	public boolean isResultMsg(Element eElement) {
		try {
			String resultMsg = eElement.getElementsByTagName("resultMsg").item(0) == null ? "-"
					: eElement.getElementsByTagName("resultMsg").item(0).getTextContent();

			return resultMsg != "-" ? true : false;
		} catch (Exception e) {
			logger.error("국토교통부 API 성공 응답 출력 오류 발생", e);
			throw new ParserAndConverterException(ErrorCode.PARSER_AND_CONVERTER_ERROR);
		}
		
	}
	
	public String getElementContent(Element element, String tagName) {
		try {
			Node node = element.getElementsByTagName(tagName).item(0);
			return node == null ? "-"
					: node.getTextContent().replaceAll("\"", "").trim().isEmpty() ? "-"
							: node.getTextContent().replaceAll("\"", "").trim();
		} catch (Exception e) {
			logger.error("국토교통부 API XML 응답 태그 내용 추출 중 오류 발생", e);
			throw new ParserAndConverterException(ErrorCode.PARSER_AND_CONVERTER_ERROR);
		}
		
	}
	
	public List<AptTransactionDto> createAptTransactionDtos(NodeList nList, String sido, String sigungu) {
		try {
			List<AptTransactionDto> aptTransactionDtos = new ArrayList<>();
			
			for(int i = 0 ; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				if(nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					aptTransactionDtos.add(createAptTransactionDto(eElement, sido, sigungu));			
					}
			}
			return aptTransactionDtos;
		} catch (Exception e) {
			logger.error("아파트 거래내역 목록 DTO 생성 중 오류 발생 sido: {} sigungu: {}" , sido, sigungu, e);
			throw new ParserAndConverterException(ErrorCode.PARSER_AND_CONVERTER_ERROR);
		}
	}
	
	public AptTransactionDto createAptTransactionDto(Element eElement, String sido, String sigungu) {
		
		try {
			String DealAmount = getElementContent(eElement,Constant.API_DEAL_AMOUNT);
			String ReqgbN = getElementContent(eElement,Constant.API_DEALING_GBN);
			String BuildYear = getElementContent(eElement,Constant.API_BUILD_YEAR);
			String DealYear = getElementContent(eElement,Constant.API_DEAL_YEAR);
			String ApartmentDong = getElementContent(eElement,Constant.API_APT_DONG);
			String RegistartionDate = getElementContent(eElement,Constant.API_RGST_DATE);
			String SellerGBN = getElementContent(eElement,Constant.API_SLER_GBN);
			String BuyerGBN = getElementContent(eElement,Constant.API_BUYER_GBN);
			String Dong = getElementContent(eElement,Constant.API_UMD_NM);
			String ApartmentName = getElementContent(eElement,Constant.API_APT_NM);
			String DealMonth = getElementContent(eElement,Constant.API_DEAL_MONTH);
			String DealDay = getElementContent(eElement,Constant.API_DEAL_DAY);
			String AreaforExcusiveUse = getElementContent(eElement,Constant.API_EXCLU_USEAR);
			String RdealerLawdnm = getElementContent(eElement,Constant.API_ESTATE_AGENT_SGG_NM);
			String Jibun = getElementContent(eElement,Constant.API_JIBUN);
			//String RegionalCode = getElementContent(eElement,Constant.API_LAND_CD);
			String Floor = getElementContent(eElement,Constant.API_FLOOR);
			String CancleDealDay = getElementContent(eElement,Constant.API_CDEAL_DAY);
			//String CancleDealType = getElementContent(eElement,Constant.API_CDEAL_TYPE);
			String RoadName = getElementContent(eElement,Constant.API_ROAD_NM);
			String Bonbun = getElementContent(eElement,Constant.API_BONBUN);
			String Bubun = getElementContent(eElement,Constant.API_BUBUN);
			String RoadNameBonbun = getElementContent(eElement,Constant.API_ROAD_NM_BONBUN);
			String RoadNameBubun = getElementContent(eElement,Constant.API_ROAD_NM_BUBUN);
			String SggCd = getElementContent(eElement,Constant.API_SGGCD);
			return new AptTransactionDto(sido + " " + sigungu + " " +  Dong,
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
					createRoadName(RoadName, RoadNameBonbun, RoadNameBubun),
					CancleDealDay,
					ReqgbN,
					RdealerLawdnm,
					RegistartionDate,
					SggCd);
		} catch (Exception e) {
			logger.error("단일 아파트 거래내역 DTO 생성 중 오류 발생 sido: {} sigungu: {}",sido, sigungu, e);
			throw new ParserAndConverterException(ErrorCode.PARSER_AND_CONVERTER_ERROR);
		}
	}
	
	public String createRoadName(String roadName, String roadNameBonbun, String roadNameBubun) {
		try {
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
		} catch (Exception e) {
			logger.error("도로명 주소 생성 중 오류 발생 roadName: {} roadNameBonbun: {} roadNameBubun: {]", roadName, roadNameBonbun, roadNameBubun, e);
			throw new ParserAndConverterException(ErrorCode.PARSER_AND_CONVERTER_ERROR);
		}
		
	}
	
	public Element createNodeList(StringBuilder sb) throws SAXException, IOException, ParserConfigurationException {
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
	
	public String createDealYearMonth(int num) {
		LocalDate now = LocalDate.now();
		String yearMonth = now.minusMonths(num).format(DateTimeFormatter.ofPattern("yyyyMM"));
		return yearMonth;
	}
	
	public List<String> createDealYearMonths(int num) {
	    List<String> dealYearMonths = new ArrayList<>();
	    
	    for(int i = 0; i <= num; i++) {
	        String yearMonth = createDealYearMonth(i);
	        dealYearMonths.add(yearMonth);
	    }
	    
	    return dealYearMonths;
	}
	
	public List<AptTransactionDto> createSuccessedAptTransactionDtos(List<ProcessedRes> successProcesseds) {
		return successProcesseds.stream().flatMap(successProcessed -> successProcessed.getProcesedResData().stream()).collect(Collectors.toList());
	}
	
	public Map<Boolean, List<ProcessedRes>> createProcessedsMap(List<ProcessedRes> processeds) {
		return processeds.stream().collect(Collectors.groupingBy(processedDto -> processedDto.isSuccess()));
	}
}
