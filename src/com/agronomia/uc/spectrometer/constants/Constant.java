package com.agronomia.uc.spectrometer.constants;

public class Constant {
	
	String name;
	String value;
	
	public Constant(String name, String value)
	{
		this.name = name;
		this.value = value;
	}
	
	public String getInstruction()
	{
		return this.name + " = " + this.value;
	}

	public boolean equals(Constant constant)
	{
		return this.name.compareToIgnoreCase(constant.name) == 0;
	}	
	
	public String getName()
	{
		return this.name;
	}
	
	
}
