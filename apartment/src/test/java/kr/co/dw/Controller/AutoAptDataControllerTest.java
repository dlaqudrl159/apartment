package kr.co.dw.Controller;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.co.dw.Controller.AutoData.Apt.AutoAptDataController;
import kr.co.dw.Domain.Sido;
import kr.co.dw.Exception.CustomException;
import kr.co.dw.Service.AutoData.Apt.AutoAptDataServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AutoAptDataControllerTest {

	@Mock
	AutoAptDataServiceImpl autoAptDataSeriviceImpl;
	
	@InjectMocks
	AutoAptDataController autoAptDataContorller;
	
	@Test
	void test() {
		// given

		// when

		// then
	}
	
	@Test
	void autoDataInsert_sido파라미터_null_테스트() {
		// given
		Sido sido = null;
		// then
		assertThrows(CustomException.class, () -> {
			autoAptDataContorller.autoDataInsert(sido);
		});
	}
	@Test
	void autoDataInsert_sido_getKorSido_null_테스트() {
		// given
		Sido sido = new Sido(null, null);
		// then
		assertThrows(CustomException.class, () -> {
			autoAptDataContorller.autoDataInsert(sido);
		});
	}
	@Test
	void autoDataInsert_sido_getKorSido_isEmpty_테스트() {
		// given
		Sido sido = new Sido(" ", null);//isEmpty 전에 trim()이 잘 작동하는지 테스트 위해 공백 추가
		// then
		assertThrows(CustomException.class, () -> {
			autoAptDataContorller.autoDataInsert(sido);
		});
	}
	
}
