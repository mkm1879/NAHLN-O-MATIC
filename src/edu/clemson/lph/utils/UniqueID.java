/**
 * Copyright Jan 25, 2018 Michael K Martin
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.clemson.lph.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;

import edu.clemson.lph.amr.NahlnOMaticAMR;

/**
 * 
 */
public class UniqueID {
	public static final Logger logger = Logger.getLogger(NahlnOMaticAMR.class.getName());
	private static final String sFileName = "UniqueIDs.txt";
	private static Properties pSessionIDs;
	private static int iSessionID = 1;
	/**
	 * 
	 */
	public static String getUniqueID(String sPrefix) {
		Integer iRet = null;
		String sRet = null;
		if(pSessionIDs == null) {
			pSessionIDs = new Properties();
			File f = new File(sFileName);
			try {
				pSessionIDs.load(new FileReader(f));
			} catch (IOException e) {
				logger.info("UniqueIDs.txt not found.  Starting new");
			}
		}
		sRet = pSessionIDs.getProperty(sPrefix);
		if( sRet == null ) {
			sRet = "1";
		}
		iRet = Integer.parseInt(sRet);
		pSessionIDs.put(sPrefix, Integer.toString(iRet + 1));
		return sRet;
	}
	
	public static String getSessionID() {
		String sRet = Integer.toString(iSessionID++);
		return sRet;
	}
	
	public static void save() {
		if(pSessionIDs == null) return;
		File f = new File(sFileName);
		try {
			pSessionIDs.store(new FileWriter(f), "");
		} catch (IOException e) {
			logger.error("Could not write UniqueIDs.txt", e);
			e.printStackTrace();
		}
	}
	
}
