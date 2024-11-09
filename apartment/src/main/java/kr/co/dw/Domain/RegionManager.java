package kr.co.dw.Domain;

import java.util.List;

public class RegionManager {

	private static RegionManager instance;
	
	private RegionManager() {}
	
	public static synchronized RegionManager getInstance() {
        if (instance == null) {
            instance = new RegionManager();
        }
        return instance;
    }
	
	public static final List<String> PARENTREGIONLIST = List.of(
			"SEOUL",
			"BUSAN",
			"DAEGU",
			"INCHEON",
			"GWANGJU",
			"DAEJEON",
			"ULSAN",
			"SEJONG",
			"GYEONGGIDO",
			"GANGWONDO",
			"CHUNGCHEONGBUKDO",
			"CHUNGCHEONGNAMDO",
			"JEOLLABUKDO",
			"JEOLLANAMDO",
			"GYEONGSANGBUKDO",
			"GYEONGSANGNAMDO",
			"JEJU"
			);
	
	public static final List<Region> SEOUL = List.of(
			
			new Region("11110", "종로구"), new Region("11140", "중구"),
			new Region("11170", "용산구"), new Region("11200", "성동구"), new Region("11215", "광진구"),
			new Region("11230", "동대문구"), new Region("11260", "중랑구"), new Region("11290", "성북구"),
			new Region("11305", "강북구"), new Region("11320", "도봉구"), new Region("11350", "노원구"),
			new Region("11380", "은평구"), new Region("11410", "서대문구"), new Region("11440", "마포구"),
			new Region("11470", "양천구"), new Region("11500", "강서구"), new Region("11530", "구로구"),
			new Region("11545", "금천구"), new Region("11560", "영등포구"), new Region("11590", "동작구"),
			new Region("11620", "관악구"), new Region("11650", "서초구"), new Region("11680", "강남구"),
			new Region("11710", "송파구"), new Region("11740", "강동구")
			
			);
	
	public static final List<Region> BUSAN = List.of(

			new Region("26110", "중구"), new Region("26140", "서구"), new Region("26170", "동구"),
			new Region("26200", "영도구"), new Region("26230", "부산진구"), new Region("26260", "동래구"),
			new Region("26290", "남구"), new Region("26320", "북구"), new Region("26350", "해운대구"),
			new Region("26380", "사하구"), new Region("26410", "금정구"), new Region("26440", "강서구"),
			new Region("26470", "연제구"), new Region("26500", "수영구"), new Region("26530", "사상구"),
			new Region("26710", "기장군")

			);
	
	public static final List<Region> DAEGU = List.of(
			
			new Region("27110", "중구"),
            new Region("27140", "동구"),
            new Region("27170", "서구"),
            new Region("27200", "남구"),
            new Region("27230", "북구"),
            new Region("27260", "수성구"),
            new Region("27290", "달서구"),
            new Region("27710", "달성군"),
            new Region("27720", "군위군")
			
			);
	
	public static final List<Region> INCHEON = List.of(
			
			new Region("28110", "중구"),
            new Region("28140", "동구"),
            new Region("28177", "미추홀구"),
            new Region("28185", "연수구"),
            new Region("28200", "남동구"),
            new Region("28237", "부평구"),
            new Region("28245", "계양구"),
            new Region("28260", "서구"),
            new Region("28710", "강화군"),
            new Region("28720", "옹진군")
			
			);
	
	public static final List<Region> GWANGJU = List.of(
			
			new Region("29110", "동구"),
            new Region("29140", "서구"),
            new Region("29155", "남구"),
            new Region("29170", "북구"),
            new Region("29200", "광산구")
			
			);
	
	public static final List<Region> DAEJEON = List.of(
			
			new Region("30110", "동구"),
            new Region("30140", "중구"),
            new Region("30170", "서구"),
            new Region("30200", "유성구"),
            new Region("30230", "대덕구")
			
			);
	
	public static final List<Region> ULSAN = List.of(
			
			new Region("31110", "중구"),
            new Region("31140", "남구"),
            new Region("31170", "동구"),
            new Region("31200", "북구"),
            new Region("31710", "울주군")
			
			);
	
