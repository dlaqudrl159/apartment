package kr.co.dw.Controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import kr.co.dw.ApartmentApplication;
import kr.co.dw.Controller.Apt.AptController;
import kr.co.dw.Dto.Common.AptCoordsDto;
import kr.co.dw.Exception.CustomException;
import kr.co.dw.Service.Apt.AptServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AptControllerTest {

	@Mock
	AptServiceImpl aptServiceImpl;

	@InjectMocks
	AptController aptController;

	@Test
	void test() {
		// given

		// when

		// then
	}

	@Test
	void getMarkers_주소목록_null_테스트() {
		// given
		List<String> addresses = null;
		// then
		assertThrows(CustomException.class, () -> {
			aptController.getMarkers(addresses);
		});
	}

	@Test
	void getMarkers_주소목록_isEmpty_테스트() {
		// given
		List<String> addresses = new ArrayList<>();
		// then
		assertThrows(CustomException.class, () -> {
			aptController.getMarkers(addresses);
		});
	}
	
	@Test
    void getMarkerData_AptCoordsDto_null_테스트() {
        // given
        AptCoordsDto aptCoordsDto = null;

        // then
        assertThrows(CustomException.class, () -> {
            aptController.getMarkerData(aptCoordsDto);
        });
    }
	
}
