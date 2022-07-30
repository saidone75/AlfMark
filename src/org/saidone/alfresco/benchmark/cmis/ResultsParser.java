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

import java.util.HashMap;
import java.util.Map.Entry;

import org.saidone.alfresco.benchmark.config.CreateDocumentsTestDefinition;
import org.saidone.alfresco.benchmark.config.DeleteDocumentsTestDefinition;
import org.saidone.alfresco.benchmark.config.GenericTestDefinition;

public final class ResultsParser {

	public static void parseResults(HashMap<GenericTestDefinition, Long> resultsMap) {

		HashMap<String, Long> temp = new HashMap<String, Long>();

		for (Entry<GenericTestDefinition, Long> entry: resultsMap.entrySet()) {
			String newKey = "";
			if (entry.getKey() instanceof CreateDocumentsTestDefinition) {
				newKey = "Create documents";
				if (entry.getKey().getThreads() == 1) {
					newKey += " - single thread";
				}
				else if (entry.getKey().getThreads() == 16) {
					newKey += " - 16 threads";
				}
				else if (entry.getKey().getThreads() == 32) {
					newKey += " - 32 threads";
				}
				if (temp.get(newKey) == null) {
					temp.put(newKey, entry.getValue());
				}
				else {
					temp.put(newKey, temp.get(newKey) + entry.getValue());
				}
			}
			else if (entry.getKey() instanceof DeleteDocumentsTestDefinition) {
				newKey = "Delete documents";
				if (entry.getKey().getThreads() == 1) {
					newKey += " - single thread";
				}
				else if (entry.getKey().getThreads() == 16) {
					newKey += " - 16 threads";
				}
				else if (entry.getKey().getThreads() == 32) {
					newKey += " - 32 threads";
				}
				if (temp.get(newKey) == null) {
					temp.put(newKey, entry.getValue());
				}
				else {
					temp.put(newKey, temp.get(newKey) + entry.getValue());
				}
			}
		}

		// FIXME temp for debug
//		System.out.println(temp);

	}

}
