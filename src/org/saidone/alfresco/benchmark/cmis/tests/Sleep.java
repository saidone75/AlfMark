/*******************************************************************************
 * AlfMark is a tool for benchmarking Alfresco installations
 * Copyright (C) 2011 saidone@saidone.org (Marco Marini)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package org.saidone.alfresco.benchmark.cmis.tests;

import org.apache.log4j.Logger;
import org.saidone.alfresco.benchmark.config.ConfigParser;

public final class Sleep {

	private static Logger logger = Logger.getLogger(Sleep.class);

	public static void sleep() {
		if (ConfigParser.getConfig().getSleepBetweenTests() > 0) {
			logger.debug("Sleeping for --> " + ConfigParser.getConfig().getSleepBetweenTests() + " milliseconds");
			try {
				Thread.sleep(ConfigParser.getConfig().getSleepBetweenTests());
			} catch (InterruptedException e) {
				logger.warn(e.getMessage());
				if (logger.isTraceEnabled()) e.printStackTrace();
			}	
		}
	}
	
}
