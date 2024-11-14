package kr.co.dw.Service.AutoData.Scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import kr.co.dw.Service.AutoData.Apt.AutoAptDataService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Scheduler {

	private final AutoAptDataService autoAptDataService;
	
	@Scheduled(cron = "0 57 21 * * *")
	public void taskallex1() {
		
		autoAptDataService.autoDataInsert(null);
	}
	
}
