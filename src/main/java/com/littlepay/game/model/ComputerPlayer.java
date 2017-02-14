/******************************************************
 * Copyright (c) Quant Pty. Ltd. [2015-2017]
 * Copyright subsists in this code. 
 * Except as permitted under the Copyright Act 1968 (Cth), no part of this 
 * work may be reproduced, published or adapted in any way, without the 
 * specific written permission of Quant Pty. Ltd. All rights reserved.
 *  
 * 
 * ComputerPlayer.java - com.littlepay.game.model
 * @author Long Xu, 7Feb.,2017
******************************************************/
package com.littlepay.game.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Neo Xu 
 *
 */
public class ComputerPlayer extends AbstractPlayer implements IComputerPlayer {
	
	private static final Logger logger = LoggerFactory.getLogger(ComputerPlayer.class);
	/**
	 * @param name
	 * @param password
	 */
	public ComputerPlayer(String name, String password, int point) {
		 super(name, password, point, -1, 999);
	}
	
	/**
	 * 
	 */
	public ComputerPlayer(String name, String password, int point, long lastGuessTime, int lastGuessNumber) {
		super(name, password, point, lastGuessTime, lastGuessNumber);
	}
	
	/* (non-Javadoc)
	 * @see com.littlepay.game.model.IPlayer#getPlayerType()
	 */
	@Override
	public PlayerType getPlayerType() {
		return PlayerType.COMPUTER;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String palyerJson = "{\"name\":\""+getName()+"\","
				+ "\"password\":\""+getPassword()+"\","
				+ "\"point\":"+getPoint()+","
				+ "\"lastGuessTime\":"+getLastGuessTime()+","
			    + "\"lastGuessNumber\":"+getLastGuessNumber()+","
				+ "\"playerType\":\""+getPlayerType().getName()+"\"}";
		logger.debug(palyerJson);
		return palyerJson;
	}
}