	public static final List<Region> SEJONG = List.of(
			
			new Region("36110", "")
			
			);
	
	public static final List<Region> GYEONGGIDO = List.of(
			
			new Region("41111", "수원시 장안구"),
            new Region("41113", "수원시 권선구"),
            new Region("41115", "수원시 팔달구"),
            new Region("41117", "수원시 영통구"),
            new Region("41131", "성남시 수정구"),
            new Region("41133", "성남시 중원구"),
            new Region("41135", "성남시 분당구"),
            new Region("41150", "의정부시"),
            new Region("41171", "안양시 만안구"),
            new Region("41173", "안양시 동안구"),
            new Region("41194", "부천시 소사구"),
            new Region("41196", "부천시 오정구"),
            new Region("41192", "부천시 원미구"),
            new Region("41210", "광명시"),
            new Region("41220", "평택시"),
            new Region("41250", "동두천시"),
            new Region("41271", "안산시 상록구"),
            new Region("41273", "안산시 단원구"),
            new Region("41281", "고양시 덕양구"),
            new Region("41285", "고양시 일산동구"),
            new Region("41287", "고양시 일산서구"),
            new Region("41290", "과천시"),
            new Region("41310", "구리시"),
            new Region("41360", "남양주시"),
            new Region("41370", "오산시"),
            new Region("41390", "시흥시"),
            new Region("41410", "군포시"),
            new Region("41430", "의왕시"),
            new Region("41450", "하남시"),
            new Region("41461", "용인시 처인구"),
            new Region("41463", "용인시 기흥구"),
            new Region("41465", "용인시 수지구"),
            new Region("41480", "파주시"),
            new Region("41500", "이천시"),
            new Region("41550", "안성시"),
            new Region("41570", "김포시"),
            new Region("41590", "화성시"),
            new Region("41610", "광주시"),
            new Region("41630", "양주시"),
            new Region("41650", "포천시"),
            new Region("41670", "여주시"),
            new Region("41800", "연천군"),
            new Region("41820", "가평군"),
            new Region("41830", "양평군")
			
			);
	
	public static final List<Region> GANGWONDO = List.of(
			
			new Region("51110", "춘천시"),
            new Region("51130", "원주시"),
            new Region("51150", "강릉시"),
            new Region("51170", "동해시"),
            new Region("51190", "태백시"),
            new Region("51210", "속초시"),
            new Region("51230", "삼척시"),
            new Region("51720", "홍천군"),
            new Region("51730", "횡성군"),
            new Region("51750", "영월군"),
            new Region("51760", "평창군"),
            new Region("51770", "정선군"),
            new Region("51780", "철원군"),
            new Region("51790", "화천군"),
            new Region("51800", "양구군"),
            new Region("51810", "인제군"),
            new Region("51820", "고성군"),
            new Region("51830", "양양군")
			);
	
	public static final List<Region> CHUNGCHEONGBUKDO = List.of(
			
			new Region("43111", "청주시 상당구"),
            new Region("43112", "청주시 서원구"),
            new Region("43113", "청주시 흥덕구"),
            new Region("43114", "청주시 청원구"),
            new Region("43130", "충주시"),
            new Region("43150", "제천시"),
            new Region("43720", "보은군"),
            new Region("43730", "옥천군"),
            new Region("43740", "영동군"),
            new Region("43745", "증평군"),
            new Region("43750", "진천군"),
            new Region("43760", "괴산군"),
            new Region("43770", "음성군"),
            new Region("43800", "단양군")
			
			);
	
	public static final List<Region> CHUNGCHEONGNAMDO = List.of(
			
			new Region("44131", "천안시 동남구"),
            new Region("44133", "천안시 서북구"),
            new Region("44150", "공주시"),
            new Region("44180", "보령시"),
            new Region("44200", "아산시"),
            new Region("44210", "서산시"),
            new Region("44230", "논산시"),
            new Region("44250", "계룡시"),
            new Region("44270", "당진시"),
            new Region("44710", "금산군"),
            new Region("44760", "부여군"),
            new Region("44770", "서천군"),
            new Region("44790", "청양군"),
            new Region("44800", "홍성군"),
            new Region("44810", "예산군"),
            new Region("44825", "태안군")
			
			);
	
