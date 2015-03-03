package com.agronomia.uc.spectrometer.variables;

public class Variable {

	static public String type_int_8 = "INT_8";
	static public String type_int_16 = "INT_16";
	static public String type_int_32 = "INT_32";
	static public String type_text = "TEXT";
	static public String type_real = "REAL";
	static public String type_spectral = "SPECTRAL";
	static public String type_file = "FILE";
	static public String type_table = "TABLE";
	
	static public String file_type_asc = "ASC";
	static public String file_type_csv = "CSV";
	static public String file_type_raw = "RAW"; 
	
	String name;
	String type;
	int count;
	String fileName;
	String fileType;
	
	public Variable(String name, String type)
	{
		this.name = name;
		this.type = type;
		this.count = 0;
	}

	public Variable(String name, String type, int count)
	{
		this.name = name;
		this.type = type;
		this.count = count;
	}
	
	public Variable(String name, String type, String fileName, String fileType)
	{
		this.name = name;
		this.type = type;
		this.count = 0;
		this.fileName = fileName;
		this.fileType = fileType;
		
	}
	
	
	public String getName()
	{
		return this.name;
	}
	
	public String getInitInstruction()
	{
		if(this.type.equals(type_file))
		{
			return this.name + " " + this.type + " \"" + this.fileName + "\" " + this.fileType; 
		}
		else
		{
			return this.name + " " + this.type + " " + ((this.count > 0) ? this.count : "");
		}
		
	}
	
	public boolean equals(Variable variable)
	{
		return this.name.compareToIgnoreCase(variable.name) == 0;
	}

	public String setValueInstruction(String value) {
		return this.name + " := " +  value;
	}
	
	public String setIncrementInstruction(String value) {
		return this.name + " := " + this.name + " + " +  value;
	}
	
	
	
}
