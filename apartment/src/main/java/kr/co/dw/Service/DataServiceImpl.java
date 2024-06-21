package kr.co.dw.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.dw.Domain.NameCountDto;
import kr.co.dw.Mapper.DataMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService{

	private final DataMapper DataMapper;
	
	@Transactional
	@Override
	public void LatLngInsert() throws MalformedURLException, IOException, ParseException {
		
		String[] arr = {"SEOUL","BUSAN","DAEGU","INCHEON","GWANGJU","DAEJEON","ULSAN","SEJONG","GYEONGGIDO","CHUNGCHEONGBUKDO","CHUNGCHEONGNAMDO","JEOLLANAMDO","GYEONGSANGBUKDO","GYEONGSANGNAMDO","JEJU","GANGWONDO","JEOLLABUKDO"};
		//5.10 
		
		List<NameCountDto> list = new ArrayList<>();
		
		String tableName = "JEOLLABUKDO";
		list = DataMapper.get(tableName);
			
		
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
	public List<NameCountDto> getLatLng(List<NameCountDto> list, String tableName) throws IOException, ParseException {
		// TODO Auto-generated method stub
		
		JSONObject jsrs = null;
		
		for(int i = 0 ; i < list.size() ; i++) {
			
			NameCountDto NameCountDto = list.get(i);
			
			jsrs  = getparcel(NameCountDto,tableName);	
			
			if(jsrs.get("status").equals("OK")) {
				JSONObject jsResult = (JSONObject) jsrs.get("result");
			    JSONObject jspoint = (JSONObject) jsResult.get("point");
			    NameCountDto.setLAT((String) jspoint.get("y"));
			    NameCountDto.setLNG((String) jspoint.get("x"));			    
			}else {
				
				jsrs = getroadname(NameCountDto,tableName);
				
				if(jsrs.get("status").equals("OK")) {
					JSONObject jsResult = (JSONObject) jsrs.get("result");
				    JSONObject jspoint = (JSONObject) jsResult.get("point");
				    NameCountDto.setLAT((String) jspoint.get("y"));
				    NameCountDto.setLNG((String) jspoint.get("x"));			    
				}else {
					NameCountDto.setLAT("자료없음");
				    NameCountDto.setLNG("자료없음");		
				}
			}
			System.out.println(i + "번째 " + NameCountDto);
		    DataMapper.insert(NameCountDto);
		}
		System.out.println("끝");
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
		/*StringBuilder sb = new StringBuilder("https://api.vworld.kr/req/address");
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
		JSONObject jsrs = (JSONObject) jsob.get("response");*/
		
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
		/*StringBuilder sb = new StringBuilder("https://api.vworld.kr/req/address");
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
		JSONObject jsrs = (JSONObject) jsob.get("response");*/
		
		return geocodersearchaddress(searchAddr, searchType);
		
	}
	
	/*@Override
	public List<NameCountDto> getLatLng(List<NameCountDto> list, String tableName) throws IOException, ParseException {
		// TODO Auto-generated method stub
		
		String apikey = "F0DBB350-67A6-39BB-A8BB-9237BB06612C";
		String searchType = "parcel";
		String searchType2 = "road";
		String epsg = "epsg:4326";
		
		for(int i = 0 ; i < list.size() ; i++) {
			String searchAddr = list.get(i).getSIGUNGU() + " " + list.get(i).getBUNGI();
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
				if(jsrs.get("status").equals("OK")) {
					JSONObject jsResult = (JSONObject) jsrs.get("result");
				    JSONObject jspoint = (JSONObject) jsResult.get("point");
				    list.get(i).setLAT((String) jspoint.get("y"));
				    list.get(i).setLNG((String) jspoint.get("x"));
				    
				}else {			
					String searchAddr2 = list.get(i).getSIGUNGU() + " " + list.get(i).getROADNAME();
					StringBuilder sb2 = new StringBuilder("https://api.vworld.kr/req/address");
					sb2.append("?service=address");
					sb2.append("&request=getCoord");
					sb2.append("&format=json");
					sb2.append("&crs=" + epsg);
					sb2.append("&key=" + apikey);
					sb2.append("&type=" + searchType2);
					sb2.append("&address=" + URLEncoder.encode(searchAddr2, StandardCharsets.UTF_8));
					
					URL url2 = new URL(sb2.toString());
					BufferedReader br2 = new BufferedReader(new InputStreamReader(url2.openStream(),StandardCharsets.UTF_8));
					JSONParser jspa2 = new JSONParser();
					JSONObject jsob2 = (JSONObject) jspa2.parse(br2);
					JSONObject jsrs2 = (JSONObject) jsob2.get("response");
					
					if(jsrs2.get("status").equals("OK")) {
						JSONObject jsResult2 = (JSONObject) jsrs2.get("result");
					    JSONObject jspoint2 = (JSONObject) jsResult2.get("point");
					    
					    list.get(i).setLAT((String) jspoint2.get("y"));
					    list.get(i).setLNG((String) jspoint2.get("x"));
					    
					}else {
						System.out.println("에휴 씨발");
						list.get(i).setLAT("자료없음");
					    list.get(i).setLNG("자료없음");
					}								
				}
				System.out.println(i + "번째 " + list.get(i));
			    DataMapper.insert(list.get(i));
		}
		System.out.println("끝");
		return list;
	}*/
	
}
