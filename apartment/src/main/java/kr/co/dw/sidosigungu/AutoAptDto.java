package kr.co.dw.sidosigungu;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutoAptDto {
	
	private Sido2 siDo;
	private List<Sigungu2> sigungus;
	
}
