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
package edu.clemson.lph.amr.segments;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.clemson.lph.amr.AMRWorkbook;
import edu.clemson.lph.amr.AMRSpreadsheetRow;
import edu.clemson.lph.amr.HL7Object;
import edu.clemson.lph.amr.datatypes.CWE;
import edu.clemson.lph.amr.exceptions.ConfigException;
import edu.clemson.lph.amr.exceptions.XMLException;

/**
 * Note this class is only for State-only PREM roles.  Way overly AMR specific for reuse.
 */
public class ROL extends HL7Object {

	/**
	 * @param doc
	 * @throws XMLException
	 * @throws ConfigException 
	 * @throws DOMException 
	 */
	public ROL(Document doc, AMRSpreadsheetRow row, String sRole) throws XMLException, DOMException, ConfigException {
		super(doc, row);
		this.row = row;
		me = doc.createElement("ROL");
		Element rol2 = doc.createElement("ROL.2");
		rol2.setTextContent("UC");
		me.appendChild(rol2);
		CWE rol3 = new CWE(doc, "ROL.3", "PREM", "Source Premises", "L");
		me.appendChild(rol3.toElement());
		me.appendChild(makeRol4());
		me.appendChild(makeRol11(getState()));
		
	}
	
	private Element makeRol4() {
		Element rol4 = doc.createElement("ROL.4");
		Element xcn2 = doc.createElement("XCN.2");
		Element fn1 = doc.createElement("FN.1");
		fn1.setTextContent("NA");
		xcn2.appendChild(fn1);
		rol4.appendChild(xcn2);
		return rol4;
	}
	
	
	private Element makeRol11(String sStateCode) {
		Element rol11 = doc.createElement("ROL.11");
		Element xad4 = doc.createElement("XAD.4");
		xad4.setTextContent(sStateCode);
		rol11.appendChild(xad4);
		return rol11;
	}
	
	private String getState() throws ConfigException {
		String sRet = row.getStateofAnimalOrigin();
		return sRet;
	}	
}
