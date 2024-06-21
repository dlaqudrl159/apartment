package kr.co.dw.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import kr.co.dw.ApartmentApplication;
import kr.co.dw.Domain.NameCountDto;
import kr.co.dw.Mapper.AptMapper;
import kr.co.dw.Service.AptService;
import kr.co.dw.Service.AptServiceImpl;
import lombok.RequiredArgsConstructor;

@SpringBootTest
@ContextConfiguration(classes = ApartmentApplication.class)
public class AptControllerTest {

	private AptService AptService;
	
	
	@Autowired
	public AptControllerTest(kr.co.dw.Service.AptService aptService) {
		
		AptService = aptService;
	}



	@Test
	void test() {
		
		List<String> arr = new ArrayList<>();
		String address = "서울특별시 중구 소공동";
		arr.add(address);
		
		
		AptService.get(arr, "2023");
		
	}
	
}
