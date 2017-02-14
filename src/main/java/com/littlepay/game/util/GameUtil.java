/******************************************************
 * Copyright (c) Quant Pty. Ltd. [2015-2017]
 * Copyright subsists in this code. 
 * Except as permitted under the Copyright Act 1968 (Cth), no part of this 
 * work may be reproduced, published or adapted in any way, without the 
 * specific written permission of Quant Pty. Ltd. All rights reserved.
 *  
 * 
 * GameUtil.java - com.littlepay.game.util
 * @author Long Xu, 7Feb.,2017
******************************************************/
package com.littlepay.game.util;

import java.util.Random;

/**
 * @author Neo Xu 
 *
 */
public class GameUtil {

	private static Random random = new Random();
	
	/**
	 * @return
	 */
	public static int randomNumber(int range) {
		 return random.nextInt(range);
	}

}
