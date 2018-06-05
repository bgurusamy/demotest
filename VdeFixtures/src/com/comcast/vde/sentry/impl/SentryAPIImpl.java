package com.comcast.vde.sentry.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.comcast.core.VDEutility.HttpUtility;

import fit.ColumnFixture;

public class SentryAPIImpl extends ColumnFixture{

	String portNum; 
	String sentryHost;
	String sourceIPAddr;
	String grpAddr;
	String destPort;
	String name;
	String desc;
	String vlanID;
	
	

	public String getPortStatus(){

		HttpUtility httpUtil = new HttpUtility();

		String status=null;
		String portStr=null;

		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		nvps.add(new BasicNameValuePair("rpc", "{\"jsonrpc\":2.0, \"method\":\"Report.GetPortStatus\", \"params\":{\"outputType\":\"json\",\"activeOnly\":false}, \"id\":1}"));

		JSONObject response = httpUtil.sendPostReqWithCredentials(nvps,sentryHost);
		String message = (String)response.get("message");

		if(null==message){
			JSONArray resultArray = (JSONArray) response.get("result");
			for(int i=0;i<resultArray.size();i++){
				JSONObject result = (JSONObject)resultArray.get(i);
				Object port = result.get("number");
				if(port instanceof String){
					portStr = (String)port;
				}
				else if(port instanceof Long){
					portStr = String.valueOf((Long)port);
				}
				System.out.println(portStr);
				if(portNum.equals(port)){
					status = (String)result.get("status");
					break;
				}
			}
		}
		else{
			status=message;
		}
		//System.out.println(response.toJSONString());

		return status;
	}
	
	public String getMPEGInput(){
		HttpUtility httpUtil = new HttpUtility();
		
		String sourceIP=null;
		String desc=null;

		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		nvps.add(new BasicNameValuePair("rpc", "{\"jsonrpc\":2.0, \"method\":\"Input.GetMPEGInput\", \"params\":{\"outputType\":\"json\",\"activeOnly\":false,\"inputType\":\"ETH\"}, \"id\":1}"));
		

		JSONObject response = httpUtil.sendPostReqWithCredentials(nvps,sentryHost);
		Object result = response.get("result");
		if(result instanceof JSONArray){
			JSONArray resultArr = (JSONArray)result;
			for(int i=0;i<resultArr.size();i++){
				JSONObject details = (JSONObject)resultArr.get(i);
				sourceIP = (String)details.get("ssm_mc_ip");
				desc=(String)details.get("description");
				if(sourceIP.equals("*") && null!=desc && desc.isEmpty()){
					portNum = (String)details.get("port_number");
					break;
				}
			}
		}
		
		return portNum;
	}
	
	
	public String setMPEGInput(){
		HttpUtility httpUtil = new HttpUtility();
		String status=null;
		
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		nvps.add(new BasicNameValuePair("rpc", "{\"jsonrpc\":2.0, \"method\":\"Input.SetMPEGInput\", \"params\":{\"inputType\":\"json\",\"inputSettings\":[{\"portnum\":"+portNum +",\"sourceIp\":\""+sourceIPAddr +"\",\"groupAddr\":\""+grpAddr +"\",\"destPort\":"+destPort+",\"name\":\""+name+"\",\"desc\":\""+desc+"\"}]}, \"id\":1}"));
		
		JSONObject response = httpUtil.sendPostReqWithCredentials(nvps,sentryHost);
		JSONObject result = (JSONObject)response.get("result");
		status = (String)result.get("resultMsg");
		
		return status;
	}

	public static void main (String args[]){
		SentryAPIImpl impl = new SentryAPIImpl();
		impl.sentryHost="10.35.159.250";
		//impl.portNum="64";
		impl.portNum =impl.getMPEGInput();
		impl.sourceIPAddr="10.35.184.181";
		impl.grpAddr="232.36.3.21";
		impl.destPort="36021";
		impl.name="Port " + impl.portNum;
		impl.desc="VDE Automation";
		//String status = impl.setMPEGInput();
		//if(status.equals("Success")){
			//System.out.println(impl.getPortStatus());
		//}

	}

}
