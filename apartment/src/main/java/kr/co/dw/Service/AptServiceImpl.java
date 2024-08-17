package kr.co.dw.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import kr.co.dw.Domain.NameCountDto;
import kr.co.dw.Mapper.AptMapper;
import kr.co.dw.Mapper.DataMapper;
import kr.co.dw.Utils.AptUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AptServiceImpl implements AptService{

	private final AptMapper AptMapper;

	@Override
	public List<NameCountDto> get(List<String> arr, String year) {
		// TODO Auto-generated method stub
		
		arr = arr.stream().distinct().collect(Collectors.toList());	
		
		Collections.sort(arr);
		
		List<NameCountDto> list = new ArrayList<>();
		
		List<String> list2 = new ArrayList<>();
		
		for(int i = 0 ; i < arr.size() ; i++) {
			String[] temp = arr.get(i).split(" ");
			if(temp[0].equals("세종특별자치시")) {
				list2.add(temp[0]);
			}else {
				list2.add(temp[0] + " " + temp[1]);
			}
			
		}
		list2 = list2.stream().distinct().collect(Collectors.toList());
		
		AptUtils utils = new AptUtils();
		
		
		for(int i = 0 ; i < list2.size() ; i++) {
		    	
				String region_1depth_name = "";
				String region_2depth_name = "";
			
				if(list2.get(i).equals("세종특별자치시")) {
					region_1depth_name = list2.get(i);
					region_2depth_name = list2.get(i);
				}else {
					String[] strarr = list2.get(i).split(" ");
					region_1depth_name = strarr[0];
					region_2depth_name = strarr[1];
				}
				
				String tableName = utils.MappingRegion(region_1depth_name);
									
				Map<String, String> map = new HashMap<>();
				map.put("tableName", tableName);
				map.put("region_1depth_name", region_1depth_name);
				map.put("region_2depth_name", region_2depth_name);
				map.put("year", year);
				
				list.addAll(AptMapper.get(map));
			
		}
		
		return list;
	}
}
