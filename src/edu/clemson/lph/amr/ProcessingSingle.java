/**
 * Copyright Feb 12, 2018 Michael K Martin
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

import java.io.File;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import edu.clemson.lph.amr.exceptions.ConfigException;
import edu.clemson.lph.dialogs.MessageDialog;
import edu.clemson.lph.dialogs.ProgressDialog;
import edu.clemson.lph.utils.FileUtils;
import edu.clemson.lph.utils.UniqueID;

/**
 * 
 */
public class ProcessingSingle extends Thread {
	public static final Logger logger = Logger.getLogger(NahlnOMaticAMR.class.getName());
	private ProgressDialog prog;
	private String sInbox;
	private String sOutbox;
	private String sErrorsbox;

	/**
	 * @throws ConfigException 
	 * 
	 */
	public ProcessingSingle() throws ConfigException {
		prog = new ProgressDialog(null, "NAHLN-O-MATIC_AMR", "Ready to process");
		prog.setAuto(true);
		prog.setVisible(true);
		sInbox = ConfigFile.getInBox();
		sOutbox = ConfigFile.getOutBox();
		sErrorsbox = ConfigFile.getErrorsBox();
	}

	@Override
	public void run() {
		try {
			IntPair retVal = step();
			prog.setVisible(false);
			prog.dispose();
			MessageDialog msg = new MessageDialog(null, "NAHLN-O-MATIC_AMR", retVal.iAccept + " messages accepted\n"
					+ retVal.iFail + " messages failed");
			msg.setButtons(MessageDialog.OK_ONLY);
			msg.setModal(true);
			msg.setVisible(true);
		}
		
		catch( Throwable e ) {
			logger.error("Unexpected exception in main loop", e);
			MessageDialog.messageWait(null, "NAHLN-O-MATIC_AMR", "Unexpected exception in main loop");
		}
		finally {
			logger.info("Exiting NAHLN-O-MATIC_AMR");
			UniqueID.save();
			System.exit(0);
		}
	}
	
	private void updateProgress( String sProgMsg, String sProgLabel ) {
		synchronized (prog) {
			try {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						prog.setMessage(sProgMsg);
						prog.setProgLabel(sProgLabel);
					}
				});
			}
			catch (Exception ex) {
				logger.error(ex);
			}
		}

	}
	
	private IntPair step() {
		Sender sender = null;
		IntPair retVal = new IntPair();
		try {
			if(ConfigFile.isSHICHost() )
				sender = new SHICPostSender();
			else
				sender = new NAHLNPostSender();
		} catch (ConfigException e1) {
			logger.error(e1);
		}
		File fDirIn = new File(sInbox);
		File fDirOut = new File(sOutbox);
		File fDirErrors = new File(sErrorsbox);
		
		for( File fFile : fDirIn.listFiles() ) {
			try {
				NahlnOMaticAMR.setCurrentFile(fFile);
				updateProgress("Reading " + fFile.getName(), "Progress ...");
				AMRWorkbook sheet = new AMRWorkbook(fFile);
				NahlnOMaticAMR.setCurrentSheet(sheet);
				boolean bHasErrors = false;
				String sID = null;
				while( sheet.hasNextSheet() ) {
					sheet.nextSheet();
					String sSheetName = sheet.getCurrentSheetName();
					updateProgress( "Processing " + sheet.getCurrentSheetName(), "Progress ...");
					NahlnOMaticAMR.setCurrentTab(sheet.getCurrentSheetName());
					while( sheet.hasNextRow() ) {
						try {
						AMRSpreadsheetRow row = sheet.nextRow();
						NahlnOMaticAMR.setCurrentRow(row);
						OpuR25Document opu = new OpuR25Document(row);
						String sMsg = opu.toXMLString();
						sID = opu.getUniqueSpecimen();
						updateProgress("Sending " + fFile.getName() + "\n" + sSheetName + "\n" + sID, "Progress ...");
						String sRet = sender.send(sMsg);
						String sMsgFile = null;
						String sAckFile = null;
						if(sRet.contains("<MSA.1>AA</MSA.1>")) {
							retVal.iAccept++;
							updateProgress(fFile.getName() + "\n" + sSheetName + "\n" + sID + " Accepted", "Waiting ...");
							sMsgFile = fDirOut.getAbsolutePath() + "/" + fFile.getName() + "_" + sID + ".xml";
							sAckFile = fDirOut.getAbsolutePath() + "/"  + fFile.getName() + "_" + sID + "_ACK.xml";
						}
						else {
							retVal.iFail++;
							bHasErrors = true;
							updateProgress(fFile.getName() + "_" + sID + " contained errors", "Waiting ...");
							sMsgFile = fDirErrors.getAbsolutePath() + "/"  + fFile.getName() + "_" + sID + ".xml";
							sAckFile = fDirErrors.getAbsolutePath() + "/"  + fFile.getName() + "_" + sID + "_ACK.xml";
						}
						FileUtils.writeTextFile(sMsg, sMsgFile);
						FileUtils.writeTextFile(sRet, sAckFile);
						} catch( Exception e) {
							retVal.iFail++;
							MessageDialog.messageWait(null, "NAHLN-O-MATIC_AMR",  fFile.getName() + "_" + sID + " contained errors");
						}
					}
				}
				if( bHasErrors )
					FileUtils.move( fFile, fDirErrors );
				else
					FileUtils.move( fFile, fDirOut );
			} catch (Throwable e) {
				logger.error("Error while processing " + NahlnOMaticAMR.getCurrentFile().getName()
						+ "\r\n Error: " + e.getMessage()
						+ "\r\n Sheet: " + NahlnOMaticAMR.getCurrentTab()
						+ "\r\n Column: " + NahlnOMaticAMR.getCurrentColumn()
						+ "\r\n Row:\r\n" + NahlnOMaticAMR.getCurrentRow().toString(), e);
				MessageDialog.messageWait(null, "NAHLN-O-MATIC_AMR", "Error while processing " + NahlnOMaticAMR.getCurrentFile().getName() 
						+ "\r\n Error: " + e.getMessage()
						+ "\r\n Sheet: " + NahlnOMaticAMR.getCurrentTab()
						+ "\r\n Column: " + NahlnOMaticAMR.getCurrentColumn()
						+ "\r\n Row:\r\n" + NahlnOMaticAMR.getCurrentRow().toString() );
				e.printStackTrace();
				FileUtils.move( fFile, fDirErrors );
			}
		}
		return retVal;
	}
	
	private void pause() {
		try {
			wait(10);
		} catch( InterruptedException e) {}
	}


}
