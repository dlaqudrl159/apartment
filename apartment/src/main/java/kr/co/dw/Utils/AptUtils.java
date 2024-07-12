package kr.co.dw.Utils;

public class AptUtils {

	
	public String MappingRegion(String region_1depth_name) {
		String[] arr = {"서울특별시","부산광역시","대구광역시","인천광역시","광주광역시","대전광역시","울산광역시","세종특별자치시","경기도","충청북도","충청남도","전라남도","경상북도","경상남도","제주특별자치도","강원특별자치도","전북특별자치도"};
		
		String[] arr2 = {"SEOUL","BUSAN","DAEGU","INCHEON","GWANGJU","DAEJEON","ULSAN","SEJONG","GYEONGGIDO","CHUNGCHEONGBUKDO","CHUNGCHEONGNAMDO","JEOLLANAMDO","GYEONGSANGBUKDO","GYEONGSANGNAMDO","JEJU","GANGWONDO","JEOLLABUKDO"};
		
		for(int i = 0 ; i < arr.length ; i++) {
			if(region_1depth_name.equals(arr[i])) {
				region_1depth_name = arr2[i];
			}
		}
		//System.out.println(region_1depth_name);
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
