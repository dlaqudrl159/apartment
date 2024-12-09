package kr.co.dw.Service.AutoData.Apt.OpenApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import kr.co.dw.Domain.Sigungu;
import kr.co.dw.Dto.Common.AptTransactionDto;
import kr.co.dw.Dto.Common.ProcessedAutoAptDataDto;
import kr.co.dw.Service.ParserAndConverter.ParserAndConverter;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OpenApiService {
	
	private final Logger logger = LoggerFactory.getLogger(OpenApiService.class);
	
	@Value("${api.apt.url}")
	private String API_APT_URL;

	@Value("${api.apt.service-key}")
	private String API_APT_SERVICE_KEY;
	
	private final ParserAndConverter aptDataParserService;
	
	public ProcessedAutoAptDataDto callRTMSDataSvcAptTradeDev(ProcessedAutoAptDataDto processedAutoAptDataDto) {

		int MAX_RETRIES = 3;
		int delayMs = 500;
		Element root = null;
		for (int tryCount = 1; tryCount <= MAX_RETRIES; tryCount++) {
			try {
				if(tryCount > 1) {
					Thread.sleep(delayMs);
				}
				
				StringBuilder sb = getRTMSDataSvcAptTradeDev(processedAutoAptDataDto.getSigungu(), processedAutoAptDataDto.getDealYearMonth());
				root = aptDataParserService.createNodeList(sb);

				if (aptDataParserService.isResultMsg(root)) {
					NodeList nList = root.getElementsByTagName("item");
					logger.info("code={} name={} dealyearmonth={}" , processedAutoAptDataDto.getSigungu().getCode(), processedAutoAptDataDto.getSigungu().getName(), processedAutoAptDataDto.getDealYearMonth());
					List<AptTransactionDto> list = aptDataParserService.createAptTransactionDtos(nList, processedAutoAptDataDto.getSido().getKorSido(), processedAutoAptDataDto.getSigungu().getName());
					processedAutoAptDataDto.addProcesedResData(list);
					processedAutoAptDataDto.setMessage("success");
					return processedAutoAptDataDto;
				}

			} catch (SAXException | ParserConfigurationException | IOException e) {
				logger.error("국토교통부 Api호출 중 예외 발생 재시도 {}회 시군구:{} 코드:{}", tryCount,
						processedAutoAptDataDto.getSigungu().getName(), processedAutoAptDataDto.getSigungu().getCode(), e);
				processedAutoAptDataDto.setMessage("fail");
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				logger.error("국토교통부 Api호출 중 Thread Interrupted 발생 재시도 {}회 시군구:{} 코드:{}", tryCount,
						processedAutoAptDataDto.getSigungu().getName(), processedAutoAptDataDto.getSigungu().getCode(), e);
				processedAutoAptDataDto.setMessage("fail");
				return processedAutoAptDataDto;
			} catch (Exception e) {  // 추가된 부분
	            logger.error("예상치 못한 예외 발생 재시도 {}회 시군구:{} 코드:{}", tryCount,
	            		processedAutoAptDataDto.getSigungu().getName(), processedAutoAptDataDto.getSigungu().getCode(), e);
	            processedAutoAptDataDto.setMessage("fail");
	        }

			if (tryCount == MAX_RETRIES) {
				aptDataParserService.isErrorMsg(root);
				logger.error("국토교통부 Api호출 재시도 횟수 초과 재시도 {}회 시군구:{} 코드:{} 날짜:{}", tryCount,processedAutoAptDataDto.getSigungu().getCode(), processedAutoAptDataDto.getSigungu().getName(), processedAutoAptDataDto.getDealYearMonth());
				processedAutoAptDataDto.setMessage("fail");
			}
			delayMs = delayMs * tryCount;
		}
		return processedAutoAptDataDto;
	}
	
	public StringBuilder getRTMSDataSvcAptTradeDev(Sigungu sigungu, String dealYearMonth) throws IOException {

		StringBuilder sb = null;
		StringBuilder urlBuilder = new StringBuilder(this.API_APT_URL); /* URL */

		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + this.API_APT_SERVICE_KEY); /* Service Key */
		urlBuilder.append("&" + URLEncoder.encode("LAWD_CD", "UTF-8") + "="
				+ URLEncoder.encode(sigungu.getCode(), "UTF-8")); /* 각 지역별 코드 */
		urlBuilder.append("&" + URLEncoder.encode("DEAL_YMD", "UTF-8") + "="
				+ URLEncoder.encode(dealYearMonth/* DEAL_YMD */, "UTF-8")); /* 월 단위 신고자료 */
		urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "="
				+ URLEncoder.encode("1", "UTF-8")); /* 페이지번호 */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
				+ URLEncoder.encode("10000", "UTF-8")); /* 한 페이지 결과 수 */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");

		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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
