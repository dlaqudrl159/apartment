package kr.co.dw.Domain;

import java.util.List;

import lombok.Data;

@Data
public class AptTransactionResponseDto {

	private List<Integer> years;
	private List<ApiDto> transactions;
	
	public AptTransactionResponseDto(List<Integer> years, List<ApiDto> transactions) {
		this.years = years;
		this.transactions = transactions;
	}
	
	
	
}
