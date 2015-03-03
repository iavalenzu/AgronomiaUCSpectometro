package com.agronomia.uc.spectrometer;

import java.util.ArrayList;
import java.util.Iterator;

import com.agronomia.uc.spectrometer.variables.Variable;

public class ScriptVariables {
	
	ArrayList<Variable> variables = new ArrayList<Variable>();

	public ScriptVariables()
	{
	}
	
	public void add(Variable variable)
	{
		if(variables.lastIndexOf(variable) > 0){
			throw new RuntimeException("El nombre de variable '" + variable.getName() + "' ya se encuentra definido.");
		}
		
		variables.add(variable);
		
	}
	
	public Variable findOrAdd(Variable variable)
	{
		int index = variables.lastIndexOf(variable);
		
		if(index > 0){
			return variables.get(index);
		}
		
		variables.add(variable);
		
		return variable;
	}
	
	public Iterator<Variable> getIterator()
	{
		return variables.iterator();
	}
	

}
