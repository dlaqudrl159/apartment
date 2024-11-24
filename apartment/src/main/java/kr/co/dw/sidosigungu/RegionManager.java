package kr.co.dw.sidosigungu;

import java.util.List;

public class RegionManager {

	public static final List<Sido2> SIDO = List.of(new Sido2("서울특별시", "SEOUL"),
			new Sido2("부산광역시", "BUSAN"), new Sido2("대구광역시", "DAEGU"),
			new Sido2("인천광역시", "INCHEON"), new Sido2("광주광역시", "GWANGJU"),
			new Sido2("대전광역시", "DAEJEON"), new Sido2("울산광역시", "ULSAN"),
			new Sido2("세종특별자치시", "SEJONG"), new Sido2("경기도", "GYEONGGIDO"),
			new Sido2("강원특별자치도", "GANGWONDO"), new Sido2("충청북도", "CHUNGCHEONGBUKDO"),
			new Sido2("충청남도", "CHUNGCHEONGNAMDO"), new Sido2("전북특별자치도", "JEOLLABUKDO"),
			new Sido2("전라남도", "JEOLLANAMDO"), new Sido2("경상북도", "GYEONGSANGBUKDO"),
			new Sido2("경상남도", "GYEONGSANGNAMDO"), new Sido2("제주특별자치도", "JEJU"));

	public static final List<Sigungu2> SEOUL = List.of(

			new Sigungu2("11110", "종로구"), new Sigungu2("11140", "중구"), new Sigungu2("11170", "용산구"),
			new Sigungu2("11200", "성동구"), new Sigungu2("11215", "광진구"), new Sigungu2("11230", "동대문구"),
			new Sigungu2("11260", "중랑구"), new Sigungu2("11290", "성북구"), new Sigungu2("11305", "강북구"),
			new Sigungu2("11320", "도봉구"), new Sigungu2("11350", "노원구"), new Sigungu2("11380", "은평구"),
			new Sigungu2("11410", "서대문구"), new Sigungu2("11440", "마포구"), new Sigungu2("11470", "양천구"),
			new Sigungu2("11500", "강서구"), new Sigungu2("11530", "구로구"), new Sigungu2("11545", "금천구"),
			new Sigungu2("11560", "영등포구"), new Sigungu2("11590", "동작구"), new Sigungu2("11620", "관악구"),
			new Sigungu2("11650", "서초구"), new Sigungu2("11680", "강남구"), new Sigungu2("11710", "송파구"),
			new Sigungu2("11740", "강동구")

	);

	public static final List<Sigungu2> BUSAN = List.of(

			new Sigungu2("26110", "중구"), new Sigungu2("26140", "서구"), new Sigungu2("26170", "동구"), new Sigungu2("26200", "영도구"),
			new Sigungu2("26230", "부산진구"), new Sigungu2("26260", "동래구"), new Sigungu2("26290", "남구"),
			new Sigungu2("26320", "북구"), new Sigungu2("26350", "해운대구"), new Sigungu2("26380", "사하구"),
			new Sigungu2("26410", "금정구"), new Sigungu2("26440", "강서구"), new Sigungu2("26470", "연제구"),
			new Sigungu2("26500", "수영구"), new Sigungu2("26530", "사상구"), new Sigungu2("26710", "기장군")

	);

	public static final List<Sigungu2> DAEGU = List.of(

			new Sigungu2("27110", "중구"), new Sigungu2("27140", "동구"), new Sigungu2("27170", "서구"), new Sigungu2("27200", "남구"),
			new Sigungu2("27230", "북구"), new Sigungu2("27260", "수성구"), new Sigungu2("27290", "달서구"),
			new Sigungu2("27710", "달성군"), new Sigungu2("27720", "군위군")

	);

	public static final List<Sigungu2> INCHEON = List.of(

			new Sigungu2("28110", "중구"), new Sigungu2("28140", "동구"), new Sigungu2("28177", "미추홀구"),
			new Sigungu2("28185", "연수구"), new Sigungu2("28200", "남동구"), new Sigungu2("28237", "부평구"),
			new Sigungu2("28245", "계양구"), new Sigungu2("28260", "서구"), new Sigungu2("28710", "강화군"),
			new Sigungu2("28720", "옹진군")

	);

	public static final List<Sigungu2> GWANGJU = List.of(

			new Sigungu2("29110", "동구"), new Sigungu2("29140", "서구"), new Sigungu2("29155", "남구"), new Sigungu2("29170", "북구"),
			new Sigungu2("29200", "광산구")

	);

	public static final List<Sigungu2> DAEJEON = List.of(

			new Sigungu2("30110", "동구"), new Sigungu2("30140", "중구"), new Sigungu2("30170", "서구"), new Sigungu2("30200", "유성구"),
			new Sigungu2("30230", "대덕구")

	);

