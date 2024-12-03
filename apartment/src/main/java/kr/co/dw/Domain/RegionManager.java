package kr.co.dw.Domain;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.dw.Exception.CustomException;
import kr.co.dw.Exception.CustomExceptions.ParserAndConverterException;
import kr.co.dw.Exception.ErrorCode.ErrorCode;

public class RegionManager {

	private static final Logger logger = LoggerFactory.getLogger(RegionManager.class);
	
	public static final List<Sido> SIDOS = List.of(new Sido("서울특별시", "SEOUL"),
			new Sido("부산광역시", "BUSAN"), new Sido("대구광역시", "DAEGU"),
			new Sido("인천광역시", "INCHEON"), new Sido("광주광역시", "GWANGJU"),
			new Sido("대전광역시", "DAEJEON"), new Sido("울산광역시", "ULSAN"),
			new Sido("세종특별자치시", "SEJONG"), new Sido("경기도", "GYEONGGIDO"),
			new Sido("강원특별자치도", "GANGWONDO"), new Sido("충청북도", "CHUNGCHEONGBUKDO"),
			new Sido("충청남도", "CHUNGCHEONGNAMDO"), new Sido("전북특별자치도", "JEOLLABUKDO"),
			new Sido("전라남도", "JEOLLANAMDO"), new Sido("경상북도", "GYEONGSANGBUKDO"),
			new Sido("경상남도", "GYEONGSANGNAMDO"), new Sido("제주특별자치도", "JEJU"));

	public static List<Sido> getSidos() {
		return RegionManager.SIDOS;
	}
	
	public static Sido getSido(String korSido) {
		switch (korSido) {
		case "SEOUL":
		case "서울특별시":
			return new Sido("서울특별시", "SEOUL");
		case "BUSAN":
		case "부산광역시":
			return new Sido("부산광역시", "BUSAN");
		case "DAEGU":
		case "대구광역시":
			return new Sido("대구광역시", "DAEGU");
		case "INCHEON":
		case "인천광역시":
			return new Sido("인천광역시", "INCHEON");
		case "GWANGJU":
		case "광주광역시":
			return new Sido("광주광역시", "GWANGJU");
		case "DAEJEON":
		case "대전광역시":
			return new Sido("대전광역시", "DAEJEON");
		case "ULSAN":
		case "울산광역시":
			return new Sido("울산광역시", "ULSAN");
		case "SEJONG":
		case "세종특별자치시":
			return new Sido("세종특별자치시", "SEJONG");
		case "GYEONGGIDO":
		case "경기도":
			return new Sido("경기도", "GYEONGGIDO");
		case "CHUNGCHEONGBUKDO":
		case "충청북도":
			return new Sido("충청북도", "CHUNGCHEONGBUKDO");
		case "CHUNGCHEONGNAMDO":
		case "충청남도":
			return new Sido("충청남도", "CHUNGCHEONGNAMDO");
		case "JEOLLANAMDO":
		case "전라남도":
			return new Sido("전라남도", "JEOLLANAMDO");
		case "GYEONGSANGBUKDO":
		case "경상북도":
			return new Sido("경상북도", "GYEONGSANGBUKDO");
		case "GYEONGSANGNAMDO":
		case "경상남도":
			return new Sido("경상남도", "GYEONGSANGNAMDO");
		case "JEJU":
		case "제주특별자치도":
			return new Sido("제주특별자치도", "JEJU");
		case "GANGWONDO":
		case "강원특별자치도":
			return new Sido("강원특별자치도", "GANGWONDO");
		case "JEOLLABUKDO":
		case "전북특별자치도":
			return new Sido("전북특별자치도", "JEOLLABUKDO");
		default:
			return null;
		}
	
}
	
