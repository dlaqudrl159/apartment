package kr.co.dw.Service.AutoData;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import kr.co.dw.Domain.ParentRegionName;
import kr.co.dw.Domain.Region;
import kr.co.dw.Dto.Common.AptLatLngDto;
import kr.co.dw.Dto.Common.AptTransactionDto;
import kr.co.dw.Dto.Common.RegionYearDto;
import kr.co.dw.Dto.Response.DataAutoInsertResponseDto;

public interface DataService {

	List<AptLatLngDto> LatLngInsert(String tableName) throws MalformedURLException, IOException, ParseException, InterruptedException;

	List<AptLatLngDto> getLatLng(List<AptLatLngDto> list, String tableName) throws IOException, ParseException, InterruptedException;

	JSONObject geocodersearchaddress(String searchAddr, String searchType) throws IOException, ParseException;

	JSONObject getparcel(AptLatLngDto AptLatLngDto, String tableName) throws IOException, ParseException;

	JSONObject getroadname(AptLatLngDto AptLatLngDto, String tableName) throws IOException, ParseException;
	
	DataAutoInsertResponseDto AutoDataInsert(String parentEngRegionName);

	DataAutoInsertResponseDto allex1(List<ParentRegionName> parentRegionList);

	DataAutoInsertResponseDto ex1(List<Region> regionList, ParentRegionName parentRegionName);

	StringBuilder getRTMSDataSvcAptTradeDev(RegionYearDto regionYearDto) throws IOException;

	String makeDealYearMonth(int num);

	List<String> makeDealYearMonthList(int num);

	String getElementContent(Element element, String tagName);

	List<AptTransactionDto> makeAptTransactionDto(NodeList nList, RegionYearDto regionYearDto);

	String AptTransactionDtoInsert(List<AptTransactionDto> list, ParentRegionName ParentRegionName);

	NodeList makeNodeList(StringBuilder sb) throws SAXException, IOException, ParserConfigurationException;

	String makeRoadName(String RoadName, String RoadNameBonbun, String RoadNameBubun);

	List<AptLatLngDto> makeAptLatLngDto(List<AptTransactionDto> list);
}
