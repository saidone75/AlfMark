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

import org.saidone.utils.KCountDown;

public final class CounterThread extends GenericThread {

	private KCountDown counter;

	public CounterThread(KCountDown counter) {
		super();
		this.counter = counter;
		System.out.print("     ");
	}

	public void run() {
		while (counter.read() > 0) {
			if (halt) break;
 			int p = (int)(((float)(counter.getCeil() - counter.read()) / (float)counter.getCeil()) * 100);
			System.out.print("\b\b\b\b");
			System.out.print(String.format("%3d", p) + "%");
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.print("\b\b\b\b");
	}

}
