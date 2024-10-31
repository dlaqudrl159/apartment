package kr.co.dw.Domain;

import java.util.List;

import lombok.Data;

@Data
public class AptTransactionResponseDtolist {

	private List<Integer> years;
	private List<ApiDto> ApiDtoList;
	private NameCountDto NameCountDto;
	
	public AptTransactionResponseDtolist(List<Integer> years, List<ApiDto> apiDtoList,
			NameCountDto nameCountDto) {
		this.years = years;
		this.ApiDtoList = apiDtoList;
		this.NameCountDto = nameCountDto;
	}
	
    	
	
	
	
	
}
