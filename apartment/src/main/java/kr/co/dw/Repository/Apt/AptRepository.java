package kr.co.dw.Repository.Apt;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import kr.co.dw.Dto.Common.AptCoordsDto;
import kr.co.dw.Dto.Common.AptTransactionDto;
import kr.co.dw.Dto.Common.SearchAptDataDto;
import kr.co.dw.Dto.Common.SearchRoadNamesDto;
import kr.co.dw.Exception.CustomExceptions.DatabaseException;
import kr.co.dw.Exception.ErrorCode.ErrorCode;
import kr.co.dw.Mapper.AptMapper;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AptRepository {
	
	private final AptMapper aptMapper;
	
	private final Logger logger = LoggerFactory.getLogger(AptRepository.class);
	
	public List<AptCoordsDto> getMarkers(Map<String, String> map) {
		
		try {
			List<AptCoordsDto> aptCoordsDtos = aptMapper.getMarkers(map);
			return aptCoordsDtos;
		} catch (Exception e) {
			logger.error("마커 좌표 목록 조회 중 데이터베이스 오류 발생 map: {}", map, e);
			throw new DatabaseException(ErrorCode.DATABASE_ERROR);
		}
	}
	
	public List<AptTransactionDto> getAptTransactionHistory(AptCoordsDto aptCoordsDto, String korSido) {
		try {
			List<AptTransactionDto> aptTransactions = aptMapper.getAptTransactionHistory(aptCoordsDto, korSido);
			return aptTransactions;
		} catch (Exception e) {
			logger.error("거래내역 조회 중 데이터베이스 오류 발생 aptCoordsDto: {} korSido: {}",aptCoordsDto,korSido,e);
			throw new DatabaseException(ErrorCode.DATABASE_ERROR);
		}
	}
	
	public List<String> getRoadName(AptCoordsDto aptCoordsDto) {
		try {
			List<String> roadNames = aptMapper.getRoadName(aptCoordsDto);
			return roadNames;
		} catch (Exception e) {
			logger.error("도로주소명 조회 중 데이터베이스 오류 발생 aptCoordsDto: {}", aptCoordsDto, e);
			throw new DatabaseException(ErrorCode.DATABASE_ERROR);
		}
		
	}

	public SearchRoadNamesDto getRoadNames(SearchRoadNamesDto searchRoadNamesDto) {
		try {
			List<AptCoordsDto> aptCoordsDtos = aptMapper.getRoadNames(searchRoadNamesDto);
			aptCoordsDtos = aptCoordsDtos.stream().filter(t -> !"-".equals(t.getROADNAME())).sorted(Comparator.comparing(t -> t.getROADNAME())).toList();
			searchRoadNamesDto.setAptCoordsDto(aptCoordsDtos);
			return searchRoadNamesDto;
		} catch (Exception e) {
			logger.error("도로주소명 조회 중 데이터베이스 오류 발생 searchRoadNamesDto: {}" , searchRoadNamesDto , e);
			throw new DatabaseException(ErrorCode.DATABASE_ERROR);
		}
		
	}

	public SearchAptDataDto getCategoryClickData(SearchAptDataDto searchAptDatDto) {
		List<AptCoordsDto> aptCoordsDtos = aptMapper.getCategoryClickData(searchAptDatDto);
		aptCoordsDtos = aptCoordsDtos.stream().sorted(Comparator.comparing(t -> t.getAPARTMENTNAME())).toList();
		searchAptDatDto.setAptCoordsDto(aptCoordsDtos);
		return searchAptDatDto;
	}
}
