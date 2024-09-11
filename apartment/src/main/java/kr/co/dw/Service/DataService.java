package kr.co.dw.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import kr.co.dw.Domain.NameCountDto;

public interface DataService {

	void LatLngInsert(String tableName) throws MalformedURLException, IOException, ParseException, InterruptedException;

	List<NameCountDto> getLatLng(List<NameCountDto> list, String tableName) throws IOException, ParseException, InterruptedException;

	JSONObject geocodersearchaddress(String searchAddr, String searchType) throws IOException, ParseException;

	JSONObject getparcel(NameCountDto NameCountDto, String tableName) throws IOException, ParseException;

	JSONObject getroadname(NameCountDto NameCountDto, String tableName) throws IOException, ParseException;

	void AutoDataInsert(String RegionName);



	

}
