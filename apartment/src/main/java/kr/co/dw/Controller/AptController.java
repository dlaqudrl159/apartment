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
import kr.co.dw.Domain.LatLngDto;
import kr.co.dw.Domain.NameCountDto;
import kr.co.dw.Service.AptService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AptController {

	private final AptService AptService;
	
	@GetMapping("/api/getMarkers")
	private ResponseEntity<List<LatLngDto>>  getMarkers(@RequestParam("addressnameArr")  List<String> addressnameArr) {
		System.out.println(addressnameArr);
		return new ResponseEntity<>(AptService.getMarkers(addressnameArr), HttpStatus.OK);
	}
	
	@GetMapping("/api/getMarkerData")
	private ResponseEntity<List<AptTransactionResponseDto>> getMarkerData(@RequestParam("lat") String lat , @RequestParam("lng") String lng){
		
		return new ResponseEntity<List<AptTransactionResponseDto>>(AptService.getAptTransactionResponseDtolist(lat,lng), HttpStatus.OK);
	}
	
	@GetMapping("/api/getCategoryClickData")
	private void getCategoryClickData(@RequestParam("eex1") String Sido, @RequestParam("eex2") String Sigungu, @RequestParam("eex3") String Dong, @RequestParam(required = false, value = "eex4") String ApartmentName) {
		System.out.println(Sido + System.lineSeparator() + Sigungu + System.lineSeparator() + Dong + System.lineSeparator() + ApartmentName);
	}
	
	@GetMapping("/api/getRoadNameList")
	private void getRoadNameList(@RequestParam("ex1") String ex1, @RequestParam("ex2") String ex2, @RequestParam("ex3") String ex3) {
		System.out.println(ex1 + System.lineSeparator() + ex2 + System.lineSeparator() + ex3);
	}
}
