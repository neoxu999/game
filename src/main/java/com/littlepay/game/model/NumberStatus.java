/******************************************************
 * Copyright (c) Quant Pty. Ltd. [2015-2017]
 * Copyright subsists in this code. 
 * Except as permitted under the Copyright Act 1968 (Cth), no part of this 
 * work may be reproduced, published or adapted in any way, without the 
 * specific written permission of Quant Pty. Ltd. All rights reserved.
 *  
 * 
 * NumberStatus.java - com.littlepay.game.model
 * @author Long Xu, 14Feb.,2017
******************************************************/
package com.littlepay.game.model;

/**
 * @author Neo Xu 
 *
 */
public enum NumberStatus {
	EQUAL("The number is correct"),
	GREATER("The number is greater than target number"),
	LESS("The number is less than target number"),
	INVALID("The number is invalid.");
	
	private String description;
	/**
	 * 
	 */
	private NumberStatus(String description) {
		this.description = description;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}
}
