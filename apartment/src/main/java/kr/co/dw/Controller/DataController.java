package kr.co.dw.Controller;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import kr.co.dw.Service.DataService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DataController {

	private final DataService DataService;
	
	private final Logger logger = LoggerFactory.getLogger(DataController.class);
	
	@GetMapping("/data/LatLngInsert/{tableName}")
	private void LatLngInsert(@PathVariable("tableName") String tableName) throws MalformedURLException, IOException, ParseException, InterruptedException {
		//서울 부산 경기도 충청북도 충청남도 대구 대전 강원도 광주 경상북도 인천 *여기까지 4만정도 경상남도 제주도 전라북도 전라남도 세종 울산
		DataService.LatLngInsert(tableName);
		logger.info(tableName + " 좌표 저장 완료");
		
	}
	
	@GetMapping("/data/AutoDataInsert/{RegionName}")
	private ResponseEntity<String> AutoDataInsertRegionName(@PathVariable("RegionName") String RegionName) throws java.text.ParseException{
		
		DataService.AutoDataInsert(RegionName.toUpperCase());
		
		logger.info(RegionName + " 테이블 완료");
		
		return null;
	}
	
	@GetMapping("/data/AutoDataInsert")
	private ResponseEntity<String> AutoDataInsert() throws java.text.ParseException{
		
		String[] EnglishRegion = {"SEOUL","BUSAN","DAEGU","INCHEON","GWANGJU","DAEJEON","ULSAN","SEJONG","GYEONGGIDO","GANGWONDO",
				"CHUNGCHEONGBUKDO","CHUNGCHEONGNAMDO","JEOLLABUKDO","JEOLLANAMDO","GYEONGSANGBUKDO","GYEONGSANGNAMDO","JEJU"};
		
		for(int i = 0 ; i < EnglishRegion.length; i++) {
			DataService.AutoDataInsert(EnglishRegion[i].toUpperCase());
			logger.info(EnglishRegion[i] + " 테이블 완료");
		}
		
		return null;
	}
	
}
