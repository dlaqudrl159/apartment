package kr.co.dw.Domain;

import lombok.Data;

@Data
public class ParentRegionName {

	private String korParentName;
	private String EngParentName;
	
	public ParentRegionName(String korParentName, String engParentName) {
		this.korParentName = korParentName;
		this.EngParentName = engParentName;
	}
	
	
	
}
