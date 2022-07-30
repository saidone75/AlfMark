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

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("createDocuments")
public class CreateDocumentsTestDefinition extends GenericTestDefinition {

	private int numberOfDocuments;
	private int size;
	private int contentType;
	private boolean createTestFolder;
	private boolean overwrite;
	
	public int getNumberOfDocuments() {
		return numberOfDocuments;
	}
	
	public void setNumberOfDocuments(int numberOfDocuments) {
		this.numberOfDocuments = numberOfDocuments;
	}
	
	public int getSize() {
		return size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public int getContentType() {
		return contentType;
	}
	
	public void setContentType(int contentType) {
		this.contentType = contentType;
	}

	public boolean isCreateTestFolder() {
		return createTestFolder;
	}

	public void setCreateTestFolder(boolean createTestFolder) {
		this.createTestFolder = createTestFolder;
	}
	
	public boolean isOverwrite() {
		return overwrite;
	}

	public void setOverwrite(boolean overwrite) {
		this.overwrite = overwrite;
	}
	
	public CreateDocumentsTestDefinition(String name, int threads, int numberOfDocuments, int size, int contentType) {
		this(name, null, threads, numberOfDocuments, size, contentType, true, true);
	}
	
	public CreateDocumentsTestDefinition(String name, int threads, int numberOfDocuments, int size, int contentType, boolean createTestFolder) {
		this(name, null, threads, numberOfDocuments, size, contentType, createTestFolder, true);
	}
	
	public CreateDocumentsTestDefinition(String name, int threads, int numberOfDocuments, int size, int contentType, boolean createTestFolder, boolean overwrite) {
		this(name, null, threads, numberOfDocuments, size, contentType, createTestFolder, overwrite);
	}
	
	public CreateDocumentsTestDefinition(String name, String description, int threads, int numberOfDocuments, int size, int contentType, boolean createTestFolder, boolean overwrite) {
		setName(name);
		setDescription(description);
		setThreads(threads);
		this.setNumberOfDocuments(numberOfDocuments);
		this.setSize(size);
		this.setContentType(contentType);
		this.setCreateTestFolder(createTestFolder);
		this.setOverwrite(overwrite);
	}
	
}
