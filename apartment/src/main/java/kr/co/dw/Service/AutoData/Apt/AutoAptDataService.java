package kr.co.dw.Service.AutoData.Apt;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import kr.co.dw.Domain.Sigungu;
import kr.co.dw.Dto.Common.AptTransactionDto;
import kr.co.dw.Dto.Response.AutoAptDataResponse;

public interface AutoAptDataService {

	List<AutoAptDataResponse> allAutoAptDataInsert();

	AutoAptDataResponse autoAptDataInsert(String korSido);
	
	StringBuilder getRTMSDataSvcAptTradeDev(Sigungu sigungu, String dealYearMonth) throws IOException;
	
	boolean isResultMsg(Element eElement);
	
	Element makeNodeList(StringBuilder sb) throws SAXException, IOException, ParserConfigurationException;
	
	String getElementContent(Element element, String tagName);
	
	String makeRoadName(String roadName, String roadNameBonbun, String roadNameBubun);
	
	String aptTransactionDtoInsert(List<AptTransactionDto> list, String korSido);

	List<AptTransactionDto> makeAptTransactionDto(NodeList nList, String sido, String sigungu);

	
	
}
