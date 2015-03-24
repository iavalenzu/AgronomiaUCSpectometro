package com.agronomia.uc.spectrometer;

public class JazScriptSyntax {

	static public String setDisplayPrecision(int x, int y){
		return "SetDisplayPrecision(" + x + "," + y + ")";
	}
	
	static public String pause(String secs){
		return "Pause(" + secs + ")";
	}
	
	static public String display(String legend, String value, String units){
		return "Display(\"" + legend + "\"," + value + ", \"" + units + "\")";
	}

	static public String setIntegrationTime(String time)
	{
		return "SetIntegrationTime(" + time + ")";
	}
	
	static public String assigment(String variableName, String variableValue)
	{
		return variableName + " := " + variableValue;
	}
	
	static public String increment(String variableName, String variableValue)
	{
		return JazScriptSyntax.assigment(variableName, variableName + " + " + variableValue);
	}
	
	static public String displayMsg(String msg)
	{
		return "DisplayMsg(\"" + msg + "\")";
	}
	
	static public String getSpectrum(String spectrumChannel, String collectedSpectrum)
	{
		return "GetSpectrum(" + spectrumChannel + "," +  collectedSpectrum + ")";
	}
	
	static public String comment(String comment)
	{
		return "//" + comment;
	}
	
	static public String label(String name)
	{
		return "Label " + name; 
	}
	
	static public String showMenu(String... options)
	{
		String out = "ShowMenu(";
		
		for(int i=0; i<options.length - 1; i++)
		{
			out += "\"" + options[i] + "\",";
		}

		out += "\"" + options[options.length - 1] + "\"";
		
		out += ")";
		
		return out;
	}
	
	static public String call(String procedureName)
	{
		return "Call " + procedureName;
	}
	
	static public String saveReading(String file, String legend, String index, String units)
	{
		return "SaveReading(" + file + ",\"" + legend + "\"," + index + ",\"" + units + "\")";
	}
	
	static public String openFile(String file, String mode, String sequence)
	{
		if(sequence.isEmpty()){
			return "OpenFile(" + file + "," + mode + ")"; 
		}else{
			return "OpenFile(" + file + "," + mode + "," + sequence + ")"; 
		}
	}
	static public String closeFile(String file)
	{
		return "CloseFile(" + file + ")";
	}

	public static String addProcess(String name) 
	{
		return "[PROCESS " + name + "]";
	}
	
	public static String end() 
	{
		return "END";
	}

	public static String showGraph(String name) 
	{
		return "ShowGraph(" + name + ")";
	}

	public static String setLampIntensity(String l, String m, String v) 
	{
		return "SetLampIntensity(" + l + "," + m + "," + v + ")";
	}

	public static String locateWavelength(String a, String b, String c) {
		return "LocateWavelength(" + a + ", " + b + ", " + c + ")";

	}

	public static String gotos(String label) {
		return "GOTO " +  label;
	}

	public static String onButtonClick(String ButtonSelection, String TimeToWait) {
		return "OnButtonClick(" + ButtonSelection + ", " + TimeToWait + ")";
	}

	public static String setLampShutter(String l, String v) {
		return "SetLampShutter(" + l + ", " + v + ")";
	}

	public static String writeSpectrum(String file, String spectrum) {
		return "WriteSpectrum(" + file + ", " + spectrum + ")";

	}

	public static String writeFile(String file, String text) {
		return "WriteFile(" + file + ",\"" + text + "\")";

	}

	public static String decrement(String variableName, String variableValue) {
		return JazScriptSyntax.assigment(variableName, variableName + " - " + variableValue);
	}

	
	
}
