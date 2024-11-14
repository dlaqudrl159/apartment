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

	private String status;
	private RegionYearDto regionYearDto;
	private String message;
	private Integer totalCount;
	private LocalDateTime processedAt;
	private List<DataAutoInsertResponseDto> processDetails;
	
	/**
	 * @param status
	 * @param regionYearDto
	 * @param message
	 * @param totalCount
	 * @param processedAt
	 */
	public DataAutoInsertResponseDto(String status, RegionYearDto regionYearDto, String message, Integer totalCount,
			LocalDateTime processedAt) {
		super();
		this.status = status;
		this.regionYearDto = regionYearDto;
		this.message = message;
		this.totalCount = totalCount;
		this.processedAt = processedAt;
	}
	
	
	
}
