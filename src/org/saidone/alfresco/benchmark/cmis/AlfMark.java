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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.perf4j.StopWatch;
import org.saidone.alfresco.benchmark.cmis.tests.AddAspectsTest;
import org.saidone.alfresco.benchmark.cmis.tests.CheckPath;
import org.saidone.alfresco.benchmark.cmis.tests.CreateDocumentsTest;
import org.saidone.alfresco.benchmark.cmis.tests.DeleteDocumentsTest;
import org.saidone.alfresco.benchmark.cmis.tests.GenericThread;
import org.saidone.alfresco.benchmark.cmis.tests.Sleep;
import org.saidone.alfresco.benchmark.cmis.tests.Test;
import org.saidone.alfresco.benchmark.cmis.tests.ThreadTest;
import org.saidone.alfresco.benchmark.config.AddAspectsTestDefinition;
import org.saidone.alfresco.benchmark.config.ConfigParser;
import org.saidone.alfresco.benchmark.config.CreateDocumentsTestDefinition;
import org.saidone.alfresco.benchmark.config.CreateDocumentsThreadTestDefinition;
import org.saidone.alfresco.benchmark.config.DeleteDocumentsTestDefinition;
import org.saidone.alfresco.benchmark.config.GenericTestDefinition;
import org.saidone.utils.KDocumentList;

public class AlfMark {

	private static Logger logger = Logger.getLogger(AlfMark.class);

	public static String[] args = null;
	public static String configFileLocation = null;

	// test root folder
	private static Folder root = null;

	private static Iterator<GenericTestDefinition> testsIterator;
	
	// current test
	private static Test currentTest;

