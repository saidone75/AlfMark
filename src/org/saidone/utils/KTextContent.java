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
package org.saidone.utils;

import java.io.File;
import java.util.HashSet;
import java.util.Random;
import java.util.SortedSet;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.saidone.dictionary.KDictionarySet;
import org.saidone.dictionary.KFileReaderThread;

public final class KTextContent {

	private static Logger logger = Logger.getLogger(KTextContent.class);
	private static KDictionarySet dictionary;

	private static KTextContent instance = null;

	private KTextContent() {
		try {
			dictionary = new KDictionarySet();
			// start a thread for each file found (max 8 threads)
			File dir = new File("txt");
			if (!dir.isDirectory()) {
				logger.error(dir.getAbsolutePath() + " is not a folder!");
				throw new Exception(dir.getAbsolutePath() + " is not a folder!");
			}
			File[] files = dir.listFiles();
			HashSet<Thread> threadPool = new HashSet<Thread>();
			for (int i = 0; i < files.length; i++) {
				if (!files[i].canRead()) {
					logger.error(files[i].getAbsolutePath()
							+ " is not readable, skipping");
				}
				if (files[i].isFile()) {
					KFileReaderThread t = new KFileReaderThread(files[i], dictionary);
					t.start();
					threadPool.add(t);
				}
			}
			for (Thread thread : threadPool) {
				try {
					// wait for all threads to finish
					thread.join();
				} catch (InterruptedException e) {
					logger.error(e.getMessage());
					if (logger.isTraceEnabled())
						e.printStackTrace();
				}
			}
		} catch (Exception e) {
			instance = null;
			logger.trace(e.getMessage());
		}
	}

	public static void init() {
		if (instance == null) {
			instance = new KTextContent();
		}
	}

	public static String getTextContent(int size) {
		init();
		Object[] dictionaryArray = null;
		SortedSet<String> dictionarySet = dictionary.getDictionary();
		dictionaryArray = dictionarySet.toArray();
		String content = "";
		String line = "";
		Random rnd = new Random();
		int currentLength = 0;
		int key;
		do {
			key = Math.abs(rnd.nextInt() + 1) % dictionaryArray.length;
			currentLength = (line.length() + ((String)dictionaryArray[key]).length());
			if (currentLength > 80) {
				content = content.concat(line.trim());
				content = content.concat("\n");
				line = "";
			}
			else {
				line = line.concat((String)dictionaryArray[key]);
				line = line.concat(" ");
			}
			if (currentLength > size) {
				content = content.concat(line.trim());
				break;
			}
		} while (content.length() <= size);
		return content;
	}

	public static void main(String[] args) {
		DOMConfigurator.configure("etc/log4j.xml");
		System.out.println(KTextContent.getTextContent(40));
	}

}
