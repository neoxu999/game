/******************************************************
 * 
 * PlayGameController.java - com.littlepay.game.controller
 * @author Long Xu, 12Feb.,2017
******************************************************/
package com.littlepay.game.controller;

import java.text.MessageFormat;
import java.util.Calendar;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.littlepay.game.model.ComputerPlayer;
import com.littlepay.game.model.IPlayer;
import com.littlepay.game.model.NumberStatus;
import com.littlepay.game.service.GameService;
import com.littlepay.game.service.PlayerService;
import com.littlepay.game.service.RabbitMQFactory;

/**
 * @author Neo Xu 
 *
 */
@Controller("gameController")
public class GameController {

	private static final Logger logger = Logger.getLogger(GameController.class);
	
	@Autowired
	private PlayerService playerService;
	
	@RequestMapping(value="/guess", method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView guessNumber(ModelAndView modelView, HttpSession httpSession, String number) {
		String name = (String) httpSession.getAttribute("user");
		IPlayer player = this.playerService.getPlayer(name);
		
		
		if (player != null) {
			modelView.setViewName("home");
			if ((Calendar.getInstance().getTimeInMillis() - player.getLastGuessTime()) < 1000) {
				modelView.addObject("message", "A Human Player cannot submit more than one guess per second");
				return modelView;
			}
			
			if (NumberUtils.isNumber(number)) {
				int newNumber = NumberUtils.toInt(number);
				player.setLastGuessNumber(newNumber);
				// set guess time
				player.setLastGuessTime(Calendar.getInstance().getTimeInMillis());
			
				NumberStatus numberStatus = GameService.getInstance().checkNumber(number);
				logger.debug("The correct number is: " +GameService.getInstance().getCurrentNumber());
				
				// find the correct answer, publish message to all users.
				if (NumberStatus.EQUAL == numberStatus) {
					// update player
					player.updatePoint(NumberUtils.toInt(number));
					
					RabbitMQFactory.getInstance().pushGameMessage(MessageFormat.format("{0} guessed the correct number {1}.", name, number));
					
					// start a new name
					GameService.getInstance().startNewGame();				
				} else {
					player.updatePoint(-1);
				}
				
				this.playerService.updatePlayer(player);
	
				modelView.addObject("message", numberStatus.getDescription() +" Your poinit is "+ player.getPoint());
				return modelView;
			} else {
			  modelView.addObject("message", "Invalid Number. Your poinit is "+ player.getPoint());
			  return modelView;
			}
		}  
		modelView.setViewName("login");
		modelView.addObject("message", "Cannot find valid user.");
		return modelView;
		
	}
	
	/**
	 * This method is for computer player only
	 * 
	 * @param name
	 * @param password
	 * @param number
	 * @return
	 */
	@RequestMapping(value="/rest/guess", method=RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String guessNumberRest(String name, String password, String number) {
		IPlayer player = this.playerService.getPlayer(name);
		
		// create a convenient method for computer user.
		if (player == null) {
			player = new ComputerPlayer(name, password, 100);
			this.playerService.addPlayer(player);
		}
		
		if (NumberUtils.isNumber(number)) {
			
			int newNumber = NumberUtils.toInt(number);
			// set guess time
			player.setLastGuessTime(Calendar.getInstance().getTimeInMillis());
			player.setLastGuessNumber(newNumber);
			
			NumberStatus numberStatus = GameService.getInstance().checkNumber(number);
			logger.debug("The correct number is: " + GameService.getInstance().getCurrentNumber());
	
			// find the correct answer, publish message to all users.
			if (NumberStatus.EQUAL == numberStatus) {
				// update player
				player.updatePoint(newNumber);
				
				// publish message
				RabbitMQFactory.getInstance().pushGameMessage(MessageFormat.format("{0} guessed the correct number {1}.", name, number));
	
				// start a new name
				GameService.getInstance().startNewGame();
			} else {
				player.updatePoint(-1);
			}
			
			this.playerService.updatePlayer(player);
	
			return "{\"response\":\"" + numberStatus.getDescription() + "\",\"Point:\"" + player.getPoint() + "\"}";
		} else {
			return "{\"response\":\"Invalid Number\",\"Point:\"" + player.getPoint() + "\"}";
		}
		
	}

}
