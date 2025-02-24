package kr.co.dw.Service.ExternalAPI;

import java.io.IOException;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public interface ExternalAPI {

	public String getRTMSDataSvcAptTradeDev() throws IOException; //국토교통부 아파트 실거래가 상세자료 API
	
	public JSONObject geocodersearchaddress() throws IOException, ParseException; //지오코더 주소 > 좌표 변환 API

}
