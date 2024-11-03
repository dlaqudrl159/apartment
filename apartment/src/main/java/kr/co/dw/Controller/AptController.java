package kr.co.dw.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.common.util.StringUtils;
import kr.co.dw.Domain.ApiDto;
import kr.co.dw.Domain.AptTransactionResponseDto;
import kr.co.dw.Domain.NameCountDto;
import kr.co.dw.Service.AptService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AptController {

	private final AptService AptService;
	
	@GetMapping("/api/getMarkers")
	private ResponseEntity<List<NameCountDto>>  getMarkers(@RequestParam("addressnameArr")  List<String> addressnameArr) {
		System.out.println(addressnameArr);
		return new ResponseEntity<>(AptService.getMarkers(addressnameArr), HttpStatus.OK);
	}
	
	@GetMapping("/api/getMarkerData")
	private ResponseEntity<List<AptTransactionResponseDto>> getMarkerData(@RequestParam("lat") String lat , @RequestParam("lng") String lng){
		
		return new ResponseEntity<List<AptTransactionResponseDto>>(AptService.getAptTransactionResponseDtolist(lat,lng), HttpStatus.OK);
	}
}
