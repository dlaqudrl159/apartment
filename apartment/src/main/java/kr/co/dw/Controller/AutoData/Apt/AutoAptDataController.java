package kr.co.dw.Controller.AutoData.Apt;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.dw.Domain.Sido;
import kr.co.dw.Dto.Response.AutoAptDataRes;
import kr.co.dw.Dto.Response.DataAutoInsertResponseDto;
import kr.co.dw.Dto.Response.ProcessedRes;
import kr.co.dw.Service.AutoData.Apt.AutoAptDataService;
import kr.co.dw.Service.AutoData.Apt.AutoAptDataService2;
import kr.co.dw.Service.AutoData.Apt.AutoAptDataServiceImpl2;
import kr.co.dw.sidosigungu.Sido2;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AutoAptDataController {

	private final AutoAptDataService autoAptDataService;
	
	private final Logger logger = LoggerFactory.getLogger(AutoAptDataController.class);

	@PostMapping("/data/allautoaptdatainsert")
	public ResponseEntity<List<AutoAptDataRes>> autoAllDataInsert() {
		return ResponseEntity.ok(autoAptDataService.allAutoAptDataInsert());
	}

	@PostMapping("/data/autoaptdatainsert")
	public ResponseEntity<AutoAptDataRes> autoDataInsert(@RequestBody Sido Sido) {
		System.out.println(Sido);
		return ResponseEntity.ok(autoAptDataService.autoAptDataInsert(Sido.getKorSido()));
	}
}
