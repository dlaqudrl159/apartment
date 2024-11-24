package kr.co.dw.Service.Apt;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.co.dw.Domain.AddressNameArr;
import kr.co.dw.Domain.AddressNameArr.AddressElement;
import kr.co.dw.Dto.Common.AptCoordsDto;
import kr.co.dw.Dto.Common.AptTransactionDto;
import kr.co.dw.Dto.Response.AptTransactionResponseDto;
import kr.co.dw.Mapper.AptMapper;
import kr.co.dw.Utils.AptUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AptServiceImpl implements AptService {

	private final AptMapper aptMapper;

	private final Logger logger = LoggerFactory.getLogger(AptServiceImpl.class);
	
	@Override
	public List<AptCoordsDto> getMarkers(List<String> addressnameArr) {
		Long startTime = System.currentTimeMillis();
		
		List<AptCoordsDto> aptCoordsDtoList = new ArrayList<>();
		List<AddressElement> list = new AddressNameArr(addressnameArr).getList();
		if (!list.isEmpty()) {
			
			for(int i = 0 ; i < list.size() ; i++) {
				AddressElement addressElement = list.get(i);
				if(!(addressElement.getCity().equals("ERROR") || addressElement.getDistrict().equals("ERROR"))) {
					Map<String, String> map = Map.of("city", addressElement.getCity(), "district",
							addressElement.getDistrict());
					aptCoordsDtoList.addAll(aptMapper.getMarkers(map));
				}
			}
			
			/*list.stream().forEach(addressElement -> {
				if(!(addressElement.getCity().equals("ERROR") || addressElement.getDistrict().equals("ERROR"))) {
					Map<String, String> map = Map.of("city", addressElement.getCity(), "district",
							addressElement.getDistrict());
					aptCoordsDtoList.addAll(aptMapper.getMarkers(map));
				}
			});*/
			
			
		}
		Long endTime = System.currentTimeMillis();
		
		System.out.println(endTime - startTime);
		
		return aptCoordsDtoList;
	}
	
	@Override
	public List<AptTransactionResponseDto> getAptTransactionResponseDtolist(AptCoordsDto aptCoordsDto) {
		
		List<AptTransactionResponseDto> aptTransactionResponseDto = new ArrayList<>();
		String parentRegion = AptUtils.SplitSigungu(aptCoordsDto.getSIGUNGU());
		String tableName = AptUtils.toEngParentRegion(parentRegion);
		
		List<AptTransactionDto> getAptTrancsactionHistory = aptMapper.getAptTrancsactionHistory(aptCoordsDto, tableName);
		if(!getAptTrancsactionHistory.isEmpty()) {
			Map<String, List<AptTransactionDto>> aptTrancsactionHistoryMap = getAptTrancsactionHistory.stream().collect(Collectors.groupingBy(aptTransactionDto -> aptTransactionDto.getROADNAME()));
			aptTrancsactionHistoryMap.forEach((roadName, aptTransactionDtoList) -> {
				AptCoordsDto responseAptCoordsDto = new AptCoordsDto(aptCoordsDto.getSIGUNGU(), aptCoordsDto.getBUNGI(), aptCoordsDto.getAPARTMENTNAME(), roadName, aptCoordsDto.getLAT(), aptCoordsDto.getLNG());
				List<Integer> getTransactionYears = getTransactionYears(aptTransactionDtoList);
				aptCoordsDto.setROADNAME(roadName);
				if(getTransactionYears.isEmpty()) {
					Integer year = Calendar.getInstance().get(Calendar.YEAR);
					getTransactionYears.add(year);
				}
				aptTransactionResponseDto.add(new AptTransactionResponseDto(getTransactionYears, aptTransactionDtoList, responseAptCoordsDto));
			});
		}else {
			List<String> roadName = aptMapper.getRoadName(aptCoordsDto);
			roadName.forEach(rRoadName -> {
				AptCoordsDto responseAptCoordsDto = new AptCoordsDto(aptCoordsDto.getSIGUNGU(), aptCoordsDto.getBUNGI(), aptCoordsDto.getAPARTMENTNAME(), rRoadName, aptCoordsDto.getLAT(), aptCoordsDto.getLNG());
				aptTransactionResponseDto.add(new AptTransactionResponseDto(List.of(2024), null, responseAptCoordsDto));
			});
		}
		return aptTransactionResponseDto;
	}
	
	@Override
	public List<Integer> getTransactionYears(List<AptTransactionDto> getAptTrancsactionHistory) {

		return getAptTrancsactionHistory.stream().map(aptTransactionDtoList -> aptTransactionDtoList.getYear()).distinct()
				.sorted(Comparator.reverseOrder()).collect(Collectors.toList());
	}
}