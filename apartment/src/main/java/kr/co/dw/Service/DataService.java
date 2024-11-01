package kr.co.dw.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import kr.co.dw.Domain.NameCountDto;

public interface DataService {

	List<NameCountDto> LatLngInsert(String tableName) throws MalformedURLException, IOException, ParseException, InterruptedException;

	List<NameCountDto> getLatLng(List<NameCountDto> list, String tableName) throws IOException, ParseException, InterruptedException;

	JSONObject geocodersearchaddress(String searchAddr, String searchType) throws IOException, ParseException;

	JSONObject getparcel(NameCountDto NameCountDto, String tableName) throws IOException, ParseException;

	JSONObject getroadname(NameCountDto NameCountDto, String tableName) throws IOException, ParseException;

	void AutoDataInsert(String RegionName);



	

}
