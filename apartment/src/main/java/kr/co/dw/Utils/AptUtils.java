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
	
	public static String toEngParentRegion(String korParentRegion) {
		if(korParentRegion.equals("서울특별시")) {
			return "SEOUL";
		}else if(korParentRegion.equals("부산광역시")) {
			return "BUSAN";
		}else if(korParentRegion.equals("대구광역시")) {
			return "DAEGU";
		}else if(korParentRegion.equals("인천광역시")) {
			return "INCHEON";
		}else if(korParentRegion.equals("광주광역시")) {
			return "GWANGJU";
		}else if(korParentRegion.equals("대전광역시")) {
			return "DAEJEON";
		}else if(korParentRegion.equals("울산광역시")) {
			return "ULSAN";
		}else if(korParentRegion.equals("세종특별자치시")) {
			return "SEJONG";
		}else if(korParentRegion.equals("경기도")) {
			return "GYEONGGIDO";
		}else if(korParentRegion.equals("충청북도")) {
			return "CHUNGCHEONGBUKDO";
		}else if(korParentRegion.equals("충청남도")) {
			return "CHUNGCHEONGNAMDO";
		}else if(korParentRegion.equals("전라남도")) {
			return "JEOLLANAMDO";
		}else if(korParentRegion.equals("경상북도")) {
			return "GYEONGSANGBUKDO";
		}else if(korParentRegion.equals("경상남도")) {
			return "GYEONGSANGNAMDO";
		}else if(korParentRegion.equals("제주특별자치도")) {
			return "JEJU";
		}else if(korParentRegion.equals("강원특별자치도")) {
			return "GANGWONDO";
		}else if(korParentRegion.equals("전북특별자치도")) {
			return "JEOLLABUKDO";
		}
		return "ERROR";
	}
	public String MappingRegion(String region_1depth_name) {
		String[] arr = {"서울특별시","부산광역시","대구광역시","인천광역시","광주광역시","대전광역시","울산광역시","세종특별자치시","경기도","충청북도","충청남도","전라남도","경상북도","경상남도","제주특별자치도","강원특별자치도","전북특별자치도"};
		
		String[] arr2 = {"SEOUL","BUSAN","DAEGU","INCHEON","GWANGJU","DAEJEON","ULSAN","SEJONG","GYEONGGIDO","CHUNGCHEONGBUKDO","CHUNGCHEONGNAMDO","JEOLLANAMDO","GYEONGSANGBUKDO","GYEONGSANGNAMDO","JEJU","GANGWONDO","JEOLLABUKDO"};
		
		for(int i = 0 ; i < arr.length ; i++) {
			if(region_1depth_name.equals(arr[i])) {
				region_1depth_name = arr2[i];
			}
		}
		return region_1depth_name;
		
	}
	
	public String MappingRegionReverse(String fileReginonName) {
		
		String[] arr = {"SEOUL","BUSAN","DAEGU","INCHEON","GWANGJU","DAEJEON","ULSAN","SEJONG","GYEONGGIDO","CHUNGCHEONGBUKDO","CHUNGCHEONGNAMDO","JEOLLANAMDO","GYEONGSANGBUKDO","GYEONGSANGNAMDO","JEJU","GANGWONDO","JEOLLABUKDO"};
		
		String[] arr2 = {"서울","부산","대구","인천","광주","대전","울산","세종특별자치시","경기도","충청북도","충청남도","전라남도","경상북도","경상남도","제주도","강원특별자치도","전북특별자치도"};
		
		for(int i = 0 ; i < arr.length ; i++) {
			if(fileReginonName.toUpperCase().equals(arr[i])) {
				fileReginonName = arr2[i];
			}
		}
		
		return fileReginonName;
	}
	
	
}
