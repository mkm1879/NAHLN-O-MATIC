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

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.clemson.lph.amr.AMRSpreadsheetRow;
import edu.clemson.lph.amr.HL7Object;
import edu.clemson.lph.amr.exceptions.ConfigException;
import edu.clemson.lph.amr.exceptions.XMLException;
import edu.clemson.lph.utils.UniqueID;

/**
 * 
 */
public class AccDetail extends HL7Object {


	/**
	 * @param doc
	 * @throws XMLException
	 * @throws ConfigException 
	 */
	public AccDetail(Document doc, AMRSpreadsheetRow row) throws XMLException, ConfigException {
		super(doc, row);
		me = doc.createElement("OPU_R25.ACCESSION_DETAIL");
		addNK1();
	}
	
	private void addNK1() throws XMLException, ConfigException {
		Element nk1 = doc.createElement("NK1");
		Element nk11 = doc.createElement("NK1.1");
		nk11.setTextContent(UniqueID.getSessionID());
		nk1.appendChild(nk11);
		me.appendChild(nk1);
		addPatient();
		addSpecimen();
	}
	
	private void addPatient() throws XMLException, ConfigException {
		Patient pat = new Patient(doc, row);
		me.appendChild(pat.toElement());
	}
	
	private void addSpecimen() throws XMLException, ConfigException {
		Specimen spec = new Specimen(doc, row);
		me.appendChild(spec.toElement());
	}
}
