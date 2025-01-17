package kr.co.dw.Repository.Auto;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.co.dw.Dto.Common.AptCoordsDto;
import kr.co.dw.Exception.CustomException;
import kr.co.dw.Exception.ErrorCode.ErrorCode;
import kr.co.dw.Mapper.AutoCoordsDataMapper;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AutoCoordsDataRepository {
	
	private final Logger logger = LoggerFactory.getLogger(AutoCoordsDataRepository.class);
	
	private final AutoCoordsDataMapper autoCoordsDataMapper;
	
	public AptCoordsDto getCoordsDto(AptCoordsDto aptCoordsDto) {
		
		try {
			return autoCoordsDataMapper.getCoordsDto(aptCoordsDto);
		} catch (Exception e) {
			logger.error("aptCoordsDto: {} 좌표 정보 조회 중 데이터베이스 오류 발생", aptCoordsDto, e);
			throw new CustomException(ErrorCode.DATABASE_ERROR);
		}
	}
	
	public List<AptCoordsDto> getAptCoordsDtosBySido(String korSido) {
		try {
			List<AptCoordsDto> updateAptCoordsDtos = autoCoordsDataMapper.getAptCoordsDtosBySido(korSido);
			return updateAptCoordsDtos;
		} catch (Exception e) {
			logger.error("korSido: {} 지역 좌표 목록 조회 중 데이터베이스 오류 발생", korSido, e);
			throw new CustomException(ErrorCode.DATABASE_ERROR);
		}
	}
	
	@Transactional
	public void insertCoords(AptCoordsDto aptCoordsDto) {
		try {
			autoCoordsDataMapper.insertCoords(aptCoordsDto);
		} catch (Exception e) {
			logger.error("aptCoordsDto: {} 좌표 정보 입력 중 데이터베이스 오류 발생", aptCoordsDto, e);
			throw new CustomException(ErrorCode.DATABASE_ERROR);
		}
		
	}
	
	@Transactional
	public void notExistTransactionCoordsDelete(String korSido) {
		try {
			autoCoordsDataMapper.notExistTransactionCoordsDelete(korSido);
		} catch (Exception e) {
			logger.error("korSido: {} 지역 좌표 정리 중 데이터베이스 오류 발생", korSido, e);
			throw new CustomException(ErrorCode.DATABASE_ERROR);
		}
		
	}
	
}
