package kr.co.dw.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import kr.co.dw.Domain.NameCountDto;
import kr.co.dw.Domain.Region;
import kr.co.dw.Domain.RegionManager;
import kr.co.dw.Mapper.DataMapper;
import kr.co.dw.Utils.DataUtils;
import lombok.RequiredArgsConstructor;

@Service
@Primary
@RequiredArgsConstructor
public class DataServiceImpl2 implements DataService{

	private final DataMapper DataMapper;
	
	private final Integer DELETEYEAR = 12;
	private final String PAGENO = "1";
	private final String NUMOFROWS = "10000";
	
	@Override
	public void LatLngInsert(String tableName)
			throws MalformedURLException, IOException, ParseException, InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<NameCountDto> getLatLng(List<NameCountDto> list, String tableName)
			throws IOException, ParseException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject geocodersearchaddress(String searchAddr, String searchType) throws IOException, ParseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getparcel(NameCountDto NameCountDto, String tableName) throws IOException, ParseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getroadname(NameCountDto NameCountDto, String tableName) throws IOException, ParseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void AutoDataInsert(String RegionName) {
		// TODO Auto-generated method stub
		
		List<Region> list = RegionManager.getInstance().getListRegion(RegionName);
		
		String DbYear = makeDealYearMonth(DELETEYEAR);
		
		//DataMapper.deleteRegionYear(RegionName, DbYear);
		
		list.forEach(Region -> loopRegion(Region));
		
	}

	private void loopRegion(Region Region) {
		for(int j = DELETEYEAR ; j >= 0 ; j--) {
			String DealYmd = makeDealYearMonth(j);
			try {
				StringBuilder sb = getRTMSDataSvcAptTradeDev(Region,DealYmd);
				
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(new InputSource(new StringReader(sb.toString())));
				
				document.getDocumentElement().normalize();
				NodeList nList = document.getElementsByTagName("item");
				Element root = document.getDocumentElement();
				String resultMsg = root.getElementsByTagName("resultMsg").item(0) == null ? "-"
						: root.getElementsByTagName("resultMsg").item(0).getTextContent();
				String resultCode = root.getElementsByTagName("resultCode").item(0) == null ? "-"
						: root.getElementsByTagName("resultCode").item(0).getTextContent();
				String resultTotalCount = root.getElementsByTagName("totalCount").item(0) == null ? "-"
						: root.getElementsByTagName("totalCount").item(0).getTextContent();
				if (!resultCode.equals("000")) {
					throw new RuntimeException();
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String makeDealYearMonth(int j) {

		Calendar cal = Calendar.getInstance();
		String year = String.valueOf(cal.get(Calendar.YEAR));
		int month = (cal.get(Calendar.MONTH) + 1);
		String strMonth = String.format("%02d", month);
		String lastMonth = year + strMonth;
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMM");
		Date dt = null;
		try {
			dt = dtFormat.parse(lastMonth);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal.setTime(dt);
		cal.add(Calendar.MONTH, -j);
		return dtFormat.format(cal.getTime());
	}
	
	public StringBuilder getRTMSDataSvcAptTradeDev(Region Region, String DealYmd) throws IOException {
		
		StringBuilder sb = null;
		
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/RTMSDataSvcAptTradeDev/getRTMSDataSvcAptTradeDev"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=f4Ed1eAJYzb%2BQ%2BtpQx4G%2BQvFuO0ZJJMZIInJGo%2FpG889YetxgnnGE9umfvGSe8TPyZ88bAUWw%2Bn7ETYTooeF5A%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("LAWD_CD","UTF-8") + "=" + URLEncoder.encode(Region.getCode(), "UTF-8")); /*각 지역별 코드*/
        urlBuilder.append("&" + URLEncoder.encode("DEAL_YMD","UTF-8") + "=" + URLEncoder.encode(DealYmd/*DEAL_YMD*/, "UTF-8")); /*월 단위 신고자료*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(PAGENO, "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(NUMOFROWS, "UTF-8")); /*한 페이지 결과 수*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            System.out.println("에러");
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
