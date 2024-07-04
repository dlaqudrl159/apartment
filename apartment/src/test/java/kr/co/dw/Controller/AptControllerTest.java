package kr.co.dw.Controller;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
	void test3() {
		
		String[] arr = {"seoul","jeju","sejong"};
		String[] arr2 = {"서울","제주도","세종특별자치시"};
		Calendar cal = Calendar.getInstance();
		for(int i = 0 ; i < arr.length ; i++) {
			File file = new File("C:/Users/qkfka/OneDrive/바탕 화면/아파트데이터/"+ arr2[Arrays.asList(arr).indexOf(arr[i])]  +"/데이터");
			if(file.exists() == false) {
				file.mkdir();
			}
			String uploadFath = file.getPath();
			file = new File(uploadFath, String.valueOf(cal.get(Calendar.MONTH)+1));
			if(file.exists() == false) {
				file.mkdir();
			}
			uploadFath = file.getPath();
			file = new File(uploadFath, String.valueOf(cal.get(Calendar.DATE)));
			if(file.exists() == false) {
				file.mkdir();
			}
		}
		
		
		
		
		
		
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
