package kr.co.dw.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

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
	
	/*@Test
	void 테스트() {
		NameCountDto dto = new NameCountDto("서울특별시", null, null, null, null, null, null);
		System.out.println(dto.getSIGUNGU());
		테스트2(dto);
		System.out.println(dto.getSIGUNGU());
	}
	void 테스트2(NameCountDto dto) {
		if(dto.getSIGUNGU().equals("서울특별시")) {
			dto.setSIGUNGU("광주광역시");
		}
	}*/
}
