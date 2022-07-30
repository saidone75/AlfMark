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

import java.util.ArrayList;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("config")
public final class AlfMarkConfig {

	private String ip;
	private String port;
	private String alfrescoUser;
	private String alfrescoPassword;
	private String serviceUrl;
	private String cmisEntryPoint;
	private String testFolderPath;
	private Boolean createFolderPathIfNotExist;
	private int sleepBetweenTests;
	private int timeExpected;
	private Boolean cleanUpOnExit;
	
	private ArrayList<GenericTestDefinition> tests;
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getAlfrescoUser() {
		return alfrescoUser;
	}
	
	public void setAlfrescoUser(String alfrescoUser) {
		this.alfrescoUser = alfrescoUser;
	}
	
	public String getAlfrescoPassword() {
		return alfrescoPassword;
	}
	
	public void setAlfrescoPassword(String alfrescoPassword) {
		this.alfrescoPassword = alfrescoPassword;
	}
	
	public String getServiceUrl() {
		return serviceUrl;
	}
	
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	
	public String getCmisEntryPoint() {
		return cmisEntryPoint;
	}
	
	public void setCmisEntryPoint(String cmisEntryPoint) {
		this.cmisEntryPoint = cmisEntryPoint;
	}

	public String getTestFolderPath() {
		return testFolderPath;
	}

	public void setTestFolderPath(String testFolderPath) {
		this.testFolderPath = testFolderPath;
	}
	
	public Boolean getCreateFolderPathIfNotExist() {
		return createFolderPathIfNotExist;
	}

	public void setCreateFolderPathIfNotExist(Boolean createFolderPathIfNotExist) {
		this.createFolderPathIfNotExist = createFolderPathIfNotExist;
	}
	
	public ArrayList<GenericTestDefinition> getTests() {
		return tests;
	}

	public void setTests(ArrayList<GenericTestDefinition> tests) {
		this.tests = tests;
	}

	public int getSleepBetweenTests() {
		return sleepBetweenTests;
	}

	public void setSleepBetweenTests(int sleepBetweenTests) {
		this.sleepBetweenTests = sleepBetweenTests;
	}

	public int getTimeExpected() {
		return timeExpected;
	}

	public void setTimeExpected(int timeExpected) {
		this.timeExpected = timeExpected;
	}

	public Boolean isCleanUpOnExit() {
		return cleanUpOnExit;
	}

	public void setCleanUpOnExit(Boolean cleanUpOnExit) {
		this.cleanUpOnExit = cleanUpOnExit;
	}
	
}
