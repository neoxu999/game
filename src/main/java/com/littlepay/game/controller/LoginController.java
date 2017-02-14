/******************************************************
 * 
 * LoginController.java - com.littlepay.game.controller
 * @author Long Xu, 12Feb.,2017
******************************************************/
package com.littlepay.game.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.littlepay.game.model.HumanPlayer;
import com.littlepay.game.model.IPlayer;
import com.littlepay.game.service.PlayerService;

/**
 * @author Neo Xu
 *
 */
@Controller("loginController")
public class LoginController {

	private static final Logger logger = Logger.getLogger(LoginController.class);

	@Autowired
	private PlayerService playerService;

	/**
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	/**
	 * @param httpSession
	 * @param name
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/login/signin", method = RequestMethod.GET)
	public ModelAndView loginCheck(ModelAndView modelView, HttpSession httpSession, String username, String password) {
		IPlayer player = this.playerService.getPlayer(username);
		if (player != null) {
			if (password.equals(player.getPassword())) {
				logger.debug("login successful");
				if (httpSession != null) {
					httpSession.setAttribute("user", username);
				}

				// add active player to play a name
				this.playerService.addActivePlayer(player);

				modelView.setViewName("home");
				return modelView;
			} else {
				logger.debug("Password not correct");
				modelView.setViewName("login");
				modelView.addObject("message", "Password not correct");
				return modelView;
			}
		} else {
			// user doesn't exist
			logger.debug("User doesn't exist.");
			modelView.setViewName("login");
			modelView.addObject("message", "User doesn't exist. Please Register a new account.");
			return modelView;
		}

	}

	/**
	 * @param httpSession
	 * @param name
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/login/newAccount", method = RequestMethod.GET)
	public ModelAndView loginNewAccount(ModelAndView modelView, HttpSession httpSession, String username,
			String password) {
		IPlayer player = this.playerService.getPlayer(username);
		if (player != null) {
			modelView.setViewName("login");
			modelView.addObject("message", "The user name already exists. Please choose a different name.");
			return modelView;
		} else {
			if (httpSession != null) {
				httpSession.setAttribute("user", username);
			}

			IPlayer newPlayer = new HumanPlayer(username, password, 100);
			playerService.addPlayer(newPlayer);
			playerService.addActivePlayer(newPlayer);
			modelView.setViewName("home");

			return modelView;
		}
	}

}
