package kr.co.dw.Service.AutoData.Coords;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import kr.co.dw.Domain.Sido;
import kr.co.dw.Dto.Common.AptCoordsDto;
import kr.co.dw.Dto.Response.AutoCoordsDataResponse;

public interface AutoCoordsDataService {

	AutoCoordsDataResponse allCoordsInsert();

	AutoCoordsDataResponse CoordsInsert(String korSido);

	boolean isCoordsExist(AptCoordsDto aptCoordsDto);

	AptCoordsDto processCoords(AptCoordsDto aptCoordsDto) throws IOException, ParseException;
	
	void setCoordinates(AptCoordsDto aptCoordsDto, JSONObject response);

	String truncateCoordinate(String coordinate);

	void setNoDataCoordinates(AptCoordsDto aptCoordsDto);

	void notExistTransactionCoordsDelete(String korSido);

	
	
}
