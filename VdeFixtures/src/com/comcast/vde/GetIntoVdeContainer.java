package com.comcast.vde;

import fit.ColumnFixture;
public class GetIntoVdeContainer extends ColumnFixture{
	String Var1;
	String result = null;
	String result1 = null;
	String result2 = null;
	public String output() {
		try {	
				result = executeCommand(Var1);			
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	public String executeCommand(String Var2)
	{
		String[] abc = Var2.split(" ");
		result2 = abc[0].trim();
		result1 = "docker exec -it " + result2 + " /bin/bash";
		return result1;
	}
	public static void main(String args[]){
		GetIntoVdeContainer a = new GetIntoVdeContainer();
		a.Var1 = "b32fe8768a4b docker.comcast.net/dna/vde:2.01-26.dna supervisord -c /etc/ 4 days ago Up 4 days furious_hugle";
		//a.Var4 = "byte count: 34333582041";
		a.output();
	}
}
