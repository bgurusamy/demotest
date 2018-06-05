package com.comcast.vde;

import static net.sf.expectit.matcher.Matchers.contains;
import static net.sf.expectit.matcher.Matchers.regexp;
import static net.sf.expectit.matcher.Matchers.times;

import static net.sf.expectit.filter.Filters.removeColors;
import static net.sf.expectit.filter.Filters.removeNonPrintable;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import fit.ColumnFixture;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import net.sf.expectit.Expect;
import net.sf.expectit.ExpectBuilder;


/**
 * An example of interacting with the local SSH server
 */
public class ExecuteDockerCommand extends ColumnFixture{
	String result = null;
	String result1 = null;
	String Var1;
	String Var2;
	String UserID;
	String Password;
	String LoginIP;
	public String Output()
	{
		
		try {
			result = ExecuteCommand(Var1,Var2,UserID,Password,LoginIP);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public String ExecuteCommand(String Var3, String Var4, String Var5, String Var6, String Var7) throws IOException, JSchException
	{
		JSch jSch = new JSch();
        Session session = null;
		session = jSch.getSession(Var5, Var7, 22);
        session.setPassword(Var6);
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
        Channel channel = session.openChannel("shell");
        channel.connect();

        Expect expect = new ExpectBuilder()
        .withOutput(channel.getOutputStream())
        .withInputs(channel.getInputStream(), channel.getExtInputStream())
        .withInputFilters(removeColors(), removeNonPrintable())
        .withExceptionOnFailure()
        .withTimeout(30, TimeUnit.SECONDS)
        .build();
        try {
            expect.expect(contains("#"));
            //expect.sendLine("docker exec -it ee033cd2a8e0 /bin/bash;");
            expect.sendLine(Var3);
            System.out.println(
                    "etc:" + expect.expect(times(1, contains("\n")))
                            .getResults()
                            .get(0)
                            .getBefore());
            
            expect.expect(contains("#"));
            //expect.sendLine(". /opt/vde/setup; am -0 show out;");
            expect.sendLine(Var4);
            //System.out.println(expect.expect(regexp("#")).toString().replaceAll("\\e\\[[\\d;]*[^\\d;]", ""));
            /*System.out.println(
                    "2:" + expect.expect(times(1, regexp("#")))
                            .getResults()
                            .get(0)
                            .getBefore().replaceAll("\\e\\[[\\d;]*[^\\d;]", ""));*/
            result1 = expect.expect(times(1, regexp("#")))
                    .getResults()
                    .get(0)
                    .getBefore().replaceAll("\\e\\[[\\d;]*[^\\d;]", "");
            //result1 = expect.expect(regexp("#")).toString().replaceAll("\\e\\[[\\d;]*[^\\d;]", "");
            expect.sendLine("exit");
            } catch(Exception e){
        	//Handle the exception
        }finally {
            expect.close();
            channel.disconnect();
            session.disconnect();
        }
		return result1;
	}
	public static void main(String[] args) throws JSchException, IOException {
		ExecuteDockerCommand a = new ExecuteDockerCommand();
		a.Var1 = "docker exec -it ee033cd2a8e0 /bin/bash";
		a.Var2 = ". /opt/vde/setup; am -0 show out;";
		a.Output();
        /*JSch jSch = new JSch();
        Session session = jSch.getSession("root", "10.35.176.167", 22);
        session.setPassword("vde@dmin");
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
        Channel channel = session.openChannel("shell");
        channel.connect();

        Expect expect = new ExpectBuilder()
        .withOutput(channel.getOutputStream())
        .withInputs(channel.getInputStream(), channel.getExtInputStream())
        .withInputFilters(removeColors(), removeNonPrintable())
        .withExceptionOnFailure()
        .withTimeout(30, TimeUnit.SECONDS)
        .build();
        try {
            expect.expect(contains("#"));
            expect.sendLine("docker exec -it ee033cd2a8e0 /bin/bash;");
           
            System.out.println(
                    "etc:" + expect.expect(times(1, contains("\n")))
                            .getResults()
                            .get(0)
                            .getBefore());
            
            expect.expect(contains("#"));
            expect.sendLine(". /opt/vde/setup; am -0 show out;");
            
            System.out.println(expect.expect(regexp("#")).toString().replaceAll("\\e\\[[\\d;]*[^\\d;]", ""));
            
            expect.sendLine("exit");
        } catch(Exception e){
        	//Handle the exception
        }finally {
            expect.close();
            channel.disconnect();
            session.disconnect();
        }*/
    }
	
}