package kr.co.dw.Controller.AutoData.Apt;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.dw.Dto.Response.DataAutoInsertResponseDto;
import kr.co.dw.Exception.EmptyOrNullRegion;
import kr.co.dw.Exception.ErrorCode.ErrorCode;
import kr.co.dw.Service.AutoData.Apt.AutoAptDataService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AutoAptDataController {

	private final AutoAptDataService autoAptService;
	
	private final Logger logger = LoggerFactory.getLogger(AutoAptDataController.class);
	
	@GetMapping("/data/allautoaptdatainsert")
	public ResponseEntity<DataAutoInsertResponseDto> autoAllDataInsert() {
		return null;
	}
	
	@GetMapping("/data/autoaptdatainsert")
	public ResponseEntity<DataAutoInsertResponseDto> autoDataInsert(@RequestParam(value = "parentEngRegionName") String parentEngRegionName) throws java.text.ParseException{
		
		if(parentEngRegionName == null || parentEngRegionName.isEmpty()) {
			throw new EmptyOrNullRegion(ErrorCode.EMPTY_OR_NULL_REGION, "파라미터 입력 오류 null or empty " + parentEngRegionName);
		}
		try {
			DataAutoInsertResponseDto response = autoAptService.autoDataInsert(parentEngRegionName);
			
			return new ResponseEntity<DataAutoInsertResponseDto>(response, HttpStatus.OK);
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("처리 중 오류 발생", e);
			return new ResponseEntity<DataAutoInsertResponseDto>(new DataAutoInsertResponseDto("500", null, e.getMessage(), 0, LocalDateTime.now()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
