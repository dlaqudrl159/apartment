package kr.co.dw.Dto.Common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.dw.Exception.CustomException;
import kr.co.dw.Exception.ErrorCode.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AptTransactionDto {

	
	private static final Logger logger = LoggerFactory.getLogger(AptTransactionDto.class);

	
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
		if(DEALYEARMONTH == null) {
			logger.error("DEALYEARMONTH의 년도 추출 실패 거래년월 데이터 NULL DEALYEARMONTH={}", DEALYEARMONTH);
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
		if(DEALYEARMONTH.length() < 4) {
			logger.error("DEALYEARMONTH의 년도 추출 실패 거래년월 4자리 미만 DEALYEARMONTH={}", DEALYEARMONTH);
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
		try {
			int dealyear = Integer.parseInt(DEALYEARMONTH.substring(0, 4));
			return dealyear;
		} catch (Exception e) {
			logger.error("DEALYEARMONTH의 년도 추출 실패 거래년월 형식 변환중 에러 DEALYEARMONTH={}", DEALYEARMONTH);
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
	}
	
}
