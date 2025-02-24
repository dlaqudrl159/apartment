package kr.co.dw.Controller.Apt;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.dw.Dto.Common.AptCoordsDto;
import kr.co.dw.Dto.Common.SearchAptDataDto;
import kr.co.dw.Dto.Common.SearchRoadNamesDto;
import kr.co.dw.Dto.Response.AptTransactionResponse;
import kr.co.dw.Exception.CustomException;
import kr.co.dw.Exception.ErrorCode.ErrorCode;
import kr.co.dw.Service.Apt.AptService;
import kr.co.dw.Service.ExternalAPI.ExternalAPI;
import kr.co.dw.Service.ExternalAPI.ExternalAPIImpl;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AptController {

	private final AptService aptService;

	private final Logger logger = LoggerFactory.getLogger(AptController.class);

	@GetMapping("/api/getMarkers")
	public ResponseEntity<List<AptCoordsDto>> getMarkers(@RequestParam("addresses") List<String> addresses) {
		if (addresses == null || addresses.isEmpty()) {
			logger.error("주소목록 요청이 NULL 이거나 비어있습니다 addresses: {}", addresses);
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(aptService.getMarkers(addresses));
	}

	@GetMapping("/api/getMarkerData")
	public ResponseEntity<List<AptTransactionResponse>> getMarkerData(AptCoordsDto aptCoordsDto) {
		if (aptCoordsDto == null) {
			logger.error("aptCoordsDto: {} 아파트 정보가 NULL 입니다", aptCoordsDto);
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.status(HttpStatus.OK).body(aptService.getAptTransactionResponses(aptCoordsDto));
	}

	@PostMapping("/api/getRoadNames")
	public ResponseEntity<SearchRoadNamesDto> getRoadNames(@RequestBody SearchRoadNamesDto searchRoadNamesDto) {

		return ResponseEntity.status(HttpStatus.OK).body(aptService.getRoadNames(searchRoadNamesDto));
	}

	@PostMapping("/api/getCategoryClickData")
	public ResponseEntity<SearchAptDataDto> getCategoryClickData(@RequestBody SearchAptDataDto searchAptDatDto) {
		System.out.println(searchAptDatDto);

		return ResponseEntity.status(HttpStatus.OK).body(aptService.getCategoryClickData(searchAptDatDto));
	}
	
	 @GetMapping("/favicon.ico")
	 public void returnNoFavicon() {
	}
	
	private final ExternalAPI ExternalApIImpl;
	@GetMapping("/api/test")
	public void test() throws IOException {
		ExternalApIImpl.getRTMSDataSvcAptTradeDev();
	}
}
