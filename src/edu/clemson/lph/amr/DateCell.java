/**
 * Copyright Apr 10, 2019 Michael K Martin
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
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;

import edu.clemson.lph.amr.exceptions.ConfigException;

/**
 * 
 */
public class DateCell {
	SimpleDateFormat fmtShort = new SimpleDateFormat("MM/dd/yyyy");
	java.util.Date dRet = null;
	/**
	 * @throws ConfigException 
	 * 
	 */
	public DateCell(Cell c) throws ConfigException {
		CellType t = c.getCellTypeEnum();
		try {
			if( t == CellType.NUMERIC ) {
				Double dVal = c.getNumericCellValue();
				dRet = DateUtil.getJavaDate(dVal);
			}
			else {
				String sVal = c.toString();
				dRet = fmtShort.parse(sVal);
			}
		} catch (Exception e) {
			throw new ConfigException( "Cannot parse date field: " + c.toString() );
		}
	}
	
	public java.util.Date toDate() {
		return dRet;
	}
	
	public String toString() {
		return fmtShort.format(dRet);
	}
}
