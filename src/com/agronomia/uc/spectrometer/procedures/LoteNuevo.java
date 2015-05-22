package com.agronomia.uc.spectrometer.procedures;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.agronomia.uc.spectrometer.JazScriptSyntax;
import com.agronomia.uc.spectrometer.ModelReader;
import com.agronomia.uc.spectrometer.ScriptProcedures;
import com.agronomia.uc.spectrometer.ScriptVariables;
import com.agronomia.uc.spectrometer.variables.Variable;


public class LoteNuevo extends Procedure {

	ModelReader[] modelReaders;
	ScriptVariables scriptVariables;
	ScriptProcedures scriptProcedures;
	
	Variable spectrumChannel;
	Variable integrationTime;
	
	Variable[] CoeffModel;
	Variable[] SumAcumModel;
	Variable[] TotalAcumModel;
	Variable[] AvgAcumModel;
	
	Variable collectedSpectrum;
	Variable gettingSpectrumChoice;
	Variable index;
	Variable numMediciones;
	Variable tmpIndex;
	Variable spectrumFiles;
	
	Variable loggerFile;
	
	Procedure calibrarLuz;
	
	public LoteNuevo(ScriptVariables _scriptVariables, ScriptProcedures _scriptProcedures, ModelReader... _modelReaders)
	{
		super("LoteNuevo");
		scriptVariables = _scriptVariables;
		scriptProcedures = _scriptProcedures;
		modelReaders = _modelReaders;
	}	
	
