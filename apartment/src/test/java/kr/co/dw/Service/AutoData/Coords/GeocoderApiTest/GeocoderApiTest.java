package kr.co.dw.Service.AutoData.Coords.GeocoderApiTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GeocoderApiTest {

	private String api_Geocoder_Url = "https://api.vworld.kr/req/address";
	
	private String api_Geocoder_Service_Key = "B7417C15-3D68-309A-A5F2-ACB988833093";
	
	@Test
	void geocodersearchaddressTest() throws IOException, ParseException {
		String searchAddr = "경상북도 포항시 북구 흥해읍 이인리 이인로 90";
		String parcel = "parcel";
		String road = "road";
		String result = geocodersearchaddress(searchAddr,road).toString();
		System.out.println(result);
		
	}
	
	public JSONObject geocodersearchaddress(String searchAddr, String searchType) throws IOException, ParseException {

		String epsg = "epsg:4326";
		StringBuilder sb = new StringBuilder(this.api_Geocoder_Url);
		sb.append("?service=address");
		sb.append("&request=getCoord");
		sb.append("&format=json");
		sb.append("&crs=" + epsg);
		sb.append("&key=" + this.api_Geocoder_Service_Key);
		sb.append("&type=" + searchType);
		sb.append("&address=" + URLEncoder.encode(searchAddr, StandardCharsets.UTF_8));

		URL url = new URL(sb.toString());
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
		JSONParser jspa = new JSONParser();
		JSONObject jsob = (JSONObject) jspa.parse(br);
		JSONObject jsrs = (JSONObject) jsob.get("response");

		return jsrs;
	}

}
