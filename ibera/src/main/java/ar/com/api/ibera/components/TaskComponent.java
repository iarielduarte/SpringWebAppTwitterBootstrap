package ar.com.api.ibera.components;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component("taskComponent")
public class TaskComponent {
	
	private static final Log LOGGER = LogFactory.getLog(TaskComponent.class);
	
	// [* "0 0 * * * *" = the top of every hour of every day.]
	// [* "*/10 * * * * *" = every ten seconds.]
	// [* "0 0 8-10 * * *" = 8, 9 and 10 o'clock of every day.]
	// [* "0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30 and 10 o'clock every day.]
	// [* "0 0 9-17 * * MON-FRI" = on the hour nine-to-five weekdays]
	// [* "0 0 0 25 12 ?" = every Christmas Day at midnight]
	 
	@Scheduled(fixedDelay = 5000)
	public void doTask(){
		LOGGER.info("I'm firing the time every 5 millisecond -->" + new Date());
	}
	
	@Scheduled(cron="0 0 6 * * *", zone = "CST")
	public void doScheduledWork() {
		LOGGER.info("I'm firing the time at 6 am every day -->" + new Date());
	}

}
