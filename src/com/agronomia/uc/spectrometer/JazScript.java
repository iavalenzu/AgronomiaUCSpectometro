package com.agronomia.uc.spectrometer;

import java.util.HashMap;
import java.util.Iterator;

import com.agronomia.uc.spectrometer.constants.Constant;
import com.agronomia.uc.spectrometer.procedures.LoteNuevo;
import com.agronomia.uc.spectrometer.procedures.MainMenu;
import com.agronomia.uc.spectrometer.procedures.Procedure;
import com.agronomia.uc.spectrometer.variables.Variable;

public class JazScript extends Script{
	
	
	String scriptName;
	String scriptVersion;	
	
	Variable UserSelection;
	Constant TimeOutSeconds;
	
	Procedure LoteNuevoProcedure;
	Procedure CrearModeloProcedure;
	Procedure MainMenu;
	
	Logger logger;
	
	public JazScript(String scriptName, String scriptVersion, Logger logger)
	{
		super();
		
		this.scriptName = scriptName;
		this.scriptVersion = scriptVersion;
		
		this.logger = logger;
	}

	public void addVariablesBlock()
	{
		logger.log("Añadiendo el bloque de variables...");
		
		addInstruction("VARIABLES");
		addInstruction("");
		
		Iterator<Variable> variables = scriptVariables.getIterator();
		
		while(variables.hasNext())
		{
			Variable variable = variables.next();
			System.out.println(variable.getName());
			addInstruction(variable.getInitInstruction());
		}
		
		addInstruction("");
		addInstruction(JazScriptSyntax.comment("End of Variables Block"));
	 	addInstruction("END");
	 	addInstruction("");
	}	
	
	public void addConstantsBlock()
	{
		logger.log("Añadiendo el bloque de constantes...");
		
		addInstruction("CONSTANTS");
		addInstruction("");
		
		Iterator<Constant> constants = scriptConstants.getIterator();
		
		while(constants.hasNext())
		{
			Constant constant = constants.next();
			addInstruction(constant.getInstruction());
		}
		
		addInstruction("");
		addInstruction(JazScriptSyntax.comment("End of Constants Block"));
		addInstruction("END");
	 	addInstruction("");
	}
	
	public void addProceduresBlock()
	{
		logger.log("Añadiendo el bloque de procedimientos...");
		
		Iterator<Procedure> procedures = scriptProcedures.getIterator();
		
		while(procedures.hasNext())
		{
			Procedure procedure = procedures.next();

			logger.log("Añadiendo el procedimiento: " + procedure.getName());
			addInstruction(procedure.getInstructions());
		 	addInstruction("");
		}
		
	}
	
	public void addMainProgramBlock()
	{
		logger.log("Añadiendo el bloque MainProgram...");

		addInstruction("START");
		addInstruction(JazScriptSyntax.displayMsg(this.scriptName + " " + this.scriptVersion));
		addInstruction(JazScriptSyntax.pause("2"));		
		addInstruction(JazScriptSyntax.call(MainMenu.getName()));
		addInstruction("");
		addInstruction(JazScriptSyntax.comment("End of Start Block"));
		addInstruction("END");
		addInstruction("");
		
	}
	
	public void initialize(ModelReader... modelReaders)
	{
		logger.log("Inicializando el Script...");
		
		
		LoteNuevoProcedure = scriptProcedures.findOrAdd(new LoteNuevo(scriptVariables, scriptProcedures, modelReaders));
		LoteNuevoProcedure.initialize();

		CrearModeloProcedure = scriptProcedures.findOrAdd(new Procedure("HacerCrearModelo"));
		CrearModeloProcedure.initialize();
		
		MainMenu = scriptProcedures.findOrAdd(new MainMenu(scriptVariables, scriptConstants));
		MainMenu.initialize();

		HashMap<String, Procedure> options = new HashMap<String, Procedure>();
		options.put("Lote nuevo", LoteNuevoProcedure);
		options.put("Crear Modelo", CrearModeloProcedure);
		
		/**
		 * Se agregan las opciones al menu, cada opcion corresponde a un procedimiento
		 */
		
		((MainMenu) MainMenu).setOptions(options);		
		
				
		
	}
	
	public void generate()
	{
		logger.log("Generando el codigo del Script...");

		addInstruction("SCRIPT " + this.scriptName);
		addInstruction("Version " + this.scriptVersion);
		addInstruction("");
		
		addVariablesBlock();
		
		addConstantsBlock();
		
		addMainProgramBlock();
		
		addProceduresBlock();
		
		addInstruction(JazScriptSyntax.comment("End of Script"));
		addInstruction("STOP");		
		
	}
		
}
