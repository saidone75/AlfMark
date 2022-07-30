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

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class KDate {

	public static String rfc822Date(Date date) {
        final String DateFormat = "EEE, dd MMM yyyy HH:mm:ss ";
        SimpleDateFormat format;
        format = new SimpleDateFormat(DateFormat, Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        return format.format(date) + "GMT";
    }
	
	public static String rfc822Date(Timestamp timeStamp) {
		return rfc822Date((Date)timeStamp);	
	}
	
}
