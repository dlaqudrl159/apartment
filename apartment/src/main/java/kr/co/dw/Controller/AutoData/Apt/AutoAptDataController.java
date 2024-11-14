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
import kr.co.dw.Service.AutoData.Apt.AutoAptDataService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AutoAptDataController {

	private final AutoAptDataService autoAptService;
	
	private final Logger logger = LoggerFactory.getLogger(AutoAptDataController.class);
	
	@GetMapping("/data/autodatainsert")
	public ResponseEntity<DataAutoInsertResponseDto> autoDataInsert(@RequestParam(required = false, value = "parentEngRegionName") String parentEngRegionName) throws java.text.ParseException{
		
		try {
			DataAutoInsertResponseDto dto = autoAptService.autoDataInsert(parentEngRegionName);
			
			return new ResponseEntity<DataAutoInsertResponseDto>(dto, HttpStatus.OK);
			
		} catch (IllegalArgumentException  e) {
			// TODO: handle exception
			logger.warn("잘못된 요청: {}", e.getMessage());
			return new ResponseEntity<DataAutoInsertResponseDto>(new DataAutoInsertResponseDto("400", null, e.getMessage(), 0, LocalDateTime.now()),HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("처리 중 오류 발생", e);
			return new ResponseEntity<DataAutoInsertResponseDto>(new DataAutoInsertResponseDto("500", null, e.getMessage(), 0, LocalDateTime.now()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
