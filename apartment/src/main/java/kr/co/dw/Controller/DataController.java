package kr.co.dw.Controller;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.dw.Service.DataService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DataController {

	private final DataService DataService;
	
	@GetMapping("/api/datainsert")
	private void test() throws MalformedURLException, IOException, ParseException {
		
		DataService.LatLngInsert();
		System.out.println("좌표 저장 완료");
	}
	
}