	public void initialize()
	{
		spectrumChannel = scriptVariables.findOrAdd(new Variable("SpectrumChannel", Variable.type_int_16));
		integrationTime = scriptVariables.findOrAdd(new Variable("IntegrationTime", Variable.type_real));
		collectedSpectrum = scriptVariables.findOrAdd(new Variable("CollectedSpectrum", Variable.type_spectral));

		gettingSpectrumChoice = scriptVariables.findOrAdd(new Variable("GettingSpectrumChoice", Variable.type_int_16)); 
		
		tmpIndex = scriptVariables.findOrAdd(new Variable("TmpIndex", Variable.type_int_16)); 
		
		spectrumFiles = scriptVariables.findOrAdd(new Variable("Spectrums", Variable.type_file, "Spectrums.txt", Variable.file_type_csv)); 
		
		CoeffModel = new Variable[modelReaders.length];
		SumAcumModel = new Variable[modelReaders.length]; 
		TotalAcumModel = new Variable[modelReaders.length]; 
		AvgAcumModel = new Variable[modelReaders.length];
		
		index = scriptVariables.findOrAdd(new Variable("Index", Variable.type_int_16));
		
		numMediciones = scriptVariables.findOrAdd(new Variable("NumMediciones", Variable.type_int_16));
		
		for(int i=0; i<modelReaders.length; ++i)
		{
			ModelReader model = modelReaders[i];
			double[]coefficients = model.getCoefficients();

			CoeffModel[i] = scriptVariables.findOrAdd(new Variable("CoeffModel" + i, Variable.type_real, coefficients.length));
			SumAcumModel[i] = scriptVariables.findOrAdd(new Variable(model.getVariableName(), Variable.type_real));
			
			TotalAcumModel[i] = scriptVariables.findOrAdd(new Variable("TotalAcum" + i, Variable.type_real));
			AvgAcumModel[i] = scriptVariables.findOrAdd(new Variable("Prom" + model.getVariableName(), Variable.type_real));
		}
		
		/**
		 * Se crea la variable que maneja el archivo de logs
		 */
		
		String fileName = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss'.txt'").format(new Date());
		
		loggerFile = scriptVariables.findOrAdd(new Variable("Logger", Variable.type_file, "Results.txt", Variable.file_type_ascii));
		
		/**
		 * Se obtiene el prodecimiendo encargado de calibrar el blanco y negro
		 */
		
		calibrarLuz = scriptProcedures.findOrAdd(new Calibrar(scriptVariables, scriptProcedures));
		calibrarLuz.initialize();
		
	}
	
	
	public void addBodyInstructions()
	{

		/*
		 * Set Variables		
		 */

		addInstruction(JazScriptSyntax.assigment(spectrumChannel.getName(), "0"));
		addInstruction(JazScriptSyntax.assigment(integrationTime.getName(), "0.0004"));
		addInstruction("");

		/*
		 * Set the display precision
		 */
		
		//addInstruction(JazScriptSyntax.setDisplayPrecision(8, 6));
		addInstruction("");
		
		/*
		 * Set the integration time
		 */

		
		/*
		addInstruction(JazScriptSyntax.display("Setting$Integration$time to$", integrationTime.getName(), "secs"));
		
		addInstruction(JazScriptSyntax.pause("2"));
		addInstruction(JazScriptSyntax.setIntegrationTime(integrationTime.getName()));
		addInstruction("");
*/

		/**
		 * Se define el arreglo de coeficientes de los modelos, dado que no se como inicializar arreglos en BASIC
		 * opte por definirlos por indice
		 */ 

		for(int i=0; i<modelReaders.length; ++i)
		{
			ModelReader model = modelReaders[i];

			double[]coefficients = model.getCoefficients();
			
			for(int j=0; j<coefficients.length; j++)
			{
				
				System.out.println( (int)(coefficients[j]*100000)/100000.0);
				
				addInstruction(JazScriptSyntax.assigment(CoeffModel[i].getName() + "[" + j + "]", "" + coefficients[j] ));
			}

			addInstruction("");
			
		}
		
		
		
		addInstruction("ShowMenu(\"Iniciar medicion?\",\"Exit\")");
		addInstruction("OnButtonClick(" + gettingSpectrumChoice.getName() + ", 30)");
		addInstruction("If (" + gettingSpectrumChoice.getName() + " = 0) GOTO INICIO");
		addInstruction("If (" + gettingSpectrumChoice.getName() + " = 1) GOTO EXIT");
		addInstruction("");
		
		addInstruction(JazScriptSyntax.label("INICIO"));
		addInstruction("");
		
		
		/**
		 * Se llama a la rutina encargada de calibrar el blanco y negro
		 */
	
		addInstruction(JazScriptSyntax.call(calibrarLuz.getName()));
		
		
		/**
		 * Lo siguiente es un ciclo que permite recolectar un espectro para luego aplicar un modelo
		 */

		
		/**
		 * Se inicializa la variable que lleva la cuenta del numero de mediciones
		 */

		addInstruction(JazScriptSyntax.comment("Guarda el numero actual de mediciones"));
		addInstruction(JazScriptSyntax.assigment(numMediciones.getName(), "1"));
		

		addInstruction(JazScriptSyntax.comment("Se abre el archivo que guarda los logs"));
		addInstruction(JazScriptSyntax.openFile(loggerFile.getName(), "ForWrite", ""));
		
		
		/**
		 * Se inicializan las variables que llevan la suma acumulada que permiten calcular el promedio
		 */

		addInstruction(JazScriptSyntax.comment("Guardan la suma acumulada del valor resultante de aplicar el modelo sobre el espectro"));
		
		for(int i=0; i<modelReaders.length; ++i)
		{
			addInstruction(JazScriptSyntax.assigment(TotalAcumModel[i].getName(), "0"));
			addInstruction(JazScriptSyntax.assigment(AvgAcumModel[i].getName(), "0"));
		}
		
		addInstruction("");
		
		
		
		/**
		 * Set a label so that we can return to this location.
		 */
		
		addInstruction(JazScriptSyntax.label("TOP"));
		addInstruction("");
		
		/**
		 * Display a message and get a spectrum
		 */
		
		addInstruction(JazScriptSyntax.displayMsg("Collecting$Spectrum"));
		addInstruction(JazScriptSyntax.pause("1"));
		addInstruction(JazScriptSyntax.getSpectrum(spectrumChannel.getName(), collectedSpectrum.getName()));
		addInstruction(JazScriptSyntax.pause("1"));
		addInstruction(JazScriptSyntax.showGraph(collectedSpectrum.getName()));
		addInstruction(JazScriptSyntax.pause("1"));
		addInstruction("");
		
		addInstruction(JazScriptSyntax.openFile(spectrumFiles.getName(), "ForWrite", numMediciones.getName()));
		addInstruction(JazScriptSyntax.writeSpectrum(spectrumFiles.getName(), collectedSpectrum.getName()));
		
		
		addInstruction(JazScriptSyntax.closeFile(spectrumFiles.getName()));
		
		

		/**
		 * Se generan las variables que guardan el resultado de aplicar los modelos sobre el espectro
		 */
		
		addInstruction(JazScriptSyntax.comment("Se inicializan las variables que guardan el resultado de aplicar los modelos sobre el espectro"));

		for(int i=0; i<modelReaders.length; ++i)
		{
			addInstruction(JazScriptSyntax.assigment(SumAcumModel[i].getName(), "0"));
		}
		
		
		addInstruction("");

		/**
		 * Se recorre el spectro y se calcula la suma acumulada
		 */
		
		addInstruction(JazScriptSyntax.comment("Se recorre el spectro y se calcula la suma acumulada"));
		
		
		for(int i=0; i<modelReaders.length; ++i)
		{
			ModelReader modelReader = modelReaders[i];
			addInstruction(JazScriptSyntax.assigment(tmpIndex.getName(), "0"));
			addInstruction("DO " + index.getName() + " 0, " + modelReader.getLength() + ", 1");
			addInstruction(JazScriptSyntax.assigment(tmpIndex.getName(), index.getName() + " + " + modelReader.getTruncateIni()));
			addInstruction(JazScriptSyntax.increment(SumAcumModel[i].getName(), "(" + collectedSpectrum.getName() + "[" + tmpIndex.getName() + "] * " + CoeffModel[i].getName() + "[" + index.getName() + "])"));
			addInstruction("DONE");
			addInstruction("");
		}
		
		addInstruction("");

		/**
		 * Se calcula la suma acumulada del valor que se obtiene antes
		 */
		
		addInstruction(JazScriptSyntax.comment("Se acumula el valor obtenido de aplicar el modelo sobre el espectro"));

		for(int i=0; i<modelReaders.length; ++i)
		{
			addInstruction(JazScriptSyntax.increment(TotalAcumModel[i].getName(), SumAcumModel[i].getName()));
		}
		
		addInstruction(JazScriptSyntax.comment("Se calcula el promedio"));
		
		for(int i=0; i<modelReaders.length; ++i)
		{
			addInstruction(JazScriptSyntax.assigment(AvgAcumModel[i].getName(), TotalAcumModel[i].getName() + "/" + numMediciones.getName()));
		}
		
		
		/**
		 * Se muestra en pantalla la suma acumulada
		 */

		addInstruction("");

		addInstruction(JazScriptSyntax.comment("Se muestra el numero de medicion"));
		addInstruction(JazScriptSyntax.display("Medicion:$", numMediciones.getName(), ""));
		addInstruction(JazScriptSyntax.pause("1"));
		addInstruction("");
		
		addInstruction(JazScriptSyntax.saveReading(loggerFile.getName(), "Medicion: ", numMediciones.getName(), ""));
		
		
		for(int i=0; i<modelReaders.length; ++i)
		{
			ModelReader model = modelReaders[i];
			
			addInstruction(JazScriptSyntax.comment("Se muestra el valor obtenido de aplicar el espectro"));
			
			addInstruction(JazScriptSyntax.display(SumAcumModel[i].getName() + ":$", SumAcumModel[i].getName(), model.getUnitName()));
			addInstruction(JazScriptSyntax.saveReading(loggerFile.getName(), SumAcumModel[i].getName() + ":", SumAcumModel[i].getName(), model.getUnitName()));
			addInstruction(JazScriptSyntax.pause("1"));
			addInstruction("");
			
			addInstruction(JazScriptSyntax.comment("Se muestra el promedio"));
			addInstruction(JazScriptSyntax.display(AvgAcumModel[i].getName() + ":$", AvgAcumModel[i].getName(), model.getUnitName()));
			addInstruction(JazScriptSyntax.pause("1"));
			
		}

		addInstruction(JazScriptSyntax.writeFile(loggerFile.getName(), "========================="));
		
		
		addInstruction("");
		
		/**
		 * Se guarda el resultado en un archivo
		 */
		
		
		/**
		 * Se incrementa el numero de mediciones
		 */
		
		addInstruction(JazScriptSyntax.comment("Se incrementa el numero de mediciones"));
		addInstruction(JazScriptSyntax.increment(numMediciones.getName(), "1"));
		addInstruction("");
		
		/**
		 * Put up a menu for the user to see if they want to get another Spectrum or exit
		 * If the user wants another spectrum goto the // TOP Label otherwise exit
		 */
		
		addInstruction("ShowMenu(\"Get another?\",\"Exit\")");
		addInstruction("OnButtonClick(" + gettingSpectrumChoice.getName() + ", 30)");
		addInstruction("If (" + gettingSpectrumChoice.getName() + " = 0) GOTO TOP");
		addInstruction("If (" + gettingSpectrumChoice.getName() + " = 1) GOTO FIN");
		addInstruction("");
		
		addInstruction(JazScriptSyntax.label("FIN"));

		
		/**
		 * Se guarda el numero de mediciones
		 */

		addInstruction(JazScriptSyntax.writeFile(loggerFile.getName(), "========================="));
		addInstruction(JazScriptSyntax.writeFile(loggerFile.getName(), "========================="));

		addInstruction(JazScriptSyntax.decrement(numMediciones.getName(), "1"));
		
		addInstruction(JazScriptSyntax.saveReading(loggerFile.getName(), numMediciones.getName() + ":", numMediciones.getName(), ""));
		
		/**
		 * Se guardan los promedios de las medidas realizadas
		 */
		
		for(int i=0; i<modelReaders.length; ++i)
		{
			ModelReader model = modelReaders[i];

			addInstruction(JazScriptSyntax.saveReading(loggerFile.getName(), AvgAcumModel[i].getName() + ":", AvgAcumModel[i].getName(), model.getUnitName()));
			
		}		

		/**
		 * Se cierra el archivo de logger
		 */
		
		addInstruction(JazScriptSyntax.closeFile(loggerFile.getName()));
		
		
		addInstruction(JazScriptSyntax.label("EXIT"));
		
		
	}
	
	
	
}
