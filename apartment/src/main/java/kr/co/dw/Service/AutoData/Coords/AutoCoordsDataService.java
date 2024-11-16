package kr.co.dw.Service.AutoData.Coords;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import kr.co.dw.Dto.Common.AptCoordsDto;
import kr.co.dw.Dto.Response.AutoCoordsDataResponse;

public interface AutoCoordsDataService {

	List<AptCoordsDto> CoordsInsert(String tableName) throws MalformedURLException, IOException, ParseException, InterruptedException;

	List<AptCoordsDto> getCoords(List<AptCoordsDto> list) throws IOException, ParseException, InterruptedException;

	JSONObject geocodersearchaddress(String searchAddr, String searchType) throws IOException, ParseException;

	JSONObject getparcel(AptCoordsDto aptCoordsDto) throws IOException, ParseException;

	JSONObject getroadname(AptCoordsDto aptCoordsDto) throws IOException, ParseException;

	
	
	AutoCoordsDataResponse allCoordsInsert();

	AutoCoordsDataResponse CoordsInsert2(String parentEngRegionName);
	
}
