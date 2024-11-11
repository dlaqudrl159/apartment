package kr.co.dw.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import kr.co.dw.Controller.DataController;

@ExtendWith(MockitoExtension.class)
public class DataServiceImplTest {

	@InjectMocks
	private DataController DataController;
	
	@Mock // 가짜 객체를 만들어 반환해주는 어노테이션 
	private DataService DataService;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	public void init() {
		System.out.println("DataControlle 초기화");
		mockMvc = MockMvcBuilders.standaloneSetup(DataController).build();
	}
	
	
	
}
