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
import kr.co.dw.Exception.CustomExceptions.DatabaseException;
import kr.co.dw.Exception.CustomExceptions.ParserAndConverterException;
import kr.co.dw.Exception.ErrorCode.ErrorCode;
import kr.co.dw.Repository.Apt.AptRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AptServiceImpl implements AptService {

	private final AptRepository aptRepository;

	private final Logger logger = LoggerFactory.getLogger(AptServiceImpl.class);

	@Override
	public List<AptCoordsDto> getMarkers(List<String> addresses) {
		try {
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
		} catch (DatabaseException e) {
			logger.error("마커 좌표 목록 조회 중 데이터베이스 오류 발생 addresses: {}",addresses, e);
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.error("마커 좌표 목록 조회 중 예상치 못한 오류 발생 addresses: {}",addresses, e);
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<AptTransactionResponse> getAptTransactionResponses(AptCoordsDto aptCoordsDto) {
		try {
			List<AptTransactionResponse> aptTransactionResponses = new ArrayList<>();
			String korsido = RegionManager.splitSigungu(aptCoordsDto.getSIGUNGU()); 
			List<AptTransactionDto> aptTransactionHistory = aptRepository.getAptTransactionHistory(aptCoordsDto, korsido);
			if (!hasTransactionHistory(aptTransactionHistory)) {
				aptTransactionResponses.addAll(processTransactionHistory(aptTransactionHistory, aptCoordsDto)); 
			} else {
				aptTransactionResponses.addAll(processEmptyTransactionHistory(aptCoordsDto));
			}
			return aptTransactionResponses;
		} catch (ParserAndConverterException e) {
			logger.error("aptCoordsDto={} 파싱 또는 변환 중 오류 발생" ,aptCoordsDto, e);
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, "아파트 거래내역 조회 실패");
		} catch (DatabaseException e) {
			logger.error("aptCoordsDto={} 거래내역 조회 중 데이터베이스 오류 발생" ,aptCoordsDto, e);
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, "아파트 거래내역 조회 실패");
		} catch (Exception e) {
			logger.error("aptCoordsDto={} 거래내역 조회 중 예상치 못한 오류 발생", aptCoordsDto, e);
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, "아파트 거래내역 조회 실패");
		}
		
	}
	
	private boolean hasTransactionHistory(List<AptTransactionDto> history) {
	    return history.isEmpty();
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

	@Override
	public void getCategoryClickData(String sido, String sigungu, String dong, String apartmentName) {
		// TODO Auto-generated method stub
		
	}
}