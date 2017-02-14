/******************************************************
 * Copyright (c) Quant Pty. Ltd. [2015-2017]
 * Copyright subsists in this code. 
 * Except as permitted under the Copyright Act 1968 (Cth), no part of this 
 * work may be reproduced, published or adapted in any way, without the 
 * specific written permission of Quant Pty. Ltd. All rights reserved.
 *  
 * 
 * PlayerRepository.java - com.littlepay.game.service
 * @author Long Xu, 7Feb.,2017
******************************************************/
package com.littlepay.game.repository;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.littlepay.game.model.ComputerPlayer;
import com.littlepay.game.model.HumanPlayer;
import com.littlepay.game.model.IPlayer;
import com.littlepay.game.service.JedisFactory;

/**
 * @author Neo Xu 
 *
 */
@Repository("playerRepository")
public class PlayerRepository {
	private static final Logger logger = Logger.getLogger(PlayerRepository.class);
	
    private static final String PLAYER_TABLE_KEY = "player";
    
    private static PlayerRepository instance;
	
    private ObjectMapper mapper;
	private ConcurrentHashMap<String, IPlayer> activePlayersMap;
	
	/**
	 * @return
	 */
	public static PlayerRepository getInstance() {
		if (instance == null) {
			instance = new PlayerRepository();
		}
		return instance;
	}
	
	/**
	 * 
	 */
	public PlayerRepository() {
		 
	}

	/**
	 * @param player
	 * @return
	 */
	public boolean addPlayer(IPlayer player) {
		  JedisFactory.getInstance().getJedis().hset(PLAYER_TABLE_KEY, player.getName(), player.toString());
		  return true;
	}
	
	/**
	 * @return
	 */
	public Collection<IPlayer> getActivePlayers() {
		return getActivePlayersMap().values();
	}

	/**
	 * @return the activePlayersMap
	 */
	public ConcurrentHashMap<String, IPlayer> getActivePlayersMap() {
		if (this.activePlayersMap == null) {
			this.activePlayersMap = new ConcurrentHashMap<String, IPlayer>();
		}
		return this.activePlayersMap;
	}
	
	/**
	 * @param name
	 * @return
	 */
	public IPlayer getPlayer(String name) {
		if (StringUtils.isNotBlank(name)) {
			String playerJson = JedisFactory.getInstance().getJedis().hget(PLAYER_TABLE_KEY, name);
			return createIPlayerFromJson(playerJson);
		}  else {
			logger.debug("name is null");
		}
		return null;
	}

	/**
	 * @param player
	 * @return
	 */
	public boolean removeActivePlayer(IPlayer player) {
		return getActivePlayers().remove(player);
	}
	
	/**
	 * @param playerJson
	 * @return
	 */
	private IPlayer createIPlayerFromJson(String playerJson) {
		IPlayer player = null;
		if (StringUtils.isNotBlank(playerJson)) {
			try {
				JsonNode node = getMapper().readTree(playerJson);
				String name = node.get("name").asText();
				String playerType = node.get("playerType").asText();
				String password = node.get("password").asText();
				int point = node.get("point").asInt();
				long lastGuessTime = node.get("lastGuessTime").asLong();
				int lastGuessNumber = node.get("lastGuessNumber").asInt();
				if (StringUtils.isNotBlank(playerType)) {
					switch (playerType) {
					case "Human":
						player = new HumanPlayer(name, password, point, lastGuessTime, lastGuessNumber);
						break;
					case "Computer":
						player = new ComputerPlayer(name, password, point, lastGuessTime, lastGuessNumber);
						break;
					default:
						break;
					}
					
				}
				
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return player;
	}
	
	/**
	 * @return the mapper
	 */
	public ObjectMapper getMapper() {
		if (this.mapper == null) {
			this.mapper = new ObjectMapper();
		}
		return this.mapper;
	}

	/**
	 * @param player
	 */
	public void addActivePlayer(IPlayer player) {

		if (!getActivePlayersMap().contains(player)) {
			getActivePlayersMap().put(player.getName(), player);
		}
	}

	/**
	 * @param player
	 * @return
	 */
	public boolean updatePlayer(IPlayer player) {
		JedisFactory.getInstance().getJedis().hset(PLAYER_TABLE_KEY, player.getName(), player.toString());
		return false;
	}

}
