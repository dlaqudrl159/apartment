package kr.co.dw.Utils;

public class AptUtils {

	public static String SplitSigungu(String sigungu) {
		if(sigungu == null || sigungu.trim().isEmpty()) {
			throw new IllegalArgumentException("sigungu 값이 null이거나 비어있습니다.");
		}
		String[] arr = sigungu.split(" ");
		if (arr.length < 1) {
	        throw new IllegalArgumentException("올바르지 않은 시군구 형식입니다.");
	    }
		String tableName = arr[0];
		
		return tableName;
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
		return "ERROR";
	}
}
