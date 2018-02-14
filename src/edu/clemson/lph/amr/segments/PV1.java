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
package edu.clemson.lph.amr.segments;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.clemson.lph.amr.AMRSpreadsheetRow;
import edu.clemson.lph.amr.ConfigFile;
import edu.clemson.lph.amr.HL7Object;
import edu.clemson.lph.amr.datatypes.HD;
import edu.clemson.lph.amr.exceptions.ConfigException;
import edu.clemson.lph.amr.exceptions.XMLException;

/**
 * 
 */
public class PV1 extends HL7Object {

	/**
	 * @throws XMLException 
	 * 
	 */
	public PV1(Document doc, AMRSpreadsheetRow row) throws XMLException, ConfigException {
		super(doc);
		this.row = row;
		me = doc.createElement("PV1");
		Element e = doc.createElement("PV1.2");
		e.setTextContent("C");
		me.appendChild(e);
	}
	
	public void setCaseCoordinator(String sId, String sName) throws XMLException, ConfigException {
		Element cce = doc.createElement("PV1.7");
		Element ccxn = doc.createElement("XCN.2");
		if( sId != null && sId.trim().length() > 0 ) {
			Element cci = doc.createElement("XCN.1");
			cci.setTextContent(sId);
			cce.appendChild(cci);
		}
		Element ccf = doc.createElement("FN.1");
		if( sName != null && sName.trim().length() > 0 )
			ccf.setTextContent(sName);
		else
			ccf.setTextContent("MASK");
		ccxn.appendChild(ccf);
		cce.appendChild(ccxn);
		if( sId != null && sId.trim().length() > 0 ) {
			HD hd;
			hd = new HD(doc, "XCN.9", null, ConfigFile.getLabOID(), "ISO");
			cce.appendChild(hd.toElement());
		}

		me.appendChild(cce);
	}
	
	/**
	 * @param sAccession
	 * @throws ConfigException 
	 */
	public void setAccessionNumber(String sAccession) throws XMLException, ConfigException {
		Element an = doc.createElement("PV1.19");
		Element n = doc.createElement("CX.1");
		if( sAccession == null || sAccession.trim().length() == 0 )
			sAccession = "MASK";
		n.setTextContent(sAccession);
		an.appendChild(n);
		HD aa = new HD(doc, "CX.4", null, ConfigFile.getLabOID(), "ISO");
		an.appendChild(aa.toElement());
		me.appendChild(an);
	}
	
}
