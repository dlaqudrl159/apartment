package kr.co.dw.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.co.dw.Domain.AddressNameArrDto;
import kr.co.dw.Domain.AddressNameArrDto.AddressElement;
import kr.co.dw.Domain.ApiDto;
import kr.co.dw.Domain.AptTransactionResponseDto;
import kr.co.dw.Domain.AptTransactionResponseDtolist;
import kr.co.dw.Domain.NameCountDto;
import kr.co.dw.Mapper.AptMapper;
import kr.co.dw.Utils.AptUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AptServiceImpl implements AptService{

	private final AptMapper AptMapper;

	@Override
	public List<NameCountDto> get(List<String> addressnameArr) {
		// TODO Auto-generated method stub
		List<AddressElement> list = new AddressNameArrDto(addressnameArr).getList();
		List<NameCountDto> NameCountDtoList = new ArrayList<>();
		if(!list.isEmpty()) {
			list.stream().forEach(AddressElement -> {
				Map<String, String> map = Map.of("city",AddressElement.getCity(),"district",AddressElement.getDistrict());
				NameCountDtoList.addAll(AptMapper.get(map)); 
			});
		}
		return NameCountDtoList;
	}
	
	@Override
	public List<NameCountDto> getLatLngNameCountDto(String lat, String lng) {
		// TODO Auto-generated method stub
		
		return AptMapper.getLatLngNameCountDto(lat,lng);
	}

	@Override
	public AptTransactionResponseDto getAptTrancsactionHistory(String apartmentname, String bungi, String sigungu, String roadname) {
		// TODO Auto-generated method stub
		String parentRegion = AptUtils.SplitSigungu(sigungu);
		String tableName = AptUtils.toEngParentRegion(parentRegion);
		Map<String, String> map = Map.of(
				"tableName", tableName,
				"apartmentname", apartmentname,
				"bungi", bungi,
				"sigungu", sigungu,
				"roadname", roadname
				);
		
		List<ApiDto> getAptTrancsactionHistory = AptMapper.getAptTrancsactionHistory(map);
		List<Integer> getTransactionYears = getTransactionYears(getAptTrancsactionHistory);
		
		return new AptTransactionResponseDto(getTransactionYears, getAptTrancsactionHistory);
	}
	
	public List<Integer> getTransactionYears(List<ApiDto> getAptTrancsactionHistory){
		
		return getAptTrancsactionHistory.stream()
				.map(ApiDto -> ApiDto.getYear())
				.distinct()
				.sorted(Comparator.reverseOrder())
				.collect(Collectors.toList());
	}

	@Override
	public List<AptTransactionResponseDtolist> getAptTransactionResponseDtolist(String lat, String lng) {
		// TODO Auto-generated method stub
		List<AptTransactionResponseDtolist> AptTransactionResponseDtolist = new ArrayList<>();
		
		List<NameCountDto> list = AptMapper.getLatLngNameCountDto(lat,lng);
		
		list.forEach(NameCountDto -> {
			String parentRegion = AptUtils.SplitSigungu(NameCountDto.getSIGUNGU());
			String tableName = AptUtils.toEngParentRegion(parentRegion);
			
			Map<String, String> map = Map.of(
					"tableName", tableName,
					"apartmentname", NameCountDto.getAPARTMENTNAME(),
					"bungi", NameCountDto.getBUNGI(),
					"sigungu", NameCountDto.getSIGUNGU(),
					"roadname", NameCountDto.getROADNAME()
					);
			List<ApiDto> getAptTrancsactionHistory = AptMapper.getAptTrancsactionHistory(map);
			List<Integer> getTransactionYears = getTransactionYears(getAptTrancsactionHistory);
			AptTransactionResponseDtolist.add(new AptTransactionResponseDtolist(getTransactionYears, getAptTrancsactionHistory, NameCountDto));
			});
		
		
		
		return AptTransactionResponseDtolist;
	}
	
}
