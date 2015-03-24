package com.agronomia.uc.spectrometer.procedures;

import com.agronomia.uc.spectrometer.JazScriptSyntax;
import com.agronomia.uc.spectrometer.ScriptProcedures;
import com.agronomia.uc.spectrometer.ScriptVariables;
import com.agronomia.uc.spectrometer.variables.Variable;

public class GetTheMaxRef extends Procedure {

	ScriptVariables scriptVariables;
	ScriptProcedures scriptProcedures;
	
	Variable spectrumChannel;
	Variable highCollectedSpectrum;
	Variable light1;	
	
	Variable highReferenceFile;
	
	public GetTheMaxRef(ScriptVariables scriptVariables, ScriptProcedures scriptProcedures) 
	{
		super("GetTheMaxRef");
		
		this.scriptProcedures = scriptProcedures;
		this.scriptVariables = scriptVariables;
		
		spectrumChannel = scriptVariables.findOrAdd(new Variable("SpectrumChannel", Variable.type_int_16));
		highCollectedSpectrum = scriptVariables.findOrAdd(new Variable("HighCollectedSpectrum", Variable.type_spectral));
		light1 = scriptVariables.findOrAdd(new Variable("Light1", Variable.type_int_32));
			
		highReferenceFile = scriptVariables.findOrAdd(new Variable("HighReferenceFile", Variable.type_file, "EspectroBlanco.txt", Variable.file_type_csv));
				
		
	}

	public void addBodyInstructions()
	{
		
		addInstruction(JazScriptSyntax.comment("Turns on lamp, takes high reference"));
		addInstruction(JazScriptSyntax.setLampShutter("0", "1"));
		addInstruction(JazScriptSyntax.setLampIntensity("0", "ALLBULBS", light1.getName()));
		addInstruction(JazScriptSyntax.pause("1"));
		addInstruction(JazScriptSyntax.getSpectrum(spectrumChannel.getName(), highCollectedSpectrum.getName()));
		addInstruction(JazScriptSyntax.displayMsg("Taking High$Reference"));
		addInstruction(JazScriptSyntax.pause("2"));
		addInstruction(JazScriptSyntax.showGraph(highCollectedSpectrum.getName()));
		
		
		addInstruction(JazScriptSyntax.openFile(highReferenceFile.getName(), "ForWrite", ""));
		addInstruction(JazScriptSyntax.writeSpectrum(highReferenceFile.getName(), highCollectedSpectrum.getName()));
		addInstruction(JazScriptSyntax.closeFile(highReferenceFile.getName()));
		
		
		addInstruction(JazScriptSyntax.pause("2"));		
		
	}	
	
	
}