	public static final List<Sigungu> SEOUL = List.of(

			new Sigungu("11110", "종로구"), new Sigungu("11140", "중구"), new Sigungu("11170", "용산구"),
			new Sigungu("11200", "성동구"), new Sigungu("11215", "광진구"), new Sigungu("11230", "동대문구"),
			new Sigungu("11260", "중랑구"), new Sigungu("11290", "성북구"), new Sigungu("11305", "강북구"),
			new Sigungu("11320", "도봉구"), new Sigungu("11350", "노원구"), new Sigungu("11380", "은평구"),
			new Sigungu("11410", "서대문구"), new Sigungu("11440", "마포구"), new Sigungu("11470", "양천구"),
			new Sigungu("11500", "강서구"), new Sigungu("11530", "구로구"), new Sigungu("11545", "금천구"),
			new Sigungu("11560", "영등포구"), new Sigungu("11590", "동작구"), new Sigungu("11620", "관악구"),
			new Sigungu("11650", "서초구"), new Sigungu("11680", "강남구"), new Sigungu("11710", "송파구"),
			new Sigungu("11740", "강동구")

	);

	public static final List<Sigungu> BUSAN = List.of(

			new Sigungu("26110", "중구"), new Sigungu("26140", "서구"), new Sigungu("26170", "동구"), new Sigungu("26200", "영도구"),
			new Sigungu("26230", "부산진구"), new Sigungu("26260", "동래구"), new Sigungu("26290", "남구"),
			new Sigungu("26320", "북구"), new Sigungu("26350", "해운대구"), new Sigungu("26380", "사하구"),
			new Sigungu("26410", "금정구"), new Sigungu("26440", "강서구"), new Sigungu("26470", "연제구"),
			new Sigungu("26500", "수영구"), new Sigungu("26530", "사상구"), new Sigungu("26710", "기장군")

	);

	public static final List<Sigungu> DAEGU = List.of(

			new Sigungu("27110", "중구"), new Sigungu("27140", "동구"), new Sigungu("27170", "서구"), new Sigungu("27200", "남구"),
			new Sigungu("27230", "북구"), new Sigungu("27260", "수성구"), new Sigungu("27290", "달서구"),
			new Sigungu("27710", "달성군"), new Sigungu("27720", "군위군")

	);

	public static final List<Sigungu> INCHEON = List.of(

			new Sigungu("28110", "중구"), new Sigungu("28140", "동구"), new Sigungu("28177", "미추홀구"),
			new Sigungu("28185", "연수구"), new Sigungu("28200", "남동구"), new Sigungu("28237", "부평구"),
			new Sigungu("28245", "계양구"), new Sigungu("28260", "서구"), new Sigungu("28710", "강화군"),
			new Sigungu("28720", "옹진군")

	);

	public static final List<Sigungu> GWANGJU = List.of(

			new Sigungu("29110", "동구"), new Sigungu("29140", "서구"), new Sigungu("29155", "남구"), new Sigungu("29170", "북구"),
			new Sigungu("29200", "광산구")

	);

	public static final List<Sigungu> DAEJEON = List.of(

			new Sigungu("30110", "동구"), new Sigungu("30140", "중구"), new Sigungu("30170", "서구"), new Sigungu("30200", "유성구"),
			new Sigungu("30230", "대덕구")

	);

	public static final List<Sigungu> ULSAN = List.of(

			new Sigungu("31110", "중구"), new Sigungu("31140", "남구"), new Sigungu("31170", "동구"), new Sigungu("31200", "북구"),
			new Sigungu("31710", "울주군")

	);

	public static final List<Sigungu> SEJONG = List.of(

			new Sigungu("36110", "")

	);

