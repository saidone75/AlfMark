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
package org.saidone.alfresco.benchmark.config;

import java.util.LinkedList;

import org.saidone.alfresco.benchmark.cmis.model.Aspect;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("addAspects")
public class AddAspectsTestDefinition extends GenericTestDefinition {

	private int numberOfDocuments;

	@XStreamAlias("aspects")
	private LinkedList<Aspect> aspectsToAdd;
	
	public AddAspectsTestDefinition(String name, String description, int threads, int numberOfDocuments, LinkedList<Aspect> aspectsToAdd) {
		setName(name);
		setDescription(description);
		setThreads(threads);
		this.numberOfDocuments = numberOfDocuments;
		this.setAspectsToAdd(aspectsToAdd);
	}

	public int getNumberOfDocuments() {
		return numberOfDocuments;
	}

	public void setNumberOfDocuments(int numberOfDocuments) {
		this.numberOfDocuments = numberOfDocuments;
	}

	public LinkedList<Aspect> getAspectsToAdd() {
		return aspectsToAdd;
	}

	public void setAspectsToAdd(LinkedList<Aspect> aspectsToAdd) {
		this.aspectsToAdd = aspectsToAdd;
	}

}
