package kr.co.dw.Service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kr.co.dw.Domain.ApiDto;
import kr.co.dw.Domain.NameCountDto;
import kr.co.dw.Mapper.AptMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AptServiceImpl implements AptService{

	private final AptMapper AptMapper;
	
	@Override
	public ApiDto get() {
		
		ApiDto ApiDto = new ApiDto();
		
		ApiDto = AptMapper.get();
		
		return ApiDto;
		
	}

	@Override
	public List<NameCountDto> get2() {
		// TODO Auto-generated method stub
		NameCountDto NameCountDto = new NameCountDto();
		List<NameCountDto> list = AptMapper.get2();
		return list;
	}
	
}
