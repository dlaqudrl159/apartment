package kr.co.dw.Service;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import kr.co.dw.Mapper.TotalCountMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TotalCountSeriviceImpl implements TotalCountService{
	
	private final TotalCountMapper mapper;
	
	private String[] EnglishRegion = {"SEOUL","BUSAN","DAEGU","INCHEON","GWANGJU","DAEJEON","ULSAN","SEJONG","GYEONGGIDO","GANGWONDO",
			"CHUNGCHEONGBUKDO","CHUNGCHEONGNAMDO","JEOLLABUKDO","JEOLLANAMDO","GYEONGSANGBUKDO","GYEONGSANGNAMDO","JEJU"};
	
	private String[][] RegionCode = {
			/*서울*/
			{ "11110", "11140", "11170", "11200", "11215", "11230", "11260", "11290", "11305", "11320", "11350",
					"11380", "11410", "11440", "11470", "11500", "11530", "11545", "11560", "11590", "11620",
					"11650", "11680", "11710", "11740" },
			/*부산*/
			{ "26110", "26140", "26170", "26200", "26230", "26260", "26290", "26320", "26350", "26380", "26410",
					"26440", "26470", "26500", "26530", "26710" },
			/*대구*/
			{ "27110", "27140", "27170", "27200", "27230", "27260", "27290", "27710", "27720"},
			
			/*인천*/
			{ "28110", "28140", "28177", "28185", "28200", "28237", "28245", "28260", "28710", "28720" },
			/*광주*/
			{ "29110", "29140", "29155", "29170", "29200" },
			/*대전*/
			{ "30110", "30140", "30170", "30200", "30230" },
			/*울산*/
			{ "31110", "31140", "31170", "31200", "31710" },
			/*세종특별자치시*/
			{ "36110" },
			/*경기도*/
			{ "41111", "41113", "41115", "41117", "41131", 
			  "41133", "41135", "41150", "41171", "41173" ,
			  "41194", "41196", "41192", "41210", "41220", 
			  "41250", "41271", "41273", "41281", "41285", 
			  "41287", "41290", "41310","41360", "41370", 
			  "41390", "41410", "41430", "41450", "41461", 
			  "41463", "41465", "41480", "41500", "41550", 
			  "41570", "41590", "41610", "41630", "41650", 
			  "41670", "41800", "41820", "41830" },
			
			/*강원도*/
			{ "51110", "51130", "51150", "51170", "51190", "51210", "51230", "51720", "51730", "51750", "51760",
					"51770", "51780", "51790", "51800", "51810", "51820", "51830" },
			/*충청북도*/
			{ "43111", "43112", "43113", "43114", "43130", "43150", "43720", "43730", "43740", "43745",
					"43750", "43760", "43770", "43800" },
			/*충청남도*/
			{ "44131", "44133", "44150", "44180", "44200", "44210", "44230", "44250", "44270", "44710", "44760",
					"44770" ,"44790", "44800", "44810", "44825" },
			/*전라북도*/
			{ "52111", "52113", "52130", "52140", "52180", "52190", "52210", "52710", "52720", "52730", "52740",
					"52750", "52770", "52790", "52800" },
			/*전라남도*/
			{ "46110", "46130", "46150", "46170", "46230", "46710", "46720", "46730", "46770", "46780", "46790",
					"46800", "46810", "46820", "46830", "46840", "46860", "46870", "46880", "46890", "46900",
					"46910" },
			/*경상북도*/
			{ "47111", "47113", "47130", "47150", "47170", "47190", "47210", "47230", "47250", "47280", "47290",
					"47730", "47750", "47760", "47770", "47820", "47830", "47840", "47850", "47900",
					"47920", "47930", "47940" },
			/*경상남도*/
			{ "48121", "48123", "48125", "48127", "48129", "48170", "48220", "48240", "48250", "48270", "48310",
					"48330", "48720", "48730", "48740", "48820", "48840", "48850", "48860", "48870", "48880",
					"48890" },
			/*제주도*/
			{ "50110", "50130" } };
	
	@Override
	public void insert(String Region) {
		
		Calendar today = Calendar.getInstance();
		int year =  today.get(Calendar.YEAR);
		String Stryear = String.valueOf(year);
		int index = Arrays.asList(EnglishRegion).indexOf(Region);
		
		for(int j = 0 ; j < RegionCode[index].length ; j++) {
			String REGIONCODE = RegionCode[index][j];
			MakeMap(Region, REGIONCODE, Stryear);
			mapper.insert(MakeMap(Region, REGIONCODE, Stryear));
		}
	}
	
	

	@Override
	public Map<String, String> MakeMap(String Region, String REGIONCODE, String year) {
		
		Map<String, String> map = new HashMap<>();
		map.put("REGION", Region);
		map.put("REGIONCODE", REGIONCODE);
		map.put("YEAR", year);
		map.put("January", "0");
		map.put("February", "0");
		map.put("March", "0");
		map.put("April", "0");
		map.put("May", "0");
		map.put("June", "0");
		map.put("July", "0");
		map.put("August", "0");
		map.put("September", "0");
		map.put("October", "0");
		map.put("November", "0");
		map.put("December", "0");
		
		return map;
	}
	
}
