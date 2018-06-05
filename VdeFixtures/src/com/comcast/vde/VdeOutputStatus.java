package com.comcast.vde;

import fit.ColumnFixture;

public class VdeOutputStatus extends ColumnFixture {
	//String command;
	//String runcommandresult;
	String Var3;
	String Var4;
	String result = null;
	long abc;
	long ghi;
	//static RunCommand rc;
	//static TerminalUtility tu;
	public String output() {
		//String result = Var1;
		try {	
			//runcommandresult = tu.executeCommand(command).trim();
			result = executeCommand(Var3,Var4);			
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	public String executeCommand(String Var5,String Var6)
	{
		String result1 = null;
		String[] def;
		String jkl = null;
		String mno = null;
		if (Var5.trim().contains("byte count:"))
		{
			if (Var5.indexOf(":")>0)
			{
				def = Var5.split(":");
				if(def[0].trim().equalsIgnoreCase("byte count"))
				{
					jkl = def[1];
				}
			}
		}
		if (Var6.trim().contains("byte count:"))
		{
			if (Var6.indexOf(":")>0)
			{
				def = Var6.split(":");
				if(def[0].trim().equalsIgnoreCase("byte count"))
				{
					mno = def[1];
				}
			}
		}
		//abc = Integer.parseInt(jkl.trim());
		abc = Long.parseLong(jkl.trim());
		//ghi = Integer.parseInt(mno.trim());
		ghi = Long.parseLong(mno.trim());
			if (ghi > abc)
			{
				result1 = "VDE is Outputting";
			}
			else
			{
				result1 = "VDE is not Outputting";
			}
		return result1;
	}
	public static void main(String args[]){
		VdeOutputStatus a = new VdeOutputStatus();
		a.Var3 = "byte count: 34333582040";
		a.Var4 = "byte count: 34333582041";
		a.output();
	}
}
