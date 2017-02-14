/******************************************************
 * Copyright (c) Quant Pty. Ltd. [2015-2017]
 * Copyright subsists in this code. 
 * Except as permitted under the Copyright Act 1968 (Cth), no part of this 
 * work may be reproduced, published or adapted in any way, without the 
 * specific written permission of Quant Pty. Ltd. All rights reserved.
 *  
 * 
 * RabbitMQFactory.java - com.datacenter.factory
 * @author Long Xu, 2Feb.,2017
******************************************************/
package com.littlepay.game.service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author Neo Xu 
 *
 */
public class RabbitMQFactory {
  
	private static final Logger logger = Logger.getLogger(RabbitMQFactory.class);
	
	private static final String EXCHANGE_NAME = "GAME_MESSAGE";
	
	private static RabbitMQFactory instance;

	private Connection connection;
	
	/**
	 * stop create new instance
	 */
	private RabbitMQFactory() {
		 
	}
	
	/**
	 * @return
	 */
	public static RabbitMQFactory getInstance() {
        if (instance == null) {
            instance = new RabbitMQFactory();
        }
        return instance;
    }
	
	/**
	 * @return
	 */
	public Connection getConnection() {
		if (connection == null) {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");

			try {
				connection = factory.newConnection();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}
	
	/**
	 * @return
	 */
	public Channel getChanel() {
		try {
			return getConnection().createChannel();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * @param message
	 */
	public void pushGameMessage(String message) {
		Channel channel = getChanel();
		if (StringUtils.isNotBlank(message) && channel != null) {
			try {
				channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
				channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
			} catch (IOException e) {
				logger.debug("Cannot publish message.");
				e.printStackTrace();
			}
		}  
	}
	
 
	
	
	
	

}
