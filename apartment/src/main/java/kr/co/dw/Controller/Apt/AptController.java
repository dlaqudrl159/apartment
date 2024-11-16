package kr.co.dw.Controller.Apt;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.dw.Dto.Common.AptCoordsDto;
import kr.co.dw.Dto.Response.AptTransactionResponseDto;
import kr.co.dw.Service.Apt.AptService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AptController {

	private final AptService aptService;
	
	@GetMapping("/api/getMarkers")
	private ResponseEntity<List<AptCoordsDto>> getMarkers(@RequestParam("addressnameArr")  List<String> addressnameArr) {
		return new ResponseEntity<>(aptService.getMarkers(addressnameArr), HttpStatus.OK);
	}
	
	@GetMapping("/api/getMarkerData")
	private ResponseEntity<List<AptTransactionResponseDto>> getMarkerData(AptCoordsDto aptCoordsDto){
		return new ResponseEntity<List<AptTransactionResponseDto>>(aptService.getAptTransactionResponseDtolist(aptCoordsDto), HttpStatus.OK);
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
