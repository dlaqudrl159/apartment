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
import kr.co.dw.Domain.LatLngDto;
import kr.co.dw.Domain.NameCountDto;
import kr.co.dw.Mapper.AptMapper;
import kr.co.dw.Utils.AptUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AptServiceImpl implements AptService {

	private final AptMapper AptMapper;

	@Override
	public List<LatLngDto> getMarkers(List<String> addressnameArr) {
		// TODO Auto-generated method stub
		List<AddressElement> list = new AddressNameArrDto(addressnameArr).getList();
		List<LatLngDto> LatLngDtoList = new ArrayList<>();
		if (!list.isEmpty()) {
			list.stream().forEach(AddressElement -> {
				if(!(AddressElement.getCity().equals("ERROR") || AddressElement.getDistrict().equals("ERROR"))) {
					System.out.println(AddressElement.getCity() + " " + AddressElement.getDistrict());
					Map<String, String> map = Map.of("city", AddressElement.getCity(), "district",
							AddressElement.getDistrict());
					LatLngDtoList.addAll(AptMapper.getMarkers(map));
				}
			});
		}
		System.out.println(LatLngDtoList.size());
		System.out.println(LatLngDtoList.stream().distinct().toList().size());
		return LatLngDtoList.stream().distinct().toList();
	}

	@Override
	public List<NameCountDto> getLatLngNameCountDto(String lat, String lng) {
		// TODO Auto-generated method stub

		return AptMapper.getLatLngNameCountDto(lat, lng);
	}

	@Override
	public List<AptTransactionResponseDto> getAptTransactionResponseDtolist(String lat, String lng) {
		// TODO Auto-generated method stub
		List<AptTransactionResponseDto> AptTransactionResponseDto = new ArrayList<>();

		List<NameCountDto> list = AptMapper.getLatLngNameCountDto(lat, lng);

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
			AptTransactionResponseDto.add(new AptTransactionResponseDto(getTransactionYears, getAptTrancsactionHistory, NameCountDto));
		});

		return AptTransactionResponseDto;
	}

	public List<Integer> getTransactionYears(List<ApiDto> getAptTrancsactionHistory) {

		return getAptTrancsactionHistory.stream().map(ApiDto -> ApiDto.getYear()).distinct()
				.sorted(Comparator.reverseOrder()).collect(Collectors.toList());
	}

}
