package com.agronomia.uc.spectrometer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class LoadModelRow extends JPanel {

	JDialog parent;
	
	JTextField variableNameTextField1;
	JTextField unitsTextField1;
	JTextField fileNameLabel1;
	
	JTextField iniIndexSpectrumTextField;
	JTextField endIndexSpectrumTextField;
	
	public LoadModelRow(JDialog parent)
	{
		this.parent = parent;
		
        JLabel unitsTitleLabel1 = new JLabel("Units");
        unitsTextField1 = new JTextField();
        unitsTextField1.setColumns(5);
        
        JLabel variableNameLabel1 = new JLabel("Variable Name");
        variableNameTextField1 = new JTextField();
        variableNameTextField1.setColumns(5);


        JLabel iniIndexSpectrumLabel = new JLabel("Desde");
        iniIndexSpectrumTextField = new JTextField("500");
        iniIndexSpectrumTextField.setColumns(5);
        

        JLabel endIndexSpectrumLabel = new JLabel("Hasta");
        endIndexSpectrumTextField = new JTextField("1500");
        endIndexSpectrumTextField.setColumns(5);
        
        fileNameLabel1 = new JTextField();
        fileNameLabel1.setColumns(20);
        fileNameLabel1.setEditable(false);
        
        JButton chooseModelButton1 = new JButton("Seleccionar");
        
        chooseModelButton1.addActionListener(new ActionListener() {
    	  public void actionPerformed(ActionEvent evt) {

				JFileChooser fc = new JFileChooser();

				fc.addChoosableFileFilter(new FileNameExtensionFilter(
						"Matlab model", "mat"));

				int returnVal = fc.showOpenDialog(parent);

				if (returnVal == JFileChooser.APPROVE_OPTION) 
				{
					String modelFilePath = fc.getSelectedFile().getAbsolutePath();
					fileNameLabel1.setText(modelFilePath);
					
				}
    	  
    	  }
        });   
        
        add(variableNameLabel1);
        add(variableNameTextField1);
        add(unitsTitleLabel1);
        add(unitsTextField1);
        
        add(iniIndexSpectrumLabel);
        add(iniIndexSpectrumTextField);
        
        add(endIndexSpectrumLabel);
        add(endIndexSpectrumTextField);
        
        add(fileNameLabel1);
        add(chooseModelButton1);
		
	}
	
	public String getVariableName()
	{
		return variableNameTextField1.getText();
	}

	public String getUnitsName()
	{
		return unitsTextField1.getText();
	}
	
	public String getModelFilePath()
	{
		return fileNameLabel1.getText();
	}
	
	public int getIniIndex()
	{
		
		String value = iniIndexSpectrumTextField.getText().trim();
		
		if(value.isEmpty()){
			return 0;
		}
		
		return new Integer(value).intValue();
	}

	public int getEndIndex()
	{
		String value = endIndexSpectrumTextField.getText().trim();
		
		if(value.isEmpty()){
			return 0;
		}
		
		return new Integer(value).intValue();
	}
	
	public int getModelLength()
	{
		return getEndIndex() - getIniIndex();
	}
	
	public boolean isAvailable()
	{
		return !fileNameLabel1.getText().trim().isEmpty() && 
				!variableNameTextField1.getText().trim().isEmpty() &&
				!unitsTextField1.getText().trim().isEmpty();
	}
	
	
}
