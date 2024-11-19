package kr.co.dw.Controller.AutoData.Coords;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.dw.Dto.Response.AutoCoordsDataResponse;
import kr.co.dw.Service.AutoData.Coords.AutoCoordsDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
public class AutoCoordsDataController {

	private final AutoCoordsDataService autoCoordsDataService;
	
	private final Logger looger = LoggerFactory.getLogger(AutoCoordsDataService.class);
	
	@GetMapping("/data/autoallCoordsdatainsert")
	public ResponseEntity<AutoCoordsDataResponse> autoallCoordsdatainsert() {
		
		AutoCoordsDataResponse response = autoCoordsDataService.allCoordsInsert();
		
		return new ResponseEntity<AutoCoordsDataResponse>(response, HttpStatus.OK);
	}
	
	@GetMapping("/data/autoCoordsdatainsert")
	public ResponseEntity<AutoCoordsDataResponse> autoCoordsInsert(@RequestParam(value = "parentEngRegionName") String parentEngRegionName) throws MalformedURLException, IOException, ParseException, InterruptedException {
		
		if(parentEngRegionName == null || parentEngRegionName.trim().isEmpty()) {
			//return new ResponseEntity<String>("파라미터가 null 이거나 빈칸입니다.", HttpStatus.BAD_REQUEST);
			return null;
		}
		AutoCoordsDataResponse response = autoCoordsDataService.CoordsInsert(parentEngRegionName);
		
		looger.info(response.toString());
		
		return new ResponseEntity<AutoCoordsDataResponse>(response, HttpStatus.OK);
		
	}
	

	
}