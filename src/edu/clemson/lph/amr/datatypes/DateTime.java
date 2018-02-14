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

import java.text.SimpleDateFormat;

import org.w3c.dom.Document;

import edu.clemson.lph.amr.HL7Object;
import edu.clemson.lph.amr.exceptions.XMLException;

/**
 * 
 */
public class DateTime extends HL7Object {
	static final SimpleDateFormat dfTimeStamp = new SimpleDateFormat("yyyyMMddHHmmssZ");
	static final SimpleDateFormat dfDate = new SimpleDateFormat("yyyyMMddZ");
	/**
	 * @param doc
	 * @param tagName
	 * @throws XMLException 
	 */
	public DateTime(Document doc, String tagName ) throws XMLException {
		super(doc);
		me = doc.createElement(tagName);
		me.setTextContent(getTimestamp());
	}

	/**
	 * @return
	 */
	public static String getTimestamp() {
		String sRet= null;
		java.util.Date dNow = new java.util.Date();
		sRet = dfTimeStamp.format(dNow);
		return sRet;
	}
	
	public static String formatDate( java.util.Date d, boolean bLong ) {
		String sRet = null;
		SimpleDateFormat df = dfDate;
		if( bLong ) df = dfTimeStamp;
		sRet = df.format(d);
		return sRet;
	}

}
