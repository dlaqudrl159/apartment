package kr.co.dw.Service.Apt;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.co.dw.Dto.Common.AptCoordsDto;
import kr.co.dw.Exception.CustomException;
import kr.co.dw.Exception.CustomExceptions.DatabaseException;
import kr.co.dw.Exception.ErrorCode.ErrorCode;
import kr.co.dw.Repository.Apt.AptRepository;

@ExtendWith(MockitoExtension.class)
public class AptServiceImplTest {

	@Mock
	private AptRepository aptRepository;

	@InjectMocks
	private AptServiceImpl aptServiceImpl;

	@Test
	void test() {
		// given

		// when

		// then
	}

	@Test
	void getMarkers_데이터베이스_예외_발생() {
		// given
		List<String> addresses = List.of("서울특별시 강남구");

		// when
		when(aptRepository.getMarkers(any())).thenThrow(new DatabaseException(ErrorCode.DATABASE_ERROR));

		// then
		assertThrows(CustomException.class, () -> {
			aptServiceImpl.getMarkers(addresses);
		});
	}

	@Test
	void getMarkers_예상치_못한_예외_발생() {
		// given
		List<String> addresses = List.of("서울특별시 강남구");

		// when
		when(aptRepository.getMarkers(any())).thenThrow(new RuntimeException("에러"));

		// then
		assertThrows(CustomException.class, () -> {
			aptServiceImpl.getMarkers(addresses);
		});

	}

	@Test
	void getMarkers_주소변환_예외_테스트() {
		// given
		List<String> addresses = null;
		assertThrows(CustomException.class, () -> {
			aptServiceImpl.getMarkers(addresses);
		});
	}

	@Test
	void getAptTransactionResponses_데이터베이스_예외_발생() {
		// given
		AptCoordsDto aptCoordsDto = new AptCoordsDto(null, null, null, null, null, null);
		// when
		when(aptRepository.getAptTransactionHistory(any(),any())).thenThrow(new DatabaseException(ErrorCode.DATABASE_ERROR));
		// then
		assertThrows(CustomException.class, () -> {
			aptServiceImpl.getAptTransactionResponses(aptCoordsDto);
		});
	}
	@Test
	void getAptTransactionResponses_예상치_못한_예외_발생() {
		// given
		AptCoordsDto aptCoordsDto = new AptCoordsDto(null, null, null, null, null, null);
		// when
		when(aptRepository.getAptTransactionHistory(any(),any())).thenThrow(new RuntimeException("에러"));
		// then
		assertThrows(CustomException.class, () -> {
			aptServiceImpl.getAptTransactionResponses(aptCoordsDto);
		});
	}
	
	@Test
	void getAptTransactionResponses_파라미터_null_발생() {
		// given
		AptCoordsDto aptCoordsDto = null;
		// then
		assertThrows(CustomException.class, () -> {
			aptServiceImpl.getAptTransactionResponses(aptCoordsDto);
		});
	}
}
