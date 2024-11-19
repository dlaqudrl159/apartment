package kr.co.dw.Controller.AutoData.Apt;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.dw.Domain.ParentRegionName;
import kr.co.dw.Domain.RegionManager;
import kr.co.dw.Dto.Response.DataAutoInsertResponseDto;
import kr.co.dw.Exception.EmptyOrNullParamException;
import kr.co.dw.Exception.ErrorCode.ErrorCode;
import kr.co.dw.Service.AutoData.Apt.AutoAptDataService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AutoAptDataController {

	private final AutoAptDataService autoAptDataService;
	
	private final Logger logger = LoggerFactory.getLogger(AutoAptDataController.class);
	
	@GetMapping("/data/allautoaptdatainsert")
	public ResponseEntity<DataAutoInsertResponseDto> autoAllDataInsert() {
		return ResponseEntity.ok(autoAptDataService.allAutoAptDataInsert());
	}
	
	@GetMapping("/data/autoaptdatainsert")
	public ResponseEntity<DataAutoInsertResponseDto> autoDataInsert(@RequestParam(value = "parentEngRegionName") String parentEngRegionName) throws java.text.ParseException{
		try {
		/*if(parentEngRegionName == null || parentEngRegionName.trim().isEmpty()) {
			throw new EmptyOrNullParamException(ErrorCode.EMPTY_OR_NULL_Parameter, "파라미터 입력 오류 " + parentEngRegionName);
		}*/
		
		
			return ResponseEntity.ok(autoAptDataService.autoAptDataInsert(parentEngRegionName));
		} catch (Exception e) {
			logger.error("서버 Error 발생", e);
			return ResponseEntity.internalServerError().body(new DataAutoInsertResponseDto("500", null, e.getMessage(), 0, LocalDateTime.now()));
		}
	}
	
}
