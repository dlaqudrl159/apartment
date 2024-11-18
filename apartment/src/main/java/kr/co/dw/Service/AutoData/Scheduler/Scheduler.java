package kr.co.dw.Service.AutoData.Scheduler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import kr.co.dw.Service.AutoData.Apt.AutoAptDataService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Scheduler {

	private final AutoAptDataService autoAptDataService;

	@Value("${scheduling.job.cron}")
	private String scheduleCron;
	
	@Scheduled(cron = "${scheduling.job.cron}")
	public void taskallex1() {
		System.out.println(scheduleCron);
		autoAptDataService.allAutoAptDataInsert();
	}
	
}
