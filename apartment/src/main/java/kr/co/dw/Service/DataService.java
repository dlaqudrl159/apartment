package kr.co.dw.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import kr.co.dw.Domain.AptLatLngDto;

public interface DataService {

	List<AptLatLngDto> LatLngInsert(String tableName) throws MalformedURLException, IOException, ParseException, InterruptedException;

	List<AptLatLngDto> getLatLng(List<AptLatLngDto> list, String tableName) throws IOException, ParseException, InterruptedException;

	JSONObject geocodersearchaddress(String searchAddr, String searchType) throws IOException, ParseException;

	JSONObject getparcel(AptLatLngDto AptLatLngDto, String tableName) throws IOException, ParseException;

	JSONObject getroadname(AptLatLngDto AptLatLngDto, String tableName) throws IOException, ParseException;

	void AutoDataInsert(String RegionName);

}
