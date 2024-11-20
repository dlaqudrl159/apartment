package kr.co.dw.Service.AutoData.Coords;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import kr.co.dw.Mapper.AutoCoordsDataMapper;
import kr.co.dw.Service.AutoData.Coords.AutoCoordsDataServiceImpl;

@SpringBootTest(properties = "spring.profiles.active=dev")
@TestPropertySource("classpath:application-dev.properties")
public class AutoCoordsDataServiceImplTest2 {

	@Autowired
	private AutoCoordsDataMapper autoCoordsDataMapper; 
	
	@Autowired
	private AutoCoordsDataServiceImpl autoLatLngDataService;
	
	@Test
	@DisplayName("geocodersearchaddress 테스트")
	void geocodersearchaddresstest() throws IOException, ParseException {
		
		String address = "서울특별시 중랑구 망우동 435-2";
		String searchTypeparcel = "parcel";
		String searchTyperoad = "road";
		
		JSONObject jsrs = autoLatLngDataService.geocodersearchaddress(address, searchTypeparcel);
		System.out.println(jsrs);
	}
	
	
}
