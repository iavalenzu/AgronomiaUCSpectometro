package com.agronomia.uc.spectrometer;

import java.util.ArrayList;
import java.util.Iterator;

import com.agronomia.uc.spectrometer.procedures.Procedure;

public class ScriptProcedures {

	ArrayList<Procedure> procedures = new ArrayList<Procedure>();	

	public ScriptProcedures()
	{
		
	}

	public void addProcedure(Procedure procedure)
	{
		if(procedures.lastIndexOf(procedure) > 0)
		{
			throw new RuntimeException("El nombre de procedimiento '" + procedure.getName() + "' ya se encuentra definido.");
		}
		
		procedures.add(procedure);
		
	}	
	
	public Iterator<Procedure> getIterator()
	{
		return procedures.iterator();
	}
	
	public Procedure findOrAdd(Procedure procedure)
	{
		int index = procedures.lastIndexOf(procedure);
		
		if(index > 0){
			return procedures.get(index);
		}
		
		procedures.add(procedure);
		
		return procedure;
	}
	

	
}
