package kr.co.dw.Service;

import java.util.Map;

public interface TotalCountService {

	void insert(String region);

	Map<String, String> MakeMap(String Region, String REGIONCODE, String year);

}
