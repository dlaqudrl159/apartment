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
import kr.co.dw.Domain.RegionManager;
import kr.co.dw.Dto.Common.AptCoordsDto;
import kr.co.dw.Dto.Common.AptTransactionDto;
import kr.co.dw.Dto.Response.AptTransactionResponse;
import kr.co.dw.Exception.CustomException;
import kr.co.dw.Exception.ErrorCode.ErrorCode;
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
	public List<AptTransactionResponse> getAptTransactionResponses(AptCoordsDto aptCoordsDto) {
		try {
			List<AptTransactionResponse> aptTransactionResponses = new ArrayList<>();
			String korsido = RegionManager.splitSigungu(aptCoordsDto.getSIGUNGU()); 
			if(korsido == null) {
				logger.error("시군구에서 시도를 추출하는데 실패했습니다 파라미터 aptCoordsDto 확인 요망 aptCoordsDto={} sigungu={}" , aptCoordsDto, aptCoordsDto.getSIGUNGU());
				throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
			}
			List<AptTransactionDto> aptTransactionHistory = aptRepository.getAptTrancsactionHistory(aptCoordsDto, korsido);
			if (hasTransactionHistory(aptTransactionHistory)) {
				aptTransactionResponses.addAll(processTransactionHistory(aptTransactionHistory, aptCoordsDto)); 
			} else {
				aptTransactionResponses.addAll(processEmptyTransactionHistory(aptCoordsDto));
			}
			return aptTransactionResponses;
		} catch (Exception e) {
			logger.error("aptCoordsDto={} 거래내역 조회 중 실패 errorMessage={}",aptCoordsDto, e.getMessage());
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, "아파트 거래내역 조회 실패");
		}
		
	}
	
	private boolean hasTransactionHistory(List<AptTransactionDto> history) {
	    return !history.isEmpty();
	}
	
	private AptCoordsDto createResponseAptCoords(AptCoordsDto aptCoordsDto, String roadName) {
	    return new AptCoordsDto(
	        aptCoordsDto.getSIGUNGU(),
	        aptCoordsDto.getBUNGI(),
	        aptCoordsDto.getAPARTMENTNAME(),
	        roadName,
	        aptCoordsDto.getLAT(),
	        aptCoordsDto.getLNG()
	    );
	}
	
	private void ensureTransactionYearsNotEmpty(List<Integer> years) {
	    if (years.isEmpty()) {
	        years.add(Calendar.getInstance().get(Calendar.YEAR));
	    }
	}
	
	private List<AptTransactionResponse> processEmptyTransactionHistory(AptCoordsDto aptCoordsDto) {
	    List<AptTransactionResponse> responses = new ArrayList<>();
	    List<String> roadNames = aptRepository.getRoadName(aptCoordsDto);
	    
	    roadNames.forEach(roadName -> {
	        AptCoordsDto aptHistoryCoords = createResponseAptCoords(aptCoordsDto, roadName);
	        responses.add(new AptTransactionResponse(List.of(2024), null, aptHistoryCoords));
	    });
	    
	    return responses;
	}
	
	private List<AptTransactionResponse> processTransactionHistory(List<AptTransactionDto> aptTransactionHistory, AptCoordsDto aptCoordsDto) {
		Map<String, List<AptTransactionDto>> aptTrancsactionHistoryMap = createMapByRoadName(aptTransactionHistory);
		List<AptTransactionResponse> responses = new ArrayList<>();
		aptTrancsactionHistoryMap.forEach((roadName, aptTransactionDtos) -> {
			AptCoordsDto aptHistoryCoords = createResponseAptCoords(aptCoordsDto, roadName);
			List<Integer> transactionYears = getTransactionYears(aptTransactionDtos);
			ensureTransactionYearsNotEmpty(transactionYears);
			responses.add(new AptTransactionResponse(transactionYears, aptTransactionDtos, aptHistoryCoords));
		});
		return responses;
	}

	private Map<String, List<AptTransactionDto>> createMapByRoadName(List<AptTransactionDto> AptTransactionHistory) {
		return AptTransactionHistory.stream()
				.collect(Collectors.groupingBy(aptTransactionDto -> aptTransactionDto.getROADNAME()));
	}
	
	private List<Integer> getTransactionYears(List<AptTransactionDto> aptTransactionHistory) {
		return aptTransactionHistory.stream().map(aptTransactionDtos -> aptTransactionDtos.getYear())
				.distinct().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
	}
	
	private List<String> getRoadNames(AptCoordsDto aptCoordsDto) {
		
		List<String> roadName = aptRepository.getRoadName(aptCoordsDto);
		
		return roadName;
	}
	
}