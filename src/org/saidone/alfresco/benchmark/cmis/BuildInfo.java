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

import java.io.IOException;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.apache.log4j.Logger;

public final class BuildInfo {

	private static Logger logger = Logger.getLogger(BuildInfo.class);

	public static void logBuildNumber() {
		logger.debug("Starting version " + getVersionString());
	}

	public static String getVersionString() {
		String jarFileName = System.getProperty("java.class.path").split(System.getProperty("path.separator"))[0];
		JarFile jar;
		try {
			jar = new JarFile(jarFileName);
			Manifest mf = jar.getManifest();
			return mf.getAttributes("common").getValue("Implementation-Version");
		} catch (IOException e) {
			logger.warn("Cannot retrieve build number");
			logger.warn(e.getMessage());
			return "unknown";
		}
	}

	public static void printWelcome() {
		System.out.println("AlfMark " + getVersionString() + " Copyright (C) 2011 saidone@saidone.org");
		System.out.println("This program comes with ABSOLUTELY NO WARRANTY.");
		System.out.println("This is free software, and you are welcome to redistribute it under certain conditions.");
	}

}
