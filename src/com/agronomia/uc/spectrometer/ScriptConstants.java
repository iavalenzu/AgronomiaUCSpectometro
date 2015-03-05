package com.agronomia.uc.spectrometer;

import java.util.ArrayList;
import java.util.Iterator;

import com.agronomia.uc.spectrometer.constants.Constant;
import com.agronomia.uc.spectrometer.variables.Variable;

public class ScriptConstants {
	
	ArrayList<Constant> constants;

	public ScriptConstants()
	{
		constants = new ArrayList<Constant>();
	}
	
	public int indexOf(Constant c)
	{
		for(int i=0; i<constants.size(); i++)
		{
			Constant constant = (Constant) constants.get(i);
			
			if(constant.equals(c)){
				return i;
			}
		
		}

		return -1;
		
	}	
	
	public Constant findOrAdd(Constant constant)
	{
		int index = indexOf(constant);
		
		if(index >= 0)
		{
			return constants.get(index);
		}
		else
		{
			constants.add(constant);
			return constant;
		}
	}	
	
	public Iterator<Constant> getIterator()
	{
		return constants.iterator();
	}

}
