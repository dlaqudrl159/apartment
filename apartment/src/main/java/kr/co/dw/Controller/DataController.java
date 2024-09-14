package kr.co.dw.Controller;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import kr.co.dw.Service.DataService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DataController {

	private final DataService DataService;
	
	@GetMapping("/data/LatLngInsert/{tableName}")
	private void test(@PathVariable("tableName") String tableName) throws MalformedURLException, IOException, ParseException, InterruptedException {
		//서울 부산 경기도 충청북도 충청남도 대구 대전 강원도 광주 경상북도 인천 *여기까지 4만정도 경상남도 제주도 전라북도 전라남도 세종 울산
		DataService.LatLngInsert(tableName);
		System.out.println("좌표 저장 완료");
		
	}
	
	@GetMapping("/data/AutoDataInsert/{RegionName}")
	private ResponseEntity<String> AutoDataInsert(@PathVariable("RegionName") String RegionName) throws java.text.ParseException{
		
		DataService.AutoDataInsert(RegionName.toUpperCase());
		System.out.println("완료");
		return null;
	}
	
	@GetMapping("/data/AutoDataInsert")
	private ResponseEntity<String> AutoDataInsert2() throws java.text.ParseException{
		
		String[] EnglishRegion = {"SEOUL","BUSAN","DAEGU","INCHEON","GWANGJU","DAEJEON","ULSAN","SEJONG","GYEONGGIDO","GANGWONDO",
				"CHUNGCHEONGBUKDO","CHUNGCHEONGNAMDO","JEOLLABUKDO","JEOLLANAMDO","GYEONGSANGBUKDO","GYEONGSANGNAMDO","JEJU"};
		
		for(int i = 0 ; i < EnglishRegion.length; i++) {
			DataService.AutoDataInsert(EnglishRegion[i].toUpperCase());
			System.out.println("완료");
		}
		
		return null;
	}
	
}
