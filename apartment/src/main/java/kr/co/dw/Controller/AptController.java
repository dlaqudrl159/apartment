package kr.co.dw.Controller;

import java.util.List;

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
	
	@GetMapping("/api/get2")
	private List<NameCountDto> get2(@RequestParam("arr")  List<String> arr,
					   @RequestParam("year") String year) {
						
		return AptService.get2(arr,year);
	}
	
	@GetMapping("/api/hello")
	private String hello() {
		System.out.println(System.getProperty("os.name"));
		String os = System.getProperty("os.name");
		return os;
	}
	
}