	public static final List<Sigungu2> ULSAN = List.of(

			new Sigungu2("31110", "중구"), new Sigungu2("31140", "남구"), new Sigungu2("31170", "동구"), new Sigungu2("31200", "북구"),
			new Sigungu2("31710", "울주군")

	);

	public static final List<Sigungu2> SEJONG = List.of(

			new Sigungu2("36110", "")

	);

	public static final List<Sigungu2> GYEONGGIDO = List.of(

			new Sigungu2("41111", "수원시 장안구"), new Sigungu2("41113", "수원시 권선구"), new Sigungu2("41115", "수원시 팔달구"),
			new Sigungu2("41117", "수원시 영통구"), new Sigungu2("41131", "성남시 수정구"), new Sigungu2("41133", "성남시 중원구"),
			new Sigungu2("41135", "성남시 분당구"), new Sigungu2("41150", "의정부시"), new Sigungu2("41171", "안양시 만안구"),
			new Sigungu2("41173", "안양시 동안구"), new Sigungu2("41194", "부천시 소사구"), new Sigungu2("41196", "부천시 오정구"),
			new Sigungu2("41192", "부천시 원미구"), new Sigungu2("41210", "광명시"), new Sigungu2("41220", "평택시"),
			new Sigungu2("41250", "동두천시"), new Sigungu2("41271", "안산시 상록구"), new Sigungu2("41273", "안산시 단원구"),
			new Sigungu2("41281", "고양시 덕양구"), new Sigungu2("41285", "고양시 일산동구"), new Sigungu2("41287", "고양시 일산서구"),
			new Sigungu2("41290", "과천시"), new Sigungu2("41310", "구리시"), new Sigungu2("41360", "남양주시"),
			new Sigungu2("41370", "오산시"), new Sigungu2("41390", "시흥시"), new Sigungu2("41410", "군포시"),
			new Sigungu2("41430", "의왕시"), new Sigungu2("41450", "하남시"), new Sigungu2("41461", "용인시 처인구"),
			new Sigungu2("41463", "용인시 기흥구"), new Sigungu2("41465", "용인시 수지구"), new Sigungu2("41480", "파주시"),
			new Sigungu2("41500", "이천시"), new Sigungu2("41550", "안성시"), new Sigungu2("41570", "김포시"),
			new Sigungu2("41590", "화성시"), new Sigungu2("41610", "광주시"), new Sigungu2("41630", "양주시"),
			new Sigungu2("41650", "포천시"), new Sigungu2("41670", "여주시"), new Sigungu2("41800", "연천군"),
			new Sigungu2("41820", "가평군"), new Sigungu2("41830", "양평군")

	);

	public static final List<Sigungu2> GANGWONDO = List.of(

			new Sigungu2("51110", "춘천시"), new Sigungu2("51130", "원주시"), new Sigungu2("51150", "강릉시"),
			new Sigungu2("51170", "동해시"), new Sigungu2("51190", "태백시"), new Sigungu2("51210", "속초시"),
			new Sigungu2("51230", "삼척시"), new Sigungu2("51720", "홍천군"), new Sigungu2("51730", "횡성군"),
			new Sigungu2("51750", "영월군"), new Sigungu2("51760", "평창군"), new Sigungu2("51770", "정선군"),
			new Sigungu2("51780", "철원군"), new Sigungu2("51790", "화천군"), new Sigungu2("51800", "양구군"),
			new Sigungu2("51810", "인제군"), new Sigungu2("51820", "고성군"), new Sigungu2("51830", "양양군"));

	public static final List<Sigungu2> CHUNGCHEONGBUKDO = List.of(

			new Sigungu2("43111", "청주시 상당구"), new Sigungu2("43112", "청주시 서원구"), new Sigungu2("43113", "청주시 흥덕구"),
			new Sigungu2("43114", "청주시 청원구"), new Sigungu2("43130", "충주시"), new Sigungu2("43150", "제천시"),
			new Sigungu2("43720", "보은군"), new Sigungu2("43730", "옥천군"), new Sigungu2("43740", "영동군"),
			new Sigungu2("43745", "증평군"), new Sigungu2("43750", "진천군"), new Sigungu2("43760", "괴산군"),
			new Sigungu2("43770", "음성군"), new Sigungu2("43800", "단양군")

	);

	public static final List<Sigungu2> CHUNGCHEONGNAMDO = List.of(

			new Sigungu2("44131", "천안시 동남구"), new Sigungu2("44133", "천안시 서북구"), new Sigungu2("44150", "공주시"),
			new Sigungu2("44180", "보령시"), new Sigungu2("44200", "아산시"), new Sigungu2("44210", "서산시"),
			new Sigungu2("44230", "논산시"), new Sigungu2("44250", "계룡시"), new Sigungu2("44270", "당진시"),
			new Sigungu2("44710", "금산군"), new Sigungu2("44760", "부여군"), new Sigungu2("44770", "서천군"),
			new Sigungu2("44790", "청양군"), new Sigungu2("44800", "홍성군"), new Sigungu2("44810", "예산군"),
			new Sigungu2("44825", "태안군")

	);

