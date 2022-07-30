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

import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.log4j.Logger;
import org.saidone.alfresco.benchmark.cmis.CmisSession;

public final class CreateFolder {

	private static Logger logger = Logger.getLogger(CreateFolder.class);

	public static Folder createFolder(Folder parent, String folderName) throws Exception {
		
		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put(PropertyIds.NAME, folderName);
		properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
		return createFolder(parent, properties);
	}

	public static Folder createFolder(Folder parent, HashMap<String, Object> properties) throws Exception {

		logger.debug("begin");
		
		Folder folder = null;
		if (parent == null) {
			// if parent is null use root folder
			folder = CmisSession.getSession().getRootFolder().createFolder(properties);
			logger.trace("created root folder --> " + folder.getName());
		} else {
			// else create folder in folder parent
			folder = parent.createFolder(properties);
			logger.trace("created folder --> " + folder.getName());
		}
		
		logger.debug("end");
		return folder;
	}

}
