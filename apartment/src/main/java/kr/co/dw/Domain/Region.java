package kr.co.dw.Domain;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class Region {

	private String code;
	private String regionName;
	
	/**
	 * @param code
	 * @param regionName
	 */
	public Region(String code, String regionName) {
		this.code = code;
		this.regionName = regionName;
	}
	
	
					
		
	
	
	
}
