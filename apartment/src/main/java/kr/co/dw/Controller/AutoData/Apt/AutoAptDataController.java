package kr.co.dw.Controller.AutoData.Apt;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.dw.Domain.Sido;
import kr.co.dw.Dto.Response.AutoAptDataResponse;
import kr.co.dw.Exception.CustomException;
import kr.co.dw.Exception.ErrorCode.ErrorCode;
import kr.co.dw.Service.AutoData.Apt.AutoAptDataService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AutoAptDataController {

	private final AutoAptDataService autoAptDataService;
	
	private  final Logger logger = LoggerFactory.getLogger(AutoAptDataController.class);
	
	@PostMapping("/data/allautoaptdatainsert")
	public ResponseEntity<List<AutoAptDataResponse>> autoAllDataInsert() {
		return ResponseEntity.ok(autoAptDataService.allAutoAptDataInsert());
	}

	@PostMapping("/data/autoaptdatainsert")
	public ResponseEntity<AutoAptDataResponse> autoDataInsert(@RequestBody Sido Sido) {
		
		if(Sido.getKorSido() == null && Sido.getKorSido().isEmpty()) {
			logger.error("Sido파라미터가 NULL 이거나 isEmpty Sido={}", Sido);
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
		
		return ResponseEntity.ok(autoAptDataService.autoAptDataInsert(Sido.getKorSido()));
	}
}