	public static final List<Sigungu> GYEONGGIDO = List.of(

			new Sigungu("41111", "수원시 장안구"), new Sigungu("41113", "수원시 권선구"), new Sigungu("41115", "수원시 팔달구"),
			new Sigungu("41117", "수원시 영통구"), new Sigungu("41131", "성남시 수정구"), new Sigungu("41133", "성남시 중원구"),
			new Sigungu("41135", "성남시 분당구"), new Sigungu("41150", "의정부시"), new Sigungu("41171", "안양시 만안구"),
			new Sigungu("41173", "안양시 동안구"), new Sigungu("41194", "부천시 소사구"), new Sigungu("41196", "부천시 오정구"),
			new Sigungu("41192", "부천시 원미구"), new Sigungu("41210", "광명시"), new Sigungu("41220", "평택시"),
			new Sigungu("41250", "동두천시"), new Sigungu("41271", "안산시 상록구"), new Sigungu("41273", "안산시 단원구"),
			new Sigungu("41281", "고양시 덕양구"), new Sigungu("41285", "고양시 일산동구"), new Sigungu("41287", "고양시 일산서구"),
			new Sigungu("41290", "과천시"), new Sigungu("41310", "구리시"), new Sigungu("41360", "남양주시"),
			new Sigungu("41370", "오산시"), new Sigungu("41390", "시흥시"), new Sigungu("41410", "군포시"),
			new Sigungu("41430", "의왕시"), new Sigungu("41450", "하남시"), new Sigungu("41461", "용인시 처인구"),
			new Sigungu("41463", "용인시 기흥구"), new Sigungu("41465", "용인시 수지구"), new Sigungu("41480", "파주시"),
			new Sigungu("41500", "이천시"), new Sigungu("41550", "안성시"), new Sigungu("41570", "김포시"),
			new Sigungu("41590", "화성시"), new Sigungu("41610", "광주시"), new Sigungu("41630", "양주시"),
			new Sigungu("41650", "포천시"), new Sigungu("41670", "여주시"), new Sigungu("41800", "연천군"),
			new Sigungu("41820", "가평군"), new Sigungu("41830", "양평군")

	);

	public static final List<Sigungu> GANGWONDO = List.of(

			new Sigungu("51110", "춘천시"), new Sigungu("51130", "원주시"), new Sigungu("51150", "강릉시"),
			new Sigungu("51170", "동해시"), new Sigungu("51190", "태백시"), new Sigungu("51210", "속초시"),
			new Sigungu("51230", "삼척시"), new Sigungu("51720", "홍천군"), new Sigungu("51730", "횡성군"),
			new Sigungu("51750", "영월군"), new Sigungu("51760", "평창군"), new Sigungu("51770", "정선군"),
			new Sigungu("51780", "철원군"), new Sigungu("51790", "화천군"), new Sigungu("51800", "양구군"),
			new Sigungu("51810", "인제군"), new Sigungu("51820", "고성군"), new Sigungu("51830", "양양군"));

	public static final List<Sigungu> CHUNGCHEONGBUKDO = List.of(

			new Sigungu("43111", "청주시 상당구"), new Sigungu("43112", "청주시 서원구"), new Sigungu("43113", "청주시 흥덕구"),
			new Sigungu("43114", "청주시 청원구"), new Sigungu("43130", "충주시"), new Sigungu("43150", "제천시"),
			new Sigungu("43720", "보은군"), new Sigungu("43730", "옥천군"), new Sigungu("43740", "영동군"),
			new Sigungu("43745", "증평군"), new Sigungu("43750", "진천군"), new Sigungu("43760", "괴산군"),
			new Sigungu("43770", "음성군"), new Sigungu("43800", "단양군")

	);

	public static final List<Sigungu> CHUNGCHEONGNAMDO = List.of(

			new Sigungu("44131", "천안시 동남구"), new Sigungu("44133", "천안시 서북구"), new Sigungu("44150", "공주시"),
			new Sigungu("44180", "보령시"), new Sigungu("44200", "아산시"), new Sigungu("44210", "서산시"),
			new Sigungu("44230", "논산시"), new Sigungu("44250", "계룡시"), new Sigungu("44270", "당진시"),
			new Sigungu("44710", "금산군"), new Sigungu("44760", "부여군"), new Sigungu("44770", "서천군"),
			new Sigungu("44790", "청양군"), new Sigungu("44800", "홍성군"), new Sigungu("44810", "예산군"),
			new Sigungu("44825", "태안군")

	);

