package kr.co.dw.Service.AutoData.Coords;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.dw.Domain.ParentRegionName;
import kr.co.dw.Domain.RegionManager;
import kr.co.dw.Dto.Common.AptCoordsDto;
import kr.co.dw.Dto.Response.AutoCoordsDataResponse;
import kr.co.dw.Mapper.AutoCoordsDataMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutoCoordsDataServiceImpl implements AutoCoordsDataService{
	
	private final Logger logger = LoggerFactory.getLogger(AutoCoordsDataServiceImpl.class);
	
	private final AutoCoordsDataMapper autoCoordsDataMapper;
	
	@Value("${api.geocoder.url}")
	private String api_Geocoder_Url;
	
	@Value("${api.geocoder.service-key}") //"#{@environment.getProperty('geocodersearchaddress.apikey')}" // "${geocodersearchaddress.apikey}"
	private String api_Geocoder_Service_Key;

	@Override
	public JSONObject geocodersearchaddress(String searchAddr, String searchType) throws IOException, ParseException {
		
		String epsg = "epsg:4326";
		StringBuilder sb = new StringBuilder(this.api_Geocoder_Url);
		sb.append("?service=address");
		sb.append("&request=getCoord");
		sb.append("&format=json");
		sb.append("&crs=" + epsg);
		sb.append("&key=" + this.api_Geocoder_Service_Key);
		sb.append("&type=" + searchType);
		sb.append("&address=" + URLEncoder.encode(searchAddr, StandardCharsets.UTF_8));
		
		URL url = new URL(sb.toString());
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(),StandardCharsets.UTF_8));
		JSONParser jspa = new JSONParser();
		JSONObject jsob = (JSONObject) jspa.parse(br);
		JSONObject jsrs = (JSONObject) jsob.get("response");
		
		return jsrs;
	}

	@Override
	public JSONObject getparcel(AptCoordsDto aptCoordsDto) throws IOException, ParseException {
		String searchType = "parcel";
		String Sigungu = aptCoordsDto.getSIGUNGU();
		String Bungi = aptCoordsDto.getBUNGI();		
		String searchAddr = Sigungu + " " + Bungi;
		return geocodersearchaddress(searchAddr, searchType);
	}

	@Override
	public JSONObject getroadname(AptCoordsDto aptCoordsDto) throws IOException, ParseException {
		String searchType = "road";
		String Sigungu = aptCoordsDto.getSIGUNGU();
		String roadname = aptCoordsDto.getROADNAME();
		String searchAddr = Sigungu + " " + roadname;
		return geocodersearchaddress(searchAddr, searchType);
	}

	@Override
	public AutoCoordsDataResponse allCoordsInsert() {
		
		List<AutoCoordsDataResponse> totalresponse = new ArrayList<>();
		
		List<ParentRegionName> parentRegionNameLIst = RegionManager.getInstance().getParentRegionNameList();
		
		for(int i = 0 ; i < parentRegionNameLIst.size() ; i++) {
			AutoCoordsDataResponse response = CoordsInsert(parentRegionNameLIst.get(i).getEngParentName());
			if("ERROR".equals(response.getStatus())) {
				return new AutoCoordsDataResponse("ERROR", "01", parentRegionNameLIst.get(i).getEngParentName() + "지역 좌표 처리 중 오류 발생", null, totalresponse);
			}
			totalresponse.add(response);
		}
		
		return new AutoCoordsDataResponse("OK", "01", "전체 지역 좌표 입력 완료", null, totalresponse);
	}

	@Override
	public AutoCoordsDataResponse CoordsInsert(String parentEngRegionName) {
		logger.info(parentEngRegionName);
		ParentRegionName parentRegionName = RegionManager.getInstance().getParentName(parentEngRegionName);
		logger.info(parentRegionName.toString());
		List<AptCoordsDto> updateAptCoordsDtoList = getParentRegionAptCoordsDtoList(parentRegionName);
		
		List<AutoCoordsDataResponse> response = new ArrayList<>();
		List<AptCoordsDto> updateAptCoords = new ArrayList<>();
		for(int i = 0 ; i< updateAptCoordsDtoList.size() ; i++) {
			
			AptCoordsDto aptCoordsDto = updateAptCoordsDtoList.get(i);
			
			if(IsCoordsExist(aptCoordsDto)) {
				continue;
			}
			
			try {
				updateAptCoords.add(processCoords(aptCoordsDto));
				response.add(new AutoCoordsDataResponse("OK", "00", "좌표 조회 성공", aptCoordsDto));
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("Error processing LatLng for apt: " + aptCoordsDto.toString(), e);
				response.add(new AutoCoordsDataResponse("ERROR", "01", "좌표 입력 실패", aptCoordsDto));
				return new AutoCoordsDataResponse("ERROR", "01", parentEngRegionName + "지역 좌표 입력 실패", aptCoordsDto, response);
			}
			
		}
		
		
		
		return new AutoCoordsDataResponse("OK", "00", parentEngRegionName + "지역 좌표 입력 성공", null, response);
	}
	
	@Override
	public List<AptCoordsDto> getParentRegionAptCoordsDtoList(ParentRegionName parentRegionName) {
		
		List<AptCoordsDto> updateAptCoordsDtoList = autoCoordsDataMapper.getParentRegionAptCoordsDtoList(parentRegionName);
		return updateAptCoordsDtoList;
	}
	
	@Override
	public AptCoordsDto getCoordsDto(AptCoordsDto aptCoordsDto) {
		
		return autoCoordsDataMapper.getCoordsDto(aptCoordsDto);
	}
	
	@Override
	public boolean IsCoordsExist (AptCoordsDto aptCoordsDto) {
		
		return aptCoordsDto.equals(getCoordsDto(aptCoordsDto));
	}
	
	@Override
	public AptCoordsDto processCoords(AptCoordsDto aptCoordsDto) throws IOException, ParseException {
		JSONObject response = getparcel(aptCoordsDto);
		
	    if (!"OK".equals(response.get("status"))) {
	        response = getroadname(aptCoordsDto);
	    }
	    
	    if ("OK".equals(response.get("status"))) {
	        setCoordinates(aptCoordsDto, response);
	    } else {
	    	setNoDataCoordinates(aptCoordsDto);
	    }
	    
	    autoCoordsDataMapper.insertCoords(aptCoordsDto);
	    
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
	
	
}
