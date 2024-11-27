package kr.co.dw.Service.AutoData.Apt;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import kr.co.dw.Domain.Sido;
import kr.co.dw.Domain.Sigungu;
import kr.co.dw.Dto.Common.AptTransactionDto;
import kr.co.dw.Dto.Common.RegionYearDto;
import kr.co.dw.Dto.Response.AutoAptDataRes;
import kr.co.dw.Dto.Response.DataAutoInsertResponseDto;
import kr.co.dw.Dto.Response.ProcessedRes;

public interface AutoAptDataService2 {

	List<AutoAptDataRes> allAutoAptDataInsert();
	
	AutoAptDataRes autoAptDataInsert(String korSido);
	
}
