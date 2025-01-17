package kr.co.dw.Dto.Common;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRoadNamesDto {

	private String korSido;
	private String sigungu;
	private String roadName;
	private List<AptCoordsDto> aptCoordsDto;
}
