package kr.co.dw.Dto.Response;

import java.util.List;

import kr.co.dw.Domain.Sido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutoAptDataRes {

	private int status;
	private String message;
	private Sido sido;
	private List<ProcessedRes> failprocessed;
	private List<ProcessedRes> successprocessed;
	
}
