package kr.co.dw.Service.AutoData.Coords;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import kr.co.dw.Dto.Common.AptCoordsDto;
import kr.co.dw.Dto.Common.ProcessedAutoCoordsDataDto;
import kr.co.dw.Dto.Response.AutoCoordsDataResponse;

public interface AutoCoordsDataService {

	List<AutoCoordsDataResponse> allCoordsInsert();

	AutoCoordsDataResponse coordsInsert(String korSido);

	AutoCoordsDataResponse syncCoordinateData(String korSido);
	
	boolean isCoordsExist(AptCoordsDto aptCoordsDto);
	
	void setCoordinates(AptCoordsDto aptCoordsDto, JSONObject response);

	String truncateCoordinate(String coordinate);

	void setNoDataCoordinates(AptCoordsDto aptCoordsDto);

	void notExistTransactionCoordsDelete(String korSido);

	List<ProcessedAutoCoordsDataDto> processCoords(List<AptCoordsDto> updateAptCoordsDtos);
	
}
