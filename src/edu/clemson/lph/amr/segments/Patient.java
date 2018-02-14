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
import edu.clemson.lph.amr.NahlnOMaticAMR;
import edu.clemson.lph.amr.datatypes.CWE;
import edu.clemson.lph.amr.exceptions.ConfigException;
import edu.clemson.lph.amr.exceptions.XMLException;
import edu.clemson.lph.amr.mappings.LoincMap;
import edu.clemson.lph.amr.mappings.SnomedMap;

/**
 * 
 */
public class Patient extends HL7Object {
	private Element pid;
	/**
	 * @param doc
	 * @param row
	 * @throws XMLException
	 * @throws ConfigException 
	 */
	public Patient(Document doc, AMRSpreadsheetRow row) throws XMLException, ConfigException {
		super(doc, row);
		pid = doc.createElement("PID");
		me = doc.createElement("OPU_R25.PATIENT");
		pid = doc.createElement("PID");
		addPID3();
		addPID5();
		addPID30();
		addPID35();
		me.appendChild(pid);
		addDxObx();
	}
	
	private void addPID3() {
		Element pid3 = doc.createElement("PID.3");
		Element cx1 = doc.createElement("CX.1");
		cx1.setTextContent("MASK");
		pid3.appendChild(cx1);
		pid.appendChild(pid3);
	}
	
	private void addPID5() {
		Element pid5 = doc.createElement("PID.5");
		Element xpn1 = doc.createElement("XPN.1");
		Element fn1 = doc.createElement("FN.1");
		fn1.setTextContent("NA");
		pid5.appendChild(xpn1);
		xpn1.appendChild(fn1);
		pid.appendChild(pid5);
	}

	private void addPID30() {
		Element pid30 = doc.createElement("PID.30");
		pid30.setTextContent("N");
		pid.appendChild(pid30);
	}
	
	private void addPID35() throws XMLException, ConfigException {
		SnomedMap map = NahlnOMaticAMR.snomedMap;
		CWE pid35 = map.getCWE(doc, "PID.35", row.getAnimalSpecies());
		pid.appendChild(pid35.toElement());
		
	}
	
	private void addDxObx() throws XMLException, ConfigException {
		LoincMap map = NahlnOMaticAMR.loincMap;
		Element ptObs = doc.createElement("OPU_R25.PATIENT_OBSERVATION");
		me.appendChild(ptObs);
		Element ptObx = doc.createElement("OBX");
		ptObs.appendChild(ptObx);
		Element obx2 = doc.createElement("OBX.2");
		obx2.setTextContent("ST");
		ptObx.appendChild(obx2);
		CWE obx3 = map.getCWE(doc, "OBX.3", "Final Diagnosis");
		ptObx.appendChild(obx3.toElement());
		Element obx5 = doc.createElement("OBX.5");
		obx5.setTextContent(row.getFinalDiagnosis());
		ptObx.appendChild(obx5);
		Element obx11 = doc.createElement("OBX.11");
		obx11.setTextContent("F");
		ptObx.appendChild(obx11);
		
	}


}