	public static final List<Sigungu> JEOLLABUKDO = List.of(

			new Sigungu("52111", "전주시 완산구"), new Sigungu("52113", "전주시 덕진구"), new Sigungu("52130", "군산시"),
			new Sigungu("52140", "익산시"), new Sigungu("52180", "정읍시"), new Sigungu("52190", "남원시"),
			new Sigungu("52210", "김제시"), new Sigungu("52710", "완주군"), new Sigungu("52720", "진안군"),
			new Sigungu("52730", "무주군"), new Sigungu("52740", "장수군"), new Sigungu("52750", "임실군"),
			new Sigungu("52770", "순창군"), new Sigungu("52790", "고창군"), new Sigungu("52800", "부안군")

	);

	public static final List<Sigungu> JEOLLANAMDO = List.of(

			new Sigungu("46110", "목포시"), new Sigungu("46130", "여수시"), new Sigungu("46150", "순천시"),
			new Sigungu("46170", "나주시"), new Sigungu("46230", "광양시"), new Sigungu("46710", "담양군"),
			new Sigungu("46720", "곡성군"), new Sigungu("46730", "구례군"), new Sigungu("46770", "고흥군"),
			new Sigungu("46780", "보성군"), new Sigungu("46790", "화순군"), new Sigungu("46800", "장흥군"),
			new Sigungu("46810", "강진군"), new Sigungu("46820", "해남군"), new Sigungu("46830", "영남군"),
			new Sigungu("46840", "무안군"), new Sigungu("46860", "함평군"), new Sigungu("46870", "영광군"),
			new Sigungu("46880", "장성군"), new Sigungu("46890", "완도군"), new Sigungu("46900", "진도군"),
			new Sigungu("46910", "신안군")

	);

	public static final List<Sigungu> GYEONGSANGBUKDO = List.of(

			new Sigungu("47111", "포항시 남구"), new Sigungu("47113", "포항시 북구"), new Sigungu("47130", "경주시"),
			new Sigungu("47150", "김천시"), new Sigungu("47170", "안동시"), new Sigungu("47190", "구미시"),
			new Sigungu("47210", "영주시"), new Sigungu("47230", "영천시"), new Sigungu("47250", "상주시"),
			new Sigungu("47280", "문경시"), new Sigungu("47290", "경산시"), new Sigungu("47730", "의성군"),
			new Sigungu("47750", "청송군"), new Sigungu("47760", "영양군"), new Sigungu("47770", "영덕군"),
			new Sigungu("47820", "청도군"), new Sigungu("47830", "고령군"), new Sigungu("47840", "성주군"),
			new Sigungu("47850", "칠곡군"), new Sigungu("47900", "예천군"), new Sigungu("47920", "봉화군"),
			new Sigungu("47930", "울진군"), new Sigungu("47940", "울릉군")

	);

	public static final List<Sigungu> GYEONGSANGNAMDO = List.of(

			new Sigungu("48121", "창원시 의창구"), new Sigungu("48123", "창원시 성산구"), new Sigungu("48125", "창원시 마산합포구"),
			new Sigungu("48127", "창원시 마산회원구"), new Sigungu("48129", "창원시 진해구"), new Sigungu("48170", "진주시"),
			new Sigungu("48220", "통영시"), new Sigungu("48240", "사천시"), new Sigungu("48250", "김해시"),
			new Sigungu("48270", "밀양시"), new Sigungu("48310", "거제시"), new Sigungu("48330", "양산시"),
			new Sigungu("48720", "의령군"), new Sigungu("48730", "함안군"), new Sigungu("48740", "창녕군"),
			new Sigungu("48820", "고성군"), new Sigungu("48840", "남해군"), new Sigungu("48850", "하동군"),
			new Sigungu("48860", "산청군"), new Sigungu("48870", "함양군"), new Sigungu("48880", "거창군"),
			new Sigungu("48890", "합천군")

	);

	public static final List<Sigungu> JEJU = List.of(

			new Sigungu("50110", "제주시"), new Sigungu("50130", "서귀포시")

	);

