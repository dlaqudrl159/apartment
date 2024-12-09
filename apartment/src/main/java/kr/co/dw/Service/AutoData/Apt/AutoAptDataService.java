package kr.co.dw.Service.AutoData.Apt;

import java.util.List;

import kr.co.dw.Dto.Common.ProcessedAutoAptDataDto;
import kr.co.dw.Dto.Response.AutoAptDataResponse;

public interface AutoAptDataService {

	List<AutoAptDataResponse> allAutoAptDataInsert();

	AutoAptDataResponse autoAptDataInsert(String korSido);
	
	AutoAptDataResponse syncAptTransactionData(String korSido);

	List<ProcessedAutoAptDataDto> processedAptData(String korSido);
	
}
