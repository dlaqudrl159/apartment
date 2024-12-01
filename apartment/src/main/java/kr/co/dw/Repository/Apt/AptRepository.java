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
	
	public List<AptCoordsDto> getMarkers(Map<String, String> map) {
		return aptMapper.getMarkers(map);
	}
	
	public List<AptTransactionDto> getAptTransactionHistory(AptCoordsDto aptCoordsDto, String korSido) {
		try {
			List<AptTransactionDto> aptTransactions = aptMapper.getAptTransactionHistory(aptCoordsDto, korSido);
			return aptTransactions;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public List<String> getRoadName(AptCoordsDto aptCoordsDto) {
		return aptMapper.getRoadName(aptCoordsDto);
	}
	
}
