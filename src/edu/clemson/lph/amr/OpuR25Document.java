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


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.clemson.lph.amr.exceptions.ConfigException;
import edu.clemson.lph.amr.exceptions.DataException;
import edu.clemson.lph.amr.exceptions.XMLException;
import edu.clemson.lph.amr.segments.AccDetail;
import edu.clemson.lph.amr.segments.MSH;
import edu.clemson.lph.amr.segments.PV1;
import edu.clemson.lph.amr.segments.ROL;

/**
 * 
 */
public class OpuR25Document extends HL7Object {
	public static final Logger logger = Logger.getLogger(NahlnOMaticAMR.class.getName());
	
	private AMRSpreadsheetRow row;

	private AccDetail det;
	/**
	 * @throws XMLException 
	 * 
	 */
	public OpuR25Document(AMRSpreadsheetRow row ) throws XMLException, ConfigException, DataException {
		super();
		this.row = row;
		DocumentBuilder db = null;
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = db.newDocument();
			doc.setXmlStandalone(true);
			me = doc.createElement("OPU_R25");
			addMSH();
			addPV1();
			addROL();
			addAccDetail();
			
		} catch (ParserConfigurationException  e) {
			throw new XMLException(e);
		}
	}
	
	private void addMSH() throws XMLException, ConfigException {
		MSH msh = new MSH(doc, row);
		msh.addHD("MSH.3", "NAHLN-O-MATIC", "2.16.840.1.113883.3.5.1.9", "ISO");
		msh.addHD("MSH.4", ConfigFile.getLabPIN(), "2.16.840.1.113883.3.5.6.1.1", "ISO");
		msh.addHD("MSH.5", "LMS", "2.16.840.1.113883.3.5.10", "ISO");
		msh.addHD("MSH.6", "0034P2K", "2.16.840.1.113883.3.5.6.1.4", "ISO");
		msh.addMSH7();
		msh.addMSH9();
		msh.addMSH10();
		msh.addMSH11(ConfigFile.getMode());
		msh.addMSH12();
		msh.addMSH21();
		// Flesh out msh calling setters.
		me.appendChild(msh.toElement());
	}
	
	private void addPV1() throws XMLException, ConfigException {
		PV1 pv1 = new PV1(doc, row);
		pv1.setCaseCoordinator("MASK", null);
		pv1.setAccessionNumber("MASK");
		me.appendChild(pv1.toElement());
	}
	
	private void addROL() throws XMLException, ConfigException, DataException {
		ROL rol = new ROL(doc, row, "PREM" );
		me.appendChild(rol.toElement());
	}
	
	private void addAccDetail() throws XMLException, ConfigException, DataException {
		det = new AccDetail(doc, row);
		me.appendChild(det.toElement());
	}


	public Document getDocument() {
		return doc;
	}
	
	public Element getRoot() {
		return me;
	}

	public String getUniqueSpecimen() throws ConfigException {
		String sRet = null;
		NahlnOMaticAMR.setCurrentColumn("Specimen ID");
		sRet = row.getUniqueSpecimenID();
		return sRet;
	}
}
