package com.agronomia.uc.spectrometer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class LoadModelDialog extends JDialog implements ActionListener {

	
	JOptionPane optionPane;
	
    public LoadModelDialogAction loadModelDialogAction;
    
    private ModelReader[] readers;

    private  LoadModelRow loadPanelModel1;
    private  LoadModelRow loadPanelModel2;
    private  LoadModelRow loadPanelModel3;
    
	/** Creates the reusable dialog. */
    public LoadModelDialog(Frame aFrame) {
        super(aFrame, true);
        
        loadModelDialogAction = (LoadModelDialogAction) aFrame;
 
        setTitle("Cargar Modelos");
 

        JButton buttonLoad = new JButton("Load");
        JButton buttonCancel = new JButton("Cancel");
        
        
        JPanel optionsPane = new JPanel();
        optionsPane.add(buttonLoad);
        optionsPane.add(buttonCancel);
        
        buttonCancel.addActionListener(new ActionListener() {
        	  public void actionPerformed(ActionEvent evt) {
        		  loadModelDialogAction.onCancelAction();
        	  }
        	});        

        buttonLoad.addActionListener(new ActionListener() {
      	  public void actionPerformed(ActionEvent evt) {
      		  
      		try {
      			
          		readers = new ModelReader[3];
          		
				readers[0] = new ModelReader(loadPanelModel1.getModelFilePath());
				readers[0].setUnitName(loadPanelModel1.getUnitsName());
				readers[0].setVariableName(loadPanelModel1.getVariableName());
				readers[0].truncateCoefficients(loadPanelModel1.getIniIndex(), loadPanelModel1.getModelLength());

				readers[1] = new ModelReader(loadPanelModel2.getModelFilePath());
				readers[1].setUnitName(loadPanelModel2.getUnitsName());
				readers[1].setVariableName(loadPanelModel2.getVariableName());
				readers[1].truncateCoefficients(loadPanelModel2.getIniIndex(), loadPanelModel2.getModelLength());

				readers[2] = new ModelReader(loadPanelModel3.getModelFilePath());
				readers[2].setUnitName(loadPanelModel3.getUnitsName());
				readers[2].setVariableName(loadPanelModel3.getVariableName());
				readers[2].truncateCoefficients(loadPanelModel3.getIniIndex(), loadPanelModel3.getModelLength());
				
				
      		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
      		  
      		  
      		loadModelDialogAction.onLoadAction();
      	  }
      	});        
        
        
        JPanel centerPane = new JPanel();
        centerPane.setLayout(new BorderLayout());
        
        loadPanelModel1 = new LoadModelRow(this);
        loadPanelModel2 = new LoadModelRow(this);
        loadPanelModel3 = new LoadModelRow(this);
        
        centerPane.add(loadPanelModel1, BorderLayout.NORTH);
        centerPane.add(loadPanelModel2, BorderLayout.CENTER);
        centerPane.add(loadPanelModel3, BorderLayout.SOUTH);

        
        JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);
		
		contentPane.add(centerPane, BorderLayout.CENTER);
		contentPane.add(optionsPane, BorderLayout.SOUTH);
 
        //Make this dialog display it.
        setContentPane(contentPane);
 
        //Handle window closing correctly.
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                setVisible(false);	
            }
        });
 
 
        //setSize(750, 300);
        pack();
        setLocationRelativeTo(aFrame);
        
    }
    
    public ModelReader [] getReaders(){
    	return this.readers;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}	
	
	public interface LoadModelDialogAction
	{
		public void onLoadAction();
		public void onCancelAction();
	}
	
}
