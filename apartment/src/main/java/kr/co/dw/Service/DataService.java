package kr.co.dw.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import kr.co.dw.Domain.NameCountDto;

public interface DataService {

	void LatLngInsert(String tableName) throws MalformedURLException, IOException, ParseException;

	List<NameCountDto> getLatLng(List<NameCountDto> list, String tableName) throws IOException, ParseException;

	JSONObject geocodersearchaddress(String searchAddr, String searchType) throws IOException, ParseException;

	String CHUNGCHEONGBUKDO(NameCountDto NameCountDto);

	JSONObject getparcel(NameCountDto NameCountDto, String tableName) throws IOException, ParseException;

	JSONObject getroadname(NameCountDto NameCountDto, String tableName) throws IOException, ParseException;

	String JEOLLABUKDO(NameCountDto NameCountDto);

	String GYEONGSANGNAMDO(NameCountDto NameCountDto);

	String GYEONGSANGBUKDO(NameCountDto NameCountDto);

	String CHUNGCHEONGNAMDO(NameCountDto NameCountDto);

	String GYEONGGIDO(NameCountDto NameCountDto);



	

}
