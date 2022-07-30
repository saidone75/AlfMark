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

import java.util.LinkedList;

import org.alfresco.cmis.client.AlfrescoDocument;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.log4j.Logger;
import org.saidone.alfresco.benchmark.cmis.CmisSession;
import org.saidone.alfresco.benchmark.cmis.model.Aspect;
import org.saidone.utils.KCountDown;
import org.saidone.utils.KDocumentList;

public class AddAspectsThread extends GenericThread {

	private static Logger logger = Logger.getLogger(AddAspectsThread.class);

	KCountDown n;
	LinkedList<Aspect> aspects;

	public AddAspectsThread(KCountDown n, LinkedList<Aspect> aspects) {
		logger.trace("Creating new thread --> " + AddAspectsThread.class);
		this.aspects = aspects;
		this.n = n;
	}

	public void run() {
		Object[] ids = KDocumentList.getIds();
		int m;
		while ((m = n.next()) > 0 && !halt) {
			AlfrescoDocument currentDocument = (AlfrescoDocument)CmisSession.getSession().getObject((ObjectId)ids[--m]);
			for (Aspect aspect: aspects) {
				logger.trace("Adding aspect --> " + aspect.getName());
				currentDocument.addAspect("P:cm:" + aspect.getName());
			}
		}
	}

}