	public static void main(String[] args) {

		// make args global
		AlfMark.args = args;

		// initialize logger
		// read log4j property file
		DOMConfigurator.configure("etc/log4j.xml");

		// manage shutdown
		Thread shutDownHook = new Thread(
				new Runnable() {
					public void run() {
						logger.debug("Halting...");
						testsIterator = null;
						// stop current threads
						if (currentTest != null) {
							for (GenericThread thread: currentTest.getThreadPool()) {
								thread.halt();
							}
						}
						if (ConfigParser.getConfig().isCleanUpOnExit() == null || ConfigParser.getConfig().isCleanUpOnExit() == true) {
							cleanUp(AlfMark.root);
						}
						logger.debug("Done!");
					}
				});
		Runtime.getRuntime().addShutdownHook(shutDownHook);
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			public void uncaughtException(Thread t, Throwable e) {
				if (e instanceof Error) {
					System.exit(99);
				}
			}
		});

		// retrieve and print build number to log
		BuildInfo.logBuildNumber();
		BuildInfo.printWelcome();

		// parse command line arguments
		CommandLineParser.parse();

		// initialize a cmis session
		Session session = CmisSession.getSession();
		logger.debug("Repository name --> " + session.getRepositoryInfo().getName());

		// create a disposable root folder for tests
		// all tests will rely on this
		String path = "";
		try {
			// retrieve path string
			path = ConfigParser.getConfig().getTestFolderPath();
			// trim path string
			path = path.trim();
			// remove leading and trailing slashes, if any
			if (path.matches("^\\/.*$")) {
				path = path.substring(1);
			}
			if (path.matches("^.*\\/$")) {
				path = path.substring(0, path.length() - 1);
			}
			// split path in parts
			String[] pathParts = path.split("\\/");

			// check for tests path
			root = CheckPath.checkPath(pathParts);
		}
		catch (Exception e) {
			logger.error(e.getMessage());
			if (logger.isTraceEnabled()) e.printStackTrace();
		}

		// get the list of tests to perform
		ArrayList<GenericTestDefinition> tests = ConfigParser.getConfig().getTests();

		// tests results table
		HashMap<GenericTestDefinition, Long> resultsMap = new HashMap<GenericTestDefinition, Long>();

		// execute the tests
		testsIterator = tests.iterator();
		GenericTestDefinition testDefinition;
		while (testsIterator != null && testsIterator.hasNext()) {
			testDefinition = testsIterator.next();
			if (testDefinition instanceof CreateDocumentsThreadTestDefinition) {
				currentTest = new ThreadTest(root, (CreateDocumentsThreadTestDefinition)testDefinition);
				System.out.print("Running test --> " + testDefinition.getName() + " ...");
				currentTest.run();
				System.out.println(" Done!");
			} else if (testDefinition instanceof CreateDocumentsTestDefinition) {
				currentTest = new CreateDocumentsTest(root, (CreateDocumentsTestDefinition)testDefinition);
				System.out.print("Running test --> " + testDefinition.getName() + " ...");
				StopWatch createDocumentsTestStopWatch = new StopWatch(testDefinition.getName());
				currentTest.run();
				createDocumentsTestStopWatch.stop();
				System.out.println("Done!");
				resultsMap.put(testDefinition, createDocumentsTestStopWatch.getElapsedTime());
			} else if (testDefinition instanceof AddAspectsTestDefinition) {
				currentTest = new AddAspectsTest((AddAspectsTestDefinition)testDefinition);
				System.out.print("Running test --> " + testDefinition.getName() + " ...");
				StopWatch addAspectsTestStopWatch = new StopWatch(testDefinition.getName());
				currentTest.run();
				addAspectsTestStopWatch.stop();
				System.out.println("Done!");
				resultsMap.put(testDefinition, addAspectsTestStopWatch.getElapsedTime());
			} else if (testDefinition instanceof DeleteDocumentsTestDefinition) {
				logger.trace("KDocumentList.size() --> " + KDocumentList.size());
				if (((DeleteDocumentsTestDefinition)testDefinition).getNumberOfDocuments() <= KDocumentList.size()) {
					currentTest = new DeleteDocumentsTest((DeleteDocumentsTestDefinition)testDefinition);
					System.out.print("Running test --> " + testDefinition.getName() + " ...");
					StopWatch deleteDocumentsTestStopWatch = new StopWatch(testDefinition.getName());
					currentTest.run();
					deleteDocumentsTestStopWatch.stop();
					System.out.println("Done!");
					resultsMap.put(testDefinition, deleteDocumentsTestStopWatch.getElapsedTime());
				}
				else {
					logger.warn("Not enough documents to delete, skipping test");
				}
			}		
			// sleep after test if required
			Sleep.sleep();
		}

		// compute total time
		long totalTime = 0;
		for (Entry<GenericTestDefinition, Long> entry: resultsMap.entrySet()) {
			totalTime += entry.getValue();
		}

		// parse results
		ResultsParser.parseResults(resultsMap);

		logger.debug("Residual documents --> " + KDocumentList.size());

		// cleanup if needed
		if (ConfigParser.getConfig().isCleanUpOnExit() == null || ConfigParser.getConfig().isCleanUpOnExit() == true) {
			cleanUp(root);
		}

		// print results
		logger.info("Total time --> " + totalTime);
		double score = (double)ConfigParser.getConfig().getTimeExpected() / (float)totalTime;
		logger.info("Overall AlfMark score --> " + String.format("%.2f", score));
		System.out.println("Overall AlfMark score --> " + String.format("%.2f", score));
		System.out.println("Thank you! --- stay tuned with latest development on http://www.saidone.org");

		// all work done, shutdown hook no longer needed
		Runtime.getRuntime().removeShutdownHook(shutDownHook);
		System.exit(0);
	}

	private static long cleanUp(Folder root) {
		if (root != null) { 
			System.out.print("Cleaning up ...");
			StopWatch cleanupStopWatch = new StopWatch("cleanUp");
			logger.debug("Deleting folder --> " + root.getName());
			root.deleteTree(true, UnfileObject.DELETE, true);
			cleanupStopWatch.stop();
			System.out.println(" Done!");
			return cleanupStopWatch.getElapsedTime();
		}
		else return 0L;
	}

}
