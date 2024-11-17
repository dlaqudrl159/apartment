package kr.co.dw.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import kr.co.dw.Dto.Common.AptCoordsDto;
import kr.co.dw.Dto.Common.AptTransactionDto;

public class AptUtilsTest {

	
	//주소(지번)로 좌표검색
	@Test
	void getlatlng() throws IOException, ParseException {
		
		String sigungu = "서울특별시 강서구 방화동 마곡청구아파트";
		String bungi = "859";
		String roadname = "양천로26길 103";
		
		String apikey = "F0DBB350-67A6-39BB-A8BB-9237BB06612C";
		String searchType = "parcel";
		String searchType2 = "road";
		String epsg = "epsg:4326";
		
		String searchAddr = sigungu + " " + bungi;
		
		StringBuilder sb = new StringBuilder("https://api.vworld.kr/req/address");
		sb.append("?service=address");
		sb.append("&request=getCoord");
		sb.append("&format=json");
		sb.append("&crs=" + epsg);
		sb.append("&key=" + apikey);
		sb.append("&type=" + searchType);
		sb.append("&address=" + URLEncoder.encode(searchAddr, StandardCharsets.UTF_8));
		System.out.println(searchAddr);
		System.out.println(sb.toString());
		URL url = new URL(sb.toString());
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(),StandardCharsets.UTF_8));
		
		JSONParser jspa = new JSONParser();
		JSONObject jsob = (JSONObject) jspa.parse(br);
		JSONObject jsrs = (JSONObject) jsob.get("response");
		if(jsrs.get("status").equals("OK")) {
			JSONObject jsResult = (JSONObject) jsrs.get("result");
		    JSONObject jspoint = (JSONObject) jsResult.get("point");
		    System.out.println(jsrs);
		    
		}else {
			System.out.println(jsrs);
		}
		
	}
	//주소(도로명)로 좌표검색
	@Test
	void getlatlng2() throws IOException, ParseException {
		
		String sigungu = "충청북도 청주시 서원구 사창동";
		String bungi = "369-1";
		String roadname = "창직로 1";
		
		String apikey = "F0DBB350-67A6-39BB-A8BB-9237BB06612C";
		String searchType = "parcel";
		String searchType2 = "road";
		String epsg = "epsg:4326";
		
		String searchAddr = sigungu + " " + roadname;
		
		StringBuilder sb = new StringBuilder("https://api.vworld.kr/req/address");
		sb.append("?service=address");
		sb.append("&request=getCoord");
		sb.append("&format=json");
		sb.append("&crs=" + epsg);
		sb.append("&key=" + apikey);
		sb.append("&type=" + searchType2);
		sb.append("&address=" + URLEncoder.encode(searchAddr, StandardCharsets.UTF_8));
		System.out.println(searchAddr);
		System.out.println(sb.toString());
		URL url = new URL(sb.toString());
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(),StandardCharsets.UTF_8));
		
		JSONParser jspa = new JSONParser();
		JSONObject jsob = (JSONObject) jspa.parse(br);
		JSONObject jsrs = (JSONObject) jsob.get("response");
		if(jsrs.get("status").equals("OK")) {
			JSONObject jsResult = (JSONObject) jsrs.get("result");
		    JSONObject jspoint = (JSONObject) jsResult.get("point");
		    System.out.println(jsrs);
		    
		}else {
			System.out.println(jsrs);
		}
		
	}
	
	@Test
	void 청주시() {
		String address = "충청북도 청주시청원구 사천동";
		String[] arr = address.split(" ");
		String address2 = arr[1];
		if(address2.equals("청주서원구")) {
			arr[1] = "청주시 서원구";
		}else if(address2.equals("청주시상당구")) {
			arr[1] = "청주시 상당구";
		}else if(address2.equals("청주시청원구")) {
			arr[1] = "청주시 청원구";
		}else if(address2.equals("청주시흥덕구")) {
			arr[1] = "청주시 흥덕구";
		}
		String temp = "";
		for(int i = 0 ; i < arr.length ; i++) {
			System.out.println(arr[i]);
			temp  += arr[i]+ " ";
		}
		address = temp.trim();
		System.out.println(address);
	}
	@Test
	void test() throws java.text.ParseException {
		Calendar today = Calendar.getInstance();
		String date = "202408";
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMM");
		int Calendarmonth = (today.get(Calendar.MONTH)+1);
		System.out.println(Calendarmonth);
		Date dt = dtFormat.parse(date);
		today.setTime(dt);
		
		
		String year = String.valueOf(today.get(Calendar.YEAR));
		String minusyear = String.valueOf(today.get(Calendar.YEAR)-1);
		String month = String.format("%02d", Calendarmonth);
		System.out.println(year);
		today.add(Calendar.MONTH, -9);
		System.out.println(dtFormat.format(today.getTime()));
		
	}
	@Test
	void test2() {
		//가-53-1
		//산74-10
		//산54
		//가-131
		//가- cleanAddresssplit[0].equals("") 하면 true 나옴
		String address = "가-";
		
		String cleanAddress = address.replaceAll("[^0-9-]", "");
		System.out.println(cleanAddress);
		cleanAddress = (cleanAddress.replaceAll("-", " ").trim());
		System.out.println(cleanAddress);
		String[] cleanAddresssplit = cleanAddress.split(" ");
		System.out.println(Arrays.toString(cleanAddresssplit)); 
		System.out.println(cleanAddresssplit[0].equals(""));
	}
	
	@Test
	void test3() {
		String roadname = "22";
		String roadnamebonbun = "00000";
		String roadnamebubun = "00000";
		DateUtils DataUtils = new DateUtils();
		//roadname = DataUtils.makeroadname(roadname, roadnamebonbun, roadnamebubun);
		
		System.out.println(roadname);
		
		/*roadnamebonbun = roadnamebonbun.replace("0", " ").trim().replace(" ", "0");
		System.out.println(roadnamebonbun);
		roadnamebubun = roadnamebubun.replace("0", " ").trim().replace(" ", "0");
		System.out.println(roadnamebubun.length());*/
		
		
	}
	
	@Test
	void test4() throws UnsupportedEncodingException, MalformedURLException, IOException, ParserConfigurationException, SAXException {
		
		/*부산*/
		/*{ "26110", "26140", "26170", "26200", "26230", "26260", "26290", "26320", "26350", "26380", "26410",
				"26440", "26470", "26500", "26530", "26710" },
		
		//부산
		{ "중구", "서구", "동구", "영도구", "부산진구", "동래구", "남구", "북구", "해운대구", "사하구", "금정구", "강서구", "연제구", "수영구", "사상구",
				"기장군" },
		
		/*서울*/
		String[] seoul =	{ "11110", "11140", "11170", "11200", "11215", "11230", "11260", "11290", "11305", "11320", "11350",
					"11380", "11410", "11440", "11470", "11500", "11530", "11545", "11560", "11590", "11620",
					"11650", "11680", "11710", "11740" } ;
		
		//서울
		String[] seoul2 =	{ "종로구", "중구", "용산구", "성동구", "광진구", "동대문구", "중랑구", "성북구", "강북구", "도봉구", "노원구", "은평구", "서대문구", "마포구", "양천구",
					"강서구", "구로구", "금천구", "영등포구", "동작구", "관악구", "서초구", "강남구", "송파구", "강동구" } ;
					
					
		/*인천*/
		String[] GYEONGSANGNAMDO = { "48121", "48123", "48125", "48127", "48129", "48170", "48220", "48240", "48250", "48270", "48310",
				"48330", "48720", "48730", "48740", "48820", "48840", "48850", "48860", "48870", "48880",
				"48890" };
		//int nowtotalCount = 13;
		//int page = ((nowtotalCount-1)/10)+1;
		//String strPage = String.valueOf(page);
		//System.out.println(page);
		//System.out.println(strPage);
		int index = 2;
		String code = seoul[index];
		String koreacode = seoul2[index];
		String dealyearmonth = "202408";
		Calendar cal = Calendar.getInstance();
		int date = cal.get(Calendar.DATE);
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/RTMSDataSvcAptTradeDev/getRTMSDataSvcAptTradeDev"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=f4Ed1eAJYzb%2BQ%2BtpQx4G%2BQvFuO0ZJJMZIInJGo%2FpG889YetxgnnGE9umfvGSe8TPyZ88bAUWw%2Bn7ETYTooeF5A%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10000", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("LAWD_CD","UTF-8") + "=" + URLEncoder.encode("50130", "UTF-8")); /*지역코드*/
        urlBuilder.append("&" + URLEncoder.encode("DEAL_YMD","UTF-8") + "=" + URLEncoder.encode("202408", "UTF-8")); /*계약월*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(sb.toString())));
        document.getDocumentElement().normalize();
        
        NodeList nList = document.getElementsByTagName("item");
        
        Element root = document.getDocumentElement();
        String resultMsg = root.getElementsByTagName("resultMsg").item(0) == null ? "-" : root.getElementsByTagName("resultMsg").item(0).getTextContent();
		String resultCode = root.getElementsByTagName("resultCode").item(0) == null ? "-" : root.getElementsByTagName("resultCode").item(0).getTextContent();
		String totalCount = root.getElementsByTagName("totalCount").item(0) == null ? "-" : root.getElementsByTagName("totalCount").item(0).getTextContent();
		//System.out.println(resultMsg);
		//System.out.println(resultCode);
		//System.out.println(totalCount);
		//int a = Integer.parseInt(totalCount) - nowtotalCount;
		//System.out.println(a);
        List<AptTransactionDto> list = new ArrayList<>();
       // System.out.println(nList.getLength());
        
		
		
		
        for(int i = 0 ; i < nList.getLength() ; i++) {
        	
        	Node nNode = nList.item(i);
        	AptTransactionDto ApiDto = new AptTransactionDto();
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
        		
				StringBuilder sb2 = new StringBuilder();
				sb2.append(DealAmount + " ");
				sb2.append(ReqgbN + " ");
				sb2.append(BuildYear + " ");
				sb2.append(DealYear + " ");
				sb2.append(ApartmentDong + " ");
				sb2.append(RegistartionDate + " ");
				sb2.append(SellerGBN + " ");
				sb2.append(BuyerGBN + " ");
				sb2.append(Dong + " ");
				sb2.append(ApartmentName + " ");
				sb2.append(DealMonth + " ");
				sb2.append(DealDay + " ");
				sb2.append(AreaforExcusiveUse + " ");
				sb2.append(RdealerLawdnm + " ");
				sb2.append(Jibun + " ");
				sb2.append(RegionalCode + " ");
				sb2.append(Floor + " ");
				sb2.append(CancleDealDay + " ");
				sb2.append(CancleDealType + " ");
				sb2.append(RoadName);
				sb2.append(RoadNameBonbun + " ");
				sb2.append(RoadNameBubun+ " ");
				
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
				ApiDto.setCANCLEDEALDAY(CancleDealDay);
				ApiDto.setREQGBN(ReqgbN);
				ApiDto.setRDEALERLAWDNM(RdealerLawdnm);
				ApiDto.setREGISTRATIONDATE(RegistartionDate);
				//System.out.println(ApiDto.toString());
				list.add(ApiDto);			
				System.out.println(ApiDto);
				
				
        	}
        	
        }
        		
	}
	@Test
	void test6() {
		String lat = "123.78955456456";
		int latidx = lat.indexOf(".");
		String strlatidx = lat.substring(0,latidx+6);
		System.out.println(latidx);
		System.out.println(strlatidx);
	}
	
	@Test
	void test7() {
		
		AptCoordsDto dto = new AptCoordsDto();
		AptCoordsDto dto2 = new AptCoordsDto();
		
		String Sigungu = "서울특별시 중랑구 망우동";
		String Bungi = "435-2";
		String Roadname = "용마산로 100길";
		String Apartmentname = "상봉듀오트리스";
		
		dto.setSIGUNGU(Sigungu);
		dto.setBUNGI(Bungi);
		dto.setROADNAME(Roadname);
		dto.setAPARTMENTNAME(Apartmentname);
		
		dto2.setSIGUNGU(Sigungu);
		dto2.setBUNGI(Bungi);
		dto2.setROADNAME(Roadname);
		dto2.setAPARTMENTNAME(Apartmentname);
		
		dto.setLAT("123.12345");
		dto.setLNG("123.12345");
		
		dto2.setLAT("456.789");
		dto2.setLAT("456.789");
		
		boolean test = dto.equals(dto2);
		
		System.out.println(test);
		
		
		
		
	}
	@Test
	void test8() throws IOException, ParseException, InterruptedException {
	//	DataServiceImpl DataServiceimpl = new DataServiceImpl(null);
		List<AptCoordsDto> list = new ArrayList<>();
		
		AptCoordsDto NameCountDto = new AptCoordsDto();
		NameCountDto.setSIGUNGU("강원특별자치도 고성군 토성면 천진리");
		NameCountDto.setBUNGI("476");
		NameCountDto.setROADNAME("29");
		NameCountDto.setAPARTMENTNAME("고성천진한신더휴");
		//DataServiceimpl.getparcel(NameCountDto, "DUMMY");
		//DataServiceimpl.getroadname(NameCountDto, "DUMMY");
		list.add(NameCountDto);
		//DataServiceimpl.getLatLng(list, "DUMMY");
		
	}
	
	@Test
	void test9() {
		System.out.println(((0-1)/10)+1);
	}
	
	@Test
	void test10() throws InterruptedException {
		
			for(int i = 0 ; i < 3 ; i++) {
				Thread.sleep(1000);
				System.out.println(i);
				for(int j = 0 ; j < 10 ; j++) {
					Thread.sleep(1000);
					System.out.println(j);
					if(j == 5) {
						break;
					}
					
				}
			}
		
	}
	@Test
	void test11() {
		List<AptTransactionDto> oldList = new ArrayList<>();
		List<AptTransactionDto> newList = new ArrayList<>();
		
		AptTransactionDto dto1 = new AptTransactionDto("부산광역시 동구 초량동", "593", "0593", "0000", "부산역삼정그린코아더시티", "75.2492", "202408", "8", "40,000", "-", "20", "개인", "개인", "2021", "중앙대로179번길 27", "-", "중개거래", "부산 동구", "-", "26170");
		AptTransactionDto dto2 = new AptTransactionDto("부산광역시 동구 좌천동", "808", "0808", "0000", "동원드림타운", "84.9341", "202408", "11", "32,900", "-", "19", "개인", "개인", "2002", "고관로 173", "-", "중개거래", "부산 동구", "-", "26170");
		AptTransactionDto dto3 = new AptTransactionDto("부산광역시 동구 수정동", "1352", "1352", "0000", "협성휴포레부산진역오션뷰", "69.5108", "202408", "14", "49,000", "-", "17", "개인", "개인", "2019", "중앙대로 357", "-", "중개거래", "부산 동구", "-", "26170");
		
		oldList.add(dto1);
		oldList.add(dto2);
		
		newList.add(dto1);
		newList.add(dto2);
		newList.add(dto3);
		
		List<AptTransactionDto> newnewList = newList.stream().filter(n -> oldList.stream().noneMatch(Predicate.isEqual(n))).collect(Collectors.toList());
		System.out.println(newnewList);
	}
	
	@Test
	void test12() {
		Calendar cal = Calendar.getInstance();
		String year = String.valueOf(cal.get(Calendar.YEAR));
		int month = (cal.get(Calendar.MONTH)+1);
		String strMonth = String.format("%02d", month);
		String lastMonth = year + strMonth;
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMM");
		Date dt = null;
		try {
			dt = dtFormat.parse(lastMonth);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal.setTime(dt);
		cal.add(Calendar.MONTH, -13);
		dtFormat.format(cal.getTime());
		System.out.println(dtFormat.format(cal.getTime()));
	}
	
}
