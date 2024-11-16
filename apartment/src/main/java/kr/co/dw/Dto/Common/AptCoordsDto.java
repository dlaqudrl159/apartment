package kr.co.dw.Dto.Common;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"SIGUNGU", "BUNGI", "ROADNAME", "APARTMENTNAME"})
@AllArgsConstructor
@NoArgsConstructor
public class AptCoordsDto {

	private String SIGUNGU;
	private String BUNGI;
	private String APARTMENTNAME;
	private String ROADNAME;
	private String LAT; //LONGITUDE
	private String LNG; //LATITUDE
	
}
