package kr.co.dw.Service.AutoData.Apt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import kr.co.dw.Domain.ParentRegionName;
import kr.co.dw.Domain.Region;
import kr.co.dw.Dto.Common.AptTransactionDto;
import kr.co.dw.Dto.Common.RegionYearDto;
import kr.co.dw.Mapper.AutoAptDataMapper;

@SpringBootTest(properties = "spring.profiles.active=dev")
@TestPropertySource("classpath:application-dev.properties")
public class AutoAptDataServiceImplTest {

	@Autowired
	private AutoAptDataMapper autoAptDataMapper;
	
	@Autowired
	private AutoAptDataServiceImpl  autoAptDataServiceImpl;
	
	
	
	@Test
	@DisplayName("getRTMSDataSvcAptTradeDev 테스트")
	void getRTMSDataSvcAptTradeDev() throws IOException, ParserConfigurationException, SAXException {
		
		RegionYearDto dto = new RegionYearDto(new Region("50130", "서귀포시"), "202411", null, new ParentRegionName("제주특별자치도", "JEJU"));
		StringBuilder sb = getRTMSDataSvcAptTradeDev(dto);
		System.out.println(sb);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Document document = null;
		Element root = null;
		NodeList nList = null;
		
		
		builder = factory.newDocumentBuilder();
		document = builder.parse(new InputSource(new StringReader(sb.toString())));
		document.getDocumentElement().normalize();
		//nList = document.getElementsByTagName("item");
		root = document.getDocumentElement();
		
		
		
		String resultMsg = root.getElementsByTagName("resultMsg").item(0) == null ? "-"
				: root.getElementsByTagName("resultMsg").item(0).getTextContent();
		
		if(!resultMsg.equals("-")) {
			String resultCode = root.getElementsByTagName("resultCode").item(0) == null ? "-"
					: root.getElementsByTagName("resultCode").item(0).getTextContent();
			String resultTotalCount = root.getElementsByTagName("totalCount").item(0) == null ? "-"
					: root.getElementsByTagName("totalCount").item(0).getTextContent();
			String resultItem = root.getElementsByTagName("items").item(0) == null ? "-"
					: root.getElementsByTagName("items").item(0).getTextContent();
			
			System.out.println("resultMsg = " + resultMsg + " resultCode = " + resultCode + " resultTotalCount = " + resultTotalCount + " resultItem = " + resultItem);
			
		} else {
			String errMsg = root.getElementsByTagName("errMsg").item(0) == null ? "-"
					: root.getElementsByTagName("errMsg").item(0).getTextContent();
			String returnAuthMsg = root.getElementsByTagName("returnAuthMsg").item(0) == null ? "-"
					: root.getElementsByTagName("returnAuthMsg").item(0).getTextContent();
			String returnReasonCode = root.getElementsByTagName("returnReasonCode").item(0) == null ? "-"
					: root.getElementsByTagName("returnReasonCode").item(0).getTextContent();
			System.out.println("errMsg = " + errMsg + " returnAuthMsg = " + returnAuthMsg + " returnReasonCode = " + returnReasonCode);
		}
		
		nList = root.getElementsByTagName("item");
		System.out.println(nList.getLength());
		//System.out.println("resultMsg = " + resultMsg + " resultCode = " + resultCode + " resultTotalCount = " + resultTotalCount + " resultItem = " + resultItem);
		
		List<AptTransactionDto> list = autoAptDataServiceImpl.makeAptTransactionDto(nList, dto);
		System.out.println(list);
		
	}
	private final String Servicekey = "=f4Ed1eAJYzb%2BQ%2BtpQx4G%2BQvFuO0ZJJMZIInJGo%2FpG889YetxgnnGE9umfvGSe8TPyZ88bAUWw%2Bn7ETYTooeF5A%3D%3D";
	public StringBuilder getRTMSDataSvcAptTradeDev(RegionYearDto regionYearDto) throws IOException {
		// TODO Auto-generated method stub
		StringBuilder sb = null;
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/RTMSDataSvcAptTradeDev/getRTMSDataSvcAptTradeDev"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + Servicekey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("LAWD_CD","UTF-8") + "=" + URLEncoder.encode(regionYearDto.getRegion().getCode(), "UTF-8")); /*각 지역별 코드*/
        urlBuilder.append("&" + URLEncoder.encode("DEAL_YMD","UTF-8") + "=" + URLEncoder.encode(regionYearDto.getYear()/*DEAL_YMD*/, "UTF-8")); /*월 단위 신고자료*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10000", "UTF-8")); /*한 페이지 결과 수*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
		
		return sb;
	}
}
