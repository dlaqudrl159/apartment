package kr.co.dw.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.co.dw.Domain.NameCountDto;
import kr.co.dw.Mapper.AptMapper;
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
		
		AptUtils utils = new AptUtils();
		String region_1depth_name = "";
		String region_2depth_name = "";
		for(int i = 0 ; i < arr.size() ; i++) {
		    	
			String[] strarr = arr.get(i).split(" ");
			
			if(((!region_1depth_name.equals(strarr[0])) || (!region_2depth_name.equals(strarr[1])))) {
				
				region_1depth_name = strarr[0];
				region_2depth_name = strarr[1];
				String tableName = utils.MappingRegion(region_1depth_name);
				System.out.println(region_1depth_name);
				System.out.println(region_2depth_name);
				System.out.println(tableName);
				System.out.println(year);
				Map<String, String> map = new HashMap<>();
				map.put("tableName", tableName);
				map.put("region_1depth_name", region_1depth_name);
				map.put("region_2depth_name", region_2depth_name);
				map.put("year", year);
				list.addAll(AptMapper.get(map));
			}
		}
		
		return list;
	}
	
	
	
}
