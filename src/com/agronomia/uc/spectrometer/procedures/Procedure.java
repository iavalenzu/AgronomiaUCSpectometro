package com.agronomia.uc.spectrometer.procedures;

import com.agronomia.uc.spectrometer.JazScriptSyntax;

public class Procedure {

	private String name;
	private StringBuilder body;
	
	public Procedure(String name){
		this.name = name;
		this.body = new StringBuilder();
		
	}

	public void initialize(){
		
	}
	
	public void addInstruction(String line)
	{
		body.append(line + "\n");
	}	

	public void addBodyInstructions()
	{
		addInstruction(JazScriptSyntax.displayMsg(name));
		addInstruction(JazScriptSyntax.pause("5"));
	}
	
	public String getInstructions()
	{
		addInstruction(JazScriptSyntax.addProcess(this.name));
		addInstruction("");
		
		addBodyInstructions();
		
		addInstruction("//End of " + this.name + " procedure definition");
		addInstruction(JazScriptSyntax.end());
		addInstruction("");
		
		return body.toString();
	}
	
	public boolean equals(Procedure procedure)
	{
		return this.name.compareToIgnoreCase(procedure.name) == 0;
	}
	
	public String getName()
	{
		return this.name;
	}
	
}
