package com.agronomia.uc.spectrometer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextArea;

public class TextAreaLogger extends Logger {
	
	JTextArea jTextArea;
	DateFormat dateFormat;
	
	public TextAreaLogger(JTextArea jTextArea)
	{
		this.jTextArea = jTextArea;
		
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	}
	
	public void log(String message)
	{
		Date now = new Date();
		this.jTextArea.append(dateFormat.format(now) + ": " + message + "\n");
	}

	public void nl(int max) 
	{
		for(int i=0; i<max; i++){
			this.jTextArea.append("\n");
		}
		
	}

	public void print(String message) {
		this.jTextArea.append(message);
	}
	
}
