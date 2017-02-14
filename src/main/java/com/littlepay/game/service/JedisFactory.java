/******************************************************
 * Copyright (c) Quant Pty. Ltd. [2015-2017]
 * Copyright subsists in this code. 
 * Except as permitted under the Copyright Act 1968 (Cth), no part of this 
 * work may be reproduced, published or adapted in any way, without the 
 * specific written permission of Quant Pty. Ltd. All rights reserved.
 *  
 * 
 * JedisFactory.java - com.littlepay.game.service
 * @author Long Xu, 12Feb.,2017
******************************************************/
package com.littlepay.game.service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author Neo Xu 
 *
 */
public class JedisFactory {

	// Redis server IP
	private static String ADDR = "localhost";

	// Redis port
	private static int PORT = 6379;

//	// password
//	private static String AUTH = "admin";
//
//	// Connection max size default 8；
//	// if set -1，not limitation； If the pool allocate maxActive jedis instance，the pool status exhausted。
//	private static int MAX_ACTIVE = 1024;

	// pool idle jedis instance，default 8.
	private static int MAX_IDLE = 200;

	private static int TIMEOUT = 10000;
	
	private static boolean TEST_ON_BORROW = true;

	private static JedisPool jedisPool = null;
	
	private static JedisFactory instance;

	/**
	 * 
	 */
	public JedisFactory() {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
//			config.setMaxActive(MAX_ACTIVE);
			config.setMaxIdle(MAX_IDLE);
//			config.setMaxWait(MAX_WAIT);
			config.setTestOnBorrow(TEST_ON_BORROW);
			jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return
	 */
	public static JedisFactory getInstance() {
        if (instance == null) {
            instance = new JedisFactory();
        }
        return instance;
    }
	
	/**
	 * @return
	 */
	public synchronized Jedis getJedis() {
		try {
			if (jedisPool != null) {
				return jedisPool.getResource();
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
