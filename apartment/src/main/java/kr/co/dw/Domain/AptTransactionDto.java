package kr.co.dw.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AptTransactionDto {

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
	
	public int getYear() {
		return Integer.parseInt(DEALYEARMONTH.substring(0, 4));
	}
	
}
