package kr.co.dw.Domain;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AptLatLngDto {

	private String SIGUNGU;
	private String BUNGI;
	private String APARTMENTNAME;
	private String ROADNAME;
	private String LAT;
	private String LNG;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AptLatLngDto other = (AptLatLngDto) obj;
		return Objects.equals(APARTMENTNAME, other.APARTMENTNAME) && Objects.equals(BUNGI, other.BUNGI)
				&& Objects.equals(ROADNAME, other.ROADNAME) && Objects.equals(SIGUNGU, other.SIGUNGU);
	}
	@Override
	public int hashCode() {
		return Objects.hash(APARTMENTNAME, BUNGI, ROADNAME, SIGUNGU);
	}
	
	
	
	
}
