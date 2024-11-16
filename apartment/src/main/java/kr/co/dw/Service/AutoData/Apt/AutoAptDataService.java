package kr.co.dw.Service.AutoData.Apt;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import kr.co.dw.Domain.ParentRegionName;
import kr.co.dw.Domain.Region;
import kr.co.dw.Dto.Common.AptTransactionDto;
import kr.co.dw.Dto.Common.RegionYearDto;
import kr.co.dw.Dto.Response.DataAutoInsertResponseDto;

public interface AutoAptDataService {

	DataAutoInsertResponseDto autoDataInsert(String parentRegionName);
	
	DataAutoInsertResponseDto allex1(List<ParentRegionName> parentRegionList);

	DataAutoInsertResponseDto ex1(List<Region> regionList, ParentRegionName parentRegionName);
	
	String makeDealYearMonth(int num);

	List<String> makeDealYearMonthList(int num);
	
	StringBuilder getRTMSDataSvcAptTradeDev(RegionYearDto regionYearDto) throws IOException;
	
	Element makeNodeList(StringBuilder sb) throws SAXException, IOException, ParserConfigurationException;
	
	List<AptTransactionDto> makeAptTransactionDto(NodeList nList, RegionYearDto regionYearDto);
	
	String getElementContent(Element element, String tagName);
	
	String makeRoadName(String roadName, String roadNameBonbun, String roadNameBubun);
	
	String aptTransactionDtoInsert(List<AptTransactionDto> list, ParentRegionName parentRegionName);

	void deleteByRegionYear(ParentRegionName parentRegionName, String deleteYearMonth);
	
}
