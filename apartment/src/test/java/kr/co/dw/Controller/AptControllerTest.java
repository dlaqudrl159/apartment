package kr.co.dw.Controller;


import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import kr.co.dw.ApartmentApplication;
import kr.co.dw.Domain.NameCountDto;
import kr.co.dw.Service.AptService;

@SpringBootTest
@ContextConfiguration(classes = ApartmentApplication.class)
public class AptControllerTest {

	private AptService AptService;
	
	
	@Autowired
	public AptControllerTest(kr.co.dw.Service.AptService aptService) {
		
		AptService = aptService;
	}

	@Test
	void test2() {
		NameCountDto dto = new NameCountDto();
		dto.setSIGUNGU("충청북도 충주시 호암동");
		dto.setBUNGI("906");
		dto.setROADNAME("형설로 32");
		dto.setAPARTMENTNAME("세영더-조은");
		dto.setLAT("36.95680676244391");
		dto.setLNG("127.94202223387529");
		
		NameCountDto dto2 = new NameCountDto();
		
		dto2.setSIGUNGU("충청북도 충주시 호암동");
		dto2.setBUNGI("906");
		dto2.setROADNAME("형설로 32");
		dto2.setAPARTMENTNAME("세영더-조은");
		dto2.setLAT("36.95680676244391");
		dto2.setLNG("127.94202223387529");
		
		System.out.println(dto.equals(dto2));
		
	}

	@Test
	void test() {
		
		List<String> arr = new ArrayList<>();
		String address = "서울특별시 중구 소공동";
		arr.add(address);
		
		
		AptService.get(arr, "2023");
		
	}
	
}
