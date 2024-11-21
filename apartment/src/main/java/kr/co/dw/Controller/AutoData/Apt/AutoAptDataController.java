package kr.co.dw.Controller.AutoData.Apt;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	
	@PostMapping("/data/allautoaptdatainsert")
	public ResponseEntity<DataAutoInsertResponseDto> autoAllDataInsert() {
		return ResponseEntity.ok(autoAptDataService.allAutoAptDataInsert());
	}
	
	@PostMapping("/data/autoaptdatainsert")
	public ResponseEntity<DataAutoInsertResponseDto> autoDataInsert(@RequestBody String parentEngRegionName) throws java.text.ParseException{
		return null;
		/*try {
		if(parentEngRegionName == null || parentEngRegionName.trim().isEmpty()) {
			throw new EmptyOrNullParamException(ErrorCode.EMPTY_OR_NULL_Parameter, "파라미터 입력 오류 " + parentEngRegionName);
		}
		
		
			return ResponseEntity.ok(autoAptDataService.autoAptDataInsert(parentEngRegionName));
		} catch (Exception e) {
			logger.error("서버 Error 발생", e);
			return ResponseEntity.internalServerError().body(new DataAutoInsertResponseDto("500", null, e.getMessage(), 0, LocalDateTime.now()));
		}*/
	}
	
}
