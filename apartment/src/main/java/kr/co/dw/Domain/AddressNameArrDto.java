package kr.co.dw.Domain;

import java.util.List;
import java.util.stream.Collectors;

public class AddressNameArrDto {

	private List<AddressElement> list;
	
	public AddressNameArrDto(List<String> addresses) {
        this.list = processAddresses(addresses);
    }
	
	private List<AddressElement> processAddresses(List<String> addresses) {
        return addresses.stream()
                .map(AddressElement::new)
                .distinct()
                .collect(Collectors.toList());
    }
	
	public List<AddressElement> getList() {
        return list;
    }
	
	public static class AddressElement {
        private String city;
        private String district;

        public AddressElement(String fullAddress) {
            String[] parts = fullAddress.split(" ");
            if(parts[0].equals("세종특별자치시")) {
            	this.city = parts[0];
            	this.district = parts[0];
            	/*세종특별 자치시는 통합이므로 district는 똑같이함 이러면 중복제거 방법은 좋지않은데 나중에 생각해봄 다른 지역과의 분류 처리는 sql에서 if문으로 처리 지도가 서해 북해 북한 이런식으로 가면 ""빈칸이 들어가서 문제생김 
            	AddressElement 를 어떻게 처리해야할지 고민 react의 displayinfo쪽에서 처리하고 받은 데이터를 어떻게 할지 기억 안나면 서해 동해 남해 북한 쪽으로 지도를 끌어 볼것*/
            }else {
            	this.city = parts[0];
                this.district = parts[1];
            }
            
        }

        public String getCity() {
            return city;
        }

        public String getDistrict() {
            return district;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AddressElement that = (AddressElement) o;
            return city.equals(that.city) && district.equals(that.district);
        }

        @Override
        public int hashCode() {
            return 31 * city.hashCode() + district.hashCode();
        }

        @Override
        public String toString() {
            return city + " " + district;
        }
    }
	
}
