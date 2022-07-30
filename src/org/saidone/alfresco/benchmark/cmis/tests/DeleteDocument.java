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

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.log4j.Logger;
import org.perf4j.StopWatch;
import org.perf4j.log4j.Log4JStopWatch;
import org.saidone.alfresco.benchmark.cmis.CmisSession;

public final class DeleteDocument {
	
	private static Logger logger = Logger.getLogger(DeleteDocument.class);
	
	public static void deleteDocument(ObjectId objectId) throws Exception {
		CmisObject object = CmisSession.getSession().getObject(objectId);
		deleteDocument(object);
	}
	
	public static void deleteDocument(String objectId) throws Exception {
		CmisObject object = CmisSession.getSession().getObject(objectId);
		deleteDocument(object);
	}
	
	private static void deleteDocument(CmisObject object) {
		StopWatch deleteDocumentStopWatch = new Log4JStopWatch("deleteDocument");
		deleteDocumentStopWatch.start();
		object.delete(true);
		deleteDocumentStopWatch.stop();
		logger.debug("deleted document --> " + object.getId());
	}
	
}
