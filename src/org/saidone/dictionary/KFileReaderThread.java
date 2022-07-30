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
package org.saidone.dictionary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class KFileReaderThread extends Thread {

	private static Logger logger = Logger.getLogger(KFileReaderThread.class);
	private File file;
	private KDictionarySet dictionary;

	public KFileReaderThread(File file, KDictionarySet dictionary) {
		super();
		this.dictionary = dictionary;
		this.file = file;
		logger.trace("Adding file --> " + file.getAbsolutePath());
	}

	public void run() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String line;
			while ((line = in.readLine()) != null) {
				addLineToDictionary(line);
			}
			in.close();
		} catch (IOException e) {
		} finally {
		}
	}

	protected static String cleanLine(String line) {
		line = line.toLowerCase();
		line = line.replaceAll("[_<>=\\\"#/&\\:\\?\\;%\\{\\}\\\\\\[\\]]", " ");
		line = line.replaceAll("^[\\s\\t]+", "");
		line = line.trim();
		return line;
	}

	protected void addLineToDictionary(String line) {
		line = cleanLine(line);
		StringTokenizer st = new StringTokenizer(line, " ");
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (token.matches("[a-zA-z0-9]{1,24}")) {
				dictionary.add(token);
			}
		}
	}

}
