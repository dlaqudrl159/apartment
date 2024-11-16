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

	private final AptMapper AptMapper;

	private final Logger logger = LoggerFactory.getLogger(AptServiceImpl.class);
	
	@Override
	public List<AptCoordsDto> getMarkers(List<String> addressnameArr) {
		// TODO Auto-generated method stub
		List<AptCoordsDto> AptLatLngDtoList = new ArrayList<>();
		List<AddressElement> list = new AddressNameArr(addressnameArr).getList();
		if (!list.isEmpty()) {
			list.stream().forEach(AddressElement -> {
				if(!(AddressElement.getCity().equals("ERROR") || AddressElement.getDistrict().equals("ERROR"))) {
					Map<String, String> map = Map.of("city", AddressElement.getCity(), "district",
							AddressElement.getDistrict());
					AptLatLngDtoList.addAll(AptMapper.getMarkers(map));
				}
			});
		}
		return AptLatLngDtoList;
	}
	
	@Override
	public List<AptTransactionResponseDto> getAptTransactionResponseDtolist(AptCoordsDto AptCoordsDto) {
		// TODO Auto-generated method stub
		
		List<AptTransactionResponseDto> AptTransactionResponseDto = new ArrayList<>();
		String parentRegion = AptUtils.SplitSigungu(AptCoordsDto.getSIGUNGU());
		String tableName = AptUtils.toEngParentRegion(parentRegion);
		
		List<AptTransactionDto> getAptTrancsactionHistory = AptMapper.getAptTrancsactionHistory(AptCoordsDto, tableName);
		if(!getAptTrancsactionHistory.isEmpty()) {
			Map<String, List<AptTransactionDto>> AptTrancsactionHistoryMap = getAptTrancsactionHistory.stream().collect(Collectors.groupingBy(AptTransactionDto -> AptTransactionDto.getROADNAME()));
			AptTrancsactionHistoryMap.forEach((roadName, AptTransactionDtoList) -> {
				AptCoordsDto responseAptLatLngDto = new AptCoordsDto(AptCoordsDto.getSIGUNGU(), AptCoordsDto.getBUNGI(), AptCoordsDto.getAPARTMENTNAME(), roadName, AptCoordsDto.getLAT(), AptCoordsDto.getLNG());
				List<Integer> getTransactionYears = getTransactionYears(AptTransactionDtoList);
				AptCoordsDto.setROADNAME(roadName);
				if(getTransactionYears.isEmpty()) {
					Integer year = Calendar.getInstance().get(Calendar.YEAR);
					getTransactionYears.add(year);
				}
				AptTransactionResponseDto.add(new AptTransactionResponseDto(getTransactionYears, AptTransactionDtoList, responseAptLatLngDto));
			});
		}else {
			List<String> roadName = AptMapper.getRoadName(AptCoordsDto);
			roadName.forEach(rRoadName -> {
				AptCoordsDto responseAptLatLngDto = new AptCoordsDto(AptCoordsDto.getSIGUNGU(), AptCoordsDto.getBUNGI(), AptCoordsDto.getAPARTMENTNAME(), rRoadName, AptCoordsDto.getLAT(), AptCoordsDto.getLNG());
				AptTransactionResponseDto.add(new AptTransactionResponseDto(List.of(2024), null, responseAptLatLngDto));
			});
		}
		return AptTransactionResponseDto;
	}
	
	@Override
	public List<Integer> getTransactionYears(List<AptTransactionDto> getAptTrancsactionHistory) {

		return getAptTrancsactionHistory.stream().map(AptTransactionDtoList -> AptTransactionDtoList.getYear()).distinct()
				.sorted(Comparator.reverseOrder()).collect(Collectors.toList());
	}
}