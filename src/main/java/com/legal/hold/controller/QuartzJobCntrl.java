package com.legal.hold.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.legal.hold.service.QuartzJobService;

@Component
public class QuartzJobCntrl extends QuartzJobBean {

	@Autowired
	private QuartzJobService quartzJobService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

			System.out.println("Entering inside executeInternal - " + formatter.format(date));
			quartzJobService.doStartJob();
			System.out.println("Exiting inside executeInternal - " + formatter.format(date));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
