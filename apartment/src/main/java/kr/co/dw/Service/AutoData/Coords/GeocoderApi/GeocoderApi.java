package kr.co.dw.Service.AutoData.Coords.GeocoderApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.co.dw.Dto.Common.AptCoordsDto;

@Service
public class GeocoderApi {

	@Value("${api.geocoder.url}")
	private String api_Geocoder_Url;
	
	@Value("${api.geocoder.service-key}") //"#{@environment.getProperty('geocodersearchaddress.apikey')}" // "${geocodersearchaddress.apikey}"
	private String api_Geocoder_Service_Key;

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
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(),StandardCharsets.UTF_8));
		JSONParser jspa = new JSONParser();
		JSONObject jsob = (JSONObject) jspa.parse(br);
		JSONObject jsrs = (JSONObject) jsob.get("response");
		
		return jsrs;
	}

	public JSONObject getparcel(AptCoordsDto aptCoordsDto) throws IOException, ParseException {
		String searchType = "parcel";
		String Sigungu = aptCoordsDto.getSIGUNGU();
		String Bungi = aptCoordsDto.getBUNGI();		
		String searchAddr = Sigungu + " " + Bungi;
		return geocodersearchaddress(searchAddr, searchType);
	}

	public JSONObject getroadname(AptCoordsDto aptCoordsDto) throws IOException, ParseException {
		String searchType = "road";
		String Sigungu = aptCoordsDto.getSIGUNGU();
		String roadname = aptCoordsDto.getROADNAME();
		String searchAddr = Sigungu + " " + roadname;
		return geocodersearchaddress(searchAddr, searchType);
	}
	
}
