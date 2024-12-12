package kr.co.dw.Dto.Common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchAptDataDto {

	private String Sido;
	private String Sigungu;
	private String DongOrRoadName;
	private String ApartmentName;
}