	public static final List<Sigungu2> JEOLLABUKDO = List.of(

			new Sigungu2("52111", "전주시 완산구"), new Sigungu2("52113", "전주시 덕진구"), new Sigungu2("52130", "군산시"),
			new Sigungu2("52140", "익산시"), new Sigungu2("52180", "정읍시"), new Sigungu2("52190", "남원시"),
			new Sigungu2("52210", "김제시"), new Sigungu2("52710", "완주군"), new Sigungu2("52720", "진안군"),
			new Sigungu2("52730", "무주군"), new Sigungu2("52740", "장수군"), new Sigungu2("52750", "임실군"),
			new Sigungu2("52770", "순창군"), new Sigungu2("52790", "고창군"), new Sigungu2("52800", "부안군")

	);

	public static final List<Sigungu2> JEOLLANAMDO = List.of(

			new Sigungu2("46110", "목포시"), new Sigungu2("46130", "여수시"), new Sigungu2("46150", "순천시"),
			new Sigungu2("46170", "나주시"), new Sigungu2("46230", "광양시"), new Sigungu2("46710", "담양군"),
			new Sigungu2("46720", "곡성군"), new Sigungu2("46730", "구례군"), new Sigungu2("46770", "고흥군"),
			new Sigungu2("46780", "보성군"), new Sigungu2("46790", "화순군"), new Sigungu2("46800", "장흥군"),
			new Sigungu2("46810", "강진군"), new Sigungu2("46820", "해남군"), new Sigungu2("46830", "영남군"),
			new Sigungu2("46840", "무안군"), new Sigungu2("46860", "함평군"), new Sigungu2("46870", "영광군"),
			new Sigungu2("46880", "장성군"), new Sigungu2("46890", "완도군"), new Sigungu2("46900", "진도군"),
			new Sigungu2("46910", "신안군")

	);

	public static final List<Sigungu2> GYEONGSANGBUKDO = List.of(

			new Sigungu2("47111", "포항시 남구"), new Sigungu2("47113", "포항시 북구"), new Sigungu2("47130", "경주시"),
			new Sigungu2("47150", "김천시"), new Sigungu2("47170", "안동시"), new Sigungu2("47190", "구미시"),
			new Sigungu2("47210", "영주시"), new Sigungu2("47230", "영천시"), new Sigungu2("47250", "상주시"),
			new Sigungu2("47280", "문경시"), new Sigungu2("47290", "경산시"), new Sigungu2("47730", "의성군"),
			new Sigungu2("47750", "청송군"), new Sigungu2("47760", "영양군"), new Sigungu2("47770", "영덕군"),
			new Sigungu2("47820", "청도군"), new Sigungu2("47830", "고령군"), new Sigungu2("47840", "성주군"),
			new Sigungu2("47850", "칠곡군"), new Sigungu2("47900", "예천군"), new Sigungu2("47920", "봉화군"),
			new Sigungu2("47930", "울진군"), new Sigungu2("47940", "울릉군")

	);

	public static final List<Sigungu2> GYEONGSANGNAMDO = List.of(

			new Sigungu2("48121", "창원시 의창구"), new Sigungu2("48123", "창원시 성산구"), new Sigungu2("48125", "창원시 마산합포구"),
			new Sigungu2("48127", "창원시 마산회원구"), new Sigungu2("48129", "창원시 진해구"), new Sigungu2("48170", "진주시"),
			new Sigungu2("48220", "통영시"), new Sigungu2("48240", "사천시"), new Sigungu2("48250", "김해시"),
			new Sigungu2("48270", "밀양시"), new Sigungu2("48310", "거제시"), new Sigungu2("48330", "양산시"),
			new Sigungu2("48720", "의령군"), new Sigungu2("48730", "함안군"), new Sigungu2("48740", "창녕군"),
			new Sigungu2("48820", "고성군"), new Sigungu2("48840", "남해군"), new Sigungu2("48850", "하동군"),
			new Sigungu2("48860", "산청군"), new Sigungu2("48870", "함양군"), new Sigungu2("48880", "거창군"),
			new Sigungu2("48890", "합천군")

	);

	public static final List<Sigungu2> JEJU = List.of(

			new Sigungu2("50110", "제주시"), new Sigungu2("50130", "서귀포시")

	);

