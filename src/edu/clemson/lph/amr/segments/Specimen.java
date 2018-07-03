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

import edu.clemson.lph.amr.AMRSpreadsheetRow;
import edu.clemson.lph.amr.ConfigFile;
import edu.clemson.lph.amr.HL7Object;
import edu.clemson.lph.amr.NahlnOMaticAMR;
import edu.clemson.lph.amr.datatypes.CWE;
import edu.clemson.lph.amr.datatypes.DateTime;
import edu.clemson.lph.amr.datatypes.EI;
import edu.clemson.lph.amr.exceptions.ConfigException;
import edu.clemson.lph.amr.exceptions.XMLException;
import edu.clemson.lph.amr.mappings.SnomedMap;
import edu.clemson.lph.utils.UniqueID;

/**
 * 
 */
public class Specimen extends HL7Object {
	private Element spm;
	/**
	 * @param doc
	 * @param row
	 * @throws XMLException
	 * @throws ConfigException 
	 * @throws DOMException 
	 */
	public Specimen(Document doc, AMRSpreadsheetRow row) throws XMLException, DOMException, ConfigException {
		super(doc, row);
		me = doc.createElement("OPU_R25.SPECIMEN");
		spm = doc.createElement("SPM");
		me.appendChild(spm);
		addSPM2();
		addSPM4();
		addSPM11();
		addSPM17();
		addSPM18();
		addOrder();
	}

	private void addSPM2() throws DOMException, ConfigException, XMLException {
		Element spm2 = doc.createElement("SPM.2");
		spm.appendChild(spm2);
		NahlnOMaticAMR.setCurrentColumn("Specimen ID");
		EI eip1 = new EI(doc, "EIP.1", row.getUniqueSpecimenID(), ConfigFile.getProgramOID(),"ISO");
		spm2.appendChild(eip1.toElement());
		EI eip2 = new EI(doc, "EIP.2", UniqueID.getUniqueID("SPM"), ConfigFile.getNahlnOMaticOID(), "ISO");
		spm2.appendChild(eip2.toElement());
	}
	
	private void addSPM4() throws XMLException, ConfigException {
		SnomedMap snomed = NahlnOMaticAMR.snomedMap;
		NahlnOMaticAMR.setCurrentColumn("Specimen Type");
		CWE spm4 = snomed.getCWE(doc, "SPM.4", row.getSpecimen());
		spm.appendChild(spm4.toElement());
	}
	
	
	private void addSPM11() throws XMLException, ConfigException {
		SnomedMap snomed = NahlnOMaticAMR.snomedMap;
		CWE spm11 = new CWE(doc, "SPM.11", "P", "Patient", "HL70369");
		spm.appendChild(spm11.toElement());
	}
	
	private void addSPM17() throws XMLException, ConfigException {
		Element spm17 = doc.createElement("SPM.17");
		Element dr1 = doc.createElement("DR.1");
		spm17.appendChild(dr1);
		NahlnOMaticAMR.setCurrentColumn("Date Of Isolation");
		dr1.setTextContent(DateTime.formatDate(row.getDateofIsolation(), false));
		spm.appendChild(spm17);
	}
	
	private void addSPM18() throws XMLException, ConfigException {
		Element spm18 = doc.createElement("SPM.18");
		NahlnOMaticAMR.setCurrentColumn("Date Of Isolation");
		spm18.setTextContent(DateTime.formatDate(row.getDateofIsolation(), false));
		spm.appendChild(spm18);
	}
	
	private void addOrder()  throws XMLException, ConfigException {
		Order order = new Order(doc, row);
		me.appendChild(order.toElement());
	}

}
