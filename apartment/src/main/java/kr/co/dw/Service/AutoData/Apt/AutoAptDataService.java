package kr.co.dw.Service.AutoData.Apt;

import java.util.List;

import kr.co.dw.Dto.Response.AutoAptDataResponse;
import kr.co.dw.Dto.Response.ProcessedRes;

public interface AutoAptDataService {

	List<AutoAptDataResponse> allAutoAptDataInsert();

	AutoAptDataResponse autoAptDataInsert(String korSido);
	
	AutoAptDataResponse syncAptTransactionData(String korSido);

	List<ProcessedRes> processedAptData(String korSido);
	
}
