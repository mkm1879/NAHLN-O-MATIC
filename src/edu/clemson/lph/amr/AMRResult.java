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

/**
 * 
 */
public class AMRResult {
	private String sAntibiotic = "";
	private String sMICComparator = "";
	private Double dMICValue = null;
	private String sInterpretation = "";
	/**
	 * 
	 */
	public AMRResult( String sAntibiotic, String sMIC, String sInterpretation ) {
		this.sAntibiotic = sAntibiotic;
		this.sInterpretation = sInterpretation;
		splitMIC( sMIC );
	}
	
	private void splitMIC( String sMIC ) {
		if( sMIC == null || sMIC.trim().length() == 0) return;
		int iChar = 0;
		char cChar = sMIC.charAt(iChar);
		while( !Character.isDigit(cChar) ) {
			if(cChar == '<' || cChar == '>' || cChar == '=') {
				sMICComparator += cChar;
			}
			cChar = sMIC.charAt(++iChar);
		}
		String sNum = sMIC.substring(iChar);
		try {
			dMICValue = Double.parseDouble(sNum);
		} catch( NumberFormatException nfe ) {
			dMICValue = null;
		}
	}
	
	public String getAntibiotic() {
		return sAntibiotic;
	}
	public String getMICComparator() {
		return sMICComparator;
	}
	public Double getMICValue() {
		return dMICValue;
	}
	public String getInterpretation() {
		return sInterpretation;
	}


}
