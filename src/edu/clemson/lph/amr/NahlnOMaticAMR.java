/**
 * Copyright Jan 24, 2018 Michael K Martin
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
 * 
 */
package edu.clemson.lph.amr;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import edu.clemson.lph.amr.exceptions.ConfigException;
import edu.clemson.lph.amr.mappings.LoincMap;
import edu.clemson.lph.amr.mappings.SnomedMap;
import edu.clemson.lph.dialogs.MessageDialog;

public class NahlnOMaticAMR {
	public static final Logger logger = Logger.getLogger(NahlnOMaticAMR.class.getName());
	static {
		// BasicConfigurator replaced with PropertyConfigurator.
		PropertyConfigurator.configure("LogConfig.txt");
		logger.setLevel(Level.INFO);
	}
	
	public static SnomedMap snomedMap;
	public static LoincMap loincMap;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ProcessingLoop loop;
		try {
			logger.setLevel(ConfigFile.getLogLevel());
			logger.info("Starting NAHLN-O-MATIC_AMR");
			snomedMap = new SnomedMap();
			loincMap = new LoincMap();
			loop = new ProcessingLoop();
			loop.start();
		} catch (ConfigException e) {
			// Record everywhere:  console, log, dialog
			e.printStackTrace();
			logger.error(e);
			MessageDialog.messageWait(null, "NAHLN-O-MATIC_AMR", "Configuration error: " + e.getMessage());
		}
	}

}
