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

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.clemson.lph.amr.AMRResult;
import edu.clemson.lph.amr.AMRSpreadsheetRow;
import edu.clemson.lph.amr.ConfigFile;
import edu.clemson.lph.amr.HL7Object;
import edu.clemson.lph.amr.NahlnOMaticAMR;
import edu.clemson.lph.amr.datatypes.CWE;
import edu.clemson.lph.amr.datatypes.DateTime;
import edu.clemson.lph.amr.datatypes.EI;
import edu.clemson.lph.amr.datatypes.SN;
import edu.clemson.lph.amr.exceptions.ConfigException;
import edu.clemson.lph.amr.exceptions.XMLException;
import edu.clemson.lph.amr.mappings.LoincMap;
import edu.clemson.lph.amr.mappings.SnomedMap;
import edu.clemson.lph.utils.UniqueID;

/**
 * 
 */
public class Result extends HL7Object {
	private Element obx;
	/**
	 * Sensitivity Result
	 * 
	 * @param doc
	 * @param row
	 * @throws XMLException
	 * @throws ConfigException 
	 * @throws DOMException 
	 */
	public Result(Document doc, AMRSpreadsheetRow row, AMRResult result, int iSeq) throws XMLException, DOMException, ConfigException {
		super(doc, row);
		me = doc.createElement("OPU_R25.RESULT");
		obx = doc.createElement("OBX");
		me.appendChild(obx);
		Element obx2 = doc.createElement("OBX.2");
		obx2.setTextContent("SN");
		obx.appendChild(obx2);
		LoincMap loinc = new LoincMap();
		CWE obx3 = loinc.getCWE(doc, "OBX.3", result.getAntibiotic() );
		obx.appendChild(obx3.toElement());
		Element obx4 = doc.createElement("OBX.4");
		obx4.setTextContent("1." + Integer.toString(iSeq));  // AMR only one isolate per message.
		obx.appendChild(obx4);
		String sComp = result.getMICComparator();
		if( sComp == null || sComp.trim().length() == 0 )
			sComp = "=";
		SN obx5 = null;
		Double dVal = result.getMICValue();
		// Take care not to add non-significant digits if the spreadsheet contains only integer value.
		if( dVal == null )
			dVal = 0.0;
		if( dVal == dVal.intValue() ) 
			obx5 = new SN(doc, "OBX.5", sComp, Integer.toString(dVal.intValue()), (String)null, (String)null);
		else
			obx5 = new SN(doc, "OBX.5", sComp, Double.toString(dVal), (String)null, (String)null);
		obx.appendChild(obx5.toElement());
		CWE obx6 = new CWE(doc, "OBX.6", "ug/mL", "micrograms per milliliter", "ISO");
		obx.appendChild(obx6.toElement());
		Element obx8 = doc.createElement("OBX.8");
		obx8.setTextContent(result.getInterpretation());
		obx.appendChild(obx8);
		Element obx11 = doc.createElement("OBX.11");
		obx11.setTextContent("F");
		obx.appendChild(obx11);
		Element obx19 = doc.createElement("OBX.19");
		NahlnOMaticAMR.setCurrentColumn("DateTested");
		obx19.setTextContent(DateTime.formatDate(row.getDateTested(), false));
		obx.appendChild(obx19);
		EI obx21 = new EI(doc, "OBX.21", UniqueID.getUniqueID("Result"), ConfigFile.getNahlnOMaticOID(), "ISO" );
		obx.appendChild(obx21.toElement());
	}
	
	/**
	 * Culture Result
	 * @param doc
	 * @param row
	 * @throws XMLException
	 * @throws ConfigException 
	 */
	public Result(Document doc, AMRSpreadsheetRow row) throws XMLException, ConfigException {
		super(doc, row);
		me = doc.createElement("OPU_R25.RESULT");
		obx = doc.createElement("OBX");
		me.appendChild(obx);
		Element obx2 = doc.createElement("OBX.2");
		obx2.setTextContent("CWE");
		obx.appendChild(obx2);
		LoincMap loinc = new LoincMap();
		CWE obx3 = loinc.getCWE(doc, "OBX.3", "Culture" );
		obx.appendChild(obx3.toElement());
		Element obx4 = doc.createElement("OBX.4");
		obx4.setTextContent("1");  // AMR only one isolate per message.
		obx.appendChild(obx4);
		SnomedMap snomed = new SnomedMap();
		NahlnOMaticAMR.setCurrentColumn("BacteriaIsolated");
		String sBact = row.getBacterialOrganismIsolated();
		NahlnOMaticAMR.setCurrentColumn("SalmonellaSerotype");
		String sSal = row.getSalmonellaSerotype();
		CWE obx5 = snomed.getCWE(doc, "OBX.5", sBact, sSal );
		obx.appendChild(obx5.toElement());
		Element obx11 = doc.createElement("OBX.11");
		obx11.setTextContent("F");
		obx.appendChild(obx11);
		Element obx19 = doc.createElement("OBX.19");
		NahlnOMaticAMR.setCurrentColumn("DateOfIsolation");
		obx19.setTextContent(DateTime.formatDate(row.getDateofIsolation(), false));
		obx.appendChild(obx19);
		EI obx21 = new EI(doc, "OBX.21", UniqueID.getUniqueID("Result"), ConfigFile.getNahlnOMaticOID(), "ISO" );
		obx.appendChild(obx21.toElement());
	}

}
