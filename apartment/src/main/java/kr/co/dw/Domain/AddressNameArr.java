package kr.co.dw.Domain;

import java.util.List;
import java.util.stream.Collectors;

public class AddressNameArr {

	private List<AddressElement> list;

	public AddressNameArr(List<String> addresses) {
		this.list = processAddresses(addresses);
	}

	private List<AddressElement> processAddresses(List<String> addresses) {
		return addresses.stream().map(AddressElement::new).distinct().collect(Collectors.toList());
	}

	public List<AddressElement> getList() {
		return list;
	}

	public static class AddressElement {
		private String city;
		private String district;

		public AddressElement(String fullAddress) {
			String[] parts = fullAddress.split(" ");
			if (parts.length <= 1) {
				if (parts[0].equals("세종특별자치시")) {
					this.city = parts[0];
					this.district = parts[0];
				} else {
					this.city = "ERROR";
					this.district = "ERROR";
				}
			} else {
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
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
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
