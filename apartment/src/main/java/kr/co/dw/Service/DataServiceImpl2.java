package kr.co.dw.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import kr.co.dw.Domain.NameCountDto;

@Service
@Primary
public class DataServiceImpl2 implements DataService{

	
	@Override
	public void LatLngInsert(String tableName)
			throws MalformedURLException, IOException, ParseException, InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<NameCountDto> getLatLng(List<NameCountDto> list, String tableName)
			throws IOException, ParseException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject geocodersearchaddress(String searchAddr, String searchType) throws IOException, ParseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getparcel(NameCountDto NameCountDto, String tableName) throws IOException, ParseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getroadname(NameCountDto NameCountDto, String tableName) throws IOException, ParseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void AutoDataInsert(String RegionName) {
		// TODO Auto-generated method stub
		
	}

}
