package com.agronomia.uc.spectrometer;

import java.util.ArrayList;
import java.util.Iterator;

import com.agronomia.uc.spectrometer.constants.Constant;
import com.agronomia.uc.spectrometer.procedures.Procedure;

public class ScriptProcedures {

	ArrayList<Procedure> procedures;	

	public ScriptProcedures()
	{
		procedures = new ArrayList<Procedure>();	
	}

	public Iterator<Procedure> getIterator()
	{
		return procedures.iterator();
	}
	
	public int indexOf(Procedure p)
	{
		for(int i=0; i<procedures.size(); i++)
		{
			Procedure procedure = (Procedure) procedures.get(i);
			
			if(procedure.equals(p)){
				return i;
			}
		
		}

		return -1;
		
	}		
	
	
	public Procedure findOrAdd(Procedure procedure)
	{
		int index = indexOf(procedure);
		
		if(index >= 0)
		{
			return procedures.get(index);
		}
		else
		{
			procedures.add(procedure);
			return procedure;
		}
	}
	

	
}
