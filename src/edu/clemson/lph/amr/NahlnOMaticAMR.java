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

import java.io.File;

import javax.swing.UIManager;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.ss.usermodel.Row;

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
	
	// Static properties just to track status for logging
	public static AMRSpreadsheetRow currentRow;
	public static void setCurrentRow( AMRSpreadsheetRow row ) {
		currentRow = row;
	}
	public static AMRSpreadsheetRow getCurrentRow() {
		return currentRow;
	}
	public static File currentFile;
	public static void setCurrentFile( File File ) {
		currentFile = File;
	}
	public static File getCurrentFile() {
		return currentFile;
	}
	public static AMRWorkbook currentSheet;
	public static void setCurrentSheet( AMRWorkbook sheet ) {
		currentSheet = sheet;
	}
	public static AMRWorkbook getCurrentSheet() {
		return currentSheet;
	}
	public static String currentTab;
	public static void setCurrentTab( String sTab ) {
		currentTab = sTab;
	}
	public static String getCurrentTab() {
		return currentTab;
	}
	public static String currentColumn;
	public static void setCurrentColumn( String sColumn ) {
		currentColumn = sColumn;
	}
	public static String getCurrentColumn() {
		return currentColumn;
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			logger.setLevel(ConfigFile.getLogLevel());
			logger.info("Starting NAHLN-O-MATIC_AMR");
			String os = System.getProperty("os.name");
			if( os.toLowerCase().contains("mac os") ) {
				System.setProperty("apple.laf.useScreenMenuBar", "true");
				System.setProperty("com.apple.mrj.application.apple.menu.about.name",
						"Civet");
				System.setProperty("com.apple.mrj.application.growbox.intrudes",
						"false");
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			}
			else {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
		} catch (ConfigException e) {
			// Record everywhere:  console, log, dialog
			e.printStackTrace();
			logger.error(e);
			MessageDialog.messageWait(null, "NAHLN-O-MATIC_AMR", "Configuration error: " + e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			snomedMap = new SnomedMap();
			loincMap = new LoincMap();
			if( args.length > 0 && args[0].equalsIgnoreCase("ROBOT") ) {
				ProcessingLoop loop = new ProcessingLoop();
				loop.start();
			}
			else {
				ProcessingSingle single = new ProcessingSingle();
				single.start();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

}
