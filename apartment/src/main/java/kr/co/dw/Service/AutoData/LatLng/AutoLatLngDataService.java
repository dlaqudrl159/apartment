package kr.co.dw.Service.AutoData.LatLng;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import kr.co.dw.Dto.Common.AptLatLngDto;

public interface AutoLatLngDataService {

	List<AptLatLngDto> latLngInsert(String tableName) throws MalformedURLException, IOException, ParseException, InterruptedException;

	List<AptLatLngDto> getLatLng(List<AptLatLngDto> list, String tableName) throws IOException, ParseException, InterruptedException;

	JSONObject geocodersearchaddress(String searchAddr, String searchType) throws IOException, ParseException;

	JSONObject getparcel(AptLatLngDto aptLatLngDto) throws IOException, ParseException;

	JSONObject getroadname(AptLatLngDto aptLatLngDto) throws IOException, ParseException;
	
}
