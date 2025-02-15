package kr.co.dw.Service.AutoData.Coords;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.co.dw.Domain.Sido;
import kr.co.dw.Domain.RegionManager;
import kr.co.dw.Dto.Common.AptCoordsDto;
import kr.co.dw.Dto.Common.ProcessedAutoCoordsDataDto;
import kr.co.dw.Dto.Response.AutoCoordsDataResponse;
import kr.co.dw.Dto.Response.AutoCoordsDataResponse;
import kr.co.dw.Exception.CustomException;
import kr.co.dw.Exception.ErrorCode.ErrorCode;
import kr.co.dw.Repository.Auto.AutoCoordsDataRepository;
import kr.co.dw.Service.AutoData.Coords.GeocoderApi.GeocoderApi;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutoCoordsDataServiceImpl implements AutoCoordsDataService{
	
	private final Logger logger = LoggerFactory.getLogger(AutoCoordsDataServiceImpl.class);
	
	private final AutoCoordsDataRepository autoCoordsDataRepository;
	
	private final GeocoderApi geocoderApi;
	
	@Override
	public List<AutoCoordsDataResponse> allCoordsInsert() {
		
		List<AutoCoordsDataResponse> totalresponse = new ArrayList<>();
		
		List<Sido> sidos = RegionManager.getSidos();
		
		for(int i = 0 ; i < sidos.size() ; i++) {
			String korSido = sidos.get(i).getKorSido();
			try {
				AutoCoordsDataResponse response = syncCoordinateData(korSido);
				totalresponse.add(response);
			} catch (Exception e) {
				totalresponse.add(new AutoCoordsDataResponse(500, korSido + " 지역 좌표 입력 정리 중 오류 발생",
						new Sido(korSido, RegionManager.toEngSido(korSido)), null, null));
			}
			
		}
		logger.info("전국 시도 아파트 좌표 조회 작업 종료");
		return totalresponse;
	}

	@Override
	public AutoCoordsDataResponse coordsInsert(String korSido) {
		return syncCoordinateData(korSido);
	}
	
	@Override
	public AutoCoordsDataResponse syncCoordinateData(String korSido) {
		try {
			logger.info("korSido: {} 지역 좌표 입력 시작", korSido);
			List<AptCoordsDto> updateAptCoordsDtos = autoCoordsDataRepository.getAptCoordsDtosBySido(korSido);
			
			List<ProcessedAutoCoordsDataDto> ProcessedAutoCoordsDataDtos = processCoords(updateAptCoordsDtos);
			
			logger.info("korSido: {} 지역 좌표 정리 시작", korSido);
			autoCoordsDataRepository.notExistTransactionCoordsDelete(korSido);
			return new AutoCoordsDataResponse(200, korSido + " 지역 좌표 입력 정리 완료",
					new Sido(korSido, RegionManager.toEngSido(korSido)),
					ProcessedAutoCoordsDataDtos, ProcessedAutoCoordsDataDtos);
		} catch (Exception e) {
			logger.error("korSido: {} 지역 좌표 입력 정리 중 오류 발생", korSido, e);
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, korSido + " 지역 좌표 입력 정리 중 오류 발생");
		}
		
	}
	
	@Override
	public List<ProcessedAutoCoordsDataDto> processCoords(List<AptCoordsDto> updateAptCoordsDtos) {
		List<ProcessedAutoCoordsDataDto> ProcessedAutoCoordsDataDtos = new ArrayList<>();
		for(int i = 0 ; i < updateAptCoordsDtos.size() ; i++) {
			AptCoordsDto aptCoordsDto = updateAptCoordsDtos.get(i);
			
			if(isCoordsExist(aptCoordsDto)) {
				continue;
			}
			ProcessedAutoCoordsDataDto processedAutoCoordsDataDto = new ProcessedAutoCoordsDataDto(null, aptCoordsDto);
			ProcessedAutoCoordsDataDtos.add(callGeoCoderApi(processedAutoCoordsDataDto)); 
		}
		
		return ProcessedAutoCoordsDataDtos;
	}
	
	@Override
	public boolean isCoordsExist (AptCoordsDto aptCoordsDto) {
		
		return aptCoordsDto.equals(autoCoordsDataRepository.getCoordsDto(aptCoordsDto));
	}
	
	public ProcessedAutoCoordsDataDto callGeoCoderApi(ProcessedAutoCoordsDataDto processedAutoCoordsDataDto) {
		try {
			logger.info("aptCoordsDto: {} 좌표 조회 시작", processedAutoCoordsDataDto.getAptCoordsDto());
			JSONObject response = geocoderApi.getparcel(processedAutoCoordsDataDto.getAptCoordsDto());
			
		    if (!"OK".equals(response.get("status"))) {
		        response = geocoderApi.getroadname(processedAutoCoordsDataDto.getAptCoordsDto());
		    }
		    
		    if ("OK".equals(response.get("status"))) {
		        setCoordinates(processedAutoCoordsDataDto.getAptCoordsDto(), response);
		    } else {
		    	logger.info("aptCoordsDto: {} 좌표 정보 없음 추후 추가 예정", processedAutoCoordsDataDto.getAptCoordsDto());
		    	setNoDataCoordinates(processedAutoCoordsDataDto.getAptCoordsDto());
		    }
		    processedAutoCoordsDataDto.setMessage("success");
		    processedAutoCoordsDataDto.setSuccess(true);
		    autoCoordsDataRepository.insertCoords(processedAutoCoordsDataDto.getAptCoordsDto());
			return processedAutoCoordsDataDto;
		} catch (IOException e) {
			logger.error("지오코더API 좌표 검색 응답 데이터 Reader 오류 발생 processedAutoCoordsDataDto: {}", processedAutoCoordsDataDto, e);
			processedAutoCoordsDataDto.setMessage("fail");
			processedAutoCoordsDataDto.setSuccess(false);
			return processedAutoCoordsDataDto;
		} catch (ParseException e) {
			logger.error("지오코더API 좌표 검색 응답 데이터 JSON 파싱 중 오류 발생 processedAutoCoordsDataDto: {}", processedAutoCoordsDataDto, e);
			processedAutoCoordsDataDto.setMessage("fail");
			processedAutoCoordsDataDto.setSuccess(false);
			return processedAutoCoordsDataDto;
		} catch (Exception e) {
			logger.error("지오코더API 좌표 검색 중 예상치 못한 오류 발생 processedAutoCoordsDataDto: {}", processedAutoCoordsDataDto, e);
			processedAutoCoordsDataDto.setMessage("fail");
			processedAutoCoordsDataDto.setSuccess(false);
			return processedAutoCoordsDataDto;
		} 
	}
	
	@Override
	public void setCoordinates(AptCoordsDto aptCoordsDto, JSONObject response) {
	    
		JSONObject jsResult = (JSONObject) response.get("result");
	    JSONObject jspoint = (JSONObject) jsResult.get("point");
	    
	    String lat = (String) jspoint.get("y");
	    String lng = (String) jspoint.get("x");
	    
	    aptCoordsDto.setLAT(truncateCoordinate(lat));
	    aptCoordsDto.setLNG(truncateCoordinate(lng));
	}
	
	@Override
	public String truncateCoordinate(String coordinate) {
	    int decimalIndex = coordinate.indexOf(".");
	    return coordinate.substring(0, decimalIndex + 6);
	}
	
	@Override
	public void setNoDataCoordinates(AptCoordsDto aptCoordsDto) {
		aptCoordsDto.setLAT("자료없음");
		aptCoordsDto.setLNG("자료없음");
	}

	@Override
	public void notExistTransactionCoordsDelete(String korSido) {
		autoCoordsDataRepository.notExistTransactionCoordsDelete(korSido);
	}
	
	
}
