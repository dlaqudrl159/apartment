package kr.co.dw.Service.AutoData.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import kr.co.dw.Dto.Common.RegionYearDto;
import kr.co.dw.Exception.ApiException;
import kr.co.dw.Mapper.AutoLatLngDataMapper;

@ExtendWith(MockitoExtension.class)
public class AutoLatLngDataServiceImplTest {

	@Mock
	private AutoLatLngDataMapper autoLatLngDataMapper; 
	
	@InjectMocks
	private AutoLatLngDataServiceImpl autoLatLngDataService;
	
	@Test
	@DisplayName("geocodersearchaddress 테스트")
	void geocodersearchaddresstest() throws IOException, ParseException {
		
		String address = "서울특별시 중랑구 망우동 435-2";
		String searchTypeparcel = "parcel";
		String searchTyperoad = "road";
		
	}
}
