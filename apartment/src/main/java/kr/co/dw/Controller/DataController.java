package kr.co.dw.Controller;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import kr.co.dw.Service.DataService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DataController {

	private final DataService DataService;
	
	@GetMapping("/api/datainsert/{tableName}")
	private void test(@PathVariable("tableName") String tableName) throws MalformedURLException, IOException, ParseException, InterruptedException {
		
		DataService.LatLngInsert(tableName);
		System.out.println("좌표 저장 완료");
		
	}
	
	@GetMapping("/api/test")
	private String test2() throws IOException, ParserConfigurationException, SAXException {
		
		String[] region = {"SEOUL","BUSAN","DAEGU","INCHEON","GWANGJU","DAEJEON","ULSAN","SEJONG","GYEONGGIDO","GANGWONDO",
				"CHUNGCHEONGBUKDO","CHUNGCHEONGNAMDO","JEOLLABUKDO","JEOLLANAMDO","GYEONGSANGBUKDO","GYEONGSANGNAMDO","JEJU"};
		for(int i = 0 ; i < region.length ; i++) {
			DataService.test(region[i],i);
		}
		return "success";
	}
	
}
