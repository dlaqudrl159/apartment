package kr.co.dw.Controller.Apt;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.dw.Dto.Common.AptCoordsDto;
import kr.co.dw.Dto.Response.AptTransactionResponse;
import kr.co.dw.Exception.CustomException;
import kr.co.dw.Exception.ErrorCode.ErrorCode;
import kr.co.dw.Service.Apt.AptService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AptController {

	private final AptService aptService;
	
	private final Logger logger = LoggerFactory.getLogger(AptController.class);
	
	@GetMapping("/api/getMarkers")
	public ResponseEntity<List<AptCoordsDto>> getMarkers(@RequestParam("addresses")  List<String> addresses) {
		
		if(addresses == null || addresses.isEmpty()) {
			logger.error("주소목록 요청이 null 이거나 비어있습니다 addresses={}", addresses);
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(aptService.getMarkers(addresses));
	}
	
	@GetMapping("/api/getMarkerData")
	public ResponseEntity<List<AptTransactionResponse>> getMarkerData(AptCoordsDto aptCoordsDto){
		if(aptCoordsDto == null) {
			logger.error("aptCoordsDto={} 아파트 정보가 null 입니다", aptCoordsDto);
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.status(HttpStatus.OK).body(aptService.getAptTransactionResponses(aptCoordsDto));
	}
	
	@GetMapping("/api/getCategoryClickData")
	public void getCategoryClickData(@RequestParam("eex1") String Sido, @RequestParam("eex2") String Sigungu, @RequestParam("eex3") String Dong, @RequestParam(required = false, value = "eex4") String ApartmentName) {
		System.out.println(Sido + System.lineSeparator() + Sigungu + System.lineSeparator() + Dong + System.lineSeparator() + ApartmentName);
	}
	
	@GetMapping("/api/getRoadNameList")
	public void getRoadNameList(@RequestParam("ex1") String ex1, @RequestParam("ex2") String ex2, @RequestParam("ex3") String ex3) {
		System.out.println(ex1 + System.lineSeparator() + ex2 + System.lineSeparator() + ex3);
	}
}
