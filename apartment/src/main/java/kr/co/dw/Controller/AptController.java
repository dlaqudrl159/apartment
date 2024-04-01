package kr.co.dw.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.dw.Domain.NameCountDto;
import kr.co.dw.Service.AptService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AptController {

	private final AptService AptService;
	
	@GetMapping("/api/get")
	private String get() {
		
		System.out.println(AptService.get()); 
		return "test";
	}
	
	@GetMapping("/api/get2")
	private List<NameCountDto> get2() {
		
		return AptService.get2(); 
		
	}
	
}
