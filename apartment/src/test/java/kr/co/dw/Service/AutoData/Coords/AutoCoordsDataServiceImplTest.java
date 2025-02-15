package kr.co.dw.Service.AutoData.Coords;


import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.co.dw.Mapper.AutoCoordsDataMapper;

@ExtendWith(MockitoExtension.class)
public class AutoCoordsDataServiceImplTest {

	@Mock
	private AutoCoordsDataMapper autoCoordsDataMapper; 
	
	@InjectMocks
	private AutoCoordsDataServiceImpl autoLatLngDataService;
	
}
