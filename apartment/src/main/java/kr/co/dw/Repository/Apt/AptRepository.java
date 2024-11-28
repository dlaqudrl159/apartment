package kr.co.dw.Repository.Apt;

import java.util.List;
import java.util.Map;

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
	
	public List<AptTransactionDto> getAptTrancsactionHistory(AptCoordsDto aptCoordsDto, String korSido) {
		return aptMapper.getAptTrancsactionHistory(aptCoordsDto, korSido);
	}
	
	public List<String> getRoadName(AptCoordsDto aptCoordsDto) {
		return aptMapper.getRoadName(aptCoordsDto);
	}
	
}
