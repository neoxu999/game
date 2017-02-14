/******************************************************
 * 
 * RabbitMQListener.java - com.littlepay.game.listener
 * @author Long Xu, 12Feb.,2017
******************************************************/
package com.littlepay.game.listener;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;

import com.littlepay.game.service.GameService;
import com.littlepay.game.service.ScheduleService;


/**
 * @author Neo Xu 
 *
 */
public class CustomSpringContextLoaderListener extends ContextLoaderListener {
	
    private static final Logger logger = Logger.getLogger(CustomSpringContextLoaderListener.class);
    
	/**
	 * 
	 */
	public CustomSpringContextLoaderListener() {
		logger.debug("Start Custom Spring Context Listener");
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.context.ContextLoaderListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		GameService.getInstance().startNewGame();
		ScheduleService.getInstance().registerJobs();
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.context.ContextLoaderListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
		GameService.getInstance().stopGame();
		ScheduleService.getInstance().deregisterJobs();
	}
	
	

}
