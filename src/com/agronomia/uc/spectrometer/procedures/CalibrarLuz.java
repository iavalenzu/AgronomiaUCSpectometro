package com.agronomia.uc.spectrometer.procedures;

import com.agronomia.uc.spectrometer.JazScriptSyntax;
import com.agronomia.uc.spectrometer.ScriptVariables;
import com.agronomia.uc.spectrometer.variables.Variable;

public class CalibrarLuz extends Procedure{

	ScriptVariables scriptVariables;

	Variable collectedSpectrum;
	Variable spectrumChannel;
	
	
	public CalibrarLuz(ScriptVariables _scriptVariables)
	{
		super("CalibrarLuz");
		scriptVariables = _scriptVariables;
		
	}
	
	public void initialize()
	{
		spectrumChannel = scriptVariables.findOrAdd(new Variable("SpectrumChannel", Variable.type_int_16));
		collectedSpectrum = scriptVariables.findOrAdd(new Variable("CollectedSpectrum", Variable.type_spectral));

	}	

	public void addBodyInstructions()
	{
		addInstruction(JazScriptSyntax.displayMsg("Calibrando Luz"));
		addInstruction(JazScriptSyntax.pause("2"));
		
		
		addInstruction(JazScriptSyntax.getSpectrum(spectrumChannel.getName(), collectedSpectrum.getName()));
		addInstruction(JazScriptSyntax.showGraph(collectedSpectrum.getName()));
		addInstruction(JazScriptSyntax.pause("5"));
		
		addInstruction("");
		
	}	
	
	
}

