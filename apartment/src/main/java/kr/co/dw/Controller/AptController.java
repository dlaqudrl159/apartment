package kr.co.dw.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	
	
	@GetMapping("/api/latlng")
	private ResponseEntity<List<NameCountDto>> test2(@RequestParam("lat") String lat , @RequestParam("lng") String lng){
		
		ResponseEntity<List<NameCountDto>> entity = null;
		
		System.out.println(lat);
		System.out.println(lng);
		
		List<NameCountDto> list = AptService.getLatLngNameCountDto(lat,lng);
		entity = new ResponseEntity<List<NameCountDto>>(list, HttpStatus.OK);
		return entity;
	}
	
	@GetMapping("/api/hello")
	private String hello() {
		System.out.println(System.getProperty("os.name"));
		String os = System.getProperty("os.name");
		return os;
	}
	
}
