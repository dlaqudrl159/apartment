package kr.co.dw.Service.ExternalAPI;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UrlCreateService {

	private StringBuilder baseUrl;
	
	/**
	 * @param baseUrl
	 * @param serviceKey
	 */
	public UrlCreateService(String baseUrl) {
		this.baseUrl = new StringBuilder();
		this.baseUrl.append(baseUrl);
		
	}
	
	public void addParam(String key, String value, boolean isKeyEncoding, boolean isValueEncoding) {
		String prefix = baseUrl.toString().contains("?") ? "&" : "?";
		baseUrl.append(prefix);
		
		if(isKeyEncoding) {
			baseUrl.append(URLEncoder.encode(key, StandardCharsets.UTF_8));
		} else {
			baseUrl.append(key);
		}
		
		baseUrl.append("=");
		
		if(isValueEncoding) {
			baseUrl.append(URLEncoder.encode(value, StandardCharsets.UTF_8));
		} else {
			baseUrl.append(value);
		}
	}
	
	public String build() {
		return baseUrl.toString();
	}
	
}
