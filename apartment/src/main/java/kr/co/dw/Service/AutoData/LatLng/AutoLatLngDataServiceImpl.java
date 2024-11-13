package kr.co.dw.Service.AutoData.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.dw.Dto.Common.AptLatLngDto;
import kr.co.dw.Mapper.AutoLatLngDataMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutoLatLngDataServiceImpl implements AutoLatLngDataService{
	
	private final Logger logger = LoggerFactory.getLogger(AutoLatLngDataServiceImpl.class);
	
	private final AutoLatLngDataMapper autoLatLngDataMapper;
	
	@Transactional
	@Override
	public List<AptLatLngDto> latLngInsert(String tableName) throws MalformedURLException, IOException, ParseException, InterruptedException {
		
		return getLatLng(autoLatLngDataMapper.getList(tableName), tableName);
		
	}

	@Override
	public List<AptLatLngDto> getLatLng(List<AptLatLngDto> list, String tableName)
			throws IOException, ParseException, InterruptedException {
		// TODO Auto-generated method stub
		JSONObject jsrs = null;
		for(int i = 0 ; i < list.size() ; i++) {
			
			AptLatLngDto aptLatLngDto = list.get(i);
			AptLatLngDto checkAptLatLngDto = autoLatLngDataMapper.getLatLng(aptLatLngDto);
			logger.info(String.valueOf(aptLatLngDto.equals(checkAptLatLngDto)));
			if(aptLatLngDto.equals(checkAptLatLngDto)) {
				continue;
			}else {
				logger.info(aptLatLngDto.toString());
				try {
					jsrs  = getparcel(aptLatLngDto);	
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
				    
				    aptLatLngDto.setLAT(((String) jspoint.get("y")).substring(0, latidx+6));
				    aptLatLngDto.setLNG(((String) jspoint.get("x")).substring(0, lngidx+6));			    
				}else {
					try {
						jsrs = getroadname(aptLatLngDto);
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
					    
					    aptLatLngDto.setLAT(((String) jspoint.get("y")).substring(0, latidx+6));
					    aptLatLngDto.setLNG(((String) jspoint.get("x")).substring(0, lngidx+6));			    
					}else {
						aptLatLngDto.setLAT("자료없음");
						aptLatLngDto.setLNG("자료없음");		
					}
				}
				
				autoLatLngDataMapper.insertLatLng(aptLatLngDto);
			}
			
		}
	
		return list;
	}

	@Override
	public JSONObject geocodersearchaddress(String searchAddr, String searchType) throws IOException, ParseException {
		// TODO Auto-generated method stub
		String apikey = "B7417C15-3D68-309A-A5F2-ACB988833093";
		String epsg = "epsg:4326";
		
		StringBuilder sb = new StringBuilder("https://api.vworld.kr/req/address");
		sb.append("?service=address");
		sb.append("&request=getCoord");
		sb.append("&format=json");
		sb.append("&crs=" + epsg);
		sb.append("&key=" + apikey);
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
	public JSONObject getparcel(AptLatLngDto aptLatLngDto) throws IOException, ParseException {
		// TODO Auto-generated method stub
		String searchType = "parcel";
		String Sigungu = aptLatLngDto.getSIGUNGU();
		String Bungi = aptLatLngDto.getBUNGI();		
		String searchAddr = Sigungu + " " + Bungi;
		return geocodersearchaddress(searchAddr, searchType);
	}

	@Override
	public JSONObject getroadname(AptLatLngDto aptLatLngDto) throws IOException, ParseException {
		// TODO Auto-generated method stub
		String searchType = "road";
		String Sigungu = aptLatLngDto.getSIGUNGU();
		String roadname = aptLatLngDto.getROADNAME();
		String searchAddr = Sigungu + " " + roadname;
		return geocodersearchaddress(searchAddr, searchType);
	}



}
