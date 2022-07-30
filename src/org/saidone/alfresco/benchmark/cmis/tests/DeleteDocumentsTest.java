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

import org.saidone.alfresco.benchmark.config.DeleteDocumentsTestDefinition;
import org.saidone.utils.KCountDown;

public class DeleteDocumentsTest extends Test {

	private DeleteDocumentsTestDefinition testDefinition;
	private static KCountDown n;
	
	public DeleteDocumentsTest(DeleteDocumentsTestDefinition testDefinition) {
		this.testDefinition = testDefinition;
	}

	public void run() {

		if (testDefinition.getNumberOfDocuments() > 0) {
			n = new KCountDown(testDefinition.getNumberOfDocuments());
		} else {
			n = new KCountDown(Integer.MAX_VALUE);
		}
		
		CounterThread counter = new CounterThread(n);
		counter.start();

		for (int i = 0; i < testDefinition.getThreads(); i++) {
			DeleteDocumentsThread deleteDocuments = new DeleteDocumentsThread(n);
			deleteDocuments.start();
			threadPool.add(deleteDocuments);
		}
		
		waitForThreads();
		
	}

}
