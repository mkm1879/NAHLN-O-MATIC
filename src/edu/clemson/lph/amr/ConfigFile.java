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
 */
package edu.clemson.lph.amr;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import edu.clemson.lph.amr.exceptions.ConfigException;

/**
 * Read configuration from Excel file Config.xslx
 */
public class ConfigFile {
	public static final Logger logger = Logger.getLogger(NahlnOMaticAMR.class.getName());
	private static Properties props = null;
	private static void readConfig() {
		if( props != null ) return;
		Workbook workbook = null;
		FileInputStream inputStream = null;
		try {
			String excelFilePath = "Config.xlsx";
			inputStream = new FileInputStream(new File(excelFilePath));
			workbook = new XSSFWorkbook(inputStream);
			Sheet sheet = workbook.getSheet("Config");
			if( sheet == null )
				throw new ConfigException("Missing Config Tab");
			props = new Properties();
			Iterator<Row> rows = sheet.rowIterator();
			while( rows.hasNext() ) {
				Row row = rows.next();
				Cell cName = row.getCell(0);
				CellType type =  cName.getCellTypeEnum();
				if(type != CellType.STRING)
					throw new ConfigException("Name column not string");
				String sName = cName.getStringCellValue();
				Cell cValue = row.getCell(1);
				if( cValue == null )
					throw new ConfigException("Missing value column");
				type = cValue.getCellTypeEnum();
				String sValue = null;
				if( type == CellType.STRING ) {
					sValue = cValue.getStringCellValue();
				}
				if( type == CellType.NUMERIC ) {
					double nValue = cValue.getNumericCellValue();
					sValue = Double.toString(nValue);
				}
				if( type == CellType.BOOLEAN ) {
					Boolean bValue = cValue.getBooleanCellValue();
					if( bValue ) sValue = "true";
					else sValue = "false";
				}
				props.put(sName, sValue);
			}
		} catch(ConfigException | IOException cfe) {
			cfe.printStackTrace();
		} finally {
			if( inputStream != null )
				try {
					workbook.close();
					inputStream.close();
				} catch (IOException e) {}
		}
		
	}

	/**
	 * 
	 */
	public ConfigFile() {
		// TODO Auto-generated constructor stub
	}
	
	public static String getHost() throws ConfigException {
		readConfig();
		String sRet = (String)props.get("Host");
		if( sRet == null ) throw new ConfigException("Missing Host value");
		return sRet;
	}

	public static String getUser() throws ConfigException {
		readConfig();
		String sRet = (String)props.get("User");
		if( sRet == null ) throw new ConfigException("Missing User value");
		return sRet;
	}

	public static String getPassword() throws ConfigException {
		readConfig();
		String sRet = (String)props.get("Password");
		if( sRet == null ) throw new ConfigException("Missing Password value");
		return sRet;
	}

	public static String getInBox() throws ConfigException {
		readConfig();
		String sRet = (String)props.get("InBox");
		if( sRet == null ) throw new ConfigException("Missing InBox value");
		File f = new File(sRet);
		if( f == null || !f.isDirectory() ) throw new ConfigException("InBox (" + f.getAbsolutePath() + ") is not a folder");
		return sRet;
	}

	public static String getOutBox() throws ConfigException {
		readConfig();
		String sRet = (String)props.get("OutBox");
		if( sRet == null ) throw new ConfigException("Missing OutBox value");
		File f = new File(sRet);
		if( f == null || !f.isDirectory() ) throw new ConfigException("OutBox (" + f.getAbsolutePath() + ") is not a folder");
		return sRet;
	}

	public static String getErrorsBox() throws ConfigException {
		readConfig();
		String sRet = (String)props.get("ErrorsBox");
		if( sRet == null ) throw new ConfigException("Missing ErrorsBox value");
		File f = new File(sRet);
		if( f == null || !f.isDirectory() ) throw new ConfigException("ErrorsBox (" + f.getAbsolutePath() + ") is not a folder");
		return sRet;
	}
	
