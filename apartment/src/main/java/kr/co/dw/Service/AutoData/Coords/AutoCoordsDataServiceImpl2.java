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
import kr.co.dw.Repository.Auto.AutoCoordsDataRepository;
import kr.co.dw.Service.AutoData.Coords.GeocoderApi.GeocoderApi;
import lombok.RequiredArgsConstructor;

//@Service
@RequiredArgsConstructor
public class AutoCoordsDataServiceImpl2 implements AutoCoordsDataService{
	
	private final Logger logger = LoggerFactory.getLogger(AutoCoordsDataServiceImpl2.class);
	
	private final AutoCoordsDataRepository autoCoordsDataRepository;
	
	private final GeocoderApi geocoderApi;
	
	@Override
	public AutoCoordsDataResponse allCoordsInsert() {
		
		List<AutoCoordsDataResponse> totalresponse = new ArrayList<>();
		
		List<Sido> sidos = RegionManager.getSidos();
		
		for(int i = 0 ; i < sidos.size() ; i++) {
			AutoCoordsDataResponse response = syncCoordinateData(sidos.get(i).getKorSido());
			if("ERROR".equals(response.getStatus())) {
				return new AutoCoordsDataResponse("ERROR", "01", sidos.get(i).getEngSido() + "지역 좌표 처리 중 오류 발생", null, totalresponse);
			}
			totalresponse.add(response);
		}
		logger.info("전국 시도 아파트 좌표 조회 작업 종료");
		return new AutoCoordsDataResponse("OK", "01", "전체 지역 좌표 입력 완료", null, totalresponse);
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
			List<AutoCoordsDataResponse> response = new ArrayList<>();
			List<AptCoordsDto> updateAptCoords = new ArrayList<>();
			
			for(int i = 0 ; i< updateAptCoordsDtos.size() ; i++) {
				AptCoordsDto aptCoordsDto = updateAptCoordsDtos.get(i);
				
				if(isCoordsExist(aptCoordsDto)) {
					continue;
				}
				
				try {
					updateAptCoords.add(processCoords(aptCoordsDto));
					response.add(new AutoCoordsDataResponse("OK", "00", "좌표 조회 성공", aptCoordsDto));
				} catch (Exception e) {
					logger.error("Error processing LatLng for apt: " + aptCoordsDto.toString(), e);
					response.add(new AutoCoordsDataResponse("ERROR", "01", "좌표 입력 실패", aptCoordsDto));
					return new AutoCoordsDataResponse("ERROR", "01", korSido + "지역 좌표 입력 실패", aptCoordsDto, response);
				}
			}
			logger.info("korSido={} 지역 좌표 정리 시작", korSido);
			autoCoordsDataRepository.notExistTransactionCoordsDelete(korSido);
			return new AutoCoordsDataResponse("OK", "00", korSido + "지역 좌표 입력 성공", null, response);
		} catch (Exception e) {
			return null;
		}
		
	}
	
	public List<ProcessedAutoCoordsDataDto> exist(List<AptCoordsDto> updateAptCoordsDtos) {
		
		for(int i = 0 ; i < updateAptCoordsDtos.size() ; i++) {
			AptCoordsDto aptCoordsDto = updateAptCoordsDtos.get(i);
			
			if(isCoordsExist(aptCoordsDto)) {
				continue;
			}
			ProcessedAutoCoordsDataDto processedAutoCoordsDataDto = new ProcessedAutoCoordsDataDto(null, aptCoordsDto);
		}
		
		return null;
	}
	
	@Override
	public boolean isCoordsExist (AptCoordsDto aptCoordsDto) {
		
		return aptCoordsDto.equals(autoCoordsDataRepository.getCoordsDto(aptCoordsDto));
	}
	
	public ProcessedAutoCoordsDataDto callGeoCoderApi(ProcessedAutoCoordsDataDto processedAutoCoordsDataDto) {
		try {
			logger.info("aptCoordsDto={} 좌표 조회 시작", processedAutoCoordsDataDto.getAptCoordsDto());
			JSONObject response = geocoderApi.getparcel(processedAutoCoordsDataDto.getAptCoordsDto());
			
		    if (!"OK".equals(response.get("status"))) {
		        response = geocoderApi.getroadname(processedAutoCoordsDataDto.getAptCoordsDto());
		    }
		    
		    if ("OK".equals(response.get("status"))) {
		        setCoordinates(processedAutoCoordsDataDto.getAptCoordsDto(), response);
		    } else {
		    	logger.info("aptCoordsDto={} 좌표 정보 없음 추후 추가 예정", processedAutoCoordsDataDto.getAptCoordsDto());
		    	setNoDataCoordinates(processedAutoCoordsDataDto.getAptCoordsDto());
		    }
		    processedAutoCoordsDataDto.setMessage("success");
		    processedAutoCoordsDataDto.setSuccess(true);
			return processedAutoCoordsDataDto;
		} catch (IOException e) {
			return null;
		} catch (ParseException e) {
			return null;
		} catch (Exception e) {
			return null;
		} 
	}
	
	@Override
	public AptCoordsDto processCoords(AptCoordsDto aptCoordsDto) throws IOException, ParseException {
		logger.info("aptCoordsDto={} 좌표 조회 시작", aptCoordsDto);
		JSONObject response = geocoderApi.getparcel(aptCoordsDto);
		
	    if (!"OK".equals(response.get("status"))) {
	        response = geocoderApi.getroadname(aptCoordsDto);
	    }
	    
	    if ("OK".equals(response.get("status"))) {
	        setCoordinates(aptCoordsDto, response);
	    } else {
	    	logger.info("aptCoordsDto={} 좌표 정보 없음 추후 추가 예정", aptCoordsDto);
	    	setNoDataCoordinates(aptCoordsDto);
	    }
	    
	    autoCoordsDataRepository.insertCoords(aptCoordsDto);
	    
		return aptCoordsDto;
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
