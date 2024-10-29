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
	
	@GetMapping("/api/get")
	private List<NameCountDto> get(@RequestParam("addressnameArr")  List<String> addressnameArr) {
		
		return AptService.get(addressnameArr);
	}
	
	@GetMapping("/api/getMarkerData")
	private ResponseEntity<List<NameCountDto>> getMarkerData(@RequestParam("lat") String lat , @RequestParam("lng") String lng){
		
		return new ResponseEntity<List<NameCountDto>>(AptService.getLatLngNameCountDto(lat,lng), HttpStatus.OK);
	}
	
	@GetMapping("/api/getAptTrancsactionHistory")
	public ResponseEntity<AptTransactionResponseDto> getAptTrancsactionHistory(
			@RequestParam("apartmentname") String apartmentname , 
			@RequestParam("bungi") String bungi , 
			@RequestParam("sigungu") String sigungu, 
			@RequestParam("roadname") String roadname) {
		
		System.out.println(apartmentname);
		System.out.println(bungi);
		System.out.println(sigungu);
		System.out.println(roadname);
		
		return ResponseEntity.ok(AptService.getAptTrancsactionHistory(apartmentname,bungi,sigungu,roadname));
	}
	
}
