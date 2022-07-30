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
package org.saidone.alfresco.benchmark.cmis;

import java.util.HashMap;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.log4j.Logger;
import org.saidone.alfresco.benchmark.config.AlfMarkConfig;
import org.saidone.alfresco.benchmark.config.ConfigParser;

public class CmisSession {
	
	private static Logger logger = Logger.getLogger(CmisSession.class);
	
	private static CmisSession instance = null;
	private static Session session = null;
	
	public static Session getSession() {
		if (instance == null) {
			try {
				instance = new CmisSession();
			}
			catch (Exception e) {
				instance = null;
				e.printStackTrace();
			}
		}
		return session;
	}
	
	private CmisSession() {
		
		logger.debug("begin");
		
		// get config
		AlfMarkConfig config = ConfigParser.getConfig();

		// user credentials
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put(SessionParameter.USER, config.getAlfrescoUser());
		parameter.put(SessionParameter.PASSWORD, config.getAlfrescoPassword());

		// connection settings
		parameter.put(SessionParameter.ATOMPUB_URL, "http://" + config.getIp() + ":" + config.getPort() + config.getCmisEntryPoint());
		parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());

		// set the object factory
		parameter.put(SessionParameter.OBJECT_FACTORY_CLASS, "org.alfresco.cmis.client.impl.AlfrescoObjectFactoryImpl");

		// create cmis session
		SessionFactory factory = SessionFactoryImpl.newInstance();
		Session session = factory.getRepositories(parameter).get(0).createSession();
		
		logger.trace("session --> " + session);

		CmisSession.session = session;
		
		logger.debug("end");
	}
	
}
