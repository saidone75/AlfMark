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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.log4j.Logger;
import org.perf4j.LoggingStopWatch;
import org.saidone.alfresco.benchmark.config.ConfigParser;
import org.saidone.alfresco.benchmark.config.CreateDocumentsTestDefinition;
import org.saidone.utils.KCountDown;

public class ThreadTest extends Test {

	private static Logger logger = Logger.getLogger(CreateDocumentsTest.class);	private CreateDocumentsTestDefinition testDefinition;

	public ThreadTest(Folder root, CreateDocumentsTestDefinition testDefinition) {
		this.root = root;
		this.testDefinition = testDefinition;
	}

	private HashMap<Integer, Long> result = new HashMap<Integer, Long>();
	private static KCountDown documentPool;

	private int[] numberOfThreads = {1, 2, 3, 4, 5, 6, 8, 10, 12, 16, 20, 24, 28, 32, 40, 48, 56, 64, 80, 128, 256};

	public void run() {

		// create test folder using test name
		Folder testMainFolder = null;
		try {
			testMainFolder = CreateFolder.createFolder(root, testDefinition.getName());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Folder threadFolder = null;
		for (int threads: numberOfThreads) {
			logger.trace("threads --> " + threads);
			if (threads > testDefinition.getThreads()) break;
			try {
				threadFolder = CreateFolder.createFolder(testMainFolder, "" + threads);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// document counter
			documentPool = new KCountDown(testDefinition.getNumberOfDocuments());

			LoggingStopWatch threadTestInternalSW = new LoggingStopWatch("threadTestInternalSW");
			HashSet<Thread> threadPool = new HashSet<Thread>();
			for (int i = 0; i < threads; i++) {
				CreateDocumentsThread createDocuments = new CreateDocumentsThread(threadFolder, testDefinition.getSize(), testDefinition.getContentType(), documentPool, true);
				createDocuments.start();
				threadPool.add(createDocuments);
			}
			for (Thread thread: threadPool) {
				try {
					thread.join();
				} catch (InterruptedException e) {
					logger.error(e.getMessage());
					if (logger.isTraceEnabled()) e.printStackTrace();
				}
			}
			threadTestInternalSW.stop();
			result.put(threads, threadTestInternalSW.getElapsedTime());
			try {
				Thread.sleep(ConfigParser.getConfig().getSleepBetweenTests());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (logger.isDebugEnabled()) printResult();
	}

	private  void printResult() {
		Set<Entry<Integer, Long>> entrySet = result.entrySet();
		for (Entry<Integer, Long> entry: entrySet) {
			logger.debug(entry.getKey() + " --> " + entry.getValue());
		}
		// FIXME
		System.exit(0);
	}
	
}
