package kr.co.dw.Utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
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

@Service
public class DataUtils {
	
	
	public void test(String dataxml) throws ParserConfigurationException, SAXException, IOException {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new StringReader(dataxml)));
		
		document.getDocumentElement().normalize();
		NodeList nList = document.getElementsByTagName("item");
		Element root = document.getDocumentElement();
		
		for(int i = 0 ; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			
			if(nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				
				String DealAmount = eElement.getElementsByTagName("거래금액").item(0) == null ? "-" : eElement.getElementsByTagName("거래금액").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("거래금액").item(0).getTextContent().trim();  
				String ReqgbN = eElement.getElementsByTagName("거래유형").item(0) == null ? "-" : eElement.getElementsByTagName("거래유형").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("거래유형").item(0).getTextContent().trim();  
				String BuildYear = eElement.getElementsByTagName("건축년도").item(0) == null ? "-" : eElement.getElementsByTagName("건축년도").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("건축년도").item(0).getTextContent().trim();  
				String DealYear = eElement.getElementsByTagName("년").item(0) == null ? "-" : eElement.getElementsByTagName("년").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("년").item(0).getTextContent().trim();  
				String ApartmentDong = eElement.getElementsByTagName("동").item(0) == null ? "-" : eElement.getElementsByTagName("동").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("동").item(0).getTextContent().trim();  
				String RegistartionDate = eElement.getElementsByTagName("등기일자").item(0) == null ? "-" : eElement.getElementsByTagName("등기일자").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("등기일자").item(0).getTextContent().trim();  
				String SellerGBN = eElement.getElementsByTagName("매도자").item(0) == null ? "-" : eElement.getElementsByTagName("매도자").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("매도자").item(0).getTextContent().trim();  
				String BuyerGBN = eElement.getElementsByTagName("매수자").item(0) == null ? "-" : eElement.getElementsByTagName("매수자").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("매수자").item(0).getTextContent().trim();  
				String Dong = eElement.getElementsByTagName("법정동").item(0) == null ? "-" : eElement.getElementsByTagName("법정동").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("법정동").item(0).getTextContent().trim();  
				String ApartmentName = eElement.getElementsByTagName("아파트").item(0) == null ? "-" : eElement.getElementsByTagName("아파트").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("아파트").item(0).getTextContent().trim();  
				String DealMonth = eElement.getElementsByTagName("월").item(0) == null ? "-" : eElement.getElementsByTagName("월").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("월").item(0).getTextContent().trim();  
				String DealDay = eElement.getElementsByTagName("일").item(0) == null ? "-" : eElement.getElementsByTagName("일").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("일").item(0).getTextContent().trim();  
				String AreaforExcusiveUse = eElement.getElementsByTagName("전용면적").item(0) == null ? "-" : eElement.getElementsByTagName("전용면적").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("전용면적").item(0).getTextContent().trim();  
				String RdealerLawdnm = eElement.getElementsByTagName("중개사소재지").item(0) == null ? "-" : eElement.getElementsByTagName("중개사소재지").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("중개사소재지").item(0).getTextContent().trim();  
				String Jibun = eElement.getElementsByTagName("지번").item(0) == null ? "-" : eElement.getElementsByTagName("지번").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("지번").item(0).getTextContent().trim();  
				String RegionalCode = eElement.getElementsByTagName("지역코드").item(0) == null ? "-" : eElement.getElementsByTagName("지역코드").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("지역코드").item(0).getTextContent().trim();  
				String Floor = eElement.getElementsByTagName("층").item(0) == null ? "-" : eElement.getElementsByTagName("층").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("층").item(0).getTextContent().trim();  
				String CancleDealDay = eElement.getElementsByTagName("해제사유발생일").item(0) == null ? "-" : eElement.getElementsByTagName("해제사유발생일").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("해제사유발생일").item(0).getTextContent().trim();  
				String CancleDealType = eElement.getElementsByTagName("해제여부").item(0) == null ? "-" : eElement.getElementsByTagName("해제여부").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("해제여부").item(0).getTextContent().trim();  
				
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
				sb.append(CancleDealType);
				sb.append(System.lineSeparator());
				System.out.println(sb.toString());
				
				ApiDto ApiDto = new ApiDto();
				
				
			}
		}
		
		
	}

	public void test(String dataxml, String tableName, String sigungu, String sigungu2) throws ParserConfigurationException, SAXException, IOException  {
		// TODO Auto-generated method stub
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new StringReader(dataxml)));
		
		document.getDocumentElement().normalize();
		NodeList nList = document.getElementsByTagName("item");
		Element root = document.getDocumentElement();
		
		for(int i = 0 ; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			
			if(nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				
				String DealAmount = eElement.getElementsByTagName("거래금액").item(0) == null ? "-" : eElement.getElementsByTagName("거래금액").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("거래금액").item(0).getTextContent().trim();  
				String ReqgbN = eElement.getElementsByTagName("거래유형").item(0) == null ? "-" : eElement.getElementsByTagName("거래유형").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("거래유형").item(0).getTextContent().trim();  
				String BuildYear = eElement.getElementsByTagName("건축년도").item(0) == null ? "-" : eElement.getElementsByTagName("건축년도").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("건축년도").item(0).getTextContent().trim();  
				String DealYear = eElement.getElementsByTagName("년").item(0) == null ? "-" : eElement.getElementsByTagName("년").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("년").item(0).getTextContent().trim();  
				String ApartmentDong = eElement.getElementsByTagName("동").item(0) == null ? "-" : eElement.getElementsByTagName("동").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("동").item(0).getTextContent().trim();  
				String RegistartionDate = eElement.getElementsByTagName("등기일자").item(0) == null ? "-" : eElement.getElementsByTagName("등기일자").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("등기일자").item(0).getTextContent().trim();  
				String SellerGBN = eElement.getElementsByTagName("매도자").item(0) == null ? "-" : eElement.getElementsByTagName("매도자").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("매도자").item(0).getTextContent().trim();  
				String BuyerGBN = eElement.getElementsByTagName("매수자").item(0) == null ? "-" : eElement.getElementsByTagName("매수자").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("매수자").item(0).getTextContent().trim();  
				String Dong = eElement.getElementsByTagName("법정동").item(0) == null ? "-" : eElement.getElementsByTagName("법정동").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("법정동").item(0).getTextContent().trim();  
				String ApartmentName = eElement.getElementsByTagName("아파트").item(0) == null ? "-" : eElement.getElementsByTagName("아파트").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("아파트").item(0).getTextContent().trim();  
				String DealMonth = eElement.getElementsByTagName("월").item(0) == null ? "-" : eElement.getElementsByTagName("월").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("월").item(0).getTextContent().trim();  
				String DealDay = eElement.getElementsByTagName("일").item(0) == null ? "-" : eElement.getElementsByTagName("일").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("일").item(0).getTextContent().trim();  
				String AreaforExcusiveUse = eElement.getElementsByTagName("전용면적").item(0) == null ? "-" : eElement.getElementsByTagName("전용면적").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("전용면적").item(0).getTextContent().trim();  
				String RdealerLawdnm = eElement.getElementsByTagName("중개사소재지").item(0) == null ? "-" : eElement.getElementsByTagName("중개사소재지").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("중개사소재지").item(0).getTextContent().trim();  
				String Jibun = eElement.getElementsByTagName("지번").item(0) == null ? "-" : eElement.getElementsByTagName("지번").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("지번").item(0).getTextContent().trim();  
				String RegionalCode = eElement.getElementsByTagName("지역코드").item(0) == null ? "-" : eElement.getElementsByTagName("지역코드").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("지역코드").item(0).getTextContent().trim();  
				String Floor = eElement.getElementsByTagName("층").item(0) == null ? "-" : eElement.getElementsByTagName("층").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("층").item(0).getTextContent().trim();  
				String CancleDealDay = eElement.getElementsByTagName("해제사유발생일").item(0) == null ? "-" : eElement.getElementsByTagName("해제사유발생일").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("해제사유발생일").item(0).getTextContent().trim();  
				String CancleDealType = eElement.getElementsByTagName("해제여부").item(0) == null ? "-" : eElement.getElementsByTagName("해제여부").item(0).getTextContent().trim().equals("") ? "-" : eElement.getElementsByTagName("해제여부").item(0).getTextContent().trim();  
				
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
				sb.append(CancleDealType);
				System.out.println(sb.toString());
				ApiDto ApiDto = new ApiDto();
				ApiDto.setSIGUNGU(sigungu + " " + sigungu2 + " " +  Dong);
				ApiDto.setBUNGI(Jibun);
				
				Map<String, String> map =  makebonbunbubun(Jibun);
				ApiDto.setBONBUN(map.get("bonbun"));
				ApiDto.setBUBUN(map.get("bubun"));
				
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
				//ApiDto.setROADNAME(road);
				ApiDto.setCANCLEDEALDAY(CancleDealDay);
				ApiDto.setREQGBN(ReqgbN);
				ApiDto.setRDEALERLAWDNM(RdealerLawdnm);
				ApiDto.setREGISTRATIONDATE(RegistartionDate);
				System.out.println(ApiDto.toString());
			}
		}
		
	}
	
	public Map<String, String> makebonbunbubun(String Jibun) {
		Map<String, String> map = new HashMap<>();
		String bonbun = "";
		String bubun = "";
		String cleanAddress = Jibun.replaceAll("[^0-9-]", "");
		cleanAddress = (cleanAddress.replaceAll("-", " ").trim());
		String[] cleanAddresssplit = cleanAddress.split(" ");
		if(cleanAddresssplit.length == 1) {
			if(cleanAddresssplit[0].equals("")) {
				bonbun = "0000";
				bubun = "0000";
			}else {
				bonbun = String.format("%04d", Integer.parseInt(cleanAddresssplit[0]));
				bubun = "0000";
			}
		}else if(cleanAddresssplit.length == 2) {
			bonbun = String.format("%04d", Integer.parseInt(cleanAddresssplit[0]));
			bubun = String.format("%04d", Integer.parseInt(cleanAddresssplit[1]));
		}
		map.put("bonbun", bonbun);
		map.put("bubun", bubun);
		return map;
		
	}
	
	
}
