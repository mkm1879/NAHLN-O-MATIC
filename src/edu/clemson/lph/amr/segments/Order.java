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
package edu.clemson.lph.amr.segments;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.clemson.lph.amr.AMRResult;
import edu.clemson.lph.amr.AMRSpreadsheetRow;
import edu.clemson.lph.amr.ConfigFile;
import edu.clemson.lph.amr.HL7Object;
import edu.clemson.lph.amr.datatypes.CWE;
import edu.clemson.lph.amr.datatypes.DateTime;
import edu.clemson.lph.amr.datatypes.EI;
import edu.clemson.lph.amr.exceptions.ConfigException;
import edu.clemson.lph.amr.exceptions.XMLException;
import edu.clemson.lph.amr.mappings.LocalMap;
import edu.clemson.lph.amr.mappings.LoincMap;
import edu.clemson.lph.utils.UniqueID;

/**
 * 
 */
public class Order extends HL7Object {
	private Element obr;
	private Element orc;

	/**
	 * @param doc
	 * @param row
	 * @throws XMLException
	 * @throws ConfigException 
	 */
	public Order(Document doc, AMRSpreadsheetRow row) throws XMLException, ConfigException {
		super(doc, row);
		me = doc.createElement("OPU_R25.ORDER");
		obr = doc.createElement("OBR");
		me.appendChild(obr);
		EI obr3 = new EI(doc, "OBR.3", UniqueID.getUniqueID("Order"), ConfigFile.getNahlnOMaticOID(), "ISO");
		obr.appendChild(obr3.toElement());
		LoincMap lmap = new LoincMap();
		CWE obr4 = lmap.getCWE(doc, "OBR.4", "C and S Panel");
		obr.appendChild(obr4.toElement());
		Element obr22 = doc.createElement("OBR.22");
		obr22.setTextContent(DateTime.formatDate(row.getDateofIsolation(), false));
		obr.appendChild(obr22);
		LocalMap local = new LocalMap();
		CWE obr31 = local.getCWE(doc, "OBR.31", row.getReasonforsubmission());
		obr.appendChild(obr31.toElement());		
		orc = doc.createElement("ORC");
		me.appendChild(orc);
		Element orc1 = doc.createElement("ORC.1");
		orc1.setTextContent("SC");
		orc.appendChild(orc1);
		EI orc4 = new EI(doc, "ORC.4", row.getUniqueSpecimenID(), ConfigFile.getProgramOID(), "ISO");
		orc.appendChild(orc4.toElement());
		Element orc5 = doc.createElement("ORC.5");
		orc5.setTextContent("CM");
		orc.appendChild(orc5);
		Result cultRes = new Result( doc, row );
		me.appendChild(cultRes.toElement());
		int iSeq = 1;
		for( AMRResult result : row.getResults() ) {
			if(result.getAntibiotic() != null && result.getMICValue() != null ) {
				Result res = new Result(doc, row, result, iSeq++);
				me.appendChild(res.toElement());
			}
		}
	}

}
