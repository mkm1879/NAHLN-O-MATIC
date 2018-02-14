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
package edu.clemson.lph.amr.segments;

import org.w3c.dom.*;

import edu.clemson.lph.amr.AMRSpreadsheetRow;
import edu.clemson.lph.amr.ConfigFile;
import edu.clemson.lph.amr.HL7Object;
import edu.clemson.lph.amr.datatypes.DateTime;
import edu.clemson.lph.amr.datatypes.HD;
import edu.clemson.lph.amr.exceptions.ConfigException;
import edu.clemson.lph.amr.exceptions.XMLException;
import edu.clemson.lph.utils.UniqueID;
/**
 * Note: MSH just uses position terminology because it is such a fixed format.
 * Other segments/groups will use business naming convention.
 */
public class MSH extends HL7Object {
	/**
	 * @throws XMLException 
	 * 
	 */
	public MSH(Document doc, AMRSpreadsheetRow row) throws XMLException {
		super(doc, row);
		this.row = row;
		me = doc.createElement("MSH");
		Element msh1 = doc.createElement("MSH.1");
		msh1.setTextContent("|");
		me.appendChild(msh1);
		Element msh2 = doc.createElement("MSH.2");
		msh2.setTextContent("^~\\&");
		me.appendChild(msh2);
	}
	
	/**
	 * Add any of the four HDs 
	 * @param tagName
	 * @param hd1text
	 * @param hd2text
	 * @param hd3text
	 * @return
	 * @throws XMLException
	 */
	public Element addHD( String tagName, String hd1text, String hd2text, String hd3text ) throws XMLException {
		HD hd = new HD(doc, tagName, hd1text, hd2text, hd3text);
		addElement(hd.toElement());
		return hd.toElement();
	}
	
	public Element addMSH7() {
		Element e = doc.createElement("MSH.7");
		e.setTextContent(DateTime.getTimestamp());
		me.appendChild(e);
		return e;
	}
	
	public Element addMSH9() {
		Element e = doc.createElement("MSH.9");
		Element msg1 = doc.createElement("MSG.1");
		msg1.setTextContent("OPU");
		e.appendChild(msg1);
		Element msg2 = doc.createElement("MSG.2");
		msg2.setTextContent("R25");
		e.appendChild(msg2);
		Element msg3 = doc.createElement("MSG.3");
		msg3.setTextContent("OPU_R25");
		e.appendChild(msg3);
		me.appendChild(e);
		return e;
	}
	
	public Element addMSH10() {
		String sUniqueID = UniqueID.getUniqueID("MSG");
		Element e = doc.createElement("MSH.10");
		e.setTextContent(sUniqueID);
		me.appendChild(e);
		return e;
	}
	
	public Element addMSH11(String sProcessingMode) {
		Element e = doc.createElement("MSH.11");
		Element pt1 = doc.createElement("PT.1");
		pt1.setTextContent(sProcessingMode);
		e.appendChild(pt1);
		me.appendChild(e);
		return e;
	}
	
	public Element addMSH12() {
		Element e = doc.createElement("MSH.12");
		Element vid1 = doc.createElement("VID.1");
		vid1.setTextContent("2.6");
		e.appendChild(vid1);
		me.appendChild(e);
		return e;
	}
	
	public Element addMSH21() throws DOMException, ConfigException {
		Element e = doc.createElement("MSH.21");
		Element ei1 = doc.createElement("EI.1");
		ei1.setTextContent(ConfigFile.getProfileID());
		e.appendChild(ei1);
		Element ei3 = doc.createElement("EI.3");
		ei3.setTextContent("2.16.840.1.113883.3.5.9");
		e.appendChild(ei3);
		Element ei4 = doc.createElement("EI.4");
		ei4.setTextContent("ISO");
		e.appendChild(ei4);
		me.appendChild(e);
		return e;
	}
	

}
