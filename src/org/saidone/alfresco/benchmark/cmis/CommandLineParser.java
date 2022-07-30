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
package org.saidone.alfresco.benchmark.cmis;

import java.util.Arrays;

import jargs.gnu.CmdLineParser;

import org.apache.log4j.Logger;

public class CommandLineParser {
	
	private static Logger logger = Logger.getLogger(CommandLineParser.class);

	public static void parse() {
		CmdLineParser parser = new CmdLineParser();
//		CmdLineParser.Option debugOption = parser.addBooleanOption('D', "debug");
		CmdLineParser.Option configFileOption = parser.addStringOption('c', "config");
		CmdLineParser.Option versionOption = parser.addBooleanOption('v', "version");
		CmdLineParser.Option helpOption = parser.addBooleanOption('h', "help");

		try {
			logger.trace("args --> " + Arrays.toString(AlfMark.args));
			parser.parse(AlfMark.args);
		}
		catch (CmdLineParser.OptionException e) {
			if (logger.isTraceEnabled()) e.printStackTrace();
			logger.error(e.getMessage());
			System.out.println(e.getMessage());
		}
		catch (NullPointerException e) {
			if (logger.isTraceEnabled()) e.printStackTrace();
			logger.error(e.getMessage());
			System.out.println(e.getMessage());
		}

		// get argoments values
		AlfMark.configFileLocation = (String)parser.getOptionValue(configFileOption);
		boolean printVersion = (boolean)(Boolean)parser.getOptionValue(versionOption, false);
		boolean printHelp = (boolean)(Boolean)parser.getOptionValue(helpOption, false);
		
		if (printVersion) {
			System.out.println("Version " + BuildInfo.getVersionString());
			System.exit(0);
		}
		if (printHelp) {
			System.out.println("help");
			System.exit(0);
		}
		
	}
	
}
