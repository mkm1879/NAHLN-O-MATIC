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
package edu.clemson.lph.amr.datatypes;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.clemson.lph.amr.exceptions.XMLException;
import edu.clemson.lph.amr.HL7Object;

/**
 * 
 */
public class HD extends HL7Object {

	public HD(Document doc, String tagName, String hd1text, String hd2text, String hd3text ) throws XMLException {
		super(doc);
		me = doc.createElement(tagName);
		if(hd1text != null && hd1text.trim().length() > 0 ) {
			Element hd1 = doc.createElement("HD.1");
			hd1.setTextContent(hd1text);
			me.appendChild(hd1);
		}
		if(hd2text != null && hd2text.trim().length() > 0 ) {
			Element hd2 = doc.createElement("HD.2");
			hd2.setTextContent(hd2text);
			me.appendChild(hd2);
		}
		if(hd3text != null && hd3text.trim().length() > 0 ) {
			Element hd3 = doc.createElement("HD.3");
			hd3.setTextContent(hd3text);
			me.appendChild(hd3);
		}
	}
}
