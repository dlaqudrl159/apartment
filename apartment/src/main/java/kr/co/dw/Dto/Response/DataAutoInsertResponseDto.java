package kr.co.dw.Dto.Response;

import java.time.LocalDateTime;
import java.util.List;

import kr.co.dw.Dto.Common.RegionYearDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataAutoInsertResponseDto {

	private int status;
	private String message;
	private Integer totalCount;
	private LocalDateTime processedAt;
	private List<RegionYearDto> processDetails;
	
}
