package kr.co.dw.Repository.Auto;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.co.dw.Dto.Common.AptCoordsDto;
import kr.co.dw.Mapper.AutoCoordsDataMapper;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AutoCoordsDataRepository {

	private final AutoCoordsDataMapper autoCoordsDataMapper;
	
	public AptCoordsDto getCoordsDto(AptCoordsDto aptCoordsDto) {
		
		return autoCoordsDataMapper.getCoordsDto(aptCoordsDto);
	}
	
	public List<AptCoordsDto> getAptCoordsDtosBySido(String korSido) {
		
		List<AptCoordsDto> updateAptCoordsDtos = autoCoordsDataMapper.getAptCoordsDtosBySido(korSido);
		return updateAptCoordsDtos;
	}
	
	@Transactional
	public void insertCoords(AptCoordsDto aptCoordsDto) {
		autoCoordsDataMapper.insertCoords(aptCoordsDto);
	}
	
	@Transactional
	public void notExistTransactionCoordsDelete(String korSido) {
		autoCoordsDataMapper.notExistTransactionCoordsDelete(korSido);
	}
	
}
