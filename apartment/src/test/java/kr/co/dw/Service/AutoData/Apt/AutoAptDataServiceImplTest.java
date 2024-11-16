package kr.co.dw.Service.AutoData.Apt;

import java.io.IOException;
import java.io.StringReader;
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
		StringBuilder sb = autoAptDataServiceImpl.getRTMSDataSvcAptTradeDev(dto);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Document document = null;
		Element root = null;
		NodeList nList = null;
		
		
		builder = factory.newDocumentBuilder();
		document = builder.parse(new InputSource(new StringReader(sb.toString())));
		document.getDocumentElement().normalize();
		nList = document.getElementsByTagName("item");
		root = document.getDocumentElement();
		
		String resultMsg = root.getElementsByTagName("resultMsg").item(0) == null ? "-"
				: root.getElementsByTagName("resultMsg").item(0).getTextContent();
		String resultCode = root.getElementsByTagName("resultCode").item(0) == null ? "-"
				: root.getElementsByTagName("resultCode").item(0).getTextContent();
		String resultTotalCount = root.getElementsByTagName("totalCount").item(0) == null ? "-"
				: root.getElementsByTagName("totalCount").item(0).getTextContent();
		String resultItem = root.getElementsByTagName("items").item(0) == null ? "-"
				: root.getElementsByTagName("items").item(0).getTextContent();
		
		System.out.println("resultMsg = " + resultMsg + " resultCode = " + resultCode + " resultTotalCount = " + resultTotalCount + " resultItem = " + resultItem);
		
		List<AptTransactionDto> list = autoAptDataServiceImpl.makeAptTransactionDto(nList, dto);
		
	}
	
}
