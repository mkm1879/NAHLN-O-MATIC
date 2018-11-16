/**
 * Copyright Jan 26, 2018 Michael K Martin
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
package edu.clemson.lph.amr.mappings;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.collections4.iterators.PeekingIterator;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;

import edu.clemson.lph.amr.NahlnOMaticAMR;
import edu.clemson.lph.amr.datatypes.CWE;
import edu.clemson.lph.amr.exceptions.XMLException;

/**
 * 
 */
public class SnomedMap {
	public static final Logger logger = Logger.getLogger(NahlnOMaticAMR.class.getName());
	private Workbook workbook = null;
	private HashMap<String, CodeValue> hMap = new HashMap<String, CodeValue>();
	/** TODO Make a static singleton hidden implementation
	 * 
	 */

	/**
	 * 
	 */
	public SnomedMap() {
		try {
			FileInputStream inputStream = new FileInputStream(new File("Config.xlsx"));
			workbook = new XSSFWorkbook(inputStream);
			Iterator<Sheet> sheetIterator = new PeekingIterator<Sheet>(workbook.iterator());
			while(sheetIterator.hasNext() ) {
				Sheet s = sheetIterator.next();
				if( "SNOMED".equals(s.getSheetName()) ) {
					Iterator<Row> rowIterator = s.iterator();
					while(rowIterator.hasNext()) {
						Row row = rowIterator.next();
						Cell c = row.getCell(0);
						String sLocal = c.getStringCellValue();
						c = row.getCell(1);
						String sCode = c.getStringCellValue();
						c = row.getCell(2);
						String sValue = c.getStringCellValue();
						hMap.put(sLocal, new CodeValue(sCode, sValue));
					}
				}
			}
		}catch( IllegalStateException e ) {
			logger.warn("Numeric cell read as string loading SnomedMap", e);
			e.printStackTrace();
		}catch( Exception e ) {
			logger.error(e);
			e.printStackTrace();
		}finally {
			try {
				if(workbook != null) workbook.close();
			} catch (IOException e) {}
		}
	}

	private class CodeValue {
		public String code;
		public String value;
		public CodeValue( String sCode, String sValue) {
			this.code = sCode;
			this.value = sValue;
		}
	}
	
	public CWE getCWE(Document doc, String sTag, String sLocal) throws XMLException {
		CWE cwe = null;
		CodeValue cv = hMap.get(sLocal);
		if(cv == null && "SPM.4".equals(sTag) )
			cv = new CodeValue("123038009","Specimen (specimen)");
		if( cv != null )
			cwe = new CWE(doc, sTag, cv.code, cv.value, "SCT", sLocal);
		else
			cwe = new CWE(doc, sTag, sLocal);
		return cwe;
	}
	
	public CWE getCWE(Document doc, String sTag, String sLocal, String sSerotype) throws XMLException {
		CWE cwe = null;
		String sSpSerotype;
		if( sSerotype != null && sSerotype.trim().length() > 0 )
			sSpSerotype = sLocal + " " + sSerotype;
		else
			sSpSerotype = sLocal;
		CodeValue cv = hMap.get(sSpSerotype);
		if( cv == null && sSerotype != null && sSerotype.trim().length() > 0 )
			cv = hMap.get(sLocal);
		if( cv != null )
			cwe = new CWE(doc, sTag, cv.code, cv.value, "SCT", sSpSerotype);
		else
			cwe = new CWE(doc, sTag, sSpSerotype);
		return cwe;
	}
}
