package edu.clemson.lph.dialogs;
/*
Copyright 2014 Michael K Martin

This file is part of Civet.

Civet is free software: you can redistribute it and/or modify
it under the terms of the Lesser GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Civet is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the Lesser GNU General Public License
along with Civet.  If not, see <http://www.gnu.org/licenses/>.
*/
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class ProgressDialog extends JDialog {
	protected JPanel panel1 = new JPanel();
	private  JProgressBar jProgressBar1 = new JProgressBar();
	private JLabel lProgress = new JLabel();
	private int iValue;
	private int iMax = 9;
	private int iClients = 0;
	private boolean bAuto = false;
	private boolean bCancel = false;
	private String sMsg = "Working ...";
	private JTextArea lMsg = new JTextArea();
	private ThreadCancelListener cancelListener = null;
	private JButton btnCancel;
	private String sProgLabel = "Waiting ...";

	/**
	 * Create the dialog.
	 */
	public ProgressDialog() {
		try {
			initGUI();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ProgressDialog( Window parent, String sTitle, String sMsg ) {
		super( parent );
		this.setTitle(sTitle);
		this.sMsg = sMsg;
		try {
			initGUI();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Window getWindowParent() {
		Window wRet = null;
		Container cParent = getParent();
		while( cParent != null && !(cParent instanceof Window ) ) {
			cParent = cParent.getParent();
		}
		wRet = (Window)cParent;
		return wRet;
	}

	  public void setAuto( boolean bAuto ) {
	    this.bAuto = bAuto;
	    if( bAuto ) {
	      Thread t = new Thread( new Runnable() {
	        public void run() {
	          while( !bCancel ) {
	            synchronized (this) {
	              try {
	                wait(1000);
	                SwingUtilities.invokeLater(new Runnable() {
	                  public void run() {
	                    step();
	                  }
	                });
	              }
	              catch (InterruptedException ex) {
	              }
	            }
	          }
	        }
	      } );
	      t.start();
	    }
	  }
	  
	  public void setCancelListener( ThreadCancelListener l ) {
		  this.cancelListener = l;
		  btnCancel.setVisible(true);
	  }
	  
	  public void setMax( int iMax ) {
		  this.iMax = iMax;
		  jProgressBar1.setMaximum(iMax);
		  jProgressBar1.setMinimum(0);
	  }
	  
	  public void setMessage( String sMsg ) {
		  this.sMsg = sMsg;
		  lMsg.setText(sMsg);
	  }
	  
	  public void setProgLabel( String sLabel ) {
		  this.sProgLabel  = sLabel;
		  lProgress.setText(sProgLabel);
	  }

	  public void setValue( int iValue ) {
	    this.iValue = iValue;
	    jProgressBar1.setValue( iValue );
	  }

	  public void step() {
	    iValue = ( iValue + 1 ) % ( iMax + 1 );
	    jProgressBar1.setValue( iValue );
	  }

	  public void setVisible( boolean bVis ) {
	    if( bAuto )
	      setValue( iMax / 2 );
	    pack();
	    if( bVis ) bCancel = false;
	    else bCancel = true;
	    super.setVisible(bVis);
	  }

	  /**
	   * Todo rework as WindowBuilder dialog
	   * @throws Exception
	   */
	  private void initGUI() throws Exception {
	    lProgress.setBounds(295, 105, 106, 22);
	    lProgress.setText(sProgLabel);
	    jProgressBar1.setBounds(109, 129, 150, 24);
	    jProgressBar1.setMaximum(iMax);
	    jProgressBar1.setMinimum(0);
	    lMsg.setBounds(10, 22, 391, 76);
	    lMsg.setLineWrap(true);
	    lMsg.setWrapStyleWord(true);
	    lMsg.setFont(new Font("Dialog", Font.PLAIN, 12));
	    lMsg.setBackground(panel1.getBackground());
	    lMsg.setText(sMsg);
	    panel1.setPreferredSize(new Dimension(430, 200));
	    getContentPane().add(panel1);
	    panel1.setLayout(null);
	    panel1.add(lMsg);
	    panel1.add(jProgressBar1);
	    panel1.add(lProgress);
	    
	    btnCancel = new JButton("Cancel");
	    btnCancel.setBounds(168, 180, 91, 23);
	    panel1.add(btnCancel);
	    btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if( cancelListener != null ) {
					if( YesNoDialog.ask(ProgressDialog.this, "NAHLN-O-MATIC_AMR", "Are you sure you want to cancel " + getTitle() + "?") ) {
						cancelListener.cancelThread();
					}
				}
			}
	    });
	    btnCancel.setVisible(false);
	    
	  }
}
