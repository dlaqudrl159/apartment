package kr.co.dw.Auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponse {

	private String token;
	private String tokenType = "Bearer";
	
	
	
}
