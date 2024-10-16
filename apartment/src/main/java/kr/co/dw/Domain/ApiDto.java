package kr.co.dw.Domain;

import lombok.Data;

@Data
public class ApiDto {

	private String SIGUNGU;
	private String BUNGI;
	private String BONBUN;
	private String BUBUN;
	private String APARTMENTNAME;
	private String AREAFOREXCLUSIVEUSE;
	private String DEALYEARMONTH;
	private String DEALDAY;
	private String DEALAMOUNT;
	private String APARTMENTDONG;
	private String FLOOR;
	private String BUYERGBN;
	private String SELLERGBN;
	private String BUILDYEAR;
	private String ROADNAME;
	private String CANCLEDEALDAY;
	private String REQGBN;
	private String RDEALERLAWDNM;
	private String REGISTRATIONDATE;
	private String SGGCD;
	
	public ApiDto(String sIGUNGU, String bUNGI, String bONBUN, String bUBUN, String aPARTMENTNAME,
			String aREAFOREXCLUSIVEUSE, String dEALYEARMONTH, String dEALDAY, String dEALAMOUNT, String aPARTMENTDONG,
			String fLOOR, String bUYERGBN, String sELLERGBN, String bUILDYEAR, String rOADNAME, String cANCLEDEALDAY,
			String rEQGBN, String rDEALERLAWDNM, String rEGISTRATIONDATE, String rSGGCD) {
		
		SIGUNGU = sIGUNGU;
		BUNGI = bUNGI;
		BONBUN = bONBUN;
		BUBUN = bUBUN;
		APARTMENTNAME = aPARTMENTNAME;
		AREAFOREXCLUSIVEUSE = aREAFOREXCLUSIVEUSE;
		DEALYEARMONTH = dEALYEARMONTH;
		DEALDAY = dEALDAY;
		DEALAMOUNT = dEALAMOUNT;
		APARTMENTDONG = aPARTMENTDONG;
		FLOOR = fLOOR;
		BUYERGBN = bUYERGBN;
		SELLERGBN = sELLERGBN;
		BUILDYEAR = bUILDYEAR;
		ROADNAME = rOADNAME;
		CANCLEDEALDAY = cANCLEDEALDAY;
		REQGBN = rEQGBN;
		RDEALERLAWDNM = rDEALERLAWDNM;
		REGISTRATIONDATE = rEGISTRATIONDATE;
		SGGCD = rSGGCD;
	}
	
	/**
	 * 
	 */
	public ApiDto() {
		
	}
	
	
	
	
	



	
	
	
	
	
}