	public static final List<Region> JEOLLABUKDO = List.of(
			
			new Region("52111", "전주시 완산구"),
            new Region("52113", "전주시 덕진구"),
            new Region("52130", "군산시"),
            new Region("52140", "익산시"),
            new Region("52180", "정읍시"),
            new Region("52190", "남원시"),
            new Region("52210", "김제시"),
            new Region("52710", "완주군"),
            new Region("52720", "진안군"),
            new Region("52730", "무주군"),
            new Region("52740", "장수군"),
            new Region("52750", "임실군"),
            new Region("52770", "순창군"),
            new Region("52790", "고창군"),
            new Region("52800", "부안군")
			
			);
	
	public static final List<Region> JEOLLANAMDO = List.of(
			
			new Region("46110", "목포시"),
            new Region("46130", "여수시"),
            new Region("46150", "순천시"),
            new Region("46170", "나주시"),
            new Region("46230", "광양시"),
            new Region("46710", "담양군"),
            new Region("46720", "곡성군"),
            new Region("46730", "구례군"),
            new Region("46770", "고흥군"),
            new Region("46780", "보성군"),
            new Region("46790", "화순군"),
            new Region("46800", "장흥군"),
            new Region("46810", "강진군"),
            new Region("46820", "해남군"),
            new Region("46830", "영남군"),
            new Region("46840", "무안군"),
            new Region("46860", "함평군"),
            new Region("46870", "영광군"),
            new Region("46880", "장성군"),
            new Region("46890", "완도군"),
            new Region("46900", "진도군"),
            new Region("46910", "신안군")
			
			);
	
	public static final List<Region> GYEONGSANGBUKDO = List.of(
			
			new Region("47111", "포항시 남구"),
            new Region("47113", "포항시 북구"),
            new Region("47130", "경주시"),
            new Region("47150", "김천시"),
            new Region("47170", "안동시"),
            new Region("47190", "구미시"),
            new Region("47210", "영주시"),
            new Region("47230", "영천시"),
            new Region("47250", "상주시"),
            new Region("47280", "문경시"),
            new Region("47290", "경산시"),
            new Region("47730", "의성군"),
            new Region("47750", "청송군"),
            new Region("47760", "영양군"),
            new Region("47770", "영덕군"),
            new Region("47820", "청도군"),
            new Region("47830", "고령군"),
            new Region("47840", "성주군"),
            new Region("47850", "칠곡군"),
            new Region("47900", "예천군"),
            new Region("47920", "봉화군"),
            new Region("47930", "울진군"),
            new Region("47940", "울릉군")
			
			);
	
	public static final List<Region> GYEONGSANGNAMDO = List.of(
			
			new Region("48121", "창원시 의창구"),
            new Region("48123", "창원시 성산구"),
            new Region("48125", "창원시 마산합포구"),
            new Region("48127", "창원시 마산회원구"),
            new Region("48129", "창원시 진해구"),
            new Region("48170", "진주시"),
            new Region("48220", "통영시"),
            new Region("48240", "사천시"),
            new Region("48250", "김해시"),
            new Region("48270", "밀양시"),
            new Region("48310", "거제시"),
            new Region("48330", "양산시"),
            new Region("48720", "의령군"),
            new Region("48730", "함안군"),
            new Region("48740", "창녕군"),
            new Region("48820", "고성군"),
            new Region("48840", "남해군"),
            new Region("48850", "하동군"),
            new Region("48860", "산청군"),
            new Region("48870", "함양군"),
            new Region("48880", "거창군"),
            new Region("48890", "합천군")
			
			);
	
	
	
	public static final List<Region> JEJU = List.of(
			
			new Region("50110", "제주시"),
            new Region("50130", "서귀포시")
			
			);
	
