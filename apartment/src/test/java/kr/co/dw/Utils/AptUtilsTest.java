package kr.co.dw.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Calendar;

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

import kr.co.dw.Domain.NameCountDto;

public class AptUtilsTest {

	@Test
	void MappingRegionTest() {
		
		String[] arr = {"서울특별시","부산광역시","대구광역시","인천광역시","광주광역시","대전광역시","울산광역시","세종특별자치시","경기도","충청북도","충청남도","전라남도","경상북도","경상남도","제주특별자치도","강원특별자치도","전북특별자치도"};
		
		AptUtils utils = new AptUtils();
		for(int i = 0 ; i < arr.length ; i++) {
			utils.MappingRegion(arr[i]);
		}
		
	}
	//주소(지번)로 좌표검색
	@Test
	void getlatlng() throws IOException, ParseException {
		
		String sigungu = "충청북도 청주시 서원구 사창동";
		String bungi = "369-1";
		String roadname = "창직로 1";
		
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
	void test() {
		Calendar today = Calendar.getInstance();
		String year = String.valueOf(today.get(Calendar.YEAR));
		int Calendarmonth = (today.get(Calendar.MONTH)+1);
		String month = String.format("%02d", Calendarmonth);
		System.out.println(year);
		System.out.println(month);
		
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
		String roadname = "사직로9길";
		String roadnamebonbun = "04001";
		String roadnamebubun = "00101";
		DataUtils DataUtils = new DataUtils();
		roadname = DataUtils.makeroadname(roadname, roadnamebonbun, roadnamebubun);
		
		System.out.println(roadname);
		
		/*roadnamebonbun = roadnamebonbun.replace("0", " ").trim().replace(" ", "0");
		System.out.println(roadnamebonbun);
		roadnamebubun = roadnamebubun.replace("0", " ").trim().replace(" ", "0");
		System.out.println(roadnamebubun.length());*/
		
		
	}
	
	@Test
	void test4() throws UnsupportedEncodingException, MalformedURLException, IOException, ParserConfigurationException, SAXException {
		StringBuilder urlBuilder = new StringBuilder("http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=f4Ed1eAJYzb%2BQ%2BtpQx4G%2BQvFuO0ZJJMZIInJGo%2FpG889YetxgnnGE9umfvGSe8TPyZ88bAUWw%2Bn7ETYTooeF5A%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("LAWD_CD","UTF-8") + "=" + URLEncoder.encode("11140", "UTF-8")); /*지역코드*/
        urlBuilder.append("&" + URLEncoder.encode("DEAL_YMD","UTF-8") + "=" + URLEncoder.encode("202406", "UTF-8")); /*계약월*/
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
        System.out.println(sb.toString());
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(sb.toString())));
        document.getDocumentElement().normalize();
        
        NodeList nList = document.getElementsByTagName("item");
        
        Element root = document.getDocumentElement();
        
        for(int i = 0 ; i < nList.getLength() ; i++) {
        	Node nNode = nList.item(i);
        	
        	if(nNode.getNodeType() == Node.ELEMENT_NODE) {
        		Element eElement = (Element) nNode;
        		String DealAmount2 = eElement.getElementsByTagName("거래금액").item(0) == null ? "자료없음" : eElement.getElementsByTagName("거래금액").item(0).getTextContent().trim().equals("") ? "자료없음" : eElement.getElementsByTagName("거래금액").item(0).getTextContent().trim();  
        		String BuildYear2 = eElement.getElementsByTagName("건축년도").item(0) == null ? "자료없음" : eElement.getElementsByTagName("건축년도").item(0).getTextContent().trim().equals("") ? "자료없음" : eElement.getElementsByTagName("건축년도").item(0).getTextContent().trim();  
        		String DealYear2 = eElement.getElementsByTagName("년").item(0) == null ? "자료없음" : eElement.getElementsByTagName("년").item(0).getTextContent().trim().equals("") ? "자료없음" : eElement.getElementsByTagName("년").item(0).getTextContent().trim();  
        		String RoadName2 = eElement.getElementsByTagName("도로명").item(0) == null ? "자료없음" : eElement.getElementsByTagName("도로명").item(0).getTextContent().trim().equals("") ? "자료없음" : eElement.getElementsByTagName("도로명").item(0).getTextContent().trim();  
        		String RoadNameBonbun2 = eElement.getElementsByTagName("도로명건물본번호코드").item(0) == null ? "자료없음" : eElement.getElementsByTagName("도로명건물본번호코드").item(0).getTextContent().trim().equals("") ? "자료없음" : eElement.getElementsByTagName("도로명건물본번호코드").item(0).getTextContent().trim();  
        		String RoadNameBubun2 = eElement.getElementsByTagName("도로명건물부번호코드").item(0) == null ? "자료없음" : eElement.getElementsByTagName("도로명건물부번호코드").item(0).getTextContent().trim().equals("") ? "자료없음" : eElement.getElementsByTagName("도로명건물부번호코드").item(0).getTextContent().trim();  
        		String RoadNameSigunguCode2 = eElement.getElementsByTagName("도로명시군구코드").item(0) == null ? "자료없음" : eElement.getElementsByTagName("도로명시군구코드").item(0).getTextContent().trim().equals("") ? "자료없음" : eElement.getElementsByTagName("도로명시군구코드").item(0).getTextContent().trim();  
        		String RoadNameSeq2 = eElement.getElementsByTagName("도로명일련번호코드").item(0) == null ? "자료없음" : eElement.getElementsByTagName("도로명일련번호코드").item(0).getTextContent().trim().equals("") ? "자료없음" : eElement.getElementsByTagName("도로명일련번호코드").item(0).getTextContent().trim();  
        		String RoadNameBasementCode2 = eElement.getElementsByTagName("도로명지상지하코드").item(0) == null ? "자료없음" : eElement.getElementsByTagName("도로명지상지하코드").item(0).getTextContent().trim().equals("") ? "자료없음" : eElement.getElementsByTagName("도로명지상지하코드").item(0).getTextContent().trim();  
        		String RoadNameCode2 = eElement.getElementsByTagName("도로명코드").item(0) == null ? "자료없음" : eElement.getElementsByTagName("도로명코드").item(0).getTextContent().trim().equals("") ? "자료없음" : eElement.getElementsByTagName("도로명코드").item(0).getTextContent().trim();  
        		String Dong2 = eElement.getElementsByTagName("법정동").item(0) == null ? "자료없음" : eElement.getElementsByTagName("법정동").item(0).getTextContent().trim().equals("") ? "자료없음" : eElement.getElementsByTagName("법정동").item(0).getTextContent().trim();  
        		String Bonbun2 = eElement.getElementsByTagName("법정동본번코드").item(0) == null ? "자료없음" : eElement.getElementsByTagName("법정동본번코드").item(0).getTextContent().trim().equals("") ? "자료없음" : eElement.getElementsByTagName("법정동본번코드").item(0).getTextContent().trim();  
        		String Bubun2 = eElement.getElementsByTagName("법정동부번코드").item(0) == null ? "자료없음" : eElement.getElementsByTagName("법정동부번코드").item(0).getTextContent().trim().equals("") ? "자료없음" : eElement.getElementsByTagName("법정동부번코드").item(0).getTextContent().trim();  
        		String SigunguCode2 = eElement.getElementsByTagName("법정동시군구코드").item(0) == null ? "자료없음" : eElement.getElementsByTagName("법정동시군구코드").item(0).getTextContent().trim().equals("") ? "자료없음" : eElement.getElementsByTagName("법정동시군구코드").item(0).getTextContent().trim();  
        		String EubyundongCode2 = eElement.getElementsByTagName("법정동읍면동코드").item(0) == null ? "자료없음" : eElement.getElementsByTagName("법정동읍면동코드").item(0).getTextContent().trim().equals("") ? "자료없음" : eElement.getElementsByTagName("법정동읍면동코드").item(0).getTextContent().trim();  
        		String LandCode2 = eElement.getElementsByTagName("법정동지번코드").item(0) == null ? "자료없음" : eElement.getElementsByTagName("법정동지번코드").item(0).getTextContent().trim().equals("") ? "자료없음" : eElement.getElementsByTagName("법정동지번코드").item(0).getTextContent().trim();  
        		String ApartmentName2 = eElement.getElementsByTagName("아파트").item(0) == null ? "자료없음" : eElement.getElementsByTagName("아파트").item(0).getTextContent().trim().equals("") ? "자료없음" : eElement.getElementsByTagName("아파트").item(0).getTextContent().trim();  
        		String DealMonth2 = eElement.getElementsByTagName("월").item(0) == null ? "자료없음" : eElement.getElementsByTagName("월").item(0).getTextContent().trim().equals("") ? "자료없음" : eElement.getElementsByTagName("월").item(0).getTextContent().trim();  
        		String DealDay2 = eElement.getElementsByTagName("일").item(0) == null ? "자료없음" : eElement.getElementsByTagName("일").item(0).getTextContent().trim().equals("") ? "자료없음" : eElement.getElementsByTagName("일").item(0).getTextContent().trim();  
        		String SerialNumber2 = eElement.getElementsByTagName("일련번호").item(0) == null ? "자료없음" : eElement.getElementsByTagName("일련번호").item(0).getTextContent().trim().equals("") ? "자료없음" : eElement.getElementsByTagName("일련번호").item(0).getTextContent().trim();  
        		String AreaforExcusiveUse2 = eElement.getElementsByTagName("전용면적").item(0) == null ? "자료없음" : eElement.getElementsByTagName("전용면적").item(0).getTextContent().trim().equals("") ? "자료없음" : eElement.getElementsByTagName("전용면적").item(0).getTextContent().trim();  
        		String Jibun2 = eElement.getElementsByTagName("지번").item(0) == null ? "자료없음" : eElement.getElementsByTagName("지번").item(0).getTextContent().trim().equals("") ? "자료없음" : eElement.getElementsByTagName("지번").item(0).getTextContent().trim();  
        		String RegionalCode2 = eElement.getElementsByTagName("지역코드").item(0) == null ? "자료없음" : eElement.getElementsByTagName("지역코드").item(0).getTextContent().trim().equals("") ? "자료없음" : eElement.getElementsByTagName("지역코드").item(0).getTextContent().trim();  
        		String Floor2 = eElement.getElementsByTagName("층").item(0) == null ? "자료없음" : eElement.getElementsByTagName("층").item(0).getTextContent().trim().equals("") ? "자료없음" : eElement.getElementsByTagName("층").item(0).getTextContent().trim();  
        		String CancleDealType2 = eElement.getElementsByTagName("해제여부").item(0) == null ? "자료없음" : eElement.getElementsByTagName("해제여부").item(0).getTextContent().trim().equals("") ? "자료없음" : eElement.getElementsByTagName("해제여부").item(0).getTextContent().trim();  
        		String CancleDealDay2 = eElement.getElementsByTagName("해제사유발생일").item(0) == null ? "자료없음" : eElement.getElementsByTagName("해제사유발생일").item(0).getTextContent().trim().equals("") ? "자료없음" : eElement.getElementsByTagName("해제사유발생일").item(0).getTextContent().trim();  
        		String REQGBN2 = eElement.getElementsByTagName("거래유형").item(0) == null ? "자료없음" : eElement.getElementsByTagName("거래유형").item(0).getTextContent().trim().equals("") ? "자료없음" : eElement.getElementsByTagName("거래유형").item(0).getTextContent().trim();  
        		String RdealerLawdnm2 = eElement.getElementsByTagName("중개사소재지").item(0) == null ? "자료없음" : eElement.getElementsByTagName("중개사소재지").item(0).getTextContent().trim().equals("") ? "자료없음" : eElement.getElementsByTagName("중개사소재지").item(0).getTextContent().trim();  
        		String RegistartionDate2 = eElement.getElementsByTagName("등기일자").item(0) == null ? "자료없음" : eElement.getElementsByTagName("등기일자").item(0).getTextContent().trim().equals("") ? "자료없음" : eElement.getElementsByTagName("등기일자").item(0).getTextContent().trim();  
        		String SellerGBN2 = eElement.getElementsByTagName("매도자").item(0) == null ? "자료없음" : eElement.getElementsByTagName("매도자").item(0).getTextContent().trim().equals("") ? "자료없음" : eElement.getElementsByTagName("매도자").item(0).getTextContent().trim();  
        		String BuyerGBN2 = eElement.getElementsByTagName("매수자").item(0) == null ? "자료없음" : eElement.getElementsByTagName("매수자").item(0).getTextContent().trim().equals("") ? "자료없음" : eElement.getElementsByTagName("매수자").item(0).getTextContent().trim();  
        		String ApartmentDong2 = eElement.getElementsByTagName("동").item(0) == null ? "자료없음" : eElement.getElementsByTagName("동").item(0).getTextContent().trim().equals("") ? "자료없음" : eElement.getElementsByTagName("동").item(0).getTextContent().trim();  
        		
        		StringBuilder sb2 = new StringBuilder();
            	sb2.append(DealAmount2 + " ");
            	sb2.append(BuildYear2 + " ");
            	sb2.append(DealYear2 + " ");
            	sb2.append(RoadName2 + " ");
            	sb2.append(RoadNameBonbun2 + " ");
            	sb2.append(RoadNameBubun2 + " ");
            	sb2.append(RoadNameSigunguCode2 + " ");
            	sb2.append(RoadNameSeq2 + " ");
            	sb2.append(RoadNameBasementCode2 + " ");
            	sb2.append(RoadNameCode2 + " ");
            	sb2.append(Dong2 + " ");
            	sb2.append(Bonbun2 + " ");
            	sb2.append(Bubun2 + " ");
            	sb2.append(SigunguCode2 + " ");
            	sb2.append(EubyundongCode2 + " ");
            	sb2.append(LandCode2 + " ");
            	sb2.append(ApartmentName2 + " ");
            	sb2.append(DealMonth2 + " ");
            	sb2.append(DealDay2 + " ");
            	sb2.append(SerialNumber2 + " ");
            	sb2.append(AreaforExcusiveUse2 + " ");
            	sb2.append(Jibun2 + " ");
            	sb2.append(RegionalCode2 + " ");
            	sb2.append(Floor2 + " ");
            	sb2.append(CancleDealType2 + " ");
            	sb2.append(CancleDealDay2 + " ");
            	sb2.append(REQGBN2 + " ");
            	sb2.append(RdealerLawdnm2 + " ");
            	sb2.append(RegistartionDate2 + " ");
            	sb2.append(SellerGBN2 + " ");
            	sb2.append(BuyerGBN2 + " ");
            	sb2.append(ApartmentDong2 + " "); 	 
            	System.out.println(sb2.toString());
        	}
        	
        }
        
	}
	
}
