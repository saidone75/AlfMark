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

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.log4j.Logger;
import org.saidone.alfresco.benchmark.cmis.CmisSession;
import org.saidone.alfresco.benchmark.config.ConfigParser;

public final class CheckPath {

	private static Logger logger = Logger.getLogger(CheckPath.class);

	public static Folder checkPath(String[] pathParts) throws Exception {
		Boolean createFolderPathIfNotExist = ConfigParser.getConfig().getCreateFolderPathIfNotExist();
		Folder rootFolder = CmisSession.getSession().getRootFolder();
		for (int i = 0; i < pathParts.length; i++) {
			rootFolder = checkPathPart(rootFolder, pathParts[i], i, createFolderPathIfNotExist);
		}
		return rootFolder;
	}

	private static Folder checkPathPart(Folder parent, String pathPart, int i, Boolean createFolderPathIfNotExist) throws Exception {
		logger.debug("begin");
		logger.trace("parent.getName() --> " + parent.getName());
		logger.trace("pathPart --> " + pathPart);
		logger.trace("createFolderPathIfNotExist --> " + createFolderPathIfNotExist);
		// check if current path part exist
		ItemIterable<CmisObject> children = parent.getChildren();
		for (CmisObject currentChild: children) {
			if (currentChild.getName().equals(pathPart)) {
				// child already exist
				logger.debug("end");
				return (Folder)currentChild;
			}
		}
		if (createFolderPathIfNotExist) {
			// create new folder
			HashMap<String, Object> properties = new HashMap<String, Object>();
			properties.put(PropertyIds.NAME, pathPart);
			properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
			logger.debug("end");
			return parent.createFolder(properties);
		}
		else {
			throw new Exception("Folder " + pathPart + " does not exist.");
		}
	}

}
