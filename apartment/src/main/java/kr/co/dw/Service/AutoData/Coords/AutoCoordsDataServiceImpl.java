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
	private String geocodersearchaddressUrl;
	
	@Value("${api.geocoder.service-key}") //"#{@environment.getProperty('geocodersearchaddress.apikey')}" // "${geocodersearchaddress.apikey}"
	private String geocodersearchaddressServiceKey;
	
	@Transactional
	@Override
	public List<AptCoordsDto> CoordsInsert(String parentEngRegionName) throws MalformedURLException, IOException, ParseException, InterruptedException {
		
		return getCoords(autoCoordsDataMapper.getList(parentEngRegionName));
		
	}

	@Override
	public List<AptCoordsDto> getCoords(List<AptCoordsDto> list)
			throws IOException, ParseException, InterruptedException {
		// TODO Auto-generated method stub
		JSONObject jsrs = null;
		for(int i = 0 ; i < list.size() ; i++) {
			
			AptCoordsDto aptCoordsDto = list.get(i);
			AptCoordsDto checkAptCoordsDto = autoCoordsDataMapper.getCoords(aptCoordsDto);
			logger.info(String.valueOf(aptCoordsDto.equals(checkAptCoordsDto)));
			if(aptCoordsDto.equals(checkAptCoordsDto)) {
				continue;
			}else {
				logger.info(aptCoordsDto.toString());
				try {
					jsrs  = getparcel(aptCoordsDto);	
				} catch (Exception e) {
					i--;	
					continue;
				}
				if(jsrs.get("status").equals("OK")) {
					JSONObject jsResult = (JSONObject) jsrs.get("result");
				    JSONObject jspoint = (JSONObject) jsResult.get("point");
				    
				    String lat = (String) jspoint.get("y");
				    int latidx =lat.indexOf(".");
				    String lng = (String) jspoint.get("x");
				    int lngidx = lng.indexOf(".");
				    
				    aptCoordsDto.setLAT(((String) jspoint.get("y")).substring(0, latidx+6));
				    aptCoordsDto.setLNG(((String) jspoint.get("x")).substring(0, lngidx+6));			    
				}else {
					try {
						jsrs = getroadname(aptCoordsDto);
					} catch (Exception e) {
						i--;	
						continue;
					}
					if(jsrs.get("status").equals("OK")) {
						JSONObject jsResult = (JSONObject) jsrs.get("result");
					    JSONObject jspoint = (JSONObject) jsResult.get("point");
					    
					    String lat = (String) jspoint.get("y");
					    int latidx =lat.indexOf(".");
					    String lng = (String) jspoint.get("x");
					    int lngidx = lng.indexOf(".");
					    
					    aptCoordsDto.setLAT(((String) jspoint.get("y")).substring(0, latidx+6));
					    aptCoordsDto.setLNG(((String) jspoint.get("x")).substring(0, lngidx+6));			    
					}else {
						aptCoordsDto.setLAT("자료없음");
						aptCoordsDto.setLNG("자료없음");		
					}
				}
				
				autoCoordsDataMapper.insertCoords(aptCoordsDto);
			}
			
		}
	
		return list;
	}

	@Override
	public JSONObject geocodersearchaddress(String searchAddr, String searchType) throws IOException, ParseException {
		// TODO Auto-generated method stub
		
		String epsg = "epsg:4326";
		StringBuilder sb = new StringBuilder(this.geocodersearchaddressUrl);
		sb.append("?service=address");
		sb.append("&request=getCoord");
		sb.append("&format=json");
		sb.append("&crs=" + epsg);
		sb.append("&key=" + this.geocodersearchaddressServiceKey);
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
		// TODO Auto-generated method stub
		String searchType = "parcel";
		String Sigungu = aptCoordsDto.getSIGUNGU();
		String Bungi = aptCoordsDto.getBUNGI();		
		String searchAddr = Sigungu + " " + Bungi;
		return geocodersearchaddress(searchAddr, searchType);
	}

	@Override
	public JSONObject getroadname(AptCoordsDto aptCoordsDto) throws IOException, ParseException {
		// TODO Auto-generated method stub
		String searchType = "road";
		String Sigungu = aptCoordsDto.getSIGUNGU();
		String roadname = aptCoordsDto.getROADNAME();
		String searchAddr = Sigungu + " " + roadname;
		return geocodersearchaddress(searchAddr, searchType);
	}

	@Override
	public AutoCoordsDataResponse allCoordsInsert() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutoCoordsDataResponse CoordsInsert2(String parentEngRegionName) {
		logger.info(parentEngRegionName);
		ParentRegionName parentRegionNmae = RegionManager.getInstance().getParentNameByEng(parentEngRegionName.toUpperCase());
		logger.info(parentRegionNmae.toString());
		getParentRegionAptCoordsDtoList(parentRegionNmae);
		return null;
	}
	
	public List<AptCoordsDto> getParentRegionAptCoordsDtoList(ParentRegionName parentRegionName) {
		
		List<AptCoordsDto> aptCoordsDto = autoCoordsDataMapper.getParentRegionAptCoordsDtoList(parentRegionName);
		logger.info(aptCoordsDto.toString());
		return null;
	}
	
	public AptCoordsDto getCoords(AptCoordsDto aptCoordsDto) {
		
		return autoCoordsDataMapper.getCoordsDto(aptCoordsDto);
	}
	
	public List<AptCoordsDto> getCoordsList(List<AptCoordsDto> aptCoordsList) {
		
		List<AptCoordsDto> aAptCoordsList = new ArrayList<>();
		
		for(int i = 0 ; i < aptCoordsList.size() ; i++) {
			aAptCoordsList.add(getCoords(aptCoordsList.get(i))); 
		}
		
		return aptCoordsList;
		
	}
	
	public ParentRegionName getParentRegionName(AptCoordsDto aptCoordsDto) {
		String sigungu = aptCoordsDto.getSIGUNGU();
		String[] splitString = sigungu.split(" ");
		if(splitString.length > 2) {
		return RegionManager.getInstance().getParentNameByEng(splitString[0]);
		}
		return null;
	}
}
