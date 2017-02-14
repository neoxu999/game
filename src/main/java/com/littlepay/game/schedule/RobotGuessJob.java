/******************************************************
 * Copyright (c) Quant Pty. Ltd. [2015-2017]
 * Copyright subsists in this code. 
 * Except as permitted under the Copyright Act 1968 (Cth), no part of this 
 * work may be reproduced, published or adapted in any way, without the 
 * specific written permission of Quant Pty. Ltd. All rights reserved.
 *  
 * 
 * RobotGuessJob.java - com.littlepay.game.schedule
 * @author Long Xu, 14Feb.,2017
******************************************************/
package com.littlepay.game.schedule;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.littlepay.game.util.GameUtil;

/**
 * @author Neo Xu 
 *
 */
public class RobotGuessJob implements Job {

	private static final Logger logger = Logger.getLogger(RobotGuessJob.class);

	private static final String link = "http://localhost:8080/game/rest/guess";
	private String urlParameters = "name=computerPlayer&password=123456";

	/**
	 * 
	 */
	public RobotGuessJob() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		try {
			URL url = new URL(link);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			// add request header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			
			int guessNumber = GameUtil.randomNumber(100);
			urlParameters +="&number="+guessNumber;
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

//			int responseCode = con.getResponseCode();
//			logger.debug("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}

			in.close();

			// print result
			logger.debug(response.toString());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
