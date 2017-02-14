/******************************************************
 * Copyright (c) Quant Pty. Ltd. [2015-2017]
 * Copyright subsists in this code. 
 * Except as permitted under the Copyright Act 1968 (Cth), no part of this 
 * work may be reproduced, published or adapted in any way, without the 
 * specific written permission of Quant Pty. Ltd. All rights reserved.
 *  
 * 
 * IPlayer.java - com.littlepay.game.model
 * @author Long Xu, 7Feb.,2017
******************************************************/
package com.littlepay.game.model;

/**
 * @author Neo Xu 
 *
 */
public interface IPlayer {
	
	/**
	 * @return
	 */
	String getName();
	
	/**
	 * @return
	 */
	String getPassword();
	
	/**
	 * @return
	 */
	int getPoint();
	
	/**
	 * @return
	 */
	double guesssNumber();
	
	/**
	 * @return
	 */
	long getLastGuessTime();
	
	/**
	 * @param time
	 */
	void setLastGuessTime(long time);
	
	/**
	 * @return
	 */
	int getLastGuessNumber();
	
	/**
	 * @param number
	 */
	void setLastGuessNumber(int number);
	
	/**
	 * @return
	 */
	PlayerType getPlayerType();

	/**
	 * @param number
	 */
	void updatePoint(int number);
}