	public static String getMode() throws ConfigException {
		readConfig();
		String sRet = (String)props.get("Mode");
		if( sRet == null ) throw new ConfigException("Missing Mode value");
		if( sRet.equalsIgnoreCase("D") || sRet.equalsIgnoreCase("T") || sRet.equalsIgnoreCase("P") )
			return sRet;
		else 
			throw new ConfigException("Invalid Mode value (D, T, or P are valid)");
	}
	
	public static String getLabPIN() throws ConfigException {
		readConfig();
		String sRet = (String)props.get("LabPIN");
		if( sRet == null ) throw new ConfigException("Missing LabPIN value");
		if( sRet.trim().length() != 7 )
			throw new ConfigException("Lab PIN is not 7 characters");
		return sRet;
	}
	
	public static String getLabOID() throws ConfigException {
		readConfig();
		String sRet = (String)props.get("LabOID");
		if( sRet == null ) throw new ConfigException("Missing LabOID value");
		if( !sRet.startsWith("2.16.840.1.113883.3.5.") )
			throw new ConfigException("Lab OID doens't look like a lab OID");
		return sRet;
	}
	
	public static String getNahlnOMaticOID() throws ConfigException {
		readConfig();
		String sRet = (String)props.get("NahlnOMaticOID");
		if( sRet == null ) throw new ConfigException("Missing NahlnOMaticOID value");
		if( !sRet.startsWith("2.16.840.1.113883.3.5.") )
			throw new ConfigException("NahlnOMaticOID OID doens't look like an OID");
		return sRet;
	}
	
	public static String getProgramOID() throws ConfigException {
		readConfig();
		String sRet = (String)props.get("ProgramOID");
		if( sRet == null ) throw new ConfigException("Missing ProgramOID value");
		if( !sRet.startsWith("2.16.840.1.113883.3.5.") )
			throw new ConfigException("Program OID doens't look like an OID");
		return sRet;
	}
	
	public static Level getLogLevel() throws ConfigException {
		readConfig();
		String sRet = (String)props.get("LogLevel");
		if( sRet == null ) throw new ConfigException("Missing LogLevel value");
		if( sRet.equalsIgnoreCase("INFO") )
			return Level.INFO;
		else if( sRet.equalsIgnoreCase("ERROR") ) 
        	return Level.ERROR;
		else 
			throw new ConfigException("Invalid LogLevel value (INFO and ERROR are valid)");
	}

	/**
	 * @return
	 */
	public static String getSHICApiKey() throws ConfigException {
		readConfig();
		String sRet = (String)props.get("SHICApiKey");
		if( sRet == null ) throw new ConfigException("Missing SHICApiKey value");
		return sRet;
	}
	
	
	public static boolean isSHICHost() throws ConfigException {
		boolean bRet = false;
		readConfig();
		String sRet = (String)props.get("Host");
		if( sRet == null ) throw new ConfigException("Missing SHICHost value");
		if( sRet.contains("iastate.edu") ) bRet = true;
		return bRet;
	}
	
	public static String getProfileID() throws ConfigException {
		readConfig();
		String sRet = (String)props.get("ProfileID");
		if( sRet == null ) throw new ConfigException("Missing ProfileID value");
		return sRet;
	}
	
	/**
	 * Excel turns "15" into a Double rather than an Integer so this tries both
	 * @return
	 * @throws ConfigException
	 */
	public static int getPollSeconds() throws ConfigException {
		readConfig();
		int iRet = 10;
		String sRet = (String)props.get("PollSeconds");
		if( sRet == null ) throw new ConfigException("Missing PollSeconds value");
		try {
			iRet = Integer.parseInt(sRet);
		} catch( NumberFormatException e ) {
			try {
				Double dRet = Double.parseDouble(sRet);
				iRet = (int)Math.round(dRet);
			} catch( NumberFormatException e2 ) {
				logger.error("Could not parse \"" + sRet + "\" as an integer");
			}
		}
		return iRet;
	}

}
