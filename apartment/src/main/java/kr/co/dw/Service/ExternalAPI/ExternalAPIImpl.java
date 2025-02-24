package kr.co.dw.Service.ExternalAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.co.dw.Constant.Constant;
import kr.co.dw.Service.AutoData.Apt.OpenApi.OpenApiService;
import kr.co.dw.Service.AutoData.Coords.GeocoderApi.GeocoderApi;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExternalAPIImpl implements ExternalAPI{

	private final OpenApiService openApiService;
	
	@Value("${api.apt.url}")
	private String API_APT_URL;

	@Value("${api.apt.service-key}")
	private String API_APT_SERVICE_KEY;
	
	private final GeocoderApi geocoderApi;
	
	@Value("${api.geocoder.url}")
	private String api_Geocoder_Url;
	
	@Value("${api.geocoder.service-key}")
	private String api_Geocoder_Service_Key;
	
	@Override
	public String getRTMSDataSvcAptTradeDev() throws IOException {
		// TODO Auto-generated method stub
		String baseUrl = API_APT_URL;
		String serviceKey = API_APT_SERVICE_KEY;
		
		UrlCreateService ucs = new UrlCreateService(baseUrl);
		
		String LAWD_CD = "11110";
		String DEAL_YMD = "202502";
		String pageNo = "1";
		String numOfRows = "10";
		
		ucs.addParam("serviceKey", serviceKey, true, false);
		ucs.addParam("LAWD_CD", LAWD_CD, true, true);
		ucs.addParam("DEAL_YMD", DEAL_YMD, true, true);
		ucs.addParam("pageNo", pageNo, true, true);
		ucs.addParam("numOfRows", numOfRows, true, true);
		
		StringBuilder sb = new StringBuilder();
		
		URL url = new URL(ucs.build());
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");

		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		System.out.println(sb.toString());
		return null;
	}

	@Override
	public JSONObject geocodersearchaddress() throws IOException, ParseException {
		// TODO Auto-generated method stub
		
		String baseUrl = api_Geocoder_Url;
		String serviceKey = api_Geocoder_Service_Key;
		//geocoderApi.geocodersearchaddress(url);
		return null;
	}
	
}
