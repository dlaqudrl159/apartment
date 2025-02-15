package kr.co.dw.Dto.Response;

import java.util.List;

import kr.co.dw.Domain.Sido;
import kr.co.dw.Dto.Common.ProcessedAutoAptDataDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutoAptDataResponse {

	private int status;
	private String message;
	private Sido sido;
	private List<ProcessedAutoAptDataDto> failProcessedAutoAptDataDtos;
	private List<ProcessedAutoAptDataDto> successProcessedAutoAptDataDtos;
	
}
