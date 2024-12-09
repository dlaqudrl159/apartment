package kr.co.dw.Controller.AutoData.Coords;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.dw.Domain.Sido;
import kr.co.dw.Dto.Response.AutoCoordsDataResponse;
import kr.co.dw.Exception.CustomException;
import kr.co.dw.Exception.ErrorCode.ErrorCode;
import kr.co.dw.Service.AutoData.Coords.AutoCoordsDataService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AutoCoordsDataController {

	private final AutoCoordsDataService autoCoordsDataService;
	
	private final Logger logger = LoggerFactory.getLogger(AutoCoordsDataController.class);
	
	@PostMapping("/data/autoallCoordsdatainsert")
	public ResponseEntity<AutoCoordsDataResponse> autoallCoordsdatainsert() {
		
		AutoCoordsDataResponse response = autoCoordsDataService.allCoordsInsert();
		
		return new ResponseEntity<AutoCoordsDataResponse>(response, HttpStatus.OK);
	}
	
	@PostMapping("/data/autoCoordsdatainsert")
	public ResponseEntity<AutoCoordsDataResponse> autoCoordsInsert(@RequestBody Sido sido) throws MalformedURLException, IOException, ParseException, InterruptedException {

		if(sido.getKorSido() == null && sido.getKorSido().isEmpty()) {
			logger.error("Sido파라미터가 NULL 이거나 isEmpty Sido={}", sido);
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
		AutoCoordsDataResponse response = autoCoordsDataService.coordsInsert(sido.getKorSido());
		return new ResponseEntity<AutoCoordsDataResponse>(response, HttpStatus.OK);
	}
	
	@PostMapping("/data/notExistTransactionCoordsDelete")
	public void notExistTransactionCoordsDelete(@RequestBody Sido sido) {
		autoCoordsDataService.notExistTransactionCoordsDelete(sido.getKorSido());
	}
}
