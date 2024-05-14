package com.legal.hold;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.legal.hold.controller.QuartzJobCntrl;

@Configuration
public class QuartzConfig {

	@Bean
	public JobDetail runJob() {
		return JobBuilder.newJob(QuartzJobCntrl.class).withIdentity("legalhold").storeDurably().build();
	}

	@Bean
	public Trigger aTestTrigger(JobDetail jobADetails) {

		return TriggerBuilder.newTrigger().forJob(jobADetails).withIdentity("legalholdJobTrigger")
				.withSchedule(CronScheduleBuilder.cronSchedule("0 0/30 7,9,12,14 * * ?")).build();

		// return
		// TriggerBuilder.newTrigger().forJob(jobADetails).withIdentity("legalholdJobTrigger")
		// .withSchedule(CronScheduleBuilder.cronSchedule("0 */5 * * * ?")).build();
	}

}
