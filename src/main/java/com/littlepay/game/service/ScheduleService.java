/******************************************************
 * Copyright (c) Quant Pty. Ltd. [2015-2017]
 * Copyright subsists in this code. 
 * Except as permitted under the Copyright Act 1968 (Cth), no part of this 
 * work may be reproduced, published or adapted in any way, without the 
 * specific written permission of Quant Pty. Ltd. All rights reserved.
 *  
 * 
 * ScheduleService.java - com.littlepay.game.service
 * @author Long Xu, 12Feb.,2017
******************************************************/
package com.littlepay.game.service;

import org.apache.log4j.Logger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.littlepay.game.schedule.RobotGuessJob;


/**
 * @author Neo Xu 
 *
 */
public class ScheduleService {

	private static final Logger logger = Logger.getLogger(ScheduleService.class);
	
	/**
	 * @author Neo Xu 
	 *
	 */
	private static class Nested {
		private static final ScheduleService INSTANCE = new ScheduleService();
	}
	
	
	/**  
	 * @return The only instance 
	 */
	public static ScheduleService getInstance() {
		return Nested.INSTANCE;
	}
	private Scheduler scheduler;
	
	/**
	 * 
	 */
	/* package priate only allow one instance */ ScheduleService() {

	}
	
	public void addJob(JobDetail job, Trigger trigger){
		try {
			getScheduler().scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * stop scheduler
	 */
	public void deregisterJobs() {
		try {
			this.scheduler.shutdown(true);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @return the schedulerFactory
	 */
	private Scheduler getScheduler() {
		if (this.scheduler == null) {
			try {
				this.scheduler = StdSchedulerFactory.getDefaultScheduler();
			} catch (SchedulerException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
		
		try {
			if (!this.scheduler.isStarted() || this.scheduler.isShutdown()) {
				this.scheduler.start();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		return this.scheduler;
	}
	
	/**
	 * 
	 */
	public void registerJobs() {
		JobDetail robotGuessJob = JobBuilder.newJob(RobotGuessJob.class).
				withIdentity("MarketDataJob", "MarketJob").build();
		Trigger robotGuessTrigger = TriggerBuilder.newTrigger()
			      .withIdentity("robotGuessJob", "robotGuessTrigger")
			      .startNow()
			      .withSchedule(SimpleScheduleBuilder.simpleSchedule()
			      .withIntervalInSeconds(10)
			      .repeatForever())
			      .build();
		addJob(robotGuessJob, robotGuessTrigger);
		logger.debug("Start Scheduler Job "+ RobotGuessJob.class.getName());
	}

}
