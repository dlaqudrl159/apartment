package kr.co.dw.Service.AutoData.Scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import kr.co.dw.Service.AutoData.Apt.AutoAptDataService;
import kr.co.dw.Service.AutoData.Coords.AutoCoordsDataService;
import kr.co.dw.Service.AutoData.Coords.AutoCoordsDataServiceImpl;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Scheduler {
	
	private final Logger logger = LoggerFactory.getLogger(Scheduler.class);
	private final AutoAptDataService autoAptDataService;
	private final AutoCoordsDataService autoCoordsDataService;

	@Value("${scheduling.job.cron}")
	private String scheduleCron;
	
	@Scheduled(cron = "${scheduling.job.cron}")
	public void taskallex1() {
		autoAptDataService.allAutoAptDataInsert();
		autoCoordsDataService.allCoordsInsert();
		logger.info("스케줄 완료");
	}
	
}
