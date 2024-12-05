package kr.co.dw.Repository;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mybatis.spring.SqlSessionTemplate;

import kr.co.dw.Dto.Common.AptTransactionDto;
import kr.co.dw.Dto.Response.ProcessedRes;
import kr.co.dw.Exception.CustomExceptions.DatabaseException;
import kr.co.dw.Exception.ErrorCode.ErrorCode;
import kr.co.dw.Mapper.AutoAptDataMapper;
import kr.co.dw.Repository.Auto.AutoAptDataRepository;

@ExtendWith(MockitoExtension.class)
public class AutoAptDataRepositoryTest {

	@Mock
	AutoAptDataMapper autoAptDataMapper;
	
	@Mock
	SqlSessionTemplate batchSqlSessionTemplate;
	
	@InjectMocks
	AutoAptDataRepository autoAptDataRepository;
	
	@Test
	void test() {
		// given

		// when

		// then
	}
	
	@Test
	void deleteAptData_오류_발생() {
		// given
		List<ProcessedRes> failProcesseds = new ArrayList<>(); 
		String korSido = "서울특별시";
		String deleteDealYearMonth = "202411";
		// when
		doThrow(new RuntimeException("에러")).when(autoAptDataMapper).deleteAptData(any(), any(), any());
		// then
		assertThrows(DatabaseException.class, () -> {
			autoAptDataRepository.deleteAptData(failProcesseds, korSido, deleteDealYearMonth);
		});
	}
	
	@Test
	void insertAptData_오류_발생() {
		// given
		AptTransactionDto dto = new AptTransactionDto();
		String korSido = "서울특별시";
		// when
		doThrow(new RuntimeException("에러")).when(autoAptDataMapper).insertAptData(any(), any());
		// then
		assertThrows(DatabaseException.class, () -> {
			autoAptDataRepository.insertAptData(dto, korSido);
		});
	}
	
	@Test
	void flushBatch_오류_발생() {
		// given
		when(batchSqlSessionTemplate.flushStatements()).thenThrow(new RuntimeException("에러"));
		assertThrows(DatabaseException.class, () -> {
			autoAptDataRepository.flushBatch();
		});
	}
}
