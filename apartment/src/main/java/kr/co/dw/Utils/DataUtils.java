package kr.co.dw.Utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import kr.co.dw.Domain.ApiDto;
import kr.co.dw.Domain.NameCountDto;

@Service
public class DataUtils {
	
	public List<ApiDto> test(String dataxml, String tableName, String sigungu, String sigungu2) throws ParserConfigurationException, SAXException, IOException  {
		// TODO Auto-generated method stub
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new StringReader(dataxml)));
		
		document.getDocumentElement().normalize();
		NodeList nList = document.getElementsByTagName("item");
		Element root = document.getDocumentElement();
		String resultMsg = root.getElementsByTagName("resultMsg").item(0) == null ? "-" : root.getElementsByTagName("resultMsg").item(0).getTextContent();
		String resultCode = root.getElementsByTagName("resultCode").item(0) == null ? "-" : root.getElementsByTagName("resultCode").item(0).getTextContent();
		String totalCount = root.getElementsByTagName("totalCount").item(0) == null ? "-" : root.getElementsByTagName("totalCount").item(0).getTextContent();
		System.out.println(resultMsg);
		System.out.println(resultCode);
		List<ApiDto> list = new ArrayList<>();
		for(int i = 0 ; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			ApiDto ApiDto = new ApiDto();
			if(nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				
				String DealAmount = eElement.getElementsByTagName("dealAmount").item(0) == null ? "-" : eElement.getElementsByTagName("dealAmount").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("dealAmount").item(0).getTextContent().replaceAll("\"", "").trim();  
				String ReqgbN = eElement.getElementsByTagName("dealingGbn").item(0) == null ? "-" : eElement.getElementsByTagName("dealingGbn").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("dealingGbn").item(0).getTextContent().replaceAll("\"", "").trim();  
				String BuildYear = eElement.getElementsByTagName("buildYear").item(0) == null ? "-" : eElement.getElementsByTagName("buildYear").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("buildYear").item(0).getTextContent().replaceAll("\"", "").trim();  
				String DealYear = eElement.getElementsByTagName("dealYear").item(0) == null ? "-" : eElement.getElementsByTagName("dealYear").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("dealYear").item(0).getTextContent().replaceAll("\"", "").trim();  
				String ApartmentDong = eElement.getElementsByTagName("aptDong").item(0) == null ? "-" : eElement.getElementsByTagName("aptDong").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("aptDong").item(0).getTextContent().replaceAll("\"", "").trim();  
				String RegistartionDate = eElement.getElementsByTagName("rgstDate").item(0) == null ? "-" : eElement.getElementsByTagName("rgstDate").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("rgstDate").item(0).getTextContent().replaceAll("\"", "").trim();  
				String SellerGBN = eElement.getElementsByTagName("slerGbn").item(0) == null ? "-" : eElement.getElementsByTagName("slerGbn").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("slerGbn").item(0).getTextContent().replaceAll("\"", "").trim();  
				String BuyerGBN = eElement.getElementsByTagName("buyerGbn").item(0) == null ? "-" : eElement.getElementsByTagName("buyerGbn").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("buyerGbn").item(0).getTextContent().replaceAll("\"", "").trim();  
				String Dong = eElement.getElementsByTagName("umdNm").item(0) == null ? "-" : eElement.getElementsByTagName("umdNm").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("umdNm").item(0).getTextContent().replaceAll("\"", "").trim();  
				String ApartmentName = eElement.getElementsByTagName("aptNm").item(0) == null ? "-" : eElement.getElementsByTagName("aptNm").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("aptNm").item(0).getTextContent().replaceAll("\"", "").trim();  
				String DealMonth = eElement.getElementsByTagName("dealMonth").item(0) == null ? "-" : eElement.getElementsByTagName("dealMonth").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("dealMonth").item(0).getTextContent().replaceAll("\"", "").trim();  
				String DealDay = eElement.getElementsByTagName("dealDay").item(0) == null ? "-" : eElement.getElementsByTagName("dealDay").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("dealDay").item(0).getTextContent().replaceAll("\"", "").trim();  
				String AreaforExcusiveUse = eElement.getElementsByTagName("excluUseAr").item(0) == null ? "-" : eElement.getElementsByTagName("excluUseAr").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("excluUseAr").item(0).getTextContent().replaceAll("\"", "").trim();  
				String RdealerLawdnm = eElement.getElementsByTagName("estateAgentSggNm").item(0) == null ? "-" : eElement.getElementsByTagName("estateAgentSggNm").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("estateAgentSggNm").item(0).getTextContent().replaceAll("\"", "").trim();  
				String Jibun = eElement.getElementsByTagName("jibun").item(0) == null ? "-" : eElement.getElementsByTagName("jibun").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("jibun").item(0).getTextContent().replaceAll("\"", "").trim();  
				String RegionalCode = eElement.getElementsByTagName("landCd").item(0) == null ? "-" : eElement.getElementsByTagName("landCd").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("landCd").item(0).getTextContent().replaceAll("\"", "").trim();  
				String Floor = eElement.getElementsByTagName("floor").item(0) == null ? "-" : eElement.getElementsByTagName("floor").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("floor").item(0).getTextContent().replaceAll("\"", "").trim();  
				String CancleDealDay = eElement.getElementsByTagName("cdealDay").item(0) == null ? "-" : eElement.getElementsByTagName("cdealDay").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("cdealDay").item(0).getTextContent().replaceAll("\"", "").trim();  
				String CancleDealType = eElement.getElementsByTagName("cdealType").item(0) == null ? "-" : eElement.getElementsByTagName("cdealType").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("cdealType").item(0).getTextContent().replaceAll("\"", "").trim();  
				String RoadName = eElement.getElementsByTagName("roadNm").item(0) == null ? "-" : eElement.getElementsByTagName("roadNm").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("roadNm").item(0).getTextContent().replaceAll("\"", "").trim();  
				String Bonbun = eElement.getElementsByTagName("bonbun").item(0) == null ? "-" : eElement.getElementsByTagName("bonbun").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("bonbun").item(0).getTextContent().replaceAll("\"", "").trim();  
				String Bubun = eElement.getElementsByTagName("bubun").item(0) == null ? "-" : eElement.getElementsByTagName("bubun").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("bubun").item(0).getTextContent().replaceAll("\"", "").trim();  
				String RoadNameBonbun = eElement.getElementsByTagName("roadNmBonbun").item(0) == null ? "-" : eElement.getElementsByTagName("roadNmBonbun").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("roadNmBonbun").item(0).getTextContent().replaceAll("\"", "").trim();  
				String RoadNameBubun = eElement.getElementsByTagName("roadNmBubun").item(0) == null ? "-" : eElement.getElementsByTagName("roadNmBubun").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("roadNmBubun").item(0).getTextContent().replaceAll("\"", "").trim();  
				
				
				StringBuilder sb = new StringBuilder();
				sb.append(DealAmount + " ");
				sb.append(ReqgbN + " ");
				sb.append(BuildYear + " ");
				sb.append(DealYear + " ");
				sb.append(ApartmentDong + " ");
				sb.append(RegistartionDate + " ");
				sb.append(SellerGBN + " ");
				sb.append(BuyerGBN + " ");
				sb.append(Dong + " ");
				sb.append(ApartmentName + " ");
				sb.append(DealMonth + " ");
				sb.append(DealDay + " ");
				sb.append(AreaforExcusiveUse + " ");
				sb.append(RdealerLawdnm + " ");
				sb.append(Jibun + " ");
				sb.append(RegionalCode + " ");
				sb.append(Floor + " ");
				sb.append(CancleDealDay + " ");
				sb.append(CancleDealType + " ");
				sb.append(RoadName);
				sb.append(RoadNameBonbun + " ");
				sb.append(RoadNameBubun+ " ");
				//System.out.println(sb.toString());
				
				ApiDto.setSIGUNGU(sigungu + " " + sigungu2 + " " +  Dong);
				ApiDto.setBUNGI(Jibun);				
				ApiDto.setBONBUN(Bonbun);
				ApiDto.setBUBUN(Bubun);
				ApiDto.setAPARTMENTNAME(ApartmentName);
				ApiDto.setAREAFOREXCLUSIVEUSE(AreaforExcusiveUse);
				ApiDto.setDEALYEARMONTH(DealYear + String.format("%02d", Integer.parseInt(DealMonth)));
				ApiDto.setDEALDAY(DealDay);
				ApiDto.setDEALAMOUNT(DealAmount);
				ApiDto.setAPARTMENTDONG(ApartmentDong);
				ApiDto.setFLOOR(Floor);
				ApiDto.setBUYERGBN(BuyerGBN);
				ApiDto.setSELLERGBN(SellerGBN);
				ApiDto.setBUILDYEAR(BuildYear);
				ApiDto.setROADNAME(makeroadname(RoadName, RoadNameBonbun, RoadNameBubun));
				ApiDto.setCANCLEDEALDAY(CancleDealDay);
				ApiDto.setREQGBN(ReqgbN);
				ApiDto.setRDEALERLAWDNM(RdealerLawdnm);
				ApiDto.setREGISTRATIONDATE(RegistartionDate);
				//System.out.println(ApiDto.toString());
				list.add(ApiDto);			
				}
		}
		return list;
		
	}
	public String makeroadname(String RoadName, String RoadNameBonbun, String RoadNameBubun) {
		RoadName = RoadName.trim();
		if(RoadName.equals("-")) {
			RoadName = "";
		}
		if(RoadNameBonbun.equals("-")) {
			RoadNameBonbun = "";
		}
		if(RoadNameBubun.equals("-")) {
			RoadNameBubun = "";
		}
		RoadNameBonbun = RoadNameBonbun + "!";
		RoadNameBubun = RoadNameBubun + "!";
		RoadNameBonbun = RoadNameBonbun.replace("0", " ").trim().replace(" ", "0").replace("!", "");
		
		RoadNameBubun = RoadNameBubun.replace("0", " ").trim().replace(" ", "0").replace("!", "");
		if(RoadNameBonbun.length() !=0 ) {
			RoadName = RoadName + " " + RoadNameBonbun;
			if(RoadNameBubun.length() != 0) {
				RoadName = RoadName + "-" + RoadNameBubun;
			}
		}else if(RoadNameBonbun.length() ==0 ) {
			if(RoadNameBubun.length() != 0) {
				RoadName = RoadName + " " + RoadNameBubun;
			}
		}
		RoadName = RoadName.trim();
		if(RoadName.equals("")) {
			RoadName = "-";
		}
		return RoadName;
		
	}
	
	public List<NameCountDto> makeNameCountDto(List<ApiDto> list) {
		
		List<NameCountDto> NameCountDtolist = new ArrayList<>();
		
		for(int i = 0 ; i < list.size() ; i++) {
			NameCountDto NameCountDto = new NameCountDto();
			String SIGUNGU = list.get(i).getSIGUNGU();
			String BUNGI = list.get(i).getBUNGI();
			String APARTMENTNAME = list.get(i).getAPARTMENTNAME();
			String ROADNAME = list.get(i).getROADNAME();			NameCountDto.setSIGUNGU(SIGUNGU);
			NameCountDto.setBUNGI(BUNGI);
			NameCountDto.setAPARTMENTNAME(APARTMENTNAME);
			NameCountDto.setROADNAME(ROADNAME);		
			NameCountDtolist.add(NameCountDto);
		}
		
		return NameCountDtolist;
		
	}
}
