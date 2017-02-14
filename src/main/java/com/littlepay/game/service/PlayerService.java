/******************************************************
 * Copyright (c) Quant Pty. Ltd. [2015-2017]
 * Copyright subsists in this code. 
 * Except as permitted under the Copyright Act 1968 (Cth), no part of this 
 * work may be reproduced, published or adapted in any way, without the 
 * specific written permission of Quant Pty. Ltd. All rights reserved.
 *  
 * 
 * PlayerService.java - com.littlepay.game
 * @author Long Xu, 7Feb.,2017
******************************************************/
package com.littlepay.game.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.littlepay.game.model.IPlayer;
import com.littlepay.game.repository.PlayerRepository;

/**
 * @author Neo Xu 
 *
 */
@Service("playerService")
public class PlayerService {

	/**
	 * 
	 */
	public PlayerService() {
		 
	}
	
	/**
	 * @param player
	 * @return
	 */
	public boolean addPlayer(IPlayer player) {
		return PlayerRepository.getInstance().addPlayer(player);
	}
	
	/**
	 * @param player
	 * @return
	 */
	public boolean updatePlayer(IPlayer player) {
		return PlayerRepository.getInstance().updatePlayer(player);
	}
	
	/**
	 * The players are current login.
	 * 
	 * @return
	 */
	public Collection<IPlayer> getActivePlayers() {
		return PlayerRepository.getInstance().getActivePlayers();
	}
	
	/**
	 * @param name
	 * @return
	 */
	public IPlayer getPlayer(String name) {
		return PlayerRepository.getInstance().getPlayer(name);
	}
	
	/**
	 * @param player
	 */
	public void addActivePlayer(IPlayer player) {
		PlayerRepository.getInstance().addActivePlayer(player);
	}
	
	/**
	 * @param player
	 * @return
	 */
	public boolean removeActivePlayer(IPlayer player) {
		return PlayerRepository.getInstance().removeActivePlayer(player);
	}

}
