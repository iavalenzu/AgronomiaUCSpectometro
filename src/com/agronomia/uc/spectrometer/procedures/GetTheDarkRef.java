package com.agronomia.uc.spectrometer.procedures;

import com.agronomia.uc.spectrometer.JazScriptSyntax;
import com.agronomia.uc.spectrometer.ScriptProcedures;
import com.agronomia.uc.spectrometer.ScriptVariables;
import com.agronomia.uc.spectrometer.variables.Variable;

public class GetTheDarkRef extends Procedure {
	
	ScriptVariables scriptVariables;
	ScriptProcedures scriptProcedures;
	
	Variable spectrumChannel;
	Variable collectedSpectrum;
	Variable light1;

	public GetTheDarkRef(ScriptVariables scriptVariables, ScriptProcedures scriptProcedures) {
		super("GetTheDarkRef");
		
		this.scriptProcedures = scriptProcedures;
		this.scriptVariables = scriptVariables;
		
		spectrumChannel = scriptVariables.findOrAdd(new Variable("SpectrumChannel", Variable.type_int_16));
		collectedSpectrum = scriptVariables.findOrAdd(new Variable("CollectedSpectrum", Variable.type_spectral));
		light1 = scriptVariables.findOrAdd(new Variable("Light1", Variable.type_int_32));
			
		
	}
	
	public void addBodyInstructions()
	{
		
		addInstruction(JazScriptSyntax.comment("Turns off lamp, takes dark reference, repowers lamp, adjusts IntTime"));
		addInstruction(JazScriptSyntax.setLampIntensity("0", "ALLBULBS", "0"));
		addInstruction(JazScriptSyntax.setLampShutter("0", "0"));
		addInstruction(JazScriptSyntax.pause("1"));
		addInstruction(JazScriptSyntax.getSpectrum(spectrumChannel.getName(), collectedSpectrum.getName()));
		addInstruction(JazScriptSyntax.displayMsg("Taking Dark$Reference"));
		addInstruction(JazScriptSyntax.pause("2"));
		addInstruction(JazScriptSyntax.showGraph(collectedSpectrum.getName()));
		addInstruction(JazScriptSyntax.pause("4"));
		
		addInstruction(JazScriptSyntax.setLampShutter("0", "1"));
		addInstruction(JazScriptSyntax.setLampIntensity("0", "ALLBULBS", light1.getName()));
		
	}

}