	public List<Region> getListRegion(String Region) {
		
		if(Region.equals("SEOUL")) {
			return RegionManager.SEOUL;
		}else if(Region.equals("BUSAN")) {
			return RegionManager.BUSAN;
		}else if(Region.equals("DAEGU")) {
			return RegionManager.DAEGU;
		}else if(Region.equals("INCHEON")) {
			return RegionManager.INCHEON;
		}else if(Region.equals("GWANGJU")) {
			return RegionManager.GWANGJU;
		}else if(Region.equals("DAEJEON")) {
			return RegionManager.DAEJEON;
		}else if(Region.equals("ULSAN")) {
			return RegionManager.ULSAN;
		}else if(Region.equals("SEJONG")) {
			return RegionManager.SEJONG;
		}else if(Region.equals("GYEONGGIDO")) {
			return RegionManager.GYEONGGIDO;
		}else if(Region.equals("GANGWONDO")) {
			return RegionManager.GANGWONDO;
		}else if(Region.equals("CHUNGCHEONGBUKDO")) {
			return RegionManager.CHUNGCHEONGBUKDO;
		}else if(Region.equals("CHUNGCHEONGNAMDO")) {
			return RegionManager.CHUNGCHEONGNAMDO;
		}else if(Region.equals("JEOLLABUKDO")) {
			return RegionManager.JEOLLABUKDO;
		}else if(Region.equals("JEOLLANAMDO")) {
			return RegionManager.JEOLLANAMDO;
		}else if(Region.equals("GYEONGSANGBUKDO")) {
			return RegionManager.GYEONGSANGBUKDO;
		}else if(Region.equals("GYEONGSANGNAMDO")) {
			return RegionManager.GYEONGSANGNAMDO;
		}else if(Region.equals("JEJU")) {
			return RegionManager.JEJU;
		}
		
		return null;
	}
	
	public ParentRegionName getkorParentName(String RegionName) {
		
		if(RegionName.equals("SEOUL")){
			return new ParentRegionName("서울특별시", "SEOUL"); 
		}else if(RegionName.equals("BUSAN")) {
			return new ParentRegionName("부산광역시", "BUSAN"); 
		}else if(RegionName.equals("DAEGU")) {
			return new ParentRegionName("대구광역시", "DAEGU"); 
		}else if(RegionName.equals("INCHEON")) {
			return new ParentRegionName("인천광역시", "INCHEON"); 
		}else if(RegionName.equals("GWANGJU")) {
			return new ParentRegionName("광주광역시", "GWANGJU"); 
		}else if(RegionName.equals("DAEJEON")) {
			return new ParentRegionName("대전광역시", "DAEJEON"); 
		}else if(RegionName.equals("ULSAN")) {
			return new ParentRegionName("울산광역시", "ULSAN"); 
		}else if(RegionName.equals("SEJONG")) {
			return new ParentRegionName("세종특별자치시", "SEJONG"); 
		}else if(RegionName.equals("GYEONGGIDO")) {
			return new ParentRegionName("경기도", "GYEONGGIDO"); 
		}else if(RegionName.equals("CHUNGCHEONGBUKDO")) {
			return new ParentRegionName("충청북도", "CHUNGCHEONGBUKDO"); 
		}else if(RegionName.equals("CHUNGCHEONGNAMDO")) {
			return new ParentRegionName("충청남도", "CHUNGCHEONGNAMDO"); 
		}else if(RegionName.equals("JEOLLANAMDO")) {
			return new ParentRegionName("전라남도", "JEOLLANAMDO"); 
		}else if(RegionName.equals("GYEONGSANGBUKDO")) {
			return new ParentRegionName("경상북도", "GYEONGSANGBUKDO"); 
		}else if(RegionName.equals("GYEONGSANGNAMDO")) {
			return new ParentRegionName("경상남도", "GYEONGSANGNAMDO"); 
		}else if(RegionName.equals("JEJU")) {
			return new ParentRegionName("제주특별자치도", "JEJU"); 
		}else if(RegionName.equals("GANGWONDO")) {
			return new ParentRegionName("강원특별자치도", "GANGWONDO"); 
		}else if(RegionName.equals("JEOLLABUKDO")) {
			return new ParentRegionName("전북특별자치도", "JEOLLABUKDO"); 
		}
		return null;
	}
	
}
