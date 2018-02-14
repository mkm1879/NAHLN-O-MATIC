/**
 * Copyright Jan 29, 2018 Michael K Martin
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
package edu.clemson.lph.amr.datatypes;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.clemson.lph.amr.AMRSpreadsheetRow;
import edu.clemson.lph.amr.HL7Object;
import edu.clemson.lph.amr.exceptions.XMLException;

/**
 * 
 */
public class SN extends HL7Object {
	
	public SN(Document doc, String tagName, String SN1text, String SN2text, String SN3text, String SN4text ) throws XMLException {
		super(doc);
		me = doc.createElement(tagName);
		if(SN1text != null && SN1text.trim().length() > 0 ) {
			Element SN1 = doc.createElement("SN.1");
			SN1.setTextContent(SN1text);
			me.appendChild(SN1);
		}
		if(SN2text != null && SN2text.trim().length() > 0 ) {
			Element SN2 = doc.createElement("SN.2");
			SN2.setTextContent(SN2text);
			me.appendChild(SN2);
		}
		if(SN3text != null && SN3text.trim().length() > 0 ) {
			Element SN3 = doc.createElement("SN.3");
			SN3.setTextContent(SN3text);
			me.appendChild(SN3);
		}
		if(SN4text != null && SN4text.trim().length() > 0 ) {
			Element SN4 = doc.createElement("SN.4");
			SN4.setTextContent(SN4text);
			me.appendChild(SN4);
		}
	}

}
