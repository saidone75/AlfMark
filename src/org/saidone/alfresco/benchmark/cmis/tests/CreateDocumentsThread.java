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

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Random;

import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;
import org.apache.log4j.Logger;
import org.saidone.utils.KCountDown;
import org.saidone.utils.KDocumentList;
import org.saidone.utils.KSequence;
import org.saidone.utils.KTextContent;

public class CreateDocumentsThread extends GenericThread {
	
	private static Logger logger = Logger.getLogger(CreateDocumentsThread.class);

	private Folder parent;
	private int size;
	private int contentType;
	KSequence documentId;
	KCountDown documentPool;
	boolean overwrite;

	public CreateDocumentsThread(Folder parent, int size, int contentType, KCountDown documentPool, boolean overwrite) {
		logger.trace("Creating new thread --> " + CreateDocumentsThread.class);
		this.parent = parent;
		this.size = size;
		this.contentType = contentType;
		this.documentPool = documentPool;
		this.overwrite = overwrite;
	}

	public void run() {

		ContentStream contentStream = null;
		byte[] content;

		while (documentPool.next() > 0 && !halt) {
			
			switch (contentType) {
			case 1:
				// all zero binary
				content = new byte[size * 1024];
				contentStream = new ContentStreamImpl(null, null, "application/octet-stream", new ByteArrayInputStream(content));
				break;
			case 2:
				// indexable text file
				content = new byte[size * 1024];
				content = KTextContent.getTextContent(size * 1024).getBytes();
				contentStream = new ContentStreamImpl(null, null, "text/plain", new ByteArrayInputStream(content));
				break;
			default:
				// random binary content
				content = new byte[size * 1024];
				Random random;
				random = new Random(System.currentTimeMillis());
				random.nextBytes(content);
				contentStream = new ContentStreamImpl(null, null, "application/octet-stream", new ByteArrayInputStream(content));
				logger.trace("content.length --> " +  content.length);
				break;
			}

			HashMap<String, Object> properties = new HashMap<String, Object>();
			properties.put(PropertyIds.NAME, "" + KSequence.next());
			properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");

			try {
				KDocumentList.add(CreateDocument.createDocument(parent, properties, contentStream, overwrite));
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
