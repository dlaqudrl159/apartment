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
import java.util.Arrays;
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
import kr.co.dw.Mapper.DataMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService{

	private String[] EnglishRegion = {"SEOUL","BUSAN","DAEGU","INCHEON","GWANGJU","DAEJEON","ULSAN","SEJONG","GYEONGGIDO","GANGWONDO",
			"CHUNGCHEONGBUKDO","CHUNGCHEONGNAMDO","JEOLLABUKDO","JEOLLANAMDO","GYEONGSANGBUKDO","GYEONGSANGNAMDO","JEJU"};
	
	private String[] KoreaRegion = {"서울특별시","부산광역시","대구광역시","인천광역시","광주광역시","대전광역시","울산광역시","세종특별자치시","경기도","강원특별자치도",
			"충청북도","충청남도","전북특별자치도","전라남도","경상북도","경상남도","제주특별자치도"};
	
	private String[][] RegionCode = {
			/*서울*/
			{ "11110", "11140", "11170", "11200", "11215", "11230", "11260", "11290", "11305", "11320",
			  "11350", "11380", "11410", "11440", "11470", "11500", "11530", "11545", "11560", "11590", 
			  "11620", "11650", "11680", "11710", "11740" }, //25
			/*부산*/
			{ "26110", "26140", "26170", "26200", "26230", "26260", "26290", "26320", "26350", "26380", 
			  "26410", "26440", "26470", "26500", "26530", "26710" }, //16
			/*대구*/
			{ "27110", "27140", "27170", "27200", "27230", "27260", "27290", "27710", "27720"}, //9
			
			/*인천*/
			{ "28110", "28140", "28177", "28185", "28200", "28237", "28245", "28260", "28710", "28720" }, //10
			/*광주*/
			{ "29110", "29140", "29155", "29170", "29200" }, //5
			/*대전*/
			{ "30110", "30140", "30170", "30200", "30230" }, //5
			/*울산*/
			{ "31110", "31140", "31170", "31200", "31710" }, //5
			/*세종특별자치시*/
			{ "36110" }, //1
			/*경기도*/
			{ "41111", "41113", "41115", "41117", "41131", 
			  "41133", "41135", "41150", "41171", "41173" ,
			  "41194", "41196", "41192", "41210", "41220", 
			  "41250", "41271", "41273", "41281", "41285", 
			  "41287", "41290", "41310", "41360", "41370", 
			  "41390", "41410", "41430", "41450", "41461", 
			  "41463", "41465", "41480", "41500", "41550", 
			  "41570", "41590", "41610", "41630", "41650", 
			  "41670", "41800", "41820", "41830" }, //44
			
			/*강원도*/
			{ "51110", "51130", "51150", "51170", "51190", "51210", "51230", "51720", "51730", "51750", 
			  "51760", "51770", "51780", "51790", "51800", "51810", "51820", "51830" }, //18
			/*충청북도*/
			{ "43111", "43112", "43113", "43114", "43130", "43150", "43720", "43730", "43740", "43745",
			  "43750", "43760", "43770", "43800" }, //14
			/*충청남도*/
			{ "44131", "44133", "44150", "44180", "44200", "44210", "44230", "44250", "44270", "44710", 
			  "44760", "44770" ,"44790", "44800", "44810", "44825" }, //16
			/*전라북도*/
			{ "52111", "52113", "52130", "52140", "52180", "52190", "52210", "52710", "52720", "52730", 
			  "52740", "52750", "52770", "52790", "52800" }, //15
			/*전라남도*/
			{ "46110", "46130", "46150", "46170", "46230", "46710", "46720", "46730", "46770", "46780", 
			  "46790", "46800", "46810", "46820", "46830", "46840", "46860", "46870", "46880", "46890", 
			  "46900", "46910" }, //22
			/*경상북도*/
			{ "47111", "47113", "47130", "47150", "47170", "47190", "47210", "47230", "47250", "47280", 
			  "47290", "47730", "47750", "47760", "47770", "47820", "47830", "47840", "47850", "47900",
			  "47920", "47930", "47940" }, //23
			/*경상남도*/
			{ "48121", "48123", "48125", "48127", "48129", "48170", "48220", "48240", "48250", "48270", 
			  "48310", "48330", "48720", "48730", "48740", "48820", "48840", "48850", "48860", "48870", 
			  "48880", "48890" }, //22
			/*제주도*/
			{ "50110", "50130" } }; //2
	private String[][] KoreaRegionCode = {
			//서울
			{ "종로구", "중구", "용산구", "성동구", "광진구", "동대문구", "중랑구", "성북구", "강북구", "도봉구", "노원구", "은평구", "서대문구", "마포구", "양천구",
					"강서구", "구로구", "금천구", "영등포구", "동작구", "관악구", "서초구", "강남구", "송파구", "강동구" },
			//부산
			{ "중구", "서구", "동구", "영도구", "부산진구", "동래구", "남구", "북구", "해운대구", "사하구", "금정구", "강서구", "연제구", "수영구", "사상구",
					"기장군" },
			//대구
			{ "중구", "동구", "서구", "남구","북구", "수성구", "달서구", "달성군", "군위군"},
			//인천
			{ "중구", "동구", "미추홀구", "연수구", "남동구", "부평구", "계양구", "서구", "강화군", "옹진군" }, 
			//광주
			{ "동구", "서구", "남구", "북구", "광산구" },
			//대전
			{ "동구", "중구", "서구", "유성구", "대덕구" }, 
			//울산
			{ "중구", "남구", "동구", "북구", "울주군" },
			//세종
			{""},
			//경기도
			{ "수원시 장안구", "수원시 권선구", "수원시 팔달구", "수원시 영통구", "성남시 수정구",
				"성남시 중원구", "성남시 분당구", "의정부시", "안양시 만안구", "안양시 동안구",
				"부천시 소사구","부천시 오정구" ,"부천시 원미구","광명시", "평택시",
				"동두천시", "안산시 상록구", "안산시 단원구", "고양시 덕양구", "고양시 일산동구", 
				"고양시 일산서구", "과천시", "구리시","남양주시", "오산시", 
				"시흥시", "군포시", "의왕시", "하남시", "용인시 처인구", 
				"용인시 기흥구", "용인시 수지구", "파주시", "이천시", "안성시",
				"김포시", "화성시", "광주시", "양주시", "포천시", 
				"여주시", "연천군", "가평군", "양평군" },
			//강원도
			{ "춘천시", "원주시", "강릉시", "동해시", "태백시", "속초시", "삼척시", "홍천군", "횡성군", "영월군", "평창군", "정선군", "철원군", "화천군", "양구군",
					"인제군", "고성군", "양양군" },
			//충청북도
			{ "청주시 상당구", "청주시 서원구", "청주시 흥덕구", "청주시 청원구", "충주시", "제천시", "보은군", "옥천군", "영동군", "증평군", "진천군", "괴산군", "음성군",
					"단양군" },
			//충청남도
			{ "천안시 동남구", "천안시 서북구", "공주시", "보령시", "아산시", "서산시", "논산시", "계룡시", "당진시", "금산군", "부여군", "서천군", "청양군", "홍성군",
					"예산군", "태안군" },
			//전라북도
			{ "전주시 완산구", "전주시 덕진구", "군산시", "익산시", "정읍시", "남원시", "김제시", "완주군", "진안군", "무주군", "장수군", "임실군", "순창군", "고창군",
					"부안군" },
			//전라남도
			{ "목포시", "여수시", "순천시", "나주시", "광양시", "담양군", "곡성군", "구례군", "고흥군", "보성군", "화순군", "장흥군", "강진군", "해남군", "영남군",
					"무안군", "함평군", "영광군", "장성군", "완도군", "진도군", "신안군" },
			//경상북도
			{ "포항시 남구", "포항시 북구", "경주시", "김천시", "안동시", "구미시", "영주시", "영천시", "상주시", "문경시", "경산시", "의성군", "청송군",
					"영양군", "영덕군", "청도군", "고령군", "성주군", "칠곡군", "예천군", "봉화군", "울진군", "울릉군" },
			//경상남도
			{ "창원시 의창구", "창원시 성산구", "창원시 마산합포구", "창원시 마산회원구", "창원시 진해구", "진주시", "통영시", "사천시", "김해시", "밀양시", "거제시",
					"양산시", "의령군", "함안군", "창녕군", "고성군", "남해군", "하동군", "산청군", "함양군", "거창군", "합천군" },
			//제주도
			{ "제주시", "서귀포시" }
			};
	private final DataMapper DataMapper;
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	@Transactional
	@Override
	public void LatLngInsert(String tableName) throws MalformedURLException, IOException, ParseException, InterruptedException {
		
		List<NameCountDto> list = new ArrayList<>();
		
		list = DataMapper.getList(tableName);
			
		getLatLng(list, tableName);
		
	}
	
	@Override
	public List<NameCountDto> getLatLng(List<NameCountDto> list, String tableName) throws IOException, ParseException, InterruptedException {
			
		JSONObject jsrs = null;
		
		for(int i = 0 ; i < list.size() ; i++) {
			
			NameCountDto NameCountDto = list.get(i);
			NameCountDto checkNameCountDto = new NameCountDto();
			checkNameCountDto = DataMapper.get(NameCountDto);
			
			if(NameCountDto.equals(checkNameCountDto)) {
				continue;
			}else {
				System.out.println("중복아닙니다");
				
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
				//System.out.println(checkNameCountDto);
				//System.out.println(NameCountDto);
				DataMapper.insert(NameCountDto);
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

		int index = Arrays.asList(EnglishRegion).indexOf(RegionName);
		int RegionCodeIndex = RegionCode[index].length;
		boolean check = true;

		String DbYear = makeDealYearMonth(13);
		DataMapper.deleteRegionYear(RegionName, DbYear);

		for (int i = 0; i < RegionCodeIndex && check == true; i++) {
			String LAWD_CD = RegionCode[index][i];
			String KoreaLAWD_CD = KoreaRegionCode[index][i];
			for (int j = 13; j >= 0; j--) {
				String DEAL_YMD = makeDealYearMonth(j);
				try {
					StringBuilder sb = getRTMSDataSvcAptTradeDev(RegionName, LAWD_CD, DEAL_YMD);

					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder builder = factory.newDocumentBuilder();
					Document document = builder.parse(new InputSource(new StringReader(sb.toString())));

					document.getDocumentElement().normalize();
					NodeList nList = document.getElementsByTagName("item");
					Element root = document.getDocumentElement();
					String resultMsg = root.getElementsByTagName("resultMsg").item(0) == null ? "-"
							: root.getElementsByTagName("resultMsg").item(0).getTextContent();
					String resultCode = root.getElementsByTagName("resultCode").item(0) == null ? "-"
							: root.getElementsByTagName("resultCode").item(0).getTextContent();
					String resultTotalCount = root.getElementsByTagName("totalCount").item(0) == null ? "-"
							: root.getElementsByTagName("totalCount").item(0).getTextContent();
					if (!resultCode.equals("000")) {
						System.out.println(resultCode);
						System.out.println("resultCode가 000이 아닙니다.");
						throw new RuntimeException();
					}
					System.out.println("DEAL_YMD = " + DEAL_YMD + " " + "LAWD_CD = " + LAWD_CD + " " + "resultMsg= "
							+ resultMsg + " " + "resultCode= " + resultCode + " " + "resultTotalCount= "
							+ resultTotalCount);
					List<ApiDto> newlist = makeApiDto(nList, KoreaRegion[index], KoreaLAWD_CD);
					SqlSession sqlSession = this.sqlSessionFactory.openSession(ExecutorType.BATCH);
					
					if (newlist.size() > 0) {
						getLatLng(makeNameCountDto(newlist), RegionName);
						for (ApiDto apiDto : newlist) {
							Map<String, Object> map = new HashMap<>();
							map.put("ApiDto", apiDto);
							map.put("RegionName", RegionName);
							sqlSession.insert("kr.co.dw.Mapper.DataMapper.DataInsert", map);
							
						}
						
					}
					sqlSession.flushStatements();
					sqlSession.commit();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println(e.getMessage() + " IOException");
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println(e.getMessage() + " ParserConfigurationException");
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println(e.getMessage() + " SAXException");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println(e.getMessage() + " ParseException");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println(e.getMessage() + " InterruptedException");
				} catch (RuntimeException e) {
					// TODO: handle exception
					e.printStackTrace();
					System.out.println(e.getMessage() + " RuntimeException");
					break;
				}
			}
		}

	}
	
	
	
	public List<ApiDto> makeApiDto(NodeList nList ,String sigungu, String sigungu2) {
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
				
				
				StringBuilder sb = new StringBuilder();
				sb.append(DealAmount + " ");
				sb.append(ReqgbN + " ");
				sb.append(BuildYear + " ");
				sb.append(DealYear + " ");
				sb.append(ApartmentDong + " ");
				sb.append(RegistartionDate + " ");
				sb.append(SellerGBN + " ");
				sb.append(BuyerGBN + " ");
				sb.append(Dong + " ");
				sb.append(ApartmentName + " ");
				sb.append(DealMonth + " ");
				sb.append(DealDay + " ");
				sb.append(AreaforExcusiveUse + " ");
				sb.append(RdealerLawdnm + " ");
				sb.append(Jibun + " ");
				sb.append(RegionalCode + " ");
				sb.append(Floor + " ");
				sb.append(CancleDealDay + " ");
				sb.append(CancleDealType + " ");
				sb.append(RoadName);
				sb.append(RoadNameBonbun + " ");
				sb.append(RoadNameBubun+ " ");
				sb.append(SggCd + " ");
				
				ApiDto.setSIGUNGU(sigungu + " " + sigungu2 + " " +  Dong);
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
	
	public List<NameCountDto> makeNameCountDto(List<ApiDto> list) {
		
		List<NameCountDto> NameCountDtolist = new ArrayList<>();
		
		for(int i = 0 ; i < list.size() ; i++) {
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
	
	public String makeRoadName(String RoadName, String RoadNameBonbun, String RoadNameBubun) {
		RoadName = RoadName.trim();
		if(RoadName.equals("-")) {
			RoadName = "";
		}
		if(RoadNameBonbun.equals("-")) {
			RoadNameBonbun = "";
		}
		if(RoadNameBubun.equals("-")) {
			RoadNameBubun = "";
		}
		RoadNameBonbun = RoadNameBonbun + "!";
		RoadNameBubun = RoadNameBubun + "!";
		RoadNameBonbun = RoadNameBonbun.replace("0", " ").trim().replace(" ", "0").replace("!", "");
		
		RoadNameBubun = RoadNameBubun.replace("0", " ").trim().replace(" ", "0").replace("!", "");
		if(RoadNameBonbun.length() !=0 ) {
			RoadName = RoadName + " " + RoadNameBonbun;
			if(RoadNameBubun.length() != 0) {
				RoadName = RoadName + "-" + RoadNameBubun;
			}
		}else if(RoadNameBonbun.length() ==0 ) {
			if(RoadNameBubun.length() != 0) {
				RoadName = RoadName + " " + RoadNameBubun;
			}
		}
		RoadName = RoadName.trim();
		if(RoadName.equals("")) {
			RoadName = "-";
		}
		return RoadName;
		
	}
	
	public String makeEngilshMonth(String DEAL_YMD) {
		
		String month = DEAL_YMD.substring(4, 6);
		int numMonth = Integer.parseInt(month);
		if(numMonth > 12) {
			numMonth = numMonth - 12;
			month = String.valueOf(numMonth);
		}
		String EnglishMonth = null;
		if(month.equals("01")) {
			EnglishMonth = "January";
		}else if(month.equals("02")) {
			EnglishMonth = "February";
		}else if(month.equals("03")) {
			EnglishMonth = "March";
		}else if(month.equals("04")) {
			EnglishMonth = "April";
		}else if(month.equals("05")) {
			EnglishMonth = "May";
		}else if(month.equals("06")) {
			EnglishMonth = "June";
		}else if(month.equals("07")) {
			EnglishMonth = "July";
		}else if(month.equals("08")) {
			EnglishMonth = "August";
		}else if(month.equals("09")) {
			EnglishMonth = "September";
		}else if(month.equals("10")) {
			EnglishMonth = "October";
		}else if(month.equals("11")) {
			EnglishMonth = "November";
		}else if(month.equals("12")) {
			EnglishMonth = "December";
		}
		return EnglishMonth;
	}
	
	public String makeDealYearMonth(int j) {
		
		Calendar cal = Calendar.getInstance();
		String year = String.valueOf(cal.get(Calendar.YEAR));
		int month = (cal.get(Calendar.MONTH)+1);
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
	
	public StringBuilder getRTMSDataSvcAptTradeDev(String RegionName, String LAWD_CD, String DEAL_YMD) throws IOException {
		
		StringBuilder sb = null;
		
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/RTMSDataSvcAptTradeDev/getRTMSDataSvcAptTradeDev"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=f4Ed1eAJYzb%2BQ%2BtpQx4G%2BQvFuO0ZJJMZIInJGo%2FpG889YetxgnnGE9umfvGSe8TPyZ88bAUWw%2Bn7ETYTooeF5A%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("LAWD_CD","UTF-8") + "=" + URLEncoder.encode(LAWD_CD, "UTF-8")); /*각 지역별 코드*/
        urlBuilder.append("&" + URLEncoder.encode("DEAL_YMD","UTF-8") + "=" + URLEncoder.encode(DEAL_YMD/*DEAL_YMD*/, "UTF-8")); /*월 단위 신고자료*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10000", "UTF-8")); /*한 페이지 결과 수*/
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
	
	}
	
	
	

