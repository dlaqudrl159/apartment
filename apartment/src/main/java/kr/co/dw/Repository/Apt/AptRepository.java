package kr.co.dw.Repository.Apt;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import kr.co.dw.Dto.Common.AptCoordsDto;
import kr.co.dw.Dto.Common.AptTransactionDto;
import kr.co.dw.Mapper.AptMapper;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AptRepository {
	
	private final AptMapper aptMapper;
	
	private final Logger logger = LoggerFactory.getLogger(AptRepository.class);
	
	public List<AptCoordsDto> getMarkers(Map<String, String> map) {
		return aptMapper.getMarkers(map);
	}
	
	public List<AptTransactionDto> getAptTransactionHistory(AptCoordsDto aptCoordsDto, String korSido) {
		try {
			List<AptTransactionDto> aptTransactions = aptMapper.getAptTransactionHistory(aptCoordsDto, korSido);
			return aptTransactions;
		} catch (Exception e) {
			logger.error("거래내역 조회 실패 aptCoordsDto={} korSido={}",aptCoordsDto,korSido,e);
		}
		return null;
	}
	
	public List<String> getRoadName(AptCoordsDto aptCoordsDto) {
		return aptMapper.getRoadName(aptCoordsDto);
	}
}
