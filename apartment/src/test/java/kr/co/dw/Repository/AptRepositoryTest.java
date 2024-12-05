package kr.co.dw.Repository;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.co.dw.Dto.Common.AptCoordsDto;
import kr.co.dw.Exception.CustomExceptions.DatabaseException;
import kr.co.dw.Mapper.AptMapper;
import kr.co.dw.Repository.Apt.AptRepository;

@ExtendWith(MockitoExtension.class)
public class AptRepositoryTest {

	@Mock
	AptMapper aptMapper;
	
	@InjectMocks
	AptRepository aptRepository;
	
	@Test
	void test() {
		// given

		// when

		// then
	}
	
	@Test
	void getMarkers_맵퍼_예외_발생() {
		// given
		Map<String, String> map = new HashMap<>();
		// when
		when(aptMapper.getMarkers(any())).thenThrow(new RuntimeException("에러"));
		// then
		assertThrows(DatabaseException.class, () -> {
			aptRepository.getMarkers(map);
		});
	}
	
	@Test
	void getAptTransactionHistory_맵퍼_예외_발생() {
		// given
		AptCoordsDto aptCoordsDto = new AptCoordsDto(null, null, null, null, null, null);
		String korSido = null;
		// when
		when(aptMapper.getAptTransactionHistory(any(),any())).thenThrow(new RuntimeException("에러"));
		// then
		assertThrows(DatabaseException.class, () -> {
			aptRepository.getAptTransactionHistory(aptCoordsDto, korSido);
		});
	}
	
	@Test
	void getRoadName_맵퍼_예외_발생() {
		// given
		AptCoordsDto aptCoordsDto = new AptCoordsDto(null, null, null, null, null, null);
		// when
		when(aptMapper.getRoadName(any())).thenThrow(new RuntimeException("에러"));
		// then
		assertThrows(DatabaseException.class, () -> {
			aptRepository.getRoadName(aptCoordsDto);
		});
	}
	
}
