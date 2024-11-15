package kr.co.dw.Controller.AutoData.LatLng;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.dw.Service.AutoData.LatLng.AutoLatLngDataService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AutoLatLngDataController {

	private final AutoLatLngDataService autoLatLngDataService;
	
	private final Logger logger = LoggerFactory.getLogger(AutoLatLngDataController.class);
	
	@GetMapping("/data/autolatlnginsert")
	public ResponseEntity<String> autoLatLngInsert(@RequestParam(required = false, value = "parentEngRegionName") String parentEngRegionName) throws MalformedURLException, IOException, ParseException, InterruptedException {
		System.out.println(parentEngRegionName);
		System.out.println(parentEngRegionName);

		
		if(parentEngRegionName == null || parentEngRegionName.trim().isEmpty()) {
			return new ResponseEntity<String>("tableName이 null 이거나 빈칸입니다.", HttpStatus.BAD_REQUEST);
		}
		autoLatLngDataService.latLngInsert(parentEngRegionName);
		return new ResponseEntity<String>(parentEngRegionName + "테이블 좌표 저장 완료", HttpStatus.OK);
		
	}
	
}
