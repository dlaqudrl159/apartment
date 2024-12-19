package kr.co.dw.Dto.Common;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchAptDataDto {

	private String searchType;
	private String korSido;
	private String sigungu;
	private String dongORroadName;
	private String apartmentname;
	private List<AptCoordsDto> aptCoordsDto;
	
}
