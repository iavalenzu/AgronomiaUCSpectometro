package com.agronomia.uc.spectrometer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.jmatio.io.MatFileReader;
import com.jmatio.types.MLArray;
import com.jmatio.types.MLDouble;

public class ModelReader {
	
	MatFileReader matfilereader;
	String modelName;
	double[]coefficients;	
	
	String variableName;
	String unitName;
	
	int truncateIni;
	
	public ModelReader(String filePath) throws FileNotFoundException, IOException
	{
		Path modelPath = Paths.get(filePath);
		
		modelName = modelPath.getFileName().toString();
		
		matfilereader = new MatFileReader(modelPath.toString());

		truncateIni = 0;
		
		findModelCoefficients();
	}
	
	
	public double[] getCoefficients()
	{
		return this.coefficients;
	}
	
	public void findModelCoefficients()
	{
		Map<String, MLArray> content = matfilereader.getContent();

		Set<String> keys = content.keySet();

		if(keys.size() > 1)
		{
			System.out.println("El archivo tiene mas de un set, se toma el primero de ellos!!");
		}
		
		Iterator<String> iterator = keys.iterator();
		
		String firstKey = iterator.next();
		
		System.out.println("FirstKey: " + firstKey);
		
		MLDouble mlCoefficients = (MLDouble) matfilereader.getMLArray(firstKey);		
		
		double[][]arrayCoefficients = mlCoefficients.getArray();
		
		coefficients = arrayCoefficients[0];		
	}
	
	public void truncateCoefficients(int ini, int length)
	{
		this.truncateIni = ini;
		
		
		if(length > coefficients.length)
		{
			System.out.println("El largo dado: " + length + ", no puede ser mayor que el largo del arreglo de coeficientes: " + coefficients.length);
			return;
		}
		
		double[] tmp = new double[length];
		
		for(int i = 0; i < length; i++)
		{
			tmp[i] = coefficients[ini + i];
		}
		
		coefficients = tmp;
		
	}

	public int getLength() {
		return coefficients.length;
	}
	
	public String getVariableName() {
		return variableName;
	}

	public String getUnitName() {
		return unitName;
	}


	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}


	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	public int getTruncateIni(){
		return this.truncateIni;
	}
	
	
	

}
