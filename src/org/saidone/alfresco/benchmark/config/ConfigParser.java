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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.saidone.alfresco.benchmark.cmis.AlfMark;
import org.saidone.alfresco.benchmark.cmis.model.Aspect;

import com.thoughtworks.xstream.XStream;

public final class ConfigParser {

	private static Logger logger = Logger.getLogger(ConfigParser.class);
	private static ConfigParser instance = null;
	private static AlfMarkConfig config = null;

	// singleton
	private ConfigParser() {
		XStream xStream = new XStream();
		xStream.autodetectAnnotations(true);
		xStream.alias("config", AlfMarkConfig.class);
		xStream.alias("createDocumentsThread", CreateDocumentsThreadTestDefinition.class);
		xStream.alias("createDocuments", CreateDocumentsTestDefinition.class);
		xStream.alias("deleteDocuments", DeleteDocumentsTestDefinition.class);
		xStream.alias("addAspects", AddAspectsTestDefinition.class);
		try {
			logger.debug("Reading config file");
			// fall back to default location if not specified
			if (AlfMark.configFileLocation == null || AlfMark.configFileLocation.equals("")) {
				AlfMark.configFileLocation = "etc/alfmark.conf.xml";
			}
			config = (AlfMarkConfig)xStream.fromXML(new FileInputStream(new File(AlfMark.configFileLocation)));
		}
		catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			if (logger.isTraceEnabled()) e.printStackTrace();
			System.exit(0);
		}
	}

	public static AlfMarkConfig getConfig() {
		if (instance == null) {
			instance = new ConfigParser();	
			return config;
		}
		else return config;
	}

	// stupid test to see how xml will likely appear
	public static void main(String[] args) {
		AlfMarkConfig config = new AlfMarkConfig();
		config.setIp("127.0.0.1");
		config.setPort("8080");
		config.setAlfrescoUser("admin");
		config.setAlfrescoPassword("admin");
		config.setServiceUrl("/alfresco/service");
		config.setCmisEntryPoint("/alfresco/service/cmis");
		config.setTestFolderPath("/tests/AlfMark");
		config.setCreateFolderPathIfNotExist(true);
		config.setSleepBetweenTests(10000);
		config.setTimeExpected(100000);
		ArrayList<GenericTestDefinition> tests = new ArrayList<GenericTestDefinition>();
//		tests.add(new CreateDocumentsThreadTestDefinition("ThreadsTest", 32, 64, 10, 0));
		tests.add(new CreateDocumentsTestDefinition("Threads=4-Documents=10-Size=10KiB-ContentType=0", 4, 10, 10, 0, true));
		tests.add(new DeleteDocumentsTestDefinition("Threads=4-Documents=10", null, 4, 10));
		LinkedList<Aspect> aspectsToAdd = new LinkedList<Aspect>();
		aspectsToAdd.add(new Aspect("versionable"));
		aspectsToAdd.add(new Aspect("taggable"));
		tests.add(new AddAspectsTestDefinition("Threads=4-Aspects=2", null, 4, 100, aspectsToAdd));
		config.setTests(tests);
		XStream xStream = new XStream();
		xStream.autodetectAnnotations(true);
		System.out.println(xStream.toXML(config));
	}

}
