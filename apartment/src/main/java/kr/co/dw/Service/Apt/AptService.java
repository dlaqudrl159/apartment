package kr.co.dw.Service.Apt;

import java.util.List;

import kr.co.dw.Dto.Common.AptCoordsDto;
import kr.co.dw.Dto.Common.SearchAptDataDto;
import kr.co.dw.Dto.Common.SearchRoadNamesDto;
import kr.co.dw.Dto.Response.AptTransactionResponse;

public interface AptService {

	List<AptCoordsDto> getMarkers(List<String> addresses);

	List<AptTransactionResponse> getAptTransactionResponses(AptCoordsDto aptCoordsDto);

	SearchRoadNamesDto getRoadNames(SearchRoadNamesDto searchRoadNamesDto);

	SearchAptDataDto getCategoryClickData(SearchAptDataDto searchAptDatDto);

}
