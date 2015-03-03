package com.agronomia.uc.spectrometer;

import com.agronomia.uc.spectrometer.constants.Constant;
import com.agronomia.uc.spectrometer.procedures.Procedure;
import com.agronomia.uc.spectrometer.variables.Variable;

public class Script {

	StringBuilder scriptBody;
	
	ScriptVariables scriptVariables;
	ScriptConstants scriptConstants;
	ScriptProcedures scriptProcedures;
	
	
	public Script()
	{
		this.scriptBody = new StringBuilder();
		
		this.scriptConstants = new ScriptConstants();
		this.scriptProcedures = new ScriptProcedures();
		this.scriptVariables = new ScriptVariables();
	}
	
	public void addInstruction(String line)
	{
		scriptBody.append(line + "\n");
	}	
	
	public void addVariable(Variable variable)
	{
		scriptVariables.add(variable);
	}
	
	public void addConstant(Constant constant)
	{
		scriptConstants.add(constant);
	}	
	
	public void addProcedure(Procedure procedure)
	{
		scriptProcedures.addProcedure(procedure);
	}		
	
	public String getCode()
	{
		return scriptBody.toString();
	} 
	
}
