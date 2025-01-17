package kr.co.dw.Service.AutoData.Apt;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.Rollback;

import kr.co.dw.Dto.Common.AptTransactionDto;
import kr.co.dw.Dto.Common.ProcessedAutoAptDataDto;
import kr.co.dw.Exception.CustomException;
import kr.co.dw.Exception.CustomExceptions.DatabaseException;
import kr.co.dw.Exception.ErrorCode.ErrorCode;
import kr.co.dw.Repository.Auto.AutoAptDataRepository;
import kr.co.dw.Service.AutoData.Apt.OpenApi.OpenApiService;
import kr.co.dw.Service.ParserAndConverter.ParserAndConverter;

@ExtendWith(MockitoExtension.class)
public class AutoAptDataServiceImplTest {

	@Mock
	AutoAptDataRepository autoAptDataRepository;
	
	@Mock
	ParserAndConverter aptDataParserService;
	
	@Mock
	OpenApiService openApiService;
	
	@InjectMocks
	AutoAptDataServiceImpl autoAptDataServiceImpl;
	
	@Test
	void test() {
		// given

		// when

		// then
	}
	
	@Rollback
	@DisplayName("deleteAptData가 DatabaseException를 throw할때")
	@Test 
	void syncAptTransactionData_오류_발생_테스트() {
		// given
		String korSido = "서울특별시";
		// when
		doThrow(new DatabaseException(ErrorCode.DATABASE_ERROR)).when(autoAptDataRepository).deleteAptData(any(), any(), any());
		// then	
		assertThrows(CustomException.class, () -> {
			autoAptDataServiceImpl.syncAptTransactionData(korSido);
		});
	}
	
	@Rollback
	@DisplayName("deleteAptData가 RuntimeException을 throw할때")
	@Test 
	void syncAptTransactionData_오류_발생_테스트2() {
		// given
		String korSido = "서울특별시";
		// when
		doThrow(new RuntimeException("에러")).when(autoAptDataRepository).deleteAptData(any(), any(), any());
		// then	
		assertThrows(CustomException.class, () -> {
			autoAptDataServiceImpl.syncAptTransactionData(korSido);
		});
	}
	
	@Rollback	
	@Test 
	void syncAptTransactionData_오류_발생_테스트3() {
		// given
		String korSido = "서울특별시";
		List<ProcessedAutoAptDataDto> successProcesseds = new ArrayList<>();
		List<ProcessedAutoAptDataDto> failProcesseds = new ArrayList<>();
		// when
		
		when(aptDataParserService.createProcessedsMap(any())).thenReturn(Map.of(false,failProcesseds,true,successProcesseds));
		
		when(aptDataParserService.createDealYearMonth(anyInt())).thenReturn("202312");
		
		when(aptDataParserService.createSuccessedAptTransactionDtos(any())).thenReturn(List.of(new AptTransactionDto()));
		
		doThrow(new DatabaseException(ErrorCode.DATABASE_ERROR)).when(autoAptDataRepository).insertAptData(any(), any());
		// then	
		assertThrows(CustomException.class, () -> {
			autoAptDataServiceImpl.syncAptTransactionData(korSido);
		});
	}
	
	@Rollback
	@DisplayName("insertAptData 오류 발생")
	@Test 
	void batchProcessAptTransactionDtos_오류_발생_테스트() {
		// given
		String korSido = "서울특별시";
		List<AptTransactionDto> dtos = List.of(new AptTransactionDto());
		// when
		doThrow(new DatabaseException(ErrorCode.DATABASE_ERROR)).when(autoAptDataRepository).insertAptData(any(), any());
		// then	
		assertThrows(DatabaseException.class, () -> {
			autoAptDataServiceImpl.batchProcessAptTransactionDtos(dtos, korSido);
		});
	}
	
	@Rollback
	@DisplayName("flushBatch 오류 발생")
	@Test 
	void batchProcessAptTransactionDtos_오류_발생_테스트2() {
		// given
		String korSido = "서울특별시";
		List<AptTransactionDto> dtos = List.of(new AptTransactionDto());
		// when
		doThrow(new DatabaseException(ErrorCode.DATABASE_ERROR)).when(autoAptDataRepository).flushBatch();
		// then	
		assertThrows(DatabaseException.class, () -> {
			autoAptDataServiceImpl.batchProcessAptTransactionDtos(dtos, korSido);
		});
	}
	
	@Rollback
	@DisplayName("autoAptDataInsert 오류 발생")
	@Test 
	void autoAptDataInsert_오류_발생_테스트() {
		// given
		String korSido = "서울특별시";
		// when
		doThrow(new DatabaseException(ErrorCode.DATABASE_ERROR))
        .when(autoAptDataRepository).
        deleteAptData(any(), any(), any());
		// then	
		assertThrows(CustomException.class, () -> {
			autoAptDataServiceImpl.autoAptDataInsert(korSido);
		});
	}
	
}
