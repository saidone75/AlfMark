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
import java.util.Iterator;

import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.log4j.Logger;
import org.perf4j.StopWatch;
import org.perf4j.log4j.Log4JStopWatch;
import org.saidone.alfresco.benchmark.cmis.CmisSession;

public final class CreateDocument {

	private static Logger logger = Logger.getLogger(CreateDocument.class);

	public static ObjectId createDocument(Folder parent, HashMap<String, Object> properties, ContentStream contentStream, boolean overwrite) throws Exception {
		ObjectId objectId = null;
		Session session = CmisSession.getSession();
		StopWatch createDocumentStopWatch = new Log4JStopWatch("createDocument");
		
		if (overwrite) {
			String query = "SELECT cmis:objectId FROM cmis:document WHERE IN_FOLDER('" + parent.getId() + "') AND cmis:name='" + properties.get(PropertyIds.NAME) + "'";
			logger.trace("query --> " + query);
			ItemIterable<QueryResult> result = session.query(query , false);
			Iterator<QueryResult> resultIterator = result.iterator();
			while (resultIterator.hasNext()) {
				QueryResult currentResult = resultIterator.next();
				String id = currentResult.getPropertyValueById(PropertyIds.OBJECT_ID);
				DeleteDocument.deleteDocument(id);
			}
		}
		
		createDocumentStopWatch.start();
		objectId = session.createDocument(properties, parent, contentStream, null);
		createDocumentStopWatch.stop();
		logger.trace("created document --> " + objectId);
		return objectId;
	}

}
