package kr.co.dw.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.dw.Domain.ApiDto;
import kr.co.dw.Domain.NameCountDto;
import kr.co.dw.Service.AptService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AptController {

	private final AptService AptService;
	
	@GetMapping("/api/get")
	private List<NameCountDto> get(@RequestParam("arr")  List<String> arr,
					   @RequestParam("year") String year) {
						
		return AptService.get(arr,year);
	}
	
	@GetMapping("/api/get2")
	private List<NameCountDto> get2(@RequestParam("addressnameArr")  List<String> addressnameArr) {
		
		return AptService.get(addressnameArr);
	}
	
	@GetMapping("/api/latlng")
	private ResponseEntity<List<NameCountDto>> test2(@RequestParam("lat") String lat , @RequestParam("lng") String lng){
		
		return new ResponseEntity<List<NameCountDto>>(AptService.getLatLngNameCountDto(lat,lng), HttpStatus.OK);
	}
	
	@GetMapping("/api/getAptTrancsactionHistory")
	public ResponseEntity<List<ApiDto>> getAptTrancsactionHistory(
			@RequestParam("apartmentname") String apartmentname , 
			@RequestParam("bungi") String bungi , 
			@RequestParam("sigungu") String sigungu, 
			@RequestParam("roadname") String roadname) {
		System.out.println(apartmentname);
		System.out.println(bungi);
		System.out.println(sigungu);
		System.out.println(roadname);
		//List<ApiDto> list = AptService.getAptTrancsactionHistory(apartmentname,bungi,sigungu,roadname);
		
		return null;
	}
	
}
