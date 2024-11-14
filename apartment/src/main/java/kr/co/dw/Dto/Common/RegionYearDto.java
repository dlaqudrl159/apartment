package kr.co.dw.Dto.Common;

import kr.co.dw.Domain.ParentRegionName;
import kr.co.dw.Domain.Region;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionYearDto {

	private Region region;
	private String year;
	private String message;
	private ParentRegionName ParentRegionName;
	private final String pageNo = "1";
	private final String NUMOFROWS = "10000";
}
