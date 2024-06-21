package kr.co.dw.Controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.dw.Domain.NameCountDto;
import kr.co.dw.Service.AptService;
import kr.co.dw.Utils.DataUtils;
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
	private List<NameCountDto> get2(@RequestParam("arr")  List<String> arr,
					   @RequestParam("year") String year) {
						
		return AptService.get2(arr,year);
	}
	
	@GetMapping("/api/hello")
	private String hello() {
		System.out.println("hello");
		return "hello";
	}
	
}
