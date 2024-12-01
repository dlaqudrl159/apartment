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

import kr.co.dw.Dto.Common.AptTransactionDto;
import kr.co.dw.Dto.Response.ProcessedRes;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParserAndConverter {
	
	private static final Logger logger = LoggerFactory.getLogger(ParserAndConverter.class);

	
	public void isErrorMsg(Element eElement) {
		String errMsg = eElement.getElementsByTagName("errMsg").item(0) == null ? "-" :
			eElement.getElementsByTagName("errMsg").item(0).getTextContent();
		
		String returnAuthMsg = eElement.getElementsByTagName("returnAuthMsg").item(0) == null ? "-" :
			eElement.getElementsByTagName("returnAuthMsg").item(0).getTextContent();
		
		String returnReasonCode = eElement.getElementsByTagName("returnReasonCode").item(0) == null ? "-" :
			eElement.getElementsByTagName("returnReasonCode").item(0).getTextContent();
		logger.error("errMsg={} returnAuthMsg={} returnReasonCode={}" , errMsg, returnAuthMsg, returnReasonCode);
	}
	
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
	
	public String getElementContent(Element element, String tagName) {
		Node node = element.getElementsByTagName(tagName).item(0);
		return node == null ? "-"
				: node.getTextContent().replaceAll("\"", "").trim().isEmpty() ? "-"
						: node.getTextContent().replaceAll("\"", "").trim();
	}
	
	public List<AptTransactionDto> createAptTransactionDto(NodeList nList, String sido, String sigungu) {
		List<AptTransactionDto> aptTransactionDtos = new ArrayList<>();
		
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
				
				aptTransactionDtos.add(new AptTransactionDto(sido + " " + sigungu + " " +  Dong,
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
						SggCd));			
				}
		}
		return aptTransactionDtos;
	}
	
	public String createRoadName(String roadName, String roadNameBonbun, String roadNameBubun) {
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
