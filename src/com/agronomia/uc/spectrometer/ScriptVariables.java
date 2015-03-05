package com.agronomia.uc.spectrometer;

import java.util.ArrayList;
import java.util.Iterator;

import com.agronomia.uc.spectrometer.variables.Variable;

public class ScriptVariables {
	
	ArrayList<Variable> variables;

	public ScriptVariables()
	{
		variables = new ArrayList<Variable>();
	}
	
	public String toString(){
		
		String out = "";
		
		for(int i=0; i<variables.size(); i++){
			Variable variable = (Variable) variables.get(i);
			
			out += variable.getName() + ", ";
		}
		
		return out;
	}
	
	public int indexOf(Variable v)
	{
		for(int i=0; i<variables.size(); i++)
		{
			Variable variable = (Variable) variables.get(i);
			
			if(variable.equals(v)){
				return i;
			}
		
		}

		return -1;
		
	}
	
	
	public Variable findOrAdd(Variable variable)
	{
		int index = indexOf(variable);

		if(index >= 0)
		{
			return variables.get(index);
		}
		else
		{
			variables.add(variable);
			return variable;
		}
	}
	
	public Iterator<Variable> getIterator()
	{
		return variables.iterator();
	}
	

}