	public static List<Sigungu2> getSigungu(String sido) {
		switch (sido) {
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

	public static List<Sido2> getSidos() {
		return RegionManager.SIDO;
	}

	public static Sido2 getSido(String sido) {
		switch (sido) {
		case "SEOUL":
		case "서울특별시":
			return new Sido2("서울특별시", "SEOUL");
		case "BUSAN":
		case "부산광역시":
			return new Sido2("부산광역시", "BUSAN");
		case "DAEGU":
		case "대구광역시":
			return new Sido2("대구광역시", "DAEGU");
		case "INCHEON":
		case "인천광역시":
			return new Sido2("인천광역시", "INCHEON");
		case "GWANGJU":
		case "광주광역시":
			return new Sido2("광주광역시", "GWANGJU");
		case "DAEJEON":
		case "대전광역시":
			return new Sido2("대전광역시", "DAEJEON");
		case "ULSAN":
		case "울산광역시":
			return new Sido2("울산광역시", "ULSAN");
		case "SEJONG":
		case "세종특별자치시":
			return new Sido2("세종특별자치시", "SEJONG");
		case "GYEONGGIDO":
		case "경기도":
			return new Sido2("경기도", "GYEONGGIDO");
		case "CHUNGCHEONGBUKDO":
		case "충청북도":
			return new Sido2("충청북도", "CHUNGCHEONGBUKDO");
		case "CHUNGCHEONGNAMDO":
		case "충청남도":
			return new Sido2("충청남도", "CHUNGCHEONGNAMDO");
		case "JEOLLANAMDO":
		case "전라남도":
			return new Sido2("전라남도", "JEOLLANAMDO");
		case "GYEONGSANGBUKDO":
		case "경상북도":
			return new Sido2("경상북도", "GYEONGSANGBUKDO");
		case "GYEONGSANGNAMDO":
		case "경상남도":
			return new Sido2("경상남도", "GYEONGSANGNAMDO");
		case "JEJU":
		case "제주특별자치도":
			return new Sido2("제주특별자치도", "JEJU");
		case "GANGWONDO":
		case "강원특별자치도":
			return new Sido2("강원특별자치도", "GANGWONDO");
		case "JEOLLABUKDO":
		case "전북특별자치도":
			return new Sido2("전북특별자치도", "JEOLLABUKDO");
		default:
			return null;
		}
	}
	
	public static AutoAptDto getAutoAptDto(String sido) {
		
		switch (sido) {
		case "서울특별시":
			return new AutoAptDto(new Sido2("서울특별시", "SEOUL"), RegionManager.SEOUL);
		case "부산광역시":
			return new AutoAptDto(new Sido2("부산광역시", "BUSAN"), RegionManager.BUSAN);
		case "대구광역시":
			return new AutoAptDto(new Sido2("대구광역시", "DAEGU"), RegionManager.DAEGU);
		case "인천광역시":
			return new AutoAptDto(new Sido2("인천광역시", "INCHEON"), RegionManager.INCHEON);
		case "광주광역시":
			return new AutoAptDto(new Sido2("광주광역시", "GWANGJU"), RegionManager.GWANGJU);
		case "대전광역시":
			return new AutoAptDto(new Sido2("대전광역시", "DAEJEON"), RegionManager.DAEJEON);
		case "울산광역시":
			return new AutoAptDto(new Sido2("울산광역시", "ULSAN"), RegionManager.ULSAN);
		case "세종특별자치시":
			return new AutoAptDto(new Sido2("세종특별자치시", "SEJONG"), RegionManager.SEJONG);
		case "경기도":
			return new AutoAptDto(new Sido2("경기도", "GYEONGGIDO"), RegionManager.GYEONGGIDO);
		case "충청북도":
			return new AutoAptDto(new Sido2("충청북도", "CHUNGCHEONGBUKDO"), RegionManager.CHUNGCHEONGBUKDO);
		case "충청남도":
			return new AutoAptDto(new Sido2("충청남도", "CHUNGCHEONGNAMDO"), RegionManager.CHUNGCHEONGNAMDO);
		case "전라남도":
			return new AutoAptDto(new Sido2("전라남도", "JEOLLANAMDO"), RegionManager.JEOLLANAMDO);
		case "경상북도":
			return new AutoAptDto(new Sido2("경상북도", "GYEONGSANGBUKDO"), RegionManager.GYEONGSANGBUKDO);
		case "경상남도":
			return new AutoAptDto(new Sido2("경상남도", "GYEONGSANGNAMDO"), RegionManager.GYEONGSANGNAMDO);
		case "제주특별자치도":
			return new AutoAptDto(new Sido2("제주특별자치도", "JEJU"), RegionManager.JEJU);
		case "강원특별자치도":
			return new AutoAptDto(new Sido2("강원특별자치도", "GANGWONDO"), RegionManager.GANGWONDO);
		case "전북특별자치도":
			return new AutoAptDto(new Sido2("전북특별자치도", "JEOLLABUKDO"), RegionManager.JEOLLABUKDO);
		default:
			return null;
		}
	}

}
