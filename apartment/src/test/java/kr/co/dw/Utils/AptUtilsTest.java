package kr.co.dw.Utils;

import org.junit.jupiter.api.Test;

public class AptUtilsTest {

	@Test
	void MappingRegionTest() {
		
		String[] arr = {"서울특별시","부산광역시","대구광역시","인천광역시","광주광역시","대전광역시","울산광역시","세종특별자치시","경기도","충청북도","충청남도","전라남도","경상북도","경상남도","제주특별자치도","강원특별자치도","전북특별자치도"};
		
		AptUtils utils = new AptUtils();
		for(int i = 0 ; i < arr.length ; i++) {
			utils.MappingRegion(arr[i]);
		}
		
	}
	
}