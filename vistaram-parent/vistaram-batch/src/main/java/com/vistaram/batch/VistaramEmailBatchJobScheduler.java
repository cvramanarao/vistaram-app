package com.vistaram.batch;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class VistaramEmailBatchJobScheduler {
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job vistaramDataExtractorJob;
	
	
	public void runSomeJob(){
		
	}
	
	@Scheduled(cron = "0 0 5 * * *")
	public void runJob() {
		System.out.println("run job()-->");
		try {
			String dateParam = new Date().toString();
			JobParameters param = new JobParametersBuilder().addString("date",
					dateParam).toJobParameters();
			System.out.println("DateParam: " + dateParam);
			JobExecution execution = jobLauncher.run(vistaramDataExtractorJob, param);
			System.out.println("Exit Status : " + execution.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("<-- run job()");
	}


}
