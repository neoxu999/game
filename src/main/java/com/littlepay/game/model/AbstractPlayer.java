/******************************************************
 * Copyright (c) Quant Pty. Ltd. [2015-2017]
 * Copyright subsists in this code. 
 * Except as permitted under the Copyright Act 1968 (Cth), no part of this 
 * work may be reproduced, published or adapted in any way, without the 
 * specific written permission of Quant Pty. Ltd. All rights reserved.
 *  
 * 
 * AbstractPlayer.java - com.littlepay.game.model
 * @author Long Xu, 7Feb.,2017
******************************************************/
package com.littlepay.game.model;

import java.util.Calendar;

import com.littlepay.game.util.GameUtil;

/**
 * @author Neo Xu 
 *
 */
public abstract class AbstractPlayer implements IPlayer {

	private String name;
	private String password;
	private int point;
	private long lastGuessTime;
	private int lastGuessNumber;
	
	
	/**
	 * 
	 */
	public AbstractPlayer() {
		  
	}
	
	/**
	 * @param name
	 * @param password
	 */
	public AbstractPlayer(String name, String password, int point) {
		this(name, password, point, -1, -999);
	}
	
	/**
	 * @param name
	 * @param password
	 */
	public AbstractPlayer(String name, String password, int point, long lastGuessTime, int lastGuessNumber) {
		this.name = name;
		this.password = password;
		this.point = point;
		this.lastGuessTime = lastGuessTime;
	}

	
	/* (non-Javadoc)
	 * @see com.littlepay.game.model.IPlayer#getName()
	 */
	@Override
	public String getName() {
		if (this.name == null) {
			this.name = "";
		}
		return this.name;
	}
	
	/* (non-Javadoc)
	 * @see com.littlepay.game.model.IPlayer#getPassword()
	 */
	@Override
	public String getPassword() {
		if (this.password == null) {
			this.password = "";
		}
		return this.password;
	}
	
	/* (non-Javadoc)
	 * @see com.littlepay.game.model.IPlayer#getPoint()
	 */
	@Override
	public int getPoint() {
		return this.point;
	}
	
	/* (non-Javadoc)
	 * @see com.littlepay.game.model.IPlayer#guesssNumber()
	 */
	@Override
	public double guesssNumber() {
		this.lastGuessTime = Calendar.getInstance().getTimeInMillis();
		return GameUtil.randomNumber(100);
	}
	
	/* (non-Javadoc)
	 * @see com.littlepay.game.model.IPlayer#lastGuessTime()
	 */
	@Override
	public long getLastGuessTime() {
		return this.lastGuessTime;
	}
	
	/* (non-Javadoc)
	 * @see com.littlepay.game.model.IPlayer#getLastGuessNumber()
	 */
	@Override
	public int getLastGuessNumber() {
		return this.lastGuessNumber;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IPlayer) {
			return ((IPlayer) obj).getName().equals(getName());
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.littlepay.game.model.IPlayer#updatePoint(int)
	 */
	@Override
	public void updatePoint(int number) {
		this.point = this.point + number;
	}
	
	/* (non-Javadoc)
	 * @see com.littlepay.game.model.IPlayer#setLastGuessTime(long)
	 */
	@Override
	public void setLastGuessTime(long time) {
		this.lastGuessTime = time;
	}
	
	/* (non-Javadoc)
	 * @see com.littlepay.game.model.IPlayer#setLastGuessNumber(int)
	 */
	@Override
	public void setLastGuessNumber(int number) {
		this.lastGuessNumber = number;
	}
}
