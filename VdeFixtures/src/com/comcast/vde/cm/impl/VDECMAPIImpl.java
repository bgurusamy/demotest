package com.comcast.vde.cm.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.comcast.core.VDEutility.HttpUtility;
import com.comcast.vde.cm.model.ElementaryStreams;
import com.google.gson.Gson;

import fit.ColumnFixture;


public class VDECMAPIImpl extends ColumnFixture{

	String token;
	String streamerConfig;
	String streamerID;
	String streamerName;
	String destIPAddr;
	int destPort;
	String mediaInfoURL;
	String status;
	String muxConfigUpdateParam;
	String muxConfigUpdateParamValue;
	String muxConfigUpdatePath;
	String location;
	String streamerVer;
	long rev;
	boolean isUpdate = true;
	String error1;

	static Properties prop;
	static InputStream input;
	
	public String loadProperties() throws IOException
	{
		try {
			prop=new Properties();
			input = null;
			input = new FileInputStream("C:\\Maneesha\\workspace\\git\\VDE-Fitnesse-master\\VDE-Fitnesse-master\\VdeFixtures\\config.properties");
			prop.load(input);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Properties loaded";
	}
	

	public String loginVDE() throws IOException{
		String result=loadProperties();
		System.out.println("Properties result" +result);
		if(result.contains("Properties loaded"));
		{
		String loginURL=prop.getProperty("cmUrl1");
	
		HttpUtility httpUtil = new HttpUtility();
		JSONObject user_auth = new JSONObject();
		user_auth.put("username", prop.getProperty("unameValue"));
		user_auth.put("password", prop.getProperty("upassValue"));	
		try {
			StringEntity params = new StringEntity(user_auth.toString());
			Header header = new BasicHeader("content-type","application/json");			

			JSONObject jsonObj = httpUtil.sendPostReq(loginURL,header,params);

			token = (String) jsonObj.get("token");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return token;
	}}

	public String getStreamer(){
		Header header = new BasicHeader(HttpHeaders.AUTHORIZATION," Bearer " + token);	
		String getStreamer="https://vdecm-wc-05q.sys.comcast.net/api/v2/streamers/{id}";	
		getStreamer = getStreamer.replace("{id}", streamerID);
		HttpUtility httpUtil = new HttpUtility();
		JSONObject jsonObj = null;

		try {

			jsonObj = httpUtil.sendGetReq(getStreamer,header);
			//JSONObject streamer = (JSONObject)jsonObj.get("streamer");			
			//streamerConfig = ((JSONObject) streamer.get("data")).toString();
			
			streamerConfig = jsonObj.toJSONString();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return streamerConfig;
	}

	public String validateProgramURL(){
		String dashURL="https://vdecm-wc-05q.sys.comcast.net/api/v2/dash/media-info?url={escaped-url}";
		dashURL = dashURL.replace("{escaped-url}",mediaInfoURL );
		Header header = new BasicHeader(HttpHeaders.AUTHORIZATION," Bearer " + token);
		HttpUtility httpUtil = new HttpUtility();
		try {

			status = httpUtil.sendGetReqForDashURL(dashURL,header);

		}
		catch(Exception e){
			e.printStackTrace();
		}

		return status;
	}

	public JSONObject getMediaInfo(){
		String dashURL="https://vdecm-wc-05q.sys.comcast.net/api/v2/dash/media-info?url={escaped-url}";
		dashURL = dashURL.replace("{escaped-url}",mediaInfoURL );
		Header header = new BasicHeader(HttpHeaders.AUTHORIZATION," Bearer " + token);
		HttpUtility httpUtil = new HttpUtility();
		JSONObject json =null;
		try {

			json = httpUtil.sendGetReq(dashURL,header);

		}
		catch(Exception e){
			e.printStackTrace();
		}

		return json;
	}

	@SuppressWarnings("unchecked")
	public String createStreamerDuplicate()	{	
		String createStreamer="https://vdecm-wc-05q.sys.comcast.net/api/v2/streamers";
		JSONObject jsonObj = null;
		try {			
			Header header = new BasicHeader(HttpHeaders.AUTHORIZATION," Bearer " + token);	
			JSONParser responseParser = new JSONParser();			
			jsonObj = (JSONObject) responseParser.parse(streamerConfig);
			JSONObject streamer = (JSONObject)jsonObj.get("streamer");			
			JSONObject data = ((JSONObject) streamer.get("data"));
			data.put("name", streamerName);
			data.put("location", location);
			JSONObject mux = (JSONObject) data.get("mux");
			mux.put("version", streamerVer);
			JSONObject config = (JSONObject) mux.get("config");
			JSONObject output = (JSONObject) config.get("output");
			output.put("dstAddress", destIPAddr);
			output.put("udpDstPort", destPort);
			StringEntity params = new StringEntity(data.toString());
			HttpUtility httpUtil = new HttpUtility();
			jsonObj = httpUtil.sendPostReq(createStreamer, header, params);
			streamerConfig = jsonObj.toString();
		}
		catch(Exception e){
			e.printStackTrace();
		}

		return streamerConfig;

	}
	public String createStreamer()	{
		
		System.out.println("in create");
		String createStreamer="https://vdecm-wc-05q.sys.comcast.net/api/v2/streamers";
		JSONObject jsonObj = null;
		try {			
			Header header = new BasicHeader(HttpHeaders.AUTHORIZATION," Bearer " + token);	
			StringEntity params = new StringEntity(streamerConfig);
			HttpUtility httpUtil = new HttpUtility();
			jsonObj = httpUtil.sendPostReq(createStreamer, header, params);
			streamerConfig = jsonObj.toString();
		}
		catch(Exception e){
			e.printStackTrace();
		}

		return streamerConfig;

	}
	public String deployStreamer(){
		System.out.println("in deploy");
		JSONObject response=null;
		String id = null;
		try {
			JSONParser responseParser = new JSONParser();			
			JSONObject jsonObj = (JSONObject) responseParser.parse(streamerConfig);
			JSONObject streamer = (JSONObject)jsonObj.get("streamer");
			rev = (long) streamer.get("rev");
			rev = rev+1;
			id = (String) streamer.get("id");
			String Url="https://vdecm-wc-05q.sys.comcast.net/api/v2/streamers/{id}/rev/{rev}/deploy";
			Url = Url.replace("{id}",id);
			Url = Url.replace("{rev}",String.valueOf(rev));
			System.out.println(Url);
			HttpUtility httpUtil = new HttpUtility();
			Header header = new BasicHeader(HttpHeaders.AUTHORIZATION," Bearer " + token);

			response = httpUtil.sendPutReq(Url, header,null);
			if(null!=response){
				streamerConfig = response.toJSONString();
				
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}

	public String deleteStreamer(){	
		String id=null;
		try {
			String url="https://vdecm-wc-05q.sys.comcast.net/api/v2/streamers/{id}/rev/{rev}";

			JSONParser responseParser = new JSONParser();			
			JSONObject jsonObj = (JSONObject) responseParser.parse(streamerConfig);
			JSONObject streamer = (JSONObject)jsonObj.get("streamer");
			long rev = (long) streamer.get("rev");
			id = (String) streamer.get("id");
			
			url = url.replace("{id}", id);
			rev = rev+1;
			url = url.replace("{rev}",String.valueOf(rev));
			
			System.out.println(url);

			Header header = new BasicHeader(HttpHeaders.AUTHORIZATION," Bearer " + token);	
			HttpUtility httpUtil = new HttpUtility();
			
			jsonObj = httpUtil.sendDeleteReq(url, header);
			
			streamer = (JSONObject)jsonObj.get("streamer");
			
			id = (String) streamer.get("id");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return id;
	}

	@SuppressWarnings("unused")
	public String getAllStreamers(){
		String streamerID=null;
		String getStreamers="https://vdecm-wc-05q.sys.comcast.net/api/v2/streamers";
		try{
			Header header = new BasicHeader(HttpHeaders.AUTHORIZATION," Bearer " + token);	
			HttpUtility httpUtil = new HttpUtility();
			JSONObject jsonObj = httpUtil.sendGetReq(getStreamers, header);
		}
		catch(Exception e){
			e.printStackTrace();
		}

		return streamerID;
	}


	public String updateStreamer(){
		String response = null;
		try{
		JSONParser responseParser = new JSONParser();			
		JSONObject jsonObj = (JSONObject) responseParser.parse(streamerConfig);
		JSONObject streamer = (JSONObject)jsonObj.get("streamer");
		JSONObject data = (JSONObject) streamer.get("data");
		rev = (long) streamer.get("rev");
		rev = rev+1;
		String id = (String) streamer.get("id");
		String url="https://vdecm-wc-05q.sys.comcast.net/api/v2/streamers/{id}/rev/{rev}";
		url = url.replace("{id}", id);
		url = url.replace("{rev}", String.valueOf(rev));
		
		System.out.println(url);
		
		System.out.println(streamerConfig.toString());
		
		Header header = new BasicHeader(HttpHeaders.AUTHORIZATION," Bearer " + token);	
		HttpUtility httpUtil = new HttpUtility();
		
			StringEntity entity = new StringEntity(data.toString());
			jsonObj = httpUtil.sendPutReq(url, header,entity);
			if(null!=jsonObj){
				response = jsonObj.toString();
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return response;

	}
	
	public String ValidateAndUpdateMUXConfig(){

		try {
			String validateResponse = validateMUXConfig();
			if(validateResponse.indexOf("validation error") > 0){
				return validateResponse;
			}
			else{
				streamerConfig = updateStreamer();
				deployStreamer();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "Success";
	}
	
	@SuppressWarnings("unchecked")
	private String updateMUXConfig(){
		
		JSONObject mux = null;
		JSONObject jsonObj = null;
		String configValue =null;
		String status = "Not Found";
		try{

			JSONParser responseParser = new JSONParser();
			JSONObject streamerConfigObj = (JSONObject) responseParser.parse(streamerConfig);
			JSONObject streamer = (JSONObject)streamerConfigObj.get("streamer");			
			JSONObject data= (JSONObject) streamer.get("data");
			mux = (JSONObject)data.get("mux");
			JSONObject config = (JSONObject)mux.get("config");
			String[] configPathArr=null;

			//JsonParser jsonParser = new JsonParser();
			//JsonElement jsonTree = jsonParser.parse(config.toString());

			//Gson gson = new Gson();

			if(null!=muxConfigUpdatePath){
				configPathArr = muxConfigUpdatePath.split("/");
				System.out.println("ffggg" + configPathArr[0]);
				if(configPathArr[0].equalsIgnoreCase("program")){
					JSONArray programArr = (JSONArray)config.get(configPathArr[0]);
					String inputProgNum = configPathArr[1];
					char[] arrChr = inputProgNum.toCharArray();
					boolean result = Character.isDigit(arrChr[0]);
					if(result){
					for(int i=0;i<programArr.size();i++){
						JSONObject progObj = (JSONObject) programArr.get(i);
						long progNum = (long)progObj.get("programIndex");
						String num = String.valueOf(progNum);
						
						if(inputProgNum.equals(num)){
							if(hasIndex(2,configPathArr) && configPathArr[2].equalsIgnoreCase("audio")){
								JSONArray audioArr = (JSONArray)progObj.get(configPathArr[2]);
								for(int j=0;j<audioArr.size();j++){	
									JSONObject audioObj = (JSONObject) audioArr.get(j);
									long audioIndex = (long)audioObj.get("audioIndex");
									String idx = String.valueOf(audioIndex);
									String audioIndexInput = configPathArr[3];
									if(idx.equalsIgnoreCase(audioIndexInput)){
										Object item = audioObj.get(muxConfigUpdateParam);
										if(isUpdate){
											if(item instanceof String){
												audioObj.put(muxConfigUpdateParam, muxConfigUpdateParamValue);
											}
											else if(item instanceof Integer){
												int value = Integer.parseInt(muxConfigUpdateParamValue);
												audioObj.put(muxConfigUpdateParam, value);
											}
											else if(item instanceof Boolean ){
												boolean value = Boolean.valueOf(muxConfigUpdateParamValue);
												audioObj.put(muxConfigUpdateParam, value);
											}
											else if(item instanceof Long ){
												long value = Long.valueOf(muxConfigUpdateParamValue);
												audioObj.put(muxConfigUpdateParam, value);
											}
										}
										else{
											
											if(item instanceof String){
												configValue = (String)item;
												if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
													status = "Found";
												}
											}
											else if(item instanceof Integer){
												configValue = String.valueOf((int)item);
												if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
													status = "Found";
												}
											}
											else if(item instanceof Boolean ){
												configValue = String.valueOf((boolean)item);
												if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
													status = "Found";
												}
											}
											else if(item instanceof Long ){
												configValue = String.valueOf((long)item);
												if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
													status = "Found";
												}
											}
										}
									}
								}
							}
							else if(hasIndex(2,configPathArr) && configPathArr[2].equalsIgnoreCase("video")){
								Object video = progObj.get(configPathArr[2]);		

								if(video instanceof JSONObject){

									JSONObject videoObj = (JSONObject) video;
									String representationId = (String)videoObj.get("representationId");
									
									if(hasIndex(3, configPathArr)){
									String representationIdInput = configPathArr[3];
									if(representationId.equalsIgnoreCase(representationIdInput)){
										Object item = videoObj.get(muxConfigUpdateParam);
										if(isUpdate){
											if(item instanceof String){
												videoObj.put(muxConfigUpdateParam, muxConfigUpdateParamValue);
											}
											else if(item instanceof Integer){
												int value = Integer.parseInt(muxConfigUpdateParamValue);
												videoObj.put(muxConfigUpdateParam, value);
											}
											else if(item instanceof Boolean){
												boolean value = Boolean.valueOf(muxConfigUpdateParamValue);
												videoObj.put(muxConfigUpdateParam, value);
											}
											else if(item instanceof Long){
												long value = Long.valueOf(muxConfigUpdateParamValue);
												videoObj.put(muxConfigUpdateParam, value);
											}
										}
										else{
											if(item instanceof String){
												configValue = (String)item;
												if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
													status="Found";
												}
											}
											else if(item instanceof Integer){
												configValue = String.valueOf((int)item);
												if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
													status="Found";
												}
											}
											else if(item instanceof Boolean){
												configValue = String.valueOf((boolean)item);
												if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
													status="Found";
												}
											}
											else if(item instanceof Long){
												configValue = String.valueOf((long)item);
												if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
													status="Found";
												}
											}
										}
									}
									}
									else{

										
											Object item = videoObj.get(muxConfigUpdateParam);
											if(isUpdate){
												if(item instanceof String){
													videoObj.put(muxConfigUpdateParam, muxConfigUpdateParamValue);
												}
												else if(item instanceof Integer){
													int value = Integer.parseInt(muxConfigUpdateParamValue);
													videoObj.put(muxConfigUpdateParam, value);
												}
												else if(item instanceof Boolean){
													boolean value = Boolean.valueOf(muxConfigUpdateParamValue);
													videoObj.put(muxConfigUpdateParam, value);
												}
												else if(item instanceof Long){
													long value = Long.valueOf(muxConfigUpdateParamValue);
													videoObj.put(muxConfigUpdateParam, value);
												}
											}
											else{
												if(item instanceof String){
													configValue = (String)item;
													if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
														status="Found";
													}
												}
												else if(item instanceof Integer){
													configValue = String.valueOf((int)item);
													if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
														status="Found";
													}
												}
												else if(item instanceof Boolean){
													configValue = String.valueOf((boolean)item);
													if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
														status="Found";
													}
												}
												else if(item instanceof Long){
													configValue = String.valueOf((long)item);
													if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
														status="Found";
													}
												}
											}
										
										
									}
								}

							}
							else if(hasIndex(2,configPathArr) && configPathArr[2].equalsIgnoreCase("ebif")){
								Object ebif = progObj.get(configPathArr[2]);
								if(ebif instanceof JSONObject){
									JSONObject ebifObj = (JSONObject) ebif;
									Object item = ebifObj.get(muxConfigUpdateParam);
									
									if(isUpdate){
										if(item instanceof String){
											ebifObj.put(muxConfigUpdateParam, muxConfigUpdateParamValue);
										}
										else if(item instanceof Integer){
											int value = Integer.parseInt(muxConfigUpdateParamValue);
											ebifObj.put(muxConfigUpdateParam, value);
										}
										else if(item instanceof Boolean){
											boolean value = Boolean.valueOf(muxConfigUpdateParamValue);
											ebifObj.put(muxConfigUpdateParam, value);
										}
										else if(item instanceof Long){
											long value = Long.valueOf(muxConfigUpdateParamValue);
											ebifObj.put(muxConfigUpdateParam, value);
										}
										else if(item instanceof JSONArray){
											JSONArray ElementaryStreamsArr = (JSONArray)item;
											ElementaryStreams value = new ElementaryStreams();
											String[] arr = muxConfigUpdateParamValue.split(",");
											long pid = Long.valueOf(arr[0]);
											long type = Long.valueOf(arr[1]);
											String desc = arr[2];
											value.setDescriptorList(desc);
											value.setPid(pid);
											value.setType(type);
											Gson gson = new Gson();
											String elem = gson.toJson(value);
											JSONParser parser = new JSONParser(); 
											JSONObject json = (JSONObject) parser.parse(elem);
											ElementaryStreamsArr.add(json);
											ebifObj.put(muxConfigUpdateParam, ElementaryStreamsArr);
											System.out.println(ElementaryStreamsArr.isEmpty());
											System.out.println(ElementaryStreamsArr.get(0).toString());
										}
									}
									else{

										if(item instanceof String){
											configValue = (String)item;
											if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
												status="Found";
											}
										}
										else if(item instanceof Integer){
											configValue = String.valueOf((int)item);
											if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
												status="Found";
											}
										}
										else if(item instanceof Boolean){
											configValue = String.valueOf((boolean)item);
											if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
												status="Found";
											}
										}
										else if(item instanceof Long){
											configValue = String.valueOf((long)item);
											if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
												status="Found";
											}
										}
										else if(item instanceof JSONArray){
											JSONArray ElementaryStreamsArr = (JSONArray)item;
											for(int k=0;k<ElementaryStreamsArr.size();k++){
												String[] arr = muxConfigUpdateParamValue.split(",");
												long inppid = Long.valueOf(arr[0]);
												long inptype = Long.valueOf(arr[1]);
												String inpdesc = arr[2];
												JSONObject elem = (JSONObject) ElementaryStreamsArr.get(k);
												long pid = (Long) elem.get("pid");
												long type = (Long) elem.get("type");
												String desc = (String) elem.get("descriptorList");
												if(inppid ==pid && inptype==type && inpdesc.equals(desc)){
													status = "Found";													
												}
												
											}
										}
									
									}
								}
							}
							else if(hasIndex(2,configPathArr)){
								jsonObj = (JSONObject)progObj.get(configPathArr[2]);
								System.out.println("hhhhh");
								Object item = jsonObj.get(muxConfigUpdateParam);
								if(isUpdate){
									if(item instanceof String){
										jsonObj.put(muxConfigUpdateParam, muxConfigUpdateParamValue);
									}
									else if(item instanceof Integer){
										int value = Integer.parseInt(muxConfigUpdateParamValue);
										jsonObj.put(muxConfigUpdateParam, value);
									}
									else if(item instanceof Boolean){
										boolean value = Boolean.valueOf(muxConfigUpdateParamValue);
										jsonObj.put(muxConfigUpdateParam, value);
									}
									else if(item instanceof Long){
										long value = Long.valueOf(muxConfigUpdateParamValue);
										jsonObj.put(muxConfigUpdateParam, value);
									}
								}
								else{
									if(item instanceof String){
										configValue = (String)item;
										if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
											status="Found";
										}
									}
									else if(item instanceof Integer){
										configValue = String.valueOf((int)item);
										if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
											status="Found";
										}
									}
									else if(item instanceof Boolean){
										configValue = String.valueOf((boolean)item);
										if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
											status="Found";
										}
									}
									else if(item instanceof Long){
										configValue = String.valueOf((long)item);
										if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
											status="Found";
										}
									}
								}
							}
							else{
								
								Object item = progObj.get(muxConfigUpdateParam);
								System.out.println(item.getClass());
								if(isUpdate){
									if(item instanceof String){
										progObj.put(muxConfigUpdateParam, muxConfigUpdateParamValue);
									}
									else if(item instanceof Integer){
										int value = Integer.parseInt(muxConfigUpdateParamValue);
										progObj.put(muxConfigUpdateParam, value);
									}
									else if(item instanceof Boolean){
										boolean value = Boolean.valueOf(muxConfigUpdateParamValue);
										progObj.put(muxConfigUpdateParam, value);
									}
									else if(item instanceof Long){
										long value = Long.valueOf(muxConfigUpdateParamValue);
										progObj.put(muxConfigUpdateParam, value);
									}
								}
								else{
									if(item instanceof String){
										configValue = (String)item;
										if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
											status="Found";
										}
									}
									else if(item instanceof Integer){
										configValue = String.valueOf((int)item);
										if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
											status="Found";
										}
									}
									else if(item instanceof Boolean){
										configValue = String.valueOf((boolean)item);
										if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
											status="Found";
										}
									}
									else if(item instanceof Long){
										configValue = String.valueOf((long)item);
										if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
											status="Found";
										}
									}
								}
							}
						}
					}
					}
					else{
						for(int i=0;i<programArr.size();i++){
							JSONObject progObj = (JSONObject) programArr.get(i);
							if(hasIndex(1,configPathArr) && configPathArr[1].equalsIgnoreCase("ads")){
								JSONObject ads = (JSONObject)progObj.get(configPathArr[1]);
								Object item = ads.get(muxConfigUpdateParam);
								if(isUpdate){
									if(item instanceof String){
										ads.put(muxConfigUpdateParam, muxConfigUpdateParamValue);
									}
									else if(item instanceof Integer){
										int value = Integer.parseInt(muxConfigUpdateParamValue);
										ads.put(muxConfigUpdateParam, value);
									}
									else if(item instanceof Boolean ){
										boolean value = Boolean.valueOf(muxConfigUpdateParamValue);
										ads.put(muxConfigUpdateParam, value);
									}
									else if(item instanceof Long ){
										long value = Long.valueOf(muxConfigUpdateParamValue);
										ads.put(muxConfigUpdateParam, value);
									}
								}
								else{
									
									if(item instanceof String){
										configValue = (String)item;
										if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
											status = "Found";
										}
									}
									else if(item instanceof Integer){
										configValue = String.valueOf((int)item);
										if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
											status = "Found";
										}
									}
									else if(item instanceof Boolean ){
										configValue = String.valueOf((boolean)item);
										if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
											status = "Found";
										}
									}
									else if(item instanceof Long ){
										configValue = String.valueOf((long)item);
										if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
											status = "Found";
										}
									}
								}

							}
						}
					}

				}
				
				else if(configPathArr[0].equalsIgnoreCase("stream")){
					JSONArray streamArr = (JSONArray)config.get(configPathArr[0]);
					System.out.println("Array: "+streamArr.size());
					String inputProgNum = configPathArr[1];
					char[] arrChr = inputProgNum.toCharArray();
					boolean result = Character.isDigit(arrChr[0]);
					
					if(result)
					{
						int num=Integer.parseInt(inputProgNum);
						for(int j=0;j<streamArr.size();j++){	
							//JSONObject strObj = (JSONObject) streamArr.get(j);
							
							JSONObject streams = (JSONObject)streamArr.get(num);
							Object item = streams.get(muxConfigUpdateParam);
							if(isUpdate){
								if(item instanceof String){
									streams.put(muxConfigUpdateParam, muxConfigUpdateParamValue);
								}
								else if(item instanceof Integer){
									int value = Integer.parseInt(muxConfigUpdateParamValue);
									streams.put(muxConfigUpdateParam, value);
								}
								else if(item instanceof Boolean ){
									boolean value = Boolean.valueOf(muxConfigUpdateParamValue);
									streams.put(muxConfigUpdateParam, value);
								}
								else if(item instanceof Long ){
									long value = Long.valueOf(muxConfigUpdateParamValue);
									streams.put(muxConfigUpdateParam, value);
								}
							}
							else{
								
								if(item instanceof String){
									configValue = (String)item;
									if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
										status = "Found";
									}
								}
								else if(item instanceof Integer){
									configValue = String.valueOf((int)item);
									if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
										status = "Found";
									}
								}
								else if(item instanceof Boolean ){
									configValue = String.valueOf((boolean)item);
									if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
										status = "Found";
									}
								}
								else if(item instanceof Long ){
									configValue = String.valueOf((long)item);
									if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
										status = "Found";
									}
								}
							}

						}	
					}
					
					
				}
				else if(muxConfigUpdatePath.equalsIgnoreCase("delete"))
				{
					JSONArray streamArr = (JSONArray)config.get("stream");
					System.out.println("After clearing size: " +streamArr.size());
					for (int i = 0, len = streamArr.size(); i < len; i++) {
					     streamArr.remove(i);
					     streamArr.clear();
					    // Do your removals
					}

					if(streamArr.size()==0)
					{
						status="deleted";
						//System.out.println("status of deletion: "+status);
						//System.out.println("stream status of deletion: "+streamArr);
					}
					else
					{
						status="Not deleted";
					}
					
				}
				
				
				
				else{
					jsonObj = (JSONObject)config.get(configPathArr[0]);
					Object item = jsonObj.get(muxConfigUpdateParam);
					if(isUpdate){
						if(item instanceof String){
							jsonObj.put(muxConfigUpdateParam, muxConfigUpdateParamValue);
						}
						else if(item instanceof Integer){
							int value = Integer.parseInt(muxConfigUpdateParamValue);
							jsonObj.put(muxConfigUpdateParam, value);
						}
						else if(item instanceof Boolean){
							boolean value = Boolean.valueOf(muxConfigUpdateParamValue);
							jsonObj.put(muxConfigUpdateParam, value);
						}
						else if(item instanceof Long){
							long value = Long.valueOf(muxConfigUpdateParamValue);
							jsonObj.put(muxConfigUpdateParam, value);
						}
					}
					else{
						if(item instanceof String){
							configValue = (String)item;
							if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
								status="Found";
							}
						}
						else if(item instanceof Integer){
							configValue = String.valueOf((int)item);
							if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
								status="Found";
							}
						}
						else if(item instanceof Boolean){
							configValue = String.valueOf((boolean)item);
							if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
								status="Found";
							}
						}
						else if(item instanceof Long){
							configValue = String.valueOf((long)item);
							if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
								status="Found";
							}
						}
					}
				}
			}
			
			else if(muxConfigUpdateParam.toString().equalsIgnoreCase("sourceCIDR"))
			{
				Object item=data.get(muxConfigUpdateParam);
				System.out.println("mux update value: "+item.toString());
				if(isUpdate){
					if(item instanceof String){
						System.out.println("Source Cidr updated" );
						data.put(muxConfigUpdateParam, muxConfigUpdateParamValue);
					}
					else if(item instanceof Integer){
						int value = Integer.parseInt(muxConfigUpdateParamValue);
						data.put(muxConfigUpdateParam, value);
					}
					else if(item instanceof Boolean){
						boolean value = Boolean.valueOf(muxConfigUpdateParamValue);
						data.put(muxConfigUpdateParam, value);
					}
					else if(item instanceof Long){
						long value = Long.valueOf(muxConfigUpdateParamValue);
						data.put(muxConfigUpdateParam, value);
					}
				}
				else{
					if(item instanceof String){
						configValue = (String)item;
						if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
							status="Found";
						}
					}
					else if(item instanceof Integer){
						configValue = String.valueOf((int)item);
						if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
							status="Found";
						}
					}
					else if(item instanceof Boolean){
						configValue = String.valueOf((boolean)item);
						if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
							status="Found";
						}
					}
					else if(item instanceof Long){
						configValue = String.valueOf((long)item);
						if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
							status="Found";
						}
					}
				}
				
			}
			
			else {
				Object item = config.get(muxConfigUpdateParam);
				if(isUpdate){
					if(item instanceof String){

						config.put(muxConfigUpdateParam, muxConfigUpdateParamValue);
					}
					else if(item instanceof Integer){
						int value = Integer.parseInt(muxConfigUpdateParamValue);
						config.put(muxConfigUpdateParam, value);
					}
					else if(item instanceof Boolean){
						boolean value = Boolean.valueOf(muxConfigUpdateParamValue);
						config.put(muxConfigUpdateParam, value);
					}
					else if(item instanceof Long){
						long value = Long.valueOf(muxConfigUpdateParamValue);
						config.put(muxConfigUpdateParam, value);
					}
				}
				else{
					if(item instanceof String){
						configValue = (String)item;
						if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
							status="Found";
						}
					}
					else if(item instanceof Integer){
						configValue = String.valueOf((int)item);
						if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
							status="Found";
						}
					}
					else if(item instanceof Boolean){
						configValue = String.valueOf((boolean)item);
						if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
							status="Found";
						}
					}
					else if(item instanceof Long){
						configValue = String.valueOf((long)item);
						if(muxConfigUpdateParamValue.equalsIgnoreCase(configValue)){
							status="Found";
						}
					}
				}
			}			
			//System.out.println(mux.toString());
			//System.out.println(streamerConfigObj.toJSONString());
			//System.out.println(streamerConfig);
			if(isUpdate){
				streamerConfig = streamerConfigObj.toJSONString();
			}
			
			
		
	}
		
		
		catch(Exception e){
			e.printStackTrace();
		}
		if(isUpdate){
			return mux.toJSONString();
		}
		else{
			return status;
		}
	}

	public String validateMUXConfig(){
		String url="https://vdecm-wc-05q.sys.comcast.net/api/v2/schemas/mux/validation";
		Header header = new BasicHeader(HttpHeaders.AUTHORIZATION," Bearer " + token);	
		HttpUtility httpUtil = new HttpUtility();
		String validateMuxResponse =null;
		try{	
			String mux = updateMUXConfig();
			System.out.println(mux);
			StringEntity entity = new StringEntity(mux);
			JSONObject jsonObj = httpUtil.sendPostReq(url, header,entity);
			validateMuxResponse = jsonObj.toString();
			System.out.println(validateMuxResponse);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return validateMuxResponse;
	}

	private boolean hasIndex(int index,String[] list){
		if(index < list.length)
			return true;
		return false;
	}

	
	public String checkMUXConfigFortheValue(){
		String status = null;
		
		isUpdate = false;
		
		status = updateMUXConfig();
		
		return status;
	}


	public String CheckStreamCreation() throws ParseException
	{
		JSONObject mux = null;
		JSONParser responseParser = new JSONParser();
		System.out.println("streamer config in update MUX " +streamerConfig);
		JSONObject streamerConfigObj = (JSONObject) responseParser.parse(streamerConfig);
		JSONObject streamer = (JSONObject)streamerConfigObj.get("streamer");			
		JSONObject data= (JSONObject) streamer.get("data");
		mux = (JSONObject)data.get("mux");
		JSONObject config = (JSONObject)mux.get("config");
		JSONArray strArr = (JSONArray)config.get("stream");
		if(strArr.size()==16)
		{
			return "16 Stream Objects Created Successfully";
		}
		return "Not created";
		
	}
	
	public String ValidateStreamError()
	{
		String sub=(String) error1.subSequence(10, 155);
		System.out.println("string value: "+sub);
		if(sub.toString().equalsIgnoreCase("validation error: validator 2.08-37.dna failed: object property 'config' validation failed: object property 'stream' validation failed: object pr"))
		{
			return "Validated Stream Error Successfully";
			
		}
		return null;
	}


	public static void main(String[] args) throws IOException{
		VDECMAPIImpl vde = new VDECMAPIImpl();
		//VDECMHelper helper = new VDECMHelper();
		System.out.print(vde.loginVDE());
		vde.streamerID="ca441c40-ce11-4810-b11d-694f629ed932";
		vde.destIPAddr="232.36.3.118";
		vde.destPort=36118;
		vde.streamerName="Odie-100";

		vde.streamerConfig = vde.getStreamer();
		System.out.println(vde.streamerConfig);
		vde.muxConfigUpdatePath="program/0/ebif";
		vde.muxConfigUpdateParam="elementaryStreams";
		vde.muxConfigUpdateParamValue="1,0,test1";
		//vde.ValidateAndUpdateMUXConfig();

		//.streamerConfig = helper.createDataJSON(vde.token);

		//vde.streamerConfig =vde.createStreamer();

		//vde.deployStreamer();

	}

}
