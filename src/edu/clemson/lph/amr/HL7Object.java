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
package edu.clemson.lph.amr;

import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.clemson.lph.amr.exceptions.XMLException;

/**
 * Provide common functionality for all HL7 objects
 */
public abstract class HL7Object {
	protected Document doc;
	protected AMRSpreadsheetRow row;
	protected Element me;
	
	/**
	 * Used only by root.
	 */
	protected HL7Object() {
		
	}
	/**
	 * @param doc Document containing this object
	 */
	public HL7Object(Document doc) throws XMLException {
		if((!(this instanceof OpuR25Document)) && doc == null)
			throw new XMLException("Attempt to create child object with null Document");
		this.doc = doc;
		this.row = null;
	}
	/**
	 * @param doc Document containing this object
	 */
	public HL7Object(Document doc, AMRSpreadsheetRow row) throws XMLException {
		if((!(this instanceof OpuR25Document)) && doc == null)
			throw new XMLException("Attempt to create child object with null Document");
		this.doc = doc;
		this.row = row;
	}
	
	public Element addElement( String tagName ) {
		Element e = doc.createElement(tagName);
		me.appendChild(e);
		return e;
	}
	
	public Element addElement( Element e ) {
		me.appendChild(e);
		return e;
	}
	
	public String toXMLString() throws XMLException {
		String sRet = null;
		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = transFactory.newTransformer();
			StringWriter buffer = new StringWriter();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.transform(new DOMSource(me),
					new StreamResult(buffer));
			sRet = buffer.toString();
		} catch (TransformerException e) {
			throw new XMLException(e);
		}
		return sRet;
	}
	
	public Element toElement() {
		return me;
	}

}
