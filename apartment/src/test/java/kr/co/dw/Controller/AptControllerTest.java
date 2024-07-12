package kr.co.dw.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import kr.co.dw.ApartmentApplication;

@SpringBootTest
@ContextConfiguration(classes = ApartmentApplication.class)
public class AptControllerTest {

	@Test
	void test() {
		
		System.out.println(System.getProperty("os.name"));
	}
	
	
	
	
}
