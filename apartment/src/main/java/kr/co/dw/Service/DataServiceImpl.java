package kr.co.dw.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import kr.co.dw.Domain.ApiDto;
import kr.co.dw.Domain.NameCountDto;
import kr.co.dw.Domain.ParentRegionName;
import kr.co.dw.Domain.Region;
import kr.co.dw.Domain.RegionManager;
import kr.co.dw.Mapper.DataMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService{

	private final Logger logger = LoggerFactory.getLogger(DataServiceImpl.class);
	
	private final DataMapper DataMapper;
	
	private final Integer DELETEYEAR = 13;
	private final String PAGENO = "1";
	private final String NUMOFROWS = "10000";
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	@Transactional
	@Override
	public void LatLngInsert(String tableName) throws MalformedURLException, IOException, ParseException, InterruptedException {
			
		getLatLng(DataMapper.getList(tableName), tableName);
		
	}

	@Override
	public List<NameCountDto> getLatLng(List<NameCountDto> list, String tableName) throws IOException, ParseException, InterruptedException {
			
		JSONObject jsrs = null;
		for(int i = 0 ; i < list.size() ; i++) {
			
			NameCountDto NameCountDto = list.get(i);
			NameCountDto checkNameCountDto = DataMapper.getLatLng(NameCountDto);
			
			if(NameCountDto.equals(checkNameCountDto)) {
				continue;
			}else {
				
				try {
					jsrs  = getparcel(NameCountDto,tableName);	
				} catch (Exception e) {
					i--;	
					continue;
				}
				if(jsrs.get("status").equals("OK")) {
					JSONObject jsResult = (JSONObject) jsrs.get("result");
				    JSONObject jspoint = (JSONObject) jsResult.get("point");
				    
				    String lat = (String) jspoint.get("y");
				    int latidx =lat.indexOf(".");
				    String lng = (String) jspoint.get("x");
				    int lngidx = lng.indexOf(".");
				    
				    NameCountDto.setLAT(((String) jspoint.get("y")).substring(0, latidx+6));
				    NameCountDto.setLNG(((String) jspoint.get("x")).substring(0, lngidx+6));			    
				}else {
					try {
						jsrs = getroadname(NameCountDto,tableName);
					} catch (Exception e) {
						i--;	
						continue;
					}
					if(jsrs.get("status").equals("OK")) {
						JSONObject jsResult = (JSONObject) jsrs.get("result");
					    JSONObject jspoint = (JSONObject) jsResult.get("point");
					    
					    String lat = (String) jspoint.get("y");
					    int latidx =lat.indexOf(".");
					    String lng = (String) jspoint.get("x");
					    int lngidx = lng.indexOf(".");
					    
					    NameCountDto.setLAT(((String) jspoint.get("y")).substring(0, latidx+6));
					    NameCountDto.setLNG(((String) jspoint.get("x")).substring(0, lngidx+6));			    
					}else {
						NameCountDto.setLAT("자료없음");
					    NameCountDto.setLNG("자료없음");		
					}
				}
				
				DataMapper.InsertLatLng(NameCountDto);
			}
			
		}
	
		return list;
	}

	@Override
	public JSONObject geocodersearchaddress(String searchAddr,String searchType) throws IOException, ParseException {
		
		String apikey = "F0DBB350-67A6-39BB-A8BB-9237BB06612C";
		String epsg = "epsg:4326";
		
		StringBuilder sb = new StringBuilder("https://api.vworld.kr/req/address");
		sb.append("?service=address");
		sb.append("&request=getCoord");
		sb.append("&format=json");
		sb.append("&crs=" + epsg);
		sb.append("&key=" + apikey);
		sb.append("&type=" + searchType);
		sb.append("&address=" + URLEncoder.encode(searchAddr, StandardCharsets.UTF_8));
		
		URL url = new URL(sb.toString());
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(),StandardCharsets.UTF_8));
		JSONParser jspa = new JSONParser();
		JSONObject jsob = (JSONObject) jspa.parse(br);
		JSONObject jsrs = (JSONObject) jsob.get("response");
		
		return jsrs;
	}
	
	@Override
	public JSONObject getroadname(NameCountDto NameCountDto, String tableName) throws IOException, ParseException {
		
		String searchType = "road";
		
		String Sigungu = NameCountDto.getSIGUNGU();
		
		String roadname = NameCountDto.getROADNAME();
		String searchAddr = Sigungu + " " + roadname;
		return geocodersearchaddress(searchAddr, searchType);
		
	}
	@Override
	public JSONObject getparcel(NameCountDto NameCountDto, String tableName) throws IOException, ParseException {
		
		String searchType = "parcel";
		String Sigungu = NameCountDto.getSIGUNGU();
		
		String Bungi = NameCountDto.getBUNGI();		
		String searchAddr = Sigungu + " " + Bungi;
		return geocodersearchaddress(searchAddr, searchType);
		
	}

	@Transactional
	@Override
	public void AutoDataInsert(String RegionName) {
		// TODO Auto-generated method stub
		logger.info(RegionName + " " + "데이터 입력 시작");
		List<Region> list = RegionManager.getInstance().getListRegion(RegionName);
		ParentRegionName ParentRegionName = RegionManager.getInstance().getkorParentName(RegionName);
		String DbYear = makeDealYearMonth(DELETEYEAR);
		logger.info(DbYear + " = 오늘 월에서 1년을 뺀 월 " + ParentRegionName.getEngParentName() + " = 1년치 데이터 삭제 할 테이블");
		logger.info("삭제 시작");
		DataMapper.deleteRegionYear(RegionName, DbYear);
		logger.info("삭제 완료");
		list.forEach(Region -> loopRegion(Region,ParentRegionName));
		
	}
	
	private void ApiDtoInsert(List<ApiDto> list, ParentRegionName ParentRegionName) {
		
		SqlSession sqlSession = this.sqlSessionFactory.openSession(ExecutorType.BATCH);
		
		if(!list.isEmpty()) {
			try {
				getLatLng(makeNameCountDto(list), ParentRegionName.getEngParentName());
				
				list.forEach(ApiDto -> {
					Map<String, Object> map = new HashMap<>();
					map.put("ApiDto", ApiDto);
					map.put("RegionName", ParentRegionName.getEngParentName());
					sqlSession.insert("kr.co.dw.Mapper.DataMapper.DataInsert", map);
				});
				sqlSession.flushStatements();
				sqlSession.commit();
				sqlSession.close();
			} catch (IOException | ParseException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private void loopRegion(Region Region, ParentRegionName ParentRegionName) {
		for(int j = DELETEYEAR ; j >= 0 ; j--) {
			String DealYmd = makeDealYearMonth(j);
			try {
				StringBuilder sb = getRTMSDataSvcAptTradeDev(Region,DealYmd);
				
				NodeList nList = makeNodeList(sb);
				
				List<ApiDto> list = makeApiDto(nList, Region,ParentRegionName);
				
				ApiDtoInsert(list, ParentRegionName);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("error:{}" , e.getClass());
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("error:{}" , e.getClass());
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("error:{}" , e.getClass());
			} catch (RuntimeException e) {
				// TODO: handle exception
				e.printStackTrace();
				logger.error("error:{}" , e.getClass());
			}
		}
	}

	public List<NameCountDto> makeNameCountDto(List<ApiDto> list) {

		List<NameCountDto> NameCountDtolist = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {
			NameCountDto NameCountDto = new NameCountDto();
			String SIGUNGU = list.get(i).getSIGUNGU();
			String BUNGI = list.get(i).getBUNGI();
			String APARTMENTNAME = list.get(i).getAPARTMENTNAME();
			String ROADNAME = list.get(i).getROADNAME();
			NameCountDto.setSIGUNGU(SIGUNGU);
			NameCountDto.setBUNGI(BUNGI);
			NameCountDto.setAPARTMENTNAME(APARTMENTNAME);
			NameCountDto.setROADNAME(ROADNAME);
			NameCountDtolist.add(NameCountDto);
		}

		return NameCountDtolist;

	}
	
	public List<ApiDto> makeApiDto(NodeList nList, Region Region, ParentRegionName ParentRegionName) {
		List<ApiDto> list = new ArrayList<>();
		for(int i = 0 ; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			ApiDto ApiDto = new ApiDto();
			if(nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				
				String DealAmount = eElement.getElementsByTagName("dealAmount").item(0) == null ? "-" : eElement.getElementsByTagName("dealAmount").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("dealAmount").item(0).getTextContent().replaceAll("\"", "").trim();  
				String ReqgbN = eElement.getElementsByTagName("dealingGbn").item(0) == null ? "-" : eElement.getElementsByTagName("dealingGbn").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("dealingGbn").item(0).getTextContent().replaceAll("\"", "").trim();  
				String BuildYear = eElement.getElementsByTagName("buildYear").item(0) == null ? "-" : eElement.getElementsByTagName("buildYear").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("buildYear").item(0).getTextContent().replaceAll("\"", "").trim();  
				String DealYear = eElement.getElementsByTagName("dealYear").item(0) == null ? "-" : eElement.getElementsByTagName("dealYear").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("dealYear").item(0).getTextContent().replaceAll("\"", "").trim();  
				String ApartmentDong = eElement.getElementsByTagName("aptDong").item(0) == null ? "-" : eElement.getElementsByTagName("aptDong").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("aptDong").item(0).getTextContent().replaceAll("\"", "").trim();  
				String RegistartionDate = eElement.getElementsByTagName("rgstDate").item(0) == null ? "-" : eElement.getElementsByTagName("rgstDate").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("rgstDate").item(0).getTextContent().replaceAll("\"", "").trim();  
				String SellerGBN = eElement.getElementsByTagName("slerGbn").item(0) == null ? "-" : eElement.getElementsByTagName("slerGbn").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("slerGbn").item(0).getTextContent().replaceAll("\"", "").trim();  
				String BuyerGBN = eElement.getElementsByTagName("buyerGbn").item(0) == null ? "-" : eElement.getElementsByTagName("buyerGbn").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("buyerGbn").item(0).getTextContent().replaceAll("\"", "").trim();  
				String Dong = eElement.getElementsByTagName("umdNm").item(0) == null ? "-" : eElement.getElementsByTagName("umdNm").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("umdNm").item(0).getTextContent().replaceAll("\"", "").trim();  
				String ApartmentName = eElement.getElementsByTagName("aptNm").item(0) == null ? "-" : eElement.getElementsByTagName("aptNm").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("aptNm").item(0).getTextContent().replaceAll("\"", "").trim();  
				String DealMonth = eElement.getElementsByTagName("dealMonth").item(0) == null ? "-" : eElement.getElementsByTagName("dealMonth").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("dealMonth").item(0).getTextContent().replaceAll("\"", "").trim();  
				String DealDay = eElement.getElementsByTagName("dealDay").item(0) == null ? "-" : eElement.getElementsByTagName("dealDay").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("dealDay").item(0).getTextContent().replaceAll("\"", "").trim();  
				String AreaforExcusiveUse = eElement.getElementsByTagName("excluUseAr").item(0) == null ? "-" : eElement.getElementsByTagName("excluUseAr").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("excluUseAr").item(0).getTextContent().replaceAll("\"", "").trim();  
				String RdealerLawdnm = eElement.getElementsByTagName("estateAgentSggNm").item(0) == null ? "-" : eElement.getElementsByTagName("estateAgentSggNm").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("estateAgentSggNm").item(0).getTextContent().replaceAll("\"", "").trim();  
				String Jibun = eElement.getElementsByTagName("jibun").item(0) == null ? "-" : eElement.getElementsByTagName("jibun").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("jibun").item(0).getTextContent().replaceAll("\"", "").trim();  
				String RegionalCode = eElement.getElementsByTagName("landCd").item(0) == null ? "-" : eElement.getElementsByTagName("landCd").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("landCd").item(0).getTextContent().replaceAll("\"", "").trim();  
				String Floor = eElement.getElementsByTagName("floor").item(0) == null ? "-" : eElement.getElementsByTagName("floor").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("floor").item(0).getTextContent().replaceAll("\"", "").trim();  
				String CancleDealDay = eElement.getElementsByTagName("cdealDay").item(0) == null ? "-" : eElement.getElementsByTagName("cdealDay").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("cdealDay").item(0).getTextContent().replaceAll("\"", "").trim();  
				String CancleDealType = eElement.getElementsByTagName("cdealType").item(0) == null ? "-" : eElement.getElementsByTagName("cdealType").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("cdealType").item(0).getTextContent().replaceAll("\"", "").trim();  
				String RoadName = eElement.getElementsByTagName("roadNm").item(0) == null ? "-" : eElement.getElementsByTagName("roadNm").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("roadNm").item(0).getTextContent().replaceAll("\"", "").trim();  
				String Bonbun = eElement.getElementsByTagName("bonbun").item(0) == null ? "-" : eElement.getElementsByTagName("bonbun").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("bonbun").item(0).getTextContent().replaceAll("\"", "").trim();  
				String Bubun = eElement.getElementsByTagName("bubun").item(0) == null ? "-" : eElement.getElementsByTagName("bubun").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("bubun").item(0).getTextContent().replaceAll("\"", "").trim();  
				String RoadNameBonbun = eElement.getElementsByTagName("roadNmBonbun").item(0) == null ? "-" : eElement.getElementsByTagName("roadNmBonbun").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("roadNmBonbun").item(0).getTextContent().replaceAll("\"", "").trim();  
				String RoadNameBubun = eElement.getElementsByTagName("roadNmBubun").item(0) == null ? "-" : eElement.getElementsByTagName("roadNmBubun").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("roadNmBubun").item(0).getTextContent().replaceAll("\"", "").trim();
				String SggCd = eElement.getElementsByTagName("sggCd").item(0) == null ? "-" : eElement.getElementsByTagName("sggCd").item(0).getTextContent().replaceAll("\"", "").trim().equals("") ? "-" : eElement.getElementsByTagName("sggCd").item(0).getTextContent().replaceAll("\"", "").trim();  
				
				ApiDto.setSIGUNGU(ParentRegionName.getKorParentName() + " " + Region.getRegionName() + " " +  Dong);
				ApiDto.setBUNGI(Jibun);				
				ApiDto.setBONBUN(Bonbun);
				ApiDto.setBUBUN(Bubun);
				ApiDto.setAPARTMENTNAME(ApartmentName);
				ApiDto.setAREAFOREXCLUSIVEUSE(AreaforExcusiveUse);
				ApiDto.setDEALYEARMONTH(DealYear + String.format("%02d", Integer.parseInt(DealMonth)));
				ApiDto.setDEALDAY(DealDay);
				ApiDto.setDEALAMOUNT(DealAmount);
				ApiDto.setAPARTMENTDONG(ApartmentDong);
				ApiDto.setFLOOR(Floor);
				ApiDto.setBUYERGBN(BuyerGBN);
				ApiDto.setSELLERGBN(SellerGBN);
				ApiDto.setBUILDYEAR(BuildYear);
				ApiDto.setROADNAME(makeRoadName(RoadName, RoadNameBonbun, RoadNameBubun));
				ApiDto.setCANCLEDEALDAY(CancleDealDay);
				ApiDto.setREQGBN(ReqgbN);
				ApiDto.setRDEALERLAWDNM(RdealerLawdnm);
				ApiDto.setREGISTRATIONDATE(RegistartionDate);
				ApiDto.setSGGCD(SggCd);
				
				list.add(ApiDto);			
				}
		}
		
		return list;
	}
	
	public String makeRoadName(String RoadName, String RoadNameBonbun, String RoadNameBubun) {
		RoadName = RoadName.trim();
		if (RoadName.equals("-")) {
			RoadName = "";
		}
		if (RoadNameBonbun.equals("-")) {
			RoadNameBonbun = "";
		}
		if (RoadNameBubun.equals("-")) {
			RoadNameBubun = "";
		}
		RoadNameBonbun = RoadNameBonbun + "!";
		RoadNameBubun = RoadNameBubun + "!";
		RoadNameBonbun = RoadNameBonbun.replace("0", " ").trim().replace(" ", "0").replace("!", "");

		RoadNameBubun = RoadNameBubun.replace("0", " ").trim().replace(" ", "0").replace("!", "");
		if (RoadNameBonbun.length() != 0) {
			RoadName = RoadName + " " + RoadNameBonbun;
			if (RoadNameBubun.length() != 0) {
				RoadName = RoadName + "-" + RoadNameBubun;
			}
		} else if (RoadNameBonbun.length() == 0) {
			if (RoadNameBubun.length() != 0) {
				RoadName = RoadName + " " + RoadNameBubun;
			}
		}
		RoadName = RoadName.trim();
		if (RoadName.equals("")) {
			RoadName = "-";
		}
		return RoadName;

	}
	
	private NodeList makeNodeList(StringBuilder sb) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Document document = null;
		Element root = null;
		NodeList nList = null;
		
		
		builder = factory.newDocumentBuilder();
		document = builder.parse(new InputSource(new StringReader(sb.toString())));
		document.getDocumentElement().normalize();
		nList = document.getElementsByTagName("item");
		root = document.getDocumentElement();
		
		
		String resultMsg = root.getElementsByTagName("resultMsg").item(0) == null ? "-"
				: root.getElementsByTagName("resultMsg").item(0).getTextContent();
		String resultCode = root.getElementsByTagName("resultCode").item(0) == null ? "-"
				: root.getElementsByTagName("resultCode").item(0).getTextContent();
		String resultTotalCount = root.getElementsByTagName("totalCount").item(0) == null ? "-"
				: root.getElementsByTagName("totalCount").item(0).getTextContent();
		if (!resultCode.equals("000")) {
			logger.info("resultMsg:{}","resultCode:{}","resultTotalCount:{}", resultMsg,resultCode,resultTotalCount);
			throw new RuntimeException();
		}
		return nList;
	}

	public String makeDealYearMonth(int j) {

		Calendar cal = Calendar.getInstance();
		String year = String.valueOf(cal.get(Calendar.YEAR));
		int month = (cal.get(Calendar.MONTH) + 1);
		String strMonth = String.format("%02d", month);
		String lastMonth = year + strMonth;
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMM");
		Date dt = null;
		try {
			dt = dtFormat.parse(lastMonth);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal.setTime(dt);
		cal.add(Calendar.MONTH, -j);
		return dtFormat.format(cal.getTime());
	}
	
	public StringBuilder getRTMSDataSvcAptTradeDev(Region Region, String DealYmd) throws IOException {
		
		StringBuilder sb = null;
		
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/RTMSDataSvcAptTradeDev/getRTMSDataSvcAptTradeDev"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=f4Ed1eAJYzb%2BQ%2BtpQx4G%2BQvFuO0ZJJMZIInJGo%2FpG889YetxgnnGE9umfvGSe8TPyZ88bAUWw%2Bn7ETYTooeF5A%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("LAWD_CD","UTF-8") + "=" + URLEncoder.encode(Region.getCode(), "UTF-8")); /*각 지역별 코드*/
        urlBuilder.append("&" + URLEncoder.encode("DEAL_YMD","UTF-8") + "=" + URLEncoder.encode(DealYmd/*DEAL_YMD*/, "UTF-8")); /*월 단위 신고자료*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(PAGENO, "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(NUMOFROWS, "UTF-8")); /*한 페이지 결과 수*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            System.out.println("에러");
        }
        sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
		
		return sb;
	}
	
	/*private String korParentName(String RegionName) {
	
	if(RegionName.equals("SEOUL")){
		return "서울특별시";
	}else if(RegionName.equals("BUSAN")) {
		return "부산광역시";
	}else if(RegionName.equals("DAEGU")) {
		return "대구광역시";
	}else if(RegionName.equals("INCHEON")) {
		return "인천광역시";
	}else if(RegionName.equals("GWANGJU")) {
		return "광주광역시";
	}else if(RegionName.equals("DAEJEON")) {
		return "대전광역시";
	}else if(RegionName.equals("ULSAN")) {
		return "울산광역시";
	}else if(RegionName.equals("SEJONG")) {
		return "세종특별자치시";
	}else if(RegionName.equals("GYEONGGIDO")) {
		return "경기도";
	}else if(RegionName.equals("CHUNGCHEONGBUKDO")) {
		return "충청북도";
	}else if(RegionName.equals("CHUNGCHEONGNAMDO")) {
		return "충청남도";
	}else if(RegionName.equals("JEOLLANAMDO")) {
		return "전라남도";
	}else if(RegionName.equals("GYEONGSANGBUKDO")) {
		return "경상북도";
	}else if(RegionName.equals("GYEONGSANGNAMDO")) {
		return "경상남도";
	}else if(RegionName.equals("JEJU")) {
		return "제주특별자치도";
	}else if(RegionName.equals("GANGWONDO")) {
		return "강원특별자치도";
	}else if(RegionName.equals("JEOLLABUKDO")) {
		return "전북특별자치도";
	}
	return null;
}*/
	
}
