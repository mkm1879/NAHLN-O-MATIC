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

import edu.clemson.lph.amr.HL7Object;
import edu.clemson.lph.amr.exceptions.XMLException;

/**
 * 
 */
public class EI extends HL7Object {

	public EI(Document doc, String tagName, String EI1text, String EI3text, String EI4text ) throws XMLException {
		super(doc);
		me = doc.createElement(tagName);
		if(EI1text != null && EI1text.trim().length() > 0 ) {
			Element EI1 = doc.createElement("EI.1");
			EI1.setTextContent(EI1text);
			me.appendChild(EI1);
		}
		if(EI3text != null && EI3text.trim().length() > 0 ) {
			Element EI3 = doc.createElement("EI.3");
			EI3.setTextContent(EI3text);
			me.appendChild(EI3);
		}
		if(EI4text != null && EI4text.trim().length() > 0 ) {
			Element EI4 = doc.createElement("EI.4");
			EI4.setTextContent(EI4text);
			me.appendChild(EI4);
		}
	}


}
