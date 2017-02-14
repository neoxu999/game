/******************************************************
 * Copyright (c) Quant Pty. Ltd. [2015-2017]
 * Copyright subsists in this code. 
 * Except as permitted under the Copyright Act 1968 (Cth), no part of this 
 * work may be reproduced, published or adapted in any way, without the 
 * specific written permission of Quant Pty. Ltd. All rights reserved.
 *  
 * 
 * GameService.java - com.littlepay.game.service
 * @author Long Xu, 11Feb.,2017
******************************************************/
package com.littlepay.game.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.littlepay.game.model.NumberStatus;
import com.littlepay.game.util.GameUtil;

/**
 * @author Neo Xu 
 *
 */
public class GameService {

	private static GameService instance;
	
	/**
	 * @return
	 */
	public static GameService getInstance() {
		if (instance == null) {
			instance = new GameService();
		}
		return instance;
	}
	
	/**
	 * current game number for guessing. If the number is -1, a game hasn't started yet
	 */
	private int currentNumber = -1;
	/**
	 * indicates if a game is in playing status 
	 */
	private boolean isPlayGame = false;
	/**
	 * prevent create new instance
	 */
	/*package private */ GameService() {
		  
	}
	
	/**
	 * @return the currentNumber
	 */
	public int getCurrentNumber() {
		return this.currentNumber;
	}
	
	/**
	 * @return the isPlayGame
	 */
	public boolean isPlayGame() {
		return this.isPlayGame;
	}
	
	/**
	 * reset a new game
	 */
	public void resetGame() {
		this.currentNumber = GameUtil.randomNumber(100);
		//notify this name is finished, start a new game
	}
	
	/**
	 * start a game
	 */
	public void startNewGame() {
		this.currentNumber = GameUtil.randomNumber(100);
		this.isPlayGame = true;
	}
	
	/**
	 * 
	 */
	public void stopGame() {
		this.currentNumber = -1;
		this.isPlayGame = false;
	}

	
	/**
	 * @param number
	 * @return
	 */
	public NumberStatus checkNumber(String number) {
		if (StringUtils.isNotBlank(number)) {
			if(NumberUtils.isNumber(number)) {
				int guessNumber = NumberUtils.toInt(number);
				if (guessNumber == getCurrentNumber()) {
					return NumberStatus.EQUAL;
				} else if (guessNumber > getCurrentNumber()) {
					return NumberStatus.GREATER;
				} else if (guessNumber < getCurrentNumber()) {
					return NumberStatus.LESS;
				}
			} 
		}
		return NumberStatus.INVALID;
	}

}
