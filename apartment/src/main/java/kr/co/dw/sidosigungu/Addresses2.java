package kr.co.dw.sidosigungu;

import java.util.List;
import java.util.stream.Collectors;

public class Addresses2 {

	private List<AddressElement> addresses;
	
	public Addresses2(List<String> addresses) {
        this.addresses = processAddresses(addresses);
    }
	
	private List<AddressElement> processAddresses(List<String> addresses) {
        return addresses.stream()
                .map(AddressElement::new)
                .distinct()
                .collect(Collectors.toList());
    }
	
	public List<AddressElement> getAddresses() {
        return addresses;
    }
	
	public static class AddressElement {
        private String city;
        private String district;

        public AddressElement(String fullAddress) {
            String[] parts = fullAddress.split(" ");
            
            if(parts.length <= 1) {
            	this.city = "ERROR";
            	this.district = "ERROR";
            }else {
            	if(parts[0].equals("세종특별자치시")) {
                	this.city = parts[0];
                	this.district = parts[0];
                }else {
                	this.city = parts[0];
                    this.district = parts[1];
                }
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
