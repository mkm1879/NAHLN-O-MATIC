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
package edu.clemson.lph.amr;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;

import edu.clemson.lph.amr.exceptions.ConfigException;
import edu.clemson.lph.dialogs.MessageDialog;
import edu.clemson.lph.utils.FileUtils;

/**
 * 
 */
public class AMRSpreadsheetRow {
	public static final Logger logger = Logger.getLogger(NahlnOMaticAMR.class.getName());
	private Row row;
	private ArrayList<String> headers;
	private ArrayList<AMRResult> results;
	
	/**
	 * 
	 */
	public AMRSpreadsheetRow(Row row, ArrayList<String> headers) {
		this.row = row;
		this.headers = headers;
		this.results = new ArrayList<AMRResult>();
		int iCol = 12;
		try {
		String sHeadAb = headers.get(iCol);
		while( sHeadAb != null && sHeadAb.trim().length() > 0 && iCol < headers.size() - 1 ) {
			String sAntibiotic = headers.get(iCol+1);
			NahlnOMaticAMR.setCurrentColumn(sAntibiotic);
			Cell cMIC = row.getCell(iCol+1);
			if( cMIC != null ) {
				NahlnOMaticAMR.setCurrentColumn(sAntibiotic + " MIC");
				String sMIC = row.getCell(iCol+1).toString();
				NahlnOMaticAMR.setCurrentColumn(sAntibiotic + " Interp");
				String sInterpretation = row.getCell(iCol+2).getStringCellValue();
				AMRResult res = new AMRResult(sAntibiotic, sMIC, sInterpretation);
				results.add(res);
			}
			iCol += 3;
			if( iCol >= headers.size() )
				break;
			sHeadAb = headers.get(iCol);
		}
		} catch( Throwable e ) {
			logger.error("Error in main loop while processing " + NahlnOMaticAMR.getCurrentFile().getName() 
					+ "\r\n Error: " + e.getMessage()
					+ "\r\n Sheet: " + NahlnOMaticAMR.getCurrentTab()
					+ "\r\n Column: " + NahlnOMaticAMR.getCurrentColumn()
					+ "\r\n Row:\r\n" + NahlnOMaticAMR.getCurrentRow().toString(), e);
			MessageDialog.messageWait(null, "NAHLN-O-MATIC_AMR", "Error in main loop while processing " + NahlnOMaticAMR.getCurrentFile().getName() 
					+ "\r\n Error: " + e.getMessage()
					+ "\r\n Sheet: " + NahlnOMaticAMR.getCurrentTab()
					+ "\r\n Column: " + NahlnOMaticAMR.getCurrentColumn()
					+ "\r\n Row:\r\n" + NahlnOMaticAMR.getCurrentRow().toString() );
			e.printStackTrace();
		}
	}


	public String getLaboratoryName() throws ConfigException {
		String sRet = null;
		String sHeader = headers.get(0);
		if( !sHeader.startsWith("Laboratory Name") )
			throw new ConfigException("Laboratory Name not found in column A");
		Cell c = row.getCell(0);
		if( c != null )
			sRet = c.toString();
		return sRet;
	}
	
	public String getUniqueSpecimenID() throws ConfigException {
		String sRet = null;
		String sHeader = headers.get(1);
		if( !sHeader.startsWith("Unique Specimen ID") )
			throw new ConfigException("Unique Specimen ID not found in column B");
		Cell c = row.getCell(1);
		if( c != null )
			sRet = c.toString();
		return sRet;
	}
	
	public String getStateofAnimalOrigin() throws ConfigException {
		String sRet = null;
		String sHeader = headers.get(2);
		if( !sHeader.startsWith("State of Animal Origin") )
			throw new ConfigException("State of Animal Origin not found in column C");
		Cell c = row.getCell(2);
		if( c != null )
			sRet = c.toString();
		return sRet;
	}
	
	public String getAnimalSpecies() throws ConfigException {
		String sRet = null;
		String sHeader = headers.get(3);
		if( !sHeader.startsWith("Animal Species") )
			throw new ConfigException("Animal Species not found in column D");
		Cell c = row.getCell(3);
		if( c != null )
			sRet = c.toString();
		return sRet;
	}

	public String getReasonforsubmission() throws ConfigException {
		String sRet = null;
		String sHeader = headers.get(4);
		if( !sHeader.startsWith("Reason for submission ") )
			throw new ConfigException("Reason for submission  not found in column E");
		Cell c = row.getCell(4);
		if( c != null )
			sRet = c.toString();
		return sRet;
	}

	public String getProgramName() throws ConfigException {
		String sRet = null;
		String sHeader = headers.get(5);
		if( !sHeader.startsWith("Program Name") )
			throw new ConfigException("Program Name not found in column F");
		Cell c = row.getCell(5);
		if( c != null )
			sRet = c.toString();
		return sRet;
	}

	public String getSpecimen() throws ConfigException {
		String sRet = null;
		String sHeader = headers.get(6);
		if( !sHeader.startsWith("Specimen") )
			throw new ConfigException("Specimen not found in column G");
		Cell c = row.getCell(6);
		if( c != null )
			sRet = c.toString();
		return sRet;
	}

	public String getBacterialOrganismIsolated() throws ConfigException {
		String sRet = null;
		String sHeader = headers.get(7);
		if( !sHeader.startsWith("Bacterial Organism Isolated") )
			throw new ConfigException("Bacterial Organism Isolated not found in column H");
		Cell c = row.getCell(7);
		if( c != null )
			sRet = c.toString();
		return sRet;
	}
	public String getSalmonellaSerotype() throws ConfigException {
		String sRet = null;
		String sHeader = headers.get(8);
		if( !sHeader.startsWith("Salmonella Serotype") )
			throw new ConfigException("Salmonella Serotype not found in column I");
		Cell c = row.getCell(8);
		if( c != null )
			sRet = c.toString();
		return sRet;
	}

	public String getFinalDiagnosis () throws ConfigException {
		String sRet = null;
		String sHeader = headers.get(9);
		if( !sHeader.startsWith("Final Diagnosis") )
			throw new ConfigException("Final Diagnosis not found in column J");
		Cell c = row.getCell(9);
		if( c != null )
			sRet = c.toString();
		return sRet;
	}

	public java.util.Date getDateofIsolation() throws ConfigException {
		java.util.Date dRet = null;
		String sHeader = headers.get(10);
		if( !sHeader.startsWith("Date of Isolation") )
			throw new ConfigException("Date of Isolation not found in column K");
		Cell c = row.getCell(10);
		if( c != null ) {
			dRet = (new DateCell(c)).toDate();
		}
		return dRet;
	}

	public java.util.Date getDateTested() throws ConfigException {
		java.util.Date dRet = null;
		String sHeader = headers.get(11);
		if( !sHeader.startsWith("Date Tested") )
			throw new ConfigException("Date Tested not found in column L");
		Cell c = row.getCell(11);
		if( c != null ) {
			dRet = (new DateCell(c)).toDate();
		}
		return dRet;
	}
	
	public ArrayList<AMRResult> getResults() {
		return results;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		int iCol = 0;
		String sHeadAb = headers.get(iCol);
		while( sHeadAb != null && sHeadAb.trim().length() > 0 && iCol < headers.size() - 1 ) {
			sb.append(headers.get(iCol));
			sb.append('=');
			Cell cell = row.getCell(iCol);
			if( cell != null ) {
				sb.append(cell.toString());
			}
			else {
				sb.append("NULL");
			}
			sb.append("\r\n");
			sHeadAb = headers.get(++iCol);
		}

		return sb.toString();
	}

}
