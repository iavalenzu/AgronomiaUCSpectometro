package com.agronomia.uc.spectrometer;

import java.util.ArrayList;
import java.util.Iterator;

import com.agronomia.uc.spectrometer.constants.Constant;

public class ScriptConstants {
	
	ArrayList<Constant> constants = new ArrayList<Constant>();

	public ScriptConstants()
	{
	}
	
	public void add(Constant constant)
	{
		if(constants.lastIndexOf(constant) > 0)
		{
			throw new RuntimeException("El nombre de constante '" + constant.getName() + "' ya se encuentra definido.");
		}
		
		constants.add(constant);
		
	}
	
	public Constant findOrAdd(Constant constant)
	{
		int index = constants.lastIndexOf(constant);
		
		if(index > 0){
			return constants.get(index);
		}
		
		constants.add(constant);
		
		return constant;
	}	
	
	public Iterator<Constant> getIterator()
	{
		return constants.iterator();
	}

}
