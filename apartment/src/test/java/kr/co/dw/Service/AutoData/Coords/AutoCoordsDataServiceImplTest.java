package kr.co.dw.Service.AutoData.Coords;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import kr.co.dw.Mapper.AutoCoordsDataMapper;
import kr.co.dw.Service.AutoData.Coords.AutoCoordsDataServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AutoCoordsDataServiceImplTest {

	@Mock
	private AutoCoordsDataMapper autoCoordsDataMapper; 
	
	@InjectMocks
	private AutoCoordsDataServiceImpl autoLatLngDataService;
	
}
