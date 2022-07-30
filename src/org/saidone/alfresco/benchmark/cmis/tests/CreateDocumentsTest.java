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

import org.apache.chemistry.opencmis.client.api.Folder;
import org.saidone.alfresco.benchmark.config.CreateDocumentsTestDefinition;
import org.saidone.utils.KCountDown;

public class CreateDocumentsTest extends Test {

	private CreateDocumentsTestDefinition testDefinition;
	private static KCountDown documentPool;

	public CreateDocumentsTest(Folder root, CreateDocumentsTestDefinition testDefinition) {
		this.root = root;
		this.testDefinition = testDefinition;
	}

	public void run() {

		Folder currentFolder = null;
		if (testDefinition.isCreateTestFolder()) {
			// create test folder using test name
			try {
				currentFolder = CreateFolder.createFolder(root, testDefinition.getName());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else currentFolder = root;

		// document counter
		documentPool = new KCountDown(testDefinition.getNumberOfDocuments());

		CounterThread counter = new CounterThread(documentPool);

		counter.start();
		threadPool.add(counter);

		for (int i = 0; i < testDefinition.getThreads(); i++) {
			CreateDocumentsThread createDocuments = new CreateDocumentsThread(currentFolder, testDefinition.getSize(), testDefinition.getContentType(), documentPool, testDefinition.isOverwrite());
			createDocuments.start();
			threadPool.add(createDocuments);
		}
		
		waitForThreads();
		
	}

}
