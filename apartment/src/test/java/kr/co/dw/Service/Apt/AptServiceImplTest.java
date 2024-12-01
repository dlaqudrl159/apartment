package kr.co.dw.Service.Apt;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import kr.co.dw.Domain.RegionManager;
import kr.co.dw.Dto.Common.AptTransactionDto;

@SpringBootTest
@TestPropertySource("classpath:application-dev.properties")
public class AptServiceImplTest {

	@Autowired
	private AptServiceImpl aptServiceImpl;
	
	@Test
	void test() {
		AptTransactionDto dto = new AptTransactionDto();
		dto.setDEALYEARMONTH("-");
		AptTransactionDto dto2 = new AptTransactionDto();
		dto2.setDEALYEARMONTH("");
		List<AptTransactionDto> years = new ArrayList<>(List.of(dto));
		//List<Integer> list = aptServiceImpl.getTransactionYears(years);
		//System.out.println(list);
	}
	
	@Test
	void test2() {
		String sigungu = "";
		RegionManager.splitSigungu(sigungu);
	}
	
}
