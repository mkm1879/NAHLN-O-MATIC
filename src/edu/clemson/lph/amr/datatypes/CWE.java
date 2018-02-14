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

import edu.clemson.lph.amr.HL7Object;
import edu.clemson.lph.amr.exceptions.XMLException;

/**
 *  
 */
public class CWE extends HL7Object {

	/**
	 * 
	 * @param doc
	 * @param tagName
	 * @param sCWE1
	 * @param sCWE2
	 * @param sCWE3
	 * @param sCWE4
	 * @param sCWE5
	 * @param sCWE6
	 * @param sCWE9
	 * @throws XMLException
	 */
	public CWE(Document doc, String tagName, String sCWE1, String sCWE2, String sCWE3
			, String sCWE4, String sCWE5, String sCWE6, String sCWE9) throws XMLException {
		super(doc);
		me = doc.createElement(tagName);
		addComponent("CWE.1",sCWE1);
		addComponent("CWE.2",sCWE2);
		addComponent("CWE.3",sCWE3);
		addComponent("CWE.4",sCWE4);
		addComponent("CWE.5",sCWE5);
		addComponent("CWE.6",sCWE6);
		addComponent("CWE.9",sCWE9);
	}
	
	public CWE(Document doc, String tagName, String sCWE1, String sCWE2, String sCWE3) throws XMLException {
		super(doc);
		me = doc.createElement(tagName);
		addComponent("CWE.1",sCWE1);
		addComponent("CWE.2",sCWE2);
		addComponent("CWE.3",sCWE3);
	}
	
	public CWE(Document doc, String tagName, String sCWE1, String sCWE2, String sCWE3, String sCWE9) throws XMLException {
		super(doc);
		me = doc.createElement(tagName);
		addComponent("CWE.1",sCWE1);
		addComponent("CWE.2",sCWE2);
		addComponent("CWE.3",sCWE3);
		addComponent("CWE.9",sCWE9);
	}
	
	public CWE(Document doc, String tagName, String sCWE9) throws XMLException {
		super(doc);
		me = doc.createElement(tagName);
		addComponent("CWE.9",sCWE9);
	}

	private void addComponent(String sName, String sValue) {
		if( sName == null || sName.trim().length() == 0 || sValue == null || sValue.trim().length() == 0 ) return;
		Element e = doc.createElement(sName);
		e.setTextContent(sValue);
		me.appendChild(e);
	}

}
