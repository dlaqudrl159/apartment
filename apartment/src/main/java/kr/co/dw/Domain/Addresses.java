package kr.co.dw.Domain;

import java.util.List;
import java.util.stream.Collectors;

import kr.co.dw.Exception.CustomException;
import kr.co.dw.Exception.ErrorCode.ErrorCode;
import lombok.Data;

public class Addresses {

	private List<Address> list;

	public Addresses(List<String> addresses) {
		this.list = processAddresses(addresses);
	}

	private List<Address> processAddresses(List<String> addresses) {
		return addresses.stream().map(Address::new).distinct().collect(Collectors.toList());
	}

	public List<Address> getList() {
		return list;
	}

	@Data
	public static class Address {
		private String sido;
		private String sigungu;

		public Address(String fullAddress) {
			String[] parts = fullAddress.split(" ");
			if (parts.length <= 1) {
				if (parts[0].equals("세종특별자치시")) {
					this.sido = parts[0];
					this.sigungu = parts[0];
				} else {
					this.sido = "ERROR";
					this.sigungu = "ERROR";
				}
			} else {
				this.sido = parts[0];
				this.sigungu = parts[1];
			}
		}
	}
}
