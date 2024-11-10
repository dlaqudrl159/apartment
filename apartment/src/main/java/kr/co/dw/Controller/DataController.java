package kr.co.dw.Controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import kr.co.dw.Domain.ParentRegionName;
import kr.co.dw.Domain.RegionManager;
import kr.co.dw.Service.DataService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DataController {

	private final DataService DataService;
	
	private final Logger logger = LoggerFactory.getLogger(DataController.class);
	
	@GetMapping("/data/LatLngInsert/{tableName}")
	private ResponseEntity<String> LatLngInsert(@PathVariable("tableName") String tableName) throws MalformedURLException, IOException, ParseException, InterruptedException {
		//서울 부산 경기도 충청북도 충청남도 대구 대전 강원도 광주 경상북도 인천 *여기까지 4만정도 경상남도 제주도 전라북도 전라남도 세종 울산
		if(tableName == null || tableName.trim().isEmpty()) {
			return new ResponseEntity<String>("tableName이 null 이거나 빈칸입니다.", HttpStatus.BAD_REQUEST);
		}
		DataService.LatLngInsert(tableName);
		
		return new ResponseEntity<String>(tableName + "테이블 좌표 저장 완료", HttpStatus.OK);
		
	}
	
	@GetMapping("/data/AutoDataInsert/{RegionName}")
	private ResponseEntity<String> AutoDataInsertRegionName(@PathVariable("RegionName") String RegionName) throws java.text.ParseException{
		if(RegionName == null || RegionName.trim().isEmpty()) {
			return new ResponseEntity<String>("RegionName이 null 이거나 빈칸입니다.", HttpStatus.BAD_REQUEST);
		}
		DataService.AutoDataInsert(RegionName.toUpperCase());
		
		return new ResponseEntity<String>(RegionName + " 테이블 입력 완료", HttpStatus.OK);
	}
	
	@GetMapping("/data/AutoDataInsert")
	private ResponseEntity<String> AutoDataInsert() throws java.text.ParseException{
		
		List<ParentRegionName> ParentRegionNameList = RegionManager.getInstance().getParentRegionNameList();
		
		ParentRegionNameList.forEach(t -> {
			DataService.AutoDataInsert(t.getEngParentName());
			logger.info(t.getEngParentName() + " 테이블 완료");
		});
		
		return new ResponseEntity<String>("전체 테이블 입력 완료", HttpStatus.OK);
	}
}
