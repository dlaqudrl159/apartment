package kr.co.dw.Controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.dw.Domain.RegionManager;
import kr.co.dw.Dto.Response.DataAutoInsertResponseDto;
import kr.co.dw.Service.AutoData.DataService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DataController {

	private final DataService DataService;
	
	private final Logger logger = LoggerFactory.getLogger(DataController.class);
	
	@GetMapping("/data/LatLngInsert/{tableName}")
	public ResponseEntity<String> LatLngInsert(@PathVariable("tableName") String tableName) throws MalformedURLException, IOException, ParseException, InterruptedException {
		
		if(tableName == null || tableName.trim().isEmpty()) {
			return new ResponseEntity<String>("tableName이 null 이거나 빈칸입니다.", HttpStatus.BAD_REQUEST);
		}
		DataService.LatLngInsert(tableName);
		return new ResponseEntity<String>(tableName + "테이블 좌표 저장 완료", HttpStatus.OK);
		
	}
	
	@GetMapping("/data/AutoDataInsert")
	public ResponseEntity<DataAutoInsertResponseDto> AutoDataInsert(@RequestParam(required = false, value = "ParentEngRegionName") String ParentEngRegionName) throws java.text.ParseException{
		
		try {
			DataAutoInsertResponseDto dto = DataService.AutoDataInsert(ParentEngRegionName);
			
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
