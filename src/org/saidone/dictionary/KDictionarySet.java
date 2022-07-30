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
package org.saidone.dictionary;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.log4j.Logger;

public class KDictionarySet {

	private SortedSet<String> dictionary;
	private Logger logger;

	public KDictionarySet() {
		logger = Logger.getLogger(this.getClass());
		setDictionary(new TreeSet<String>());
	}

	public void setDictionary(SortedSet<String> dictionary) {
		this.dictionary = dictionary;
	}

	public SortedSet<String> getDictionary() {
		return dictionary;
	}

	public synchronized void add(String token) {
		try {
			dictionary.add(token);
		}
		catch (Exception e) {
			logger.trace(e.getMessage());
		}
	}

	public int getSize() {
		return dictionary.size();
	}

	public Iterator<String> getIterator() {
		return dictionary.iterator();
	}

}
