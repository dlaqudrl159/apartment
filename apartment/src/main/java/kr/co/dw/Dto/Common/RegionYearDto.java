package kr.co.dw.Dto.Common;

import kr.co.dw.Domain.Sido;
import kr.co.dw.Domain.Sigungu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionYearDto {

	private Sigungu region;
	private String year;
	private String message;
	private Sido parentRegionName;
	private final String PAGE_NO = "1";
	private final String NUM_OF_ROWS = "10000";
}
