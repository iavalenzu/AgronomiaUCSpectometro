package com.agronomia.uc.spectrometer.procedures;

import com.agronomia.uc.spectrometer.JazScriptSyntax;
import com.agronomia.uc.spectrometer.ScriptProcedures;
import com.agronomia.uc.spectrometer.ScriptVariables;
import com.agronomia.uc.spectrometer.variables.Variable;

public class GetTheDarkRef extends Procedure {
	
	ScriptVariables scriptVariables;
	ScriptProcedures scriptProcedures;
	
	Variable spectrumChannel;
	Variable darkCollectedSpectrum;
	Variable light1;
	
	Variable darkReferenceFile;

	public GetTheDarkRef(ScriptVariables scriptVariables, ScriptProcedures scriptProcedures) {
		super("GetTheDarkRef");
		
		this.scriptProcedures = scriptProcedures;
		this.scriptVariables = scriptVariables;
		
		spectrumChannel = scriptVariables.findOrAdd(new Variable("SpectrumChannel", Variable.type_int_16));
		darkCollectedSpectrum = scriptVariables.findOrAdd(new Variable("DarkCollectedSpectrum", Variable.type_spectral));
		light1 = scriptVariables.findOrAdd(new Variable("Light1", Variable.type_int_32));
			
		darkReferenceFile = scriptVariables.findOrAdd(new Variable("DarkReferenceFile", Variable.type_file, "EspectroNegro.txt", Variable.file_type_csv));
		
	}
	
	public void addBodyInstructions()
	{
		
		addInstruction(JazScriptSyntax.comment("Turns off lamp, takes dark reference, repowers lamp, adjusts IntTime"));
		addInstruction(JazScriptSyntax.setLampIntensity("0", "ALLBULBS", "0"));
		addInstruction(JazScriptSyntax.setLampShutter("0", "0"));
		addInstruction(JazScriptSyntax.pause("1"));
		addInstruction(JazScriptSyntax.getSpectrum(spectrumChannel.getName(), darkCollectedSpectrum.getName()));
		addInstruction(JazScriptSyntax.displayMsg("Taking Dark$Reference"));
		addInstruction(JazScriptSyntax.pause("2"));
		addInstruction(JazScriptSyntax.showGraph(darkCollectedSpectrum.getName()));
		
		addInstruction(JazScriptSyntax.openFile(darkReferenceFile.getName(), "ForWrite", ""));
		addInstruction(JazScriptSyntax.writeSpectrum(darkReferenceFile.getName(), darkCollectedSpectrum.getName()));
		addInstruction(JazScriptSyntax.closeFile(darkReferenceFile.getName()));
		
		addInstruction(JazScriptSyntax.pause("2"));
		
		addInstruction(JazScriptSyntax.setLampShutter("0", "1"));
		addInstruction(JazScriptSyntax.setLampIntensity("0", "ALLBULBS", light1.getName()));
		
	}

}
