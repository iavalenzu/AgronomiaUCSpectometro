package com.agronomia.uc.spectrometer.procedures;

import java.util.HashMap;
import java.util.Map;

import com.agronomia.uc.spectrometer.ScriptConstants;
import com.agronomia.uc.spectrometer.ScriptVariables;
import com.agronomia.uc.spectrometer.constants.Constant;
import com.agronomia.uc.spectrometer.variables.Variable;

public class MainMenu extends Procedure {

	ScriptVariables scriptVariables;
	ScriptConstants scriptConstants;
	
	Variable UserSelection;
	Constant TimeOutSeconds;
	
	HashMap<String, Procedure> options;
	
	public MainMenu(ScriptVariables _scriptVariables, ScriptConstants _scriptConstants){
		super("MainMenu");
		this.scriptVariables = _scriptVariables;
		this.scriptConstants = _scriptConstants;
		
	}
	
	public void initialize()
	{
		UserSelection = this.scriptVariables.findOrAdd(new Variable("UserSelection", Variable.type_int_16));
		TimeOutSeconds = this.scriptConstants.findOrAdd(new Constant("TimeOutSeconds", "60"));
	}
	
	
	public void setOptions(HashMap<String, Procedure> options)
	{
		this.options = options;
	}
	
	public void addBodyInstructions()
	{

		addInstruction("LABEL TOP");

		String ShowMenuInstruction = "ShowMenu(";
		String IfInstructions = "";
		String LabelInstructions = "";
		int itemPosition = 0;
		
		for (Map.Entry<String, Procedure> entry : options.entrySet()) 
		{
		    String key = entry.getKey();
		    Procedure procedure = entry.getValue();

			ShowMenuInstruction += "\"" + key + "\",";

			IfInstructions += "If(" + UserSelection.getName() + " = " + itemPosition + ") GOTO LabelOption" + itemPosition + "\n";

			LabelInstructions += "LABEL LabelOption" + itemPosition + "\n";
			LabelInstructions += "Call " + procedure.getName() + "\n";
			LabelInstructions += "GOTO TOP\n\n";
			
			itemPosition++;
		}	

		IfInstructions += "If(" + UserSelection.getName() + " = " + itemPosition + ") GOTO EXIT\n";
		
		ShowMenuInstruction += "\"Salir\"";
		ShowMenuInstruction += ")";
		
		LabelInstructions += "LABEL EXIT\n";
		LabelInstructions += "DisplayMsg(\"Good bye!\")\n";
		LabelInstructions += "Pause(5)\n";
		
		
		// Mostramos el menu de inicio
		addInstruction(ShowMenuInstruction);

		addInstruction("OnButtonClick(" + UserSelection.getName() + "," + TimeOutSeconds.getName() + ")");

		addInstruction(IfInstructions);
		addInstruction(LabelInstructions);
	
		
	}

}
