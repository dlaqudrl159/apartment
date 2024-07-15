package kr.co.dw.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import kr.co.dw.Domain.NameCountDto;
import kr.co.dw.Mapper.DataMapper;
import kr.co.dw.Utils.AptUtils;
import kr.co.dw.Utils.DataUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService{

	private String[] englishregion = {"SEOUL","BUSAN","DAEGU","INCHEON","GWANGJU","DAEJEON","ULSAN","SEJONG","GYEONGGIDO","GANGWONDO",
			"CHUNGCHEONGBUKDO","CHUNGCHEONGNAMDO","JEOLLABUKDO","JEOLLANAMDO","GYEONGSANGBUKDO","GYEONGSANGNAMDO","JEJU"};
	
	private String[] koreanregion = {"서울특별시","부산광역시","대구광역시","인천광역시","광주광역시","대전광역시","울산광역시","세종특별자치시","경기도","강원특별자치도",
			"충청북도","충청남도","전북특별자치도","전라남도","경상북도","경상남도","제주특별자치도"};
	
	private String[][] region2 = {
			/*서울*/
			{ "11110", "11140", "11170", "11200", "11215", "11230", "11260", "11290", "11305", "11320", "11350",
					"11380", "11410", "11440", "11470", "11500", "11530", "11545", "11560", "11590", "11620",
					"11650", "11680", "11710", "11740" },
			/*부산*/
			{ "26110", "26140", "26170", "26200", "26230", "26260", "26290", "26320", "26350", "26380", "26410",
					"26440", "26470", "26500", "26530", "26710" },
			/*대구*/
			{ "27110", "27140", "27170", "27200", "27230", "27260", "27290", "27710" },
			/*인천*/
			{ "28110", "28140", "28177", "28185", "28200", "28237", "28245", "28260", "28710", "28720" },
			/*광주*/
			{ "29110", "29140", "29155", "29170", "29200" },
			/*대전*/
			{ "30110", "30140", "30170", "30200", "30230" },
			/*울산*/
			{ "31110", "31140", "31170", "31200", "31710" },
			/*세종특별자치시*/
			{ "36110" },
			/*경기도*/
			{ "41111", "41113", "41115", "41117", "41131", "41133", "41135", "41150", "41171", "41173", "41190",
					"41210", "41220", "41250", "41271", "41273", "41281", "41285", "41287", "41290", "41310",
					"41360", "41370", "41390", "41410", "41430", "41450", "41461", "41463", "41465", "41480",
					"41500", "41550", "41570", "41590", "41610", "41630", "41650", "41670", "41800", "41820",
					"41830" },
			/*강원도*/
			{ "51110", "51130", "51150", "51170", "51190", "51210", "51230", "51720", "51730", "51750", "51760",
					"51770", "51780", "51790", "51800", "51810", "51820", "51830" },
			/*충청북도*/
			{ "42830", "43111", "43112", "43113", "43114", "43130", "43150", "43720", "43730", "43740", "43745",
					"43750", "43760", "43770", "43800" },
			/*충청남도*/
			{ "44131", "44133", "44150", "44180", "44200", "44210", "44230", "44250", "44270", "44710", "44760",
					"44790", "44800", "44810", "44825" },
			/*전라북도*/
			{ "52111", "52113", "52130", "52140", "52180", "52190", "52210", "52710", "52720", "52730", "52740",
					"52750", "52770", "52790", "52800" },
			/*전라남도*/
			{ "46110", "46130", "46150", "46170", "46230", "46710", "46720", "46730", "46770", "46780", "46790",
					"46800", "46810", "46820", "46830", "46840", "46860", "46870", "46880", "46890", "46900",
					"46910" },
			/*경상북도*/
			{ "47111", "47113", "47130", "47150", "47170", "47190", "47210", "47230", "47250", "47280", "47290",
					"47720", "47730", "47750", "47760", "47770", "47820", "47830", "47840", "47850", "47900",
					"47920", "47930", "47940" },
			/*경상남도*/
			{ "48121", "48123", "48125", "48127", "48129", "48170", "48220", "48240", "48250", "48270", "48310",
					"48330", "48720", "48730", "48740", "48820", "48840", "48850", "48860", "48870", "48880",
					"48890" },
			/*제주도*/
			{ "50110", "50130" } };
	private String[][] region3 = {
			{ "종로구", "중구", "용산구", "성동구", "광진구", "동대문구", "중랑구", "성북구", "강북구", "도봉구", "노원구", "은평구", "서대문구", "마포구", "양천구",
					"강서구", "구로구", "금천구", "영등포구", "동작구", "관악구", "서초구", "강남구", "송파구", "강동구" },
			{ "중구", "서구", "동구", "영도구", "부산진구", "동래구", "남구", "북구", "해운대구", "사하구", "금정구", "강서구", "연제구", "수영구", "사상구",
					"기장군" },
			{ "중구", "동구", "서구", "남구", "북구", "수성구", "달서구", "달성군" },
			{ "중구", "동구", "미추홀구", "연수구", "남동구", "부평구", "계양구", "서구", "강화군", "옹진구" }, { "동구", "서구", "남구", "북구", "광산구" },
			{ "동구", "중구", "서구", "유성구", "대덕구" }, { "중구", "남구", "동구", "북구", "울주군" },
			{ "" },
			{ "수원시 장안구", "수원시 권선구", "수원시 팔달구", "수원시 영통구", "성남시 수정구", "성남시 중원구", "성남시 분당구", "의정부시", "안양시 만안구", "안양시 동안구",
					"부천시", "광명시", "평택시", "동두천시", "안산시 상록구", "안산시 단원구", "고양시 덕양구", "고양시 일산동구", "고양시 일산서구", "과천시", "구리시",
					"남양주시", "오산시", "시흥시", "군포시", "의왕시", "하남시", "용인시 처인구", "용인시 기흥구", "용인시 수지구", "파주시", "이천시", "안성시",
					"김포시", "화성시", "광주시", "양주시", "포천시", "여주시", "연천군", "가평군", "양평군" },
			{ "춘천시", "원주시", "강릉시", "동해시", "태백시", "속초시", "삼척시", "홍천군", "횡성군", "영월군", "평창군", "정선군", "철원군", "화천군", "양구군",
					"인제군", "고성군", "양양군" },
			{ "청주시 상당구", "청주시 서원구", "청주시 흥덕구", "청주시 청원구", "충주시", "제천시", "보은군", "옥천군", "영동군", "증평군", "진천군", "괴산군", "음성군",
					"단양군" },
			{ "천안시 동남구", "천안시 서북구", "공주시", "보령시", "아산시", "서산시", "논산시", "계룡시", "당진시", "금산군", "부여군", "서천군", "청양군", "홍성군",
					"예산군", "태안군" },
			{ "전주시 완산구", "전주시 덕진구", "군산시", "익산시", "정읍시", "남원시", "김제시", "완주군", "진안군", "무주군", "장수군", "임실군", "순창군", "고창군",
					"부안군" },
			{ "목포시", "여수시", "순천시", "나주시", "광양시", "담양군", "곡성군", "구례군", "고흥군", "보성군", "화순군", "장흥군", "강진군", "해남군", "영남군",
					"무안군", "함평군", "영광군", "장성군", "완도군", "진도군", "신안군" },
			{ "포항시 남구", "포항시 북구", "경주시", "김천시", "안동시", "구미시", "영주시", "영천시", "상주시", "문경시", "경산시", "군위군", "의성군", "청송군",
					"영양군", "영덕군", "청도군", "고령군", "성주군", "칠곡군", "예천군", "봉화군", "울진군", "울릉군" },
			{ "창원시 의상구", "창원시 성산구", "창원시 마산합포구", "창원시 마산회원구", "창원시 진해구", "진주시", "통영시", "사천시", "김해시", "밀양시", "거제시",
					"양산시", "의령군", "함얀군", "창녕군", "고성군", "남해군", "하동군", "산청군", "함양군", "거창군", "합천군" },
			{ "제주시", "서귀포시" }
			};
	private final DataMapper DataMapper;
	
	@Transactional
	@Override
	public void LatLngInsert(String tableName) throws MalformedURLException, IOException, ParseException, InterruptedException {
		
		//String[] arr = {"SEOUL","BUSAN","DAEGU","INCHEON","GWANGJU","DAEJEON","ULSAN","SEJONG","GYEONGGIDO","CHUNGCHEONGBUKDO","CHUNGCHEONGNAMDO","JEOLLANAMDO","GYEONGSANGBUKDO","GYEONGSANGNAMDO","JEJU","GANGWONDO","JEOLLABUKDO"}; 
		//세종 서울 부산 대구 인천 광주 대전 울산 세종 경기도 충청북도 충청남도 전라남도 경상북도 경상남도 제주 강원도 전라북도
		List<NameCountDto> list = new ArrayList<>();
		
		list = DataMapper.getList(tableName);
			
		getLatLng(list, tableName);
		
	}
	
	@Override
	public String GYEONGGIDO(NameCountDto NameCountDto) {

		String address = NameCountDto.getSIGUNGU();
		String[] arr = address.split(" ");
		String address2 = arr[1];
		if (address2.equals("고양시덕양구")) {
			arr[1] = "고양시 덕양구";
		} else if (address2.equals("고양시일산동구")) {
			arr[1] = "고양시 일산동구";
		} else if (address2.equals("고양시일산서구")) {
			arr[1] = "고양시 일산서구";
		} else if (address2.equals("부천시소사구")) {
			arr[1] = "부천시 소사구";
		} else if (address2.equals("부천시오정구")) {
			arr[1] = "부천시 오정구";
		} else if (address2.equals("부천시원미구")) {
			arr[1] = "부천시 원미구";
		} else if (address2.equals("성남시분당구")) {
			arr[1] = "성남시 분당구";
		} else if (address2.equals("성남시수정구")) {
			arr[1] = "성남시 수정구";
		} else if (address2.equals("성남시중원구")) {
			arr[1] = "성남시 중원구";
		} else if (address2.equals("수원시권선구")) {
			arr[1] = "수원시 권선구";
		} else if (address2.equals("수원시영통구")) {
			arr[1] = "수원시 영통구";
		} else if (address2.equals("수원시장안구")) {
			arr[1] = "수원시 장안구";
		} else if (address2.equals("수원시팔달구")) {
			arr[1] = "수원시 팔달구";
		} else if (address2.equals("안산시단원구")) {
			arr[1] = "안산시 단원구";
		} else if (address2.equals("안산시상록구")) {
			arr[1] = "안산시 상록구";
		} else if (address2.equals("안산시동안구")) {
			arr[1] = "안산시 동안구";
		} else if (address2.equals("안산시만안구")) {
			arr[1] = "안산시 만안구";
		} else if (address2.equals("용인시기흥구")) {
			arr[1] = "용인시 기흥구";
		} else if (address2.equals("용인시수지구")) {
			arr[1] = "용인시 수지구";
		} else if (address2.equals("용인시처인구")) {
			arr[1] = "용인시 처인구";
		}

		String temp = "";
		for (int j = 0; j < arr.length; j++) {

			temp += arr[j] + " ";
		}
		address = temp.trim();

		return address;
	}

	@Override
	public String CHUNGCHEONGBUKDO(NameCountDto NameCountDto) {

		String address = NameCountDto.getSIGUNGU();
		String[] arr = address.split(" ");
		String address2 = arr[1];
		if (address2.equals("청주서원구")) {
			arr[1] = "청주시 서원구";
		} else if (address2.equals("청주시상당구")) {
			arr[1] = "청주시 상당구";
		} else if (address2.equals("청주시청원구")) {
			arr[1] = "청주시 청원구";
		} else if (address2.equals("청주시흥덕구")) {
			arr[1] = "청주시 흥덕구";
		}
		String temp = "";
		for (int j = 0; j < arr.length; j++) {

			temp += arr[j] + " ";
		}
		address = temp.trim();

		return address;

	}

	@Override
	public String CHUNGCHEONGNAMDO(NameCountDto NameCountDto) {

		String address = NameCountDto.getSIGUNGU();
		String[] arr = address.split(" ");
		String address2 = arr[1];
		if (address2.equals("천안시동남구")) {
			arr[1] = "천안시 동남구";
		} else if (address2.equals("천안시서북구")) {
			arr[1] = "천안시 서북구";
		}
		String temp = "";
		for (int j = 0; j < arr.length; j++) {

			temp += arr[j] + " ";
		}
		address = temp.trim();

		return address;

	}

	@Override
	public String GYEONGSANGBUKDO(NameCountDto NameCountDto) {

		String address = NameCountDto.getSIGUNGU();
		String[] arr = address.split(" ");
		String address2 = arr[1];
		if (address2.equals("포항시남구")) {
			arr[1] = "포항시 남구";
		} else if (address2.equals("포항시북구")) {
			arr[1] = "포항시 북구";
		}
		String temp = "";
		for (int j = 0; j < arr.length; j++) {

			temp += arr[j] + " ";
		}
		address = temp.trim();

		return address;

	}

	@Override
	public String GYEONGSANGNAMDO(NameCountDto NameCountDto) {

		String address = NameCountDto.getSIGUNGU();
		String[] arr = address.split(" ");
		String address2 = arr[1];
		if (address2.equals("창원시마산합포구")) {
			arr[1] = "창원시 마산합포구";
		}else if (address2.equals("창원시마산회원구")) {
			arr[1] = "창원시 마산회원구";
		}else if (address2.equals("창원시성산구")) {
			arr[1] = "창원시 성산구";
		}else if (address2.equals("창원시의창구")) {
			arr[1] = "창원시 의창구";
		}else if (address2.equals("창원시진해구")) {
			arr[1] = "창원시 진해구";
		}
		String temp = "";
		for (int j = 0; j < arr.length; j++) {

			temp += arr[j] + " ";
		}
		address = temp.trim();

		return address;

	}
	
	@Override
	public String JEOLLABUKDO(NameCountDto NameCountDto) {

		String address = NameCountDto.getSIGUNGU();
		String[] arr = address.split(" ");
		String address2 = arr[1];
		if (address2.equals("전주시덕진구")) {
			arr[1] = "전주시 덕진구";
		}else if (address2.equals("전주시완산구")) {
			arr[1] = "전주시 완산구";
		}
		String temp = "";
		for (int j = 0; j < arr.length; j++) {

			temp += arr[j] + " ";
		}
		address = temp.trim();

		return address;

	}
	
	
	@Override
	public List<NameCountDto> getLatLng(List<NameCountDto> list, String tableName) throws IOException, ParseException, InterruptedException {
		// TODO Auto-generated method stub
		
		AptUtils AptUtils = new AptUtils();
		
		
		String fileRegionName = AptUtils.MappingRegionReverse(tableName);
		
		File file = null;
		if(System.getProperty("os.name").equals("Windows 10")) {
			file = new File("C:/Users/qkfka/OneDrive/바탕 화면/아파트데이터/" + fileRegionName + "/javatest.txt");
		}else if(System.getProperty("os.name").equals("Linux")) {
			file = new File(File.separator + "home" + File.separator + "ubuntu" + File.separator + "아파트데이터" + File.separator + fileRegionName + File.separator +"javatest.txt");
		}
		
		PrintWriter pw = new PrintWriter(new FileWriter(file,true));
		
		JSONObject jsrs = null;
		
		for(int i = 0 ; i < list.size() ; i++) {
			//Thread.sleep(500);
			NameCountDto NameCountDto = list.get(i);
			try {
				jsrs  = getparcel(NameCountDto,tableName);	
			} catch (Exception e) {
				// TODO: handle exception
					
				pw.write("에러발생" + e.getMessage()+"\r\n");	
				pw.write(i+"\r\n");
				i--;	
				pw.write(i+"\r\n");
				continue;
			}
			if(jsrs.get("status").equals("OK")) {
				
				pw.write(jsrs.get("status").toString()+"\r\n");
				
				JSONObject jsResult = (JSONObject) jsrs.get("result");
			    JSONObject jspoint = (JSONObject) jsResult.get("point");
			    
			    String lat = (String) jspoint.get("y");
			    int latidx =lat.indexOf(".");
			    String lng = (String) jspoint.get("x");
			    int lngidx = lng.indexOf(".");
			    
			    NameCountDto.setLAT(((String) jspoint.get("y")).substring(0, latidx+6));
			    NameCountDto.setLNG(((String) jspoint.get("x")).substring(0, lngidx+6));			    
			}else {
				
				pw.write(jsrs.get("status").toString()+"\r\n");
				
				try {
					jsrs = getroadname(NameCountDto,tableName);
				} catch (Exception e) {
					// TODO: handle exception
					pw.write("에러발생" + e.getMessage()+"\r\n");	
					pw.write(i+"\r\n");
					i--;	
					pw.write(i+"\r\n");
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
					pw.write(jsrs.get("status").toString()+"\r\n");
					NameCountDto.setLAT("자료없음");
				    NameCountDto.setLNG("자료없음");		
				}
			}
			
			pw.write(i + "번째 " + NameCountDto+"\r\n");
			NameCountDto checkNameCountDto = new NameCountDto();
			checkNameCountDto = DataMapper.get(NameCountDto);
			
			if(NameCountDto.equals(checkNameCountDto)) {
				pw.write("중복입니다 넘어갑니다"+"\r\n");
				pw.write("------------------------------------"+"\r\n");
			}else {
				DataMapper.insert(NameCountDto);
				if(checkNameCountDto == null || "".equals(checkNameCountDto)) {
					pw.write("null"+"\r\n");
				}else {
					pw.write(checkNameCountDto.toString()+"\r\n");
				}				
				pw.write(NameCountDto.toString()+"\r\n");
				pw.write("------------------------------------"+"\r\n");
			}
		}
		pw.write("끝");
		pw.close();
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
		String Sigungu = "";
		if(tableName.equals("GYEONGGIDO")) {
			Sigungu = GYEONGGIDO(NameCountDto);
		}else if(tableName.equals("CHUNGCHEONGBUKDO")) {
			Sigungu = CHUNGCHEONGBUKDO(NameCountDto);
		}else if(tableName.equals("CHUNGCHEONGNAMDO")) {
			Sigungu = CHUNGCHEONGNAMDO(NameCountDto);
		}else if(tableName.equals("GYEONGSANGBUKDO")) {
			Sigungu = GYEONGSANGBUKDO(NameCountDto);
		}else if(tableName.equals("GYEONGSANGNAMDO")) {
			Sigungu = GYEONGSANGNAMDO(NameCountDto);
		}else if(tableName.equals("JEOLLABUKDO")) {
			Sigungu = JEOLLABUKDO(NameCountDto);
		}else {
			Sigungu = NameCountDto.getSIGUNGU();
		}
		
		String roadname = NameCountDto.getROADNAME();
		String searchAddr = Sigungu + " " + roadname;
		
		return geocodersearchaddress(searchAddr, searchType);
		
	}
	@Override
	public JSONObject getparcel(NameCountDto NameCountDto, String tableName) throws IOException, ParseException {
		
		String searchType = "parcel";
		String Sigungu = "";
		if(tableName.equals("GYEONGGIDO")) {
			Sigungu = GYEONGGIDO(NameCountDto);
		}else if(tableName.equals("CHUNGCHEONGBUKDO")) {
			Sigungu = CHUNGCHEONGBUKDO(NameCountDto);
		}else if(tableName.equals("CHUNGCHEONGNAMDO")) {
			Sigungu = CHUNGCHEONGNAMDO(NameCountDto);
		}else if(tableName.equals("GYEONGSANGBUKDO")) {
			Sigungu = GYEONGSANGBUKDO(NameCountDto);
		}else if(tableName.equals("GYEONGSANGNAMDO")) {
			Sigungu = GYEONGSANGNAMDO(NameCountDto);
		}else if(tableName.equals("JEOLLABUKDO")) {
			Sigungu = JEOLLABUKDO(NameCountDto);
		}else {
			Sigungu = NameCountDto.getSIGUNGU();
		}
		
		String Bungi = NameCountDto.getBUNGI();		
		String searchAddr = Sigungu + " " + Bungi;
		
		return geocodersearchaddress(searchAddr, searchType);
		
	}

	//@Override
	public String test() throws IOException, ParserConfigurationException, SAXException {
		// TODO Auto-generated method stub
		
		
		
		StringBuilder urlBuilder = new StringBuilder("http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTrade"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=f4Ed1eAJYzb%2BQ%2BtpQx4G%2BQvFuO0ZJJMZIInJGo%2FpG889YetxgnnGE9umfvGSe8TPyZ88bAUWw%2Bn7ETYTooeF5A%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("LAWD_CD","UTF-8") + "=" + URLEncoder.encode("11110", "UTF-8")); /*각 지역별 코드*/
        urlBuilder.append("&" + URLEncoder.encode("DEAL_YMD","UTF-8") + "=" + URLEncoder.encode("202407", "UTF-8")); /*월 단위 신고자료*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            System.out.println("에러");
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
        DataUtils DataUtils = new DataUtils();
        DataUtils.test(sb.toString());
		return sb.toString();
        
    }

	@Override
	public String test(String region, int i) throws IOException, ParserConfigurationException, SAXException {
		// TODO Auto-generated method stub
		StringBuilder sb = null;
		Calendar today = Calendar.getInstance();
		String year = String.valueOf(today.get(Calendar.YEAR));
		int Calendarmonth = (today.get(Calendar.MONTH)+1);
		String month = String.format("%02d", Calendarmonth);
		
		
		
		for(int j = 0 ; j < region2[i].length;j++) {
			
			String DEAL_YMD = year + month;
			String LAWD_CD = region2[i][j];
			System.out.println(englishregion[i]);
			System.out.println(koreanregion[i]);
			System.out.println(region3[i][j]);
			System.out.println(DEAL_YMD);
			System.out.println(LAWD_CD);
			StringBuilder urlBuilder = new StringBuilder("http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTrade"); /*URL*/
	        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=f4Ed1eAJYzb%2BQ%2BtpQx4G%2BQvFuO0ZJJMZIInJGo%2FpG889YetxgnnGE9umfvGSe8TPyZ88bAUWw%2Bn7ETYTooeF5A%3D%3D"); /*Service Key*/
	        urlBuilder.append("&" + URLEncoder.encode("LAWD_CD","UTF-8") + "=" + URLEncoder.encode(LAWD_CD, "UTF-8")); /*각 지역별 코드*/
	        urlBuilder.append("&" + URLEncoder.encode("DEAL_YMD","UTF-8") + "=" + URLEncoder.encode(DEAL_YMD, "UTF-8")); /*월 단위 신고자료*/
	        URL url = new URL(urlBuilder.toString());
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/json");
	        System.out.println("Response code: " + conn.getResponseCode());
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
	        System.out.println(sb.toString());
	        DataUtils DataUtils = new DataUtils();
	        DataUtils.test(sb.toString(),englishregion[i],koreanregion[i],region3[i][j]);
	        try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        Thread.interrupted();
		}
		
		return "success";
	}
	}
	
	
	

