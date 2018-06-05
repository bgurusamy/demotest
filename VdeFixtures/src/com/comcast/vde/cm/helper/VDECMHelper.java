package com.comcast.vde.cm.helper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.message.BasicHeader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.comcast.core.VDEutility.HttpUtility;
import com.comcast.vde.cm.model.Ads;
import com.comcast.vde.cm.model.Audio;
import com.comcast.vde.cm.model.Config;
import com.comcast.vde.cm.model.Data;
import com.comcast.vde.cm.model.Decryption;
import com.comcast.vde.cm.model.Ebif;
import com.comcast.vde.cm.model.Input;
import com.comcast.vde.cm.model.Mux;
import com.comcast.vde.cm.model.Output;
import com.comcast.vde.cm.model.Program;
import com.comcast.vde.cm.model.Scte30Server;
import com.comcast.vde.cm.model.Video;
import com.comcast.vde.cm.model.Streams;
import fit.ColumnFixture;
import com.google.gson.Gson;


public class VDECMHelper extends ColumnFixture{

	String fileName;
	String token;
	Boolean addStreams=false;
	String status;
	
	public String createDataJSON(){
		
		JSONObject dataJSON = null;
		BufferedReader br = null;
		String streamerName=null;
		String location=null;
		String version=null;
		int versionInt =0;
		String dstAddress=null;
		String sourceIp=null;
		String dstPort=null;
		String programURL=null;
		String networkLatency=null;
		String systemId = null;
		List<Program> prgList = new ArrayList<Program>();
		//List<Streams> streamList=new ArrayList<Streams>();
		List<Streams> streamList1=new ArrayList<Streams>();
		String dataJSONStr = null;
		int programCount =0;

		try {
			String sCurrentLine;

			br = new BufferedReader(new FileReader(fileName));

			while ((sCurrentLine = br.readLine()) != null) {
				String[] line = sCurrentLine.split(":");
				String paramName =line[0];
				String paramValue=line[1];
				if(paramName.equalsIgnoreCase("name")){
					streamerName  =paramValue;
				}
				else if(paramName.equalsIgnoreCase("location")){
					location = paramValue;
				}
				else if(paramName.equalsIgnoreCase("version")){
					version=paramValue;
					String str = version.replaceAll("[^0-9]","");
					System.out.println("version :" + str );
					versionInt = Integer.parseInt(str);
				}
				else if(paramName.equalsIgnoreCase("dstAddress")){
					dstAddress= paramValue;
				}
				else if(paramName.equalsIgnoreCase("dstPort")){
					dstPort=paramValue;
				}
				else if(paramName.equalsIgnoreCase("programURL")){
					programURL=paramValue+":"+line[2];
					int prgIndex = programCount;
					int prgNumber = programCount + 1;
					Program prg = createProgramObj(programURL,token,prgIndex,prgNumber,versionInt);
					prgList.add(prg);
					 programCount = programCount + 1;
				}
				else if(paramName.equalsIgnoreCase("networkLatency")){
					networkLatency=paramValue;
				}
				else if(paramName.equalsIgnoreCase("systemId")){
					systemId = paramValue;
				}
				else if(paramName.equalsIgnoreCase("sourceIp")){
					sourceIp = paramValue;
				}
			}	
			
			Data data = new Data();	
			Mux mux = new Mux();
			Gson gson = new Gson();
			data.setName(streamerName);
			data.setLocation(location);
			data.setSourceCIDR(sourceIp);
			data.setDeploy(true);

			mux.setVersion(version);				
			Config config = new Config();		
			config.setSegmentDuration("PT2.002S");
			Output output = new Output();		
			output.setDstAddress(dstAddress);
			output.setUdpDstPort(Integer.parseInt(dstPort));		
			output.setEnable(true);		
			output.setTsRate(38800000);
			output.setInterface("eth1");
			output.setTsid(0);
			output.setSdtEnable(false);
			output.setUdpSrcPort(0);
			output.setTimeToLive(32);
			
			Scte30Server sct = new Scte30Server();
			
			sct.setDomainName("localhost");
			sct.setTcpPort(5169);

			
			/*streamList.add(streams.getUrl());
			streamList.add(streams.getEnable());
			streamList.add(streams.getBitRate());
			streamList.add(streams.getStreamPid());
			streamList.add(streams.getStreamName());
			streamList.add(streams.getStreamIndex());
			JSONObject strObj=new JSONObject();
			strObj.put("streamIndex",streamList.get(5));
			strObj.put("streamName",streamList.get(4));
			strObj.put("streamPid",streamList.get(3));
			strObj.put("bitrate",streamList.get(2));
			strObj.put("url",streamList.get(0));
			strObj.put("enable",streamList.get(1));*/
			
		
		//To add 16 stream objects to a streamer
			
			if(addStreams)
			{
				for(int i=0; i<16;i++)
				{
					Streams streams=new Streams();
					streams.setUrl("udp://127.0.0.1:10000");
					streams.setBitRate(1);
					streams.setEnable(true);
					streams.setStreamIndex(i);
					streams.setStreamName("str1");
					streams.setStreamPid(2);
					streamList1.add(streams);
				}

				
				
				System.out.println("The array size : " +streamList1.size());
			}
			else{
				Streams streams=new Streams();
				streams.setUrl("udp://127.0.0.1:10000");
				streams.setBitRate(1);
				streams.setEnable(true);
				streams.setStreamIndex(0);
				streams.setStreamName("str1");
				streams.setStreamPid(2);
				streamList1.add(streams);
			}
			
			config.setStream(streamList1);			
			config.setOutput(output);
			config.setProgram(prgList);
			config.setNetworkLatency(networkLatency);
			config.setSystemId(systemId);
			config.setFanoutProxy("");
			config.setScte30Server(sct);
			mux.setConfig(config);
			data.setMux(mux);		


			dataJSONStr = gson.toJson(data);		
			JSONParser responseParser = new JSONParser();		
			dataJSON = (JSONObject) responseParser.parse(dataJSONStr);
			System.out.println(dataJSON);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dataJSONStr;
	}

	
	private Program createProgramObj(String url,String token,int prgIndex,int prgNum, int versionInt){
		Program prg = new Program();
		Ads ad = new Ads();
		Audio audio = null;
		Video video = null;
		Decryption decryption = null;
		List<Audio> audioList = new ArrayList<Audio>();
				
		prg.setEnable(true);
		prg.setProgramIndex(prgIndex);
		prg.setProgramNumber(prgNum);
		prg.setMaxRate(4310000);
		Input input = new Input();

		input.setUrl(url);
		input.setContentType("non-muxed");
		input.setMpdLatency("PT8.008S");
		if(versionInt > 20526){
			input.setCspEnable(false);	
			input.setRevLocationOrder(false);
		}
		prg.setInput(input);
		prg.setProgramName("bypass");
		
		decryption = new Decryption();
		
		decryption.setEnable(false);
		decryption.setKey("");
		
		prg.setDecryption(decryption);
		
		ad.setAcrUrl("http://127.0.0.1/ads/index.html");
		ad.setPsnUrl("http://127.0.0.1/psn/report.psn");
		ad.setUdpPort(30210);
		ad.setAdBreakWindow("PT10M");
		ad.setAdType("local");
		ad.setAdZone("Test AdZone");
		ad.setChannelName("N/A");
		ad.setEnable(false);
		prg.setAds(ad);
		
		Ebif ebif = new Ebif();
		ebif.setBandwidthConfigName("ETV");
		ebif.setDelay("PT0S");
		ebif.setDpiPid(1);
		ebif.setProgramNumber(1);
		ebif.setSrcUrl("udp://127.0.0.1:10000");
		ebif.setTimingMode("fixed");
		ebif.setEnable(false);
		
		prg.setEbif(ebif);
		
				
		JSONObject mediaInfos = getMediaInfo(url,token);
		JSONArray mediaInfosArr = (JSONArray) mediaInfos.get("mediaInfos");
		for(int i=0;i<mediaInfosArr.size();i++){
			JSONObject mediaInfo = (JSONObject) mediaInfosArr.get(i);
			String contentType = (String)mediaInfo.get("contentType");
			String representationId = (String)mediaInfo.get("representationId");
			String adaptationSetIdStr = (String)mediaInfo.get("adaptationSetId");
			
			if(contentType.equalsIgnoreCase("audio")){
				
				String codecs = (String)mediaInfo.get("codecs");
				String langauge = (String)mediaInfo.get("langauge");
				int adaptationSetId = Integer.parseInt(adaptationSetIdStr);
				int audioIndex = adaptationSetId-3;
				audio = new Audio();
				audio.setAdaptationSetId(adaptationSetIdStr);
				audio.setRepresentationId(representationId);
				audio.setAudioIndex(audioIndex);
				audio.setBufferDelay("PT1S");
				if(codecs.equalsIgnoreCase("ac3") && langauge.equalsIgnoreCase("en")){
					audio.setEnable(true);
				}
				else{
					audio.setEnable(false);
				}
				
				audioList.add(audio);
				
			}
			else if(contentType.equalsIgnoreCase("video")){
				if(representationId.equalsIgnoreCase("root_video0")){
					video = new Video();
					video.setBufferDelay("PT1S");
					video.setRepresentationId(representationId);
					video.setAdaptationSetId(adaptationSetIdStr);
				}
			}
			
		}
		prg.setAudio(audioList);
		prg.setVideo(video);

		return prg;
	}

	private JSONObject getMediaInfo(String mediaInfoURL,String token){
		String dashURL="https://vdecm-wc-05q.sys.comcast.net/api/v2/dash/media-info?url={escaped-url}";
		dashURL = dashURL.replace("{escaped-url}",mediaInfoURL );
		Header header = new BasicHeader(HttpHeaders.AUTHORIZATION," Bearer " + token);
		HttpUtility httpUtil = new HttpUtility();
		JSONObject json =null;
		try {

			json = httpUtil.sendGetReq(dashURL,header);
			System.out.println("program Url valid");
			
		}
		catch(Exception e){
			e.printStackTrace();
		}

		return json;
	}
	
	public static void main(String args[]){
		//VDECMAPIImpl impl =new VDECMAPIImpl();
		//token = impl.loginVDE();
		
		//VDECMHelper helper = new VDECMHelper();
		
		//helper.createDataJSON();
	}

}
