package com.agronomia.uc.spectrometer.procedures;

import com.agronomia.uc.spectrometer.JazScriptSyntax;
import com.agronomia.uc.spectrometer.ScriptProcedures;
import com.agronomia.uc.spectrometer.ScriptVariables;
import com.agronomia.uc.spectrometer.variables.Variable;

public class Calibrar extends Procedure{

	ScriptVariables scriptVariables;
	ScriptProcedures scriptProcedures;

	Variable collectedSpectrum;
	Variable spectrumChannel;
	
	Variable light1;
	Variable lightPeak;
	
	Variable userChoice;
	
	Procedure getTheDarkRef; 
	Procedure getTheLowRef;
	Procedure getTheMaxRef;
	
	public Calibrar(ScriptVariables _scriptVariables, ScriptProcedures _scriptProcedures)
	{
		super("Calibrar");
		scriptVariables = _scriptVariables;
		scriptProcedures = _scriptProcedures;
		
	}
	
	public void initialize()
	{
		spectrumChannel = scriptVariables.findOrAdd(new Variable("SpectrumChannel", Variable.type_int_16));
		collectedSpectrum = scriptVariables.findOrAdd(new Variable("CollectedSpectrum", Variable.type_spectral));

		light1 = scriptVariables.findOrAdd(new Variable("Light1", Variable.type_int_32));
		
		lightPeak = scriptVariables.findOrAdd(new Variable("LightPeak", Variable.type_int_16));
		userChoice = scriptVariables.findOrAdd(new Variable("UserChoice", Variable.type_int_16)); 
		
		getTheDarkRef = scriptProcedures.findOrAdd(new GetTheDarkRef(scriptVariables, scriptProcedures));
		getTheDarkRef.initialize();
		
		getTheLowRef = scriptProcedures.findOrAdd(new GetTheLowRef());
		getTheLowRef.initialize();
		
		getTheMaxRef = scriptProcedures.findOrAdd(new GetTheMaxRef(scriptVariables, scriptProcedures));
		getTheMaxRef.initialize();
	}	

	public void addBodyInstructions()
	{
		
		addInstruction(JazScriptSyntax.comment("Power Lamp"));
		addInstruction("");
		
		addInstruction(JazScriptSyntax.displayMsg("Light adjustment$in progress"));
		addInstruction(JazScriptSyntax.pause("2"));
		addInstruction("SetLampShutter(0,1)");
		addInstruction(JazScriptSyntax.assigment(light1.getName(), "4000"));
		addInstruction("");
		
		addInstruction(JazScriptSyntax.label("LightLoop"));
		addInstruction(JazScriptSyntax.setLampIntensity("0", "ALLBULBS", light1.getName()));
		addInstruction(JazScriptSyntax.getSpectrum("0", collectedSpectrum.getName()));
		addInstruction(JazScriptSyntax.locateWavelength(collectedSpectrum.getName(), "566", lightPeak.getName()));
		addInstruction("IF(" + collectedSpectrum.getName() + "[" + lightPeak.getName() + "] > 32000) GOTO Again");
		addInstruction(JazScriptSyntax.gotos("EndLight"));
		addInstruction("");
		
		addInstruction(JazScriptSyntax.label("Again"));
		addInstruction(light1.getName() + " := " + light1.getName() + " - 100");
		addInstruction(JazScriptSyntax.setLampIntensity("0", "ALLBULBS", light1.getName()));
		addInstruction(JazScriptSyntax.gotos("LightLoop"));
		addInstruction("");

		addInstruction(JazScriptSyntax.label("EndLight"));
		addInstruction("");

		addInstruction(JazScriptSyntax.displayMsg("Light adjustment$complete"));
		addInstruction(JazScriptSyntax.pause("2"));
		addInstruction(JazScriptSyntax.display("Max Int: $", collectedSpectrum.getName() + "[" + lightPeak.getName() + "]", ""));
		addInstruction(JazScriptSyntax.pause("2"));
		
		
		addInstruction(JazScriptSyntax.label("TOP"));
		addInstruction(JazScriptSyntax.showMenu("Take Dark Ref", "Take High Ref", "Continuar"));
		addInstruction(JazScriptSyntax.onButtonClick(userChoice.getName(), "30"));
		
		addInstruction("If(" + userChoice.getName() + " = 0) GOTO Dark");
		addInstruction("If(" + userChoice.getName() + " = 1) GOTO High");
		addInstruction("If(" + userChoice.getName() + " = 2) GOTO EXIT");
		addInstruction("");

		addInstruction(JazScriptSyntax.label("Dark"));
		addInstruction(JazScriptSyntax.call(getTheDarkRef.getName()));
		addInstruction(JazScriptSyntax.gotos("TOP"));
		addInstruction("");
	
		addInstruction(JazScriptSyntax.label("High"));
		addInstruction(JazScriptSyntax.call(getTheMaxRef.getName()));
		addInstruction(JazScriptSyntax.gotos("TOP"));
		addInstruction("");

		addInstruction(JazScriptSyntax.label("EXIT"));
		addInstruction("");
		
	}	
	
	
}

