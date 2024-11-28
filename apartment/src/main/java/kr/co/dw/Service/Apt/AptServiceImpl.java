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

import kr.co.dw.Domain.Addresses;
import kr.co.dw.Domain.Addresses.Address;
import kr.co.dw.Dto.Common.AptCoordsDto;
import kr.co.dw.Dto.Common.AptTransactionDto;
import kr.co.dw.Dto.Response.AptTransactionResponse;
import kr.co.dw.Mapper.AptMapper;
import kr.co.dw.Repository.Apt.AptRepository;
import kr.co.dw.Utils.AptUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AptServiceImpl implements AptService {

	private final AptRepository aptRepository;

	private final Logger logger = LoggerFactory.getLogger(AptServiceImpl.class);

	@Override
	public List<AptCoordsDto> getMarkers(List<String> addresses) {
		List<AptCoordsDto> aptCoordsDtos = new ArrayList<>();
		List<Address> aAddresses = new Addresses(addresses).getList();
		if (!aAddresses.isEmpty()) {
			for (int i = 0; i < aAddresses.size(); i++) {
				Address address = aAddresses.get(i);
				if (!(address.getSido().equals("ERROR") || address.getSigungu().equals("ERROR"))) {
					Map<String, String> map = Map.of("sido", address.getSido(), "sigungu", address.getSigungu());
					aptCoordsDtos.addAll(aptRepository.getMarkers(map));
				}
			}
		}
		return aptCoordsDtos;
	}

	@Override
	public List<AptTransactionResponse> getAptTransactionResponseDtolist(AptCoordsDto aptCoordsDto) {

		List<AptTransactionResponse> aptTransactionResponses = new ArrayList<>();
		String korsido = AptUtils.SplitSigungu(aptCoordsDto.getSIGUNGU());

		List<AptTransactionDto> getAptTransactionHistory = aptRepository.getAptTrancsactionHistory(aptCoordsDto,
				korsido);
		if (getAptTransactionHistory != null && !getAptTransactionHistory.isEmpty()) {
			Map<String, List<AptTransactionDto>> aptTrancsactionHistoryMap = createMapByRoadName(getAptTransactionHistory);
			aptTrancsactionHistoryMap.forEach((roadName, aptTransactionDtos) -> {
				AptCoordsDto responseAptCoordsDto = new AptCoordsDto(aptCoordsDto.getSIGUNGU(), aptCoordsDto.getBUNGI(),
						aptCoordsDto.getAPARTMENTNAME(), roadName, aptCoordsDto.getLAT(), aptCoordsDto.getLNG());
				List<Integer> getTransactionYears = getTransactionYears(aptTransactionDtos);
				aptCoordsDto.setROADNAME(roadName);
				if (getTransactionYears.isEmpty()) {
					Integer year = Calendar.getInstance().get(Calendar.YEAR);
					getTransactionYears.add(year);
				}
				aptTransactionResponses.add(new AptTransactionResponse(getTransactionYears, aptTransactionDtos,
						responseAptCoordsDto));
			});
		} else {
			List<String> roadName = aptRepository.getRoadName(aptCoordsDto);
			roadName.forEach(rRoadName -> {
				AptCoordsDto responseAptCoordsDto = new AptCoordsDto(aptCoordsDto.getSIGUNGU(), aptCoordsDto.getBUNGI(),
						aptCoordsDto.getAPARTMENTNAME(), rRoadName, aptCoordsDto.getLAT(), aptCoordsDto.getLNG());
				aptTransactionResponses.add(new AptTransactionResponse(List.of(2024), null, responseAptCoordsDto));
			});
		}
		return aptTransactionResponses;
	}

	private Map<String, List<AptTransactionDto>> createMapByRoadName(List<AptTransactionDto> getAptTransactionHistory) {
		Map<String, List<AptTransactionDto>> aptTransactionHistoryMap = getAptTransactionHistory.stream()
				.collect(Collectors.groupingBy(aptTransactionDto -> aptTransactionDto.getROADNAME()));
		return aptTransactionHistoryMap;
	}

	private AptTransactionResponse createAptTransactionResponse(String roadName, List<AptTransactionDto> aptTransactionDtos, AptCoordsDto aptCoordsDto) {
		AptCoordsDto responseAptCoordsDto = new AptCoordsDto(aptCoordsDto.getSIGUNGU(), aptCoordsDto.getBUNGI(),
				aptCoordsDto.getAPARTMENTNAME(), roadName, aptCoordsDto.getLAT(), aptCoordsDto.getLNG());
		List<Integer> getTransactionYears = getTransactionYears(aptTransactionDtos);
		aptCoordsDto.setROADNAME(roadName);
		if (getTransactionYears.isEmpty()) {
			Integer year = Calendar.getInstance().get(Calendar.YEAR);
			getTransactionYears.add(year);
		}
		return new AptTransactionResponse(getTransactionYears, aptTransactionDtos,
				responseAptCoordsDto);
	}
	
	@Override
	public List<Integer> getTransactionYears(List<AptTransactionDto> getAptTrancsactionHistory) {

		return getAptTrancsactionHistory.stream().map(aptTransactionDtos -> aptTransactionDtos.getYear())
				.distinct().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
	}
	
	public List<String> getRoadNames(AptCoordsDto aptCoordsDto) {
		
		List<String> roadName = aptRepository.getRoadName(aptCoordsDto);
		
		return roadName;
	}
	
}