	public static List<Sigungu> getSigungus(String korSido) {
			switch (korSido) {
			case "SEOUL":
			case "서울특별시":
				return RegionManager.SEOUL;
			case "BUSAN":
			case "부산광역시":
				return RegionManager.BUSAN;
			case "DAEGU":
			case "대구광역시":
				return RegionManager.DAEGU;
			case "INCHEON":
			case "인천광역시":
				return RegionManager.INCHEON;
			case "GWANGJU":
			case "광주광역시":
				return RegionManager.GWANGJU;
			case "DAEJEON":
			case "대전광역시":
				return RegionManager.DAEJEON;
			case "ULSAN":
			case "울산광역시":
				return RegionManager.ULSAN;
			case "SEJONG":
			case "세종특별자치시":
				return RegionManager.SEJONG;
			case "GYEONGGIDO":
			case "경기도":
				return RegionManager.GYEONGGIDO;
			case "GANGWONDO":
			case "강원특별자치도":
				return RegionManager.GANGWONDO;
			case "CHUNGCHEONGBUKDO":
			case "충청북도":
				return RegionManager.CHUNGCHEONGBUKDO;
			case "CHUNGCHEONGNAMDO":
			case "충청남도":
				return RegionManager.CHUNGCHEONGNAMDO;
			case "JEOLLABUKDO":
			case "전북특별자치도":
				return RegionManager.JEOLLABUKDO;
			case "JEOLLANAMDO":
			case "전라남도":
				return RegionManager.JEOLLANAMDO;
			case "GYEONGSANGBUKDO":
			case "경상북도":
				return RegionManager.GYEONGSANGBUKDO;
			case "GYEONGSANGNAMDO":
			case "경상남도":
				return RegionManager.GYEONGSANGNAMDO;
			case "JEJU":
			case "제주특별자치도":
				return RegionManager.JEJU;
			default:
				return null;
			}
	}

	public static String splitSigungu(String sigungu) {
		
		try {
			String[] arr = sigungu.split(" ");
			String sido = arr[0];
			return sido;
		} catch (Exception e) {
			logger.error("시군구에서 시도를 추출하는데 실패했습니다 sigungu={}" , sigungu, e);
			throw new ParserAndConverterException(ErrorCode.PARSER_AND_CONVERTER_ERROR, "시군구에서 시도를 추출하는데 실패했습니다.");
		}
	}

	public static String toEngSido(String korSido) {
		
		if(korSido.equals("서울특별시")) {
			return "SEOUL";
		}else if(korSido.equals("부산광역시")) {
			return "BUSAN";
		}else if(korSido.equals("대구광역시")) {
			return "DAEGU";
		}else if(korSido.equals("인천광역시")) {
			return "INCHEON";
		}else if(korSido.equals("광주광역시")) {
			return "GWANGJU";
		}else if(korSido.equals("대전광역시")) {
			return "DAEJEON";
		}else if(korSido.equals("울산광역시")) {
			return "ULSAN";
		}else if(korSido.equals("세종특별자치시")) {
			return "SEJONG";
		}else if(korSido.equals("경기도")) {
			return "GYEONGGIDO";
		}else if(korSido.equals("충청북도")) {
			return "CHUNGCHEONGBUKDO";
		}else if(korSido.equals("충청남도")) {
			return "CHUNGCHEONGNAMDO";
		}else if(korSido.equals("전라남도")) {
			return "JEOLLANAMDO";
		}else if(korSido.equals("경상북도")) {
			return "GYEONGSANGBUKDO";
		}else if(korSido.equals("경상남도")) {
			return "GYEONGSANGNAMDO";
		}else if(korSido.equals("제주특별자치도")) {
			return "JEJU";
		}else if(korSido.equals("강원특별자치도")) {
			return "GANGWONDO";
		}else if(korSido.equals("전북특별자치도")) {
			return "JEOLLABUKDO";
		}
		return null;
	}
	

}
