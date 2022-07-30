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
package org.saidone.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.chemistry.opencmis.client.api.ObjectId;

public class KDocumentList {

	private static KDocumentList instance = null;
	private static List<ObjectId> documents;

	private KDocumentList() {
		documents = (List<ObjectId>)Collections.synchronizedList(new ArrayList<ObjectId>());
	}

	public synchronized static void add(ObjectId objectId) {
		if (instance == null) {
			instance = new KDocumentList();
		}
		documents.add(objectId);
	}
	
	public synchronized static Object[] getIds() {
		return documents.toArray();
	}
	
	public synchronized static ObjectId get() {
		if (size() > 0) {
			return documents.remove(0);
		}
		else return null;
	}
	
	public static int size() {
		if (instance == null) return 0;
		return documents.size();
	}

}
