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

import java.util.LinkedList;

import org.saidone.alfresco.benchmark.cmis.model.Aspect;
import org.saidone.alfresco.benchmark.config.AddAspectsTestDefinition;
import org.saidone.utils.KCountDown;

public class AddAspectsTest extends Test {

	private AddAspectsTestDefinition testDefinition;
	private KCountDown n;

	public AddAspectsTest(AddAspectsTestDefinition testDefinition) {
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
		
		LinkedList<Aspect> aspects = testDefinition.getAspectsToAdd();

		for (int i = 0; i < testDefinition.getThreads(); i++) {
			AddAspectsThread addAspects = new AddAspectsThread(n, aspects);
			addAspects.start();
			threadPool.add(addAspects);
		}
		
		waitForThreads();
		
	}

}
