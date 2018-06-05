package com.comcast.core.VDEutility;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.util.EntityUtils;

public class HttpUtility {

		
	/*Method to send post*/
	public JSONObject sendPostReqWithCredentials(List <NameValuePair> nvps,String host){
		JSONObject jsonObj = null;
		HttpHost target = new HttpHost(host, 80, "http");
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(new AuthScope(target.getHostName(), target.getPort()), new UsernamePasswordCredentials("Administrator", "none!!"));
        
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
		try{
            AuthCache authCache = new BasicAuthCache();

            BasicScheme basicAuth = new BasicScheme();
            authCache.put(target, basicAuth);

            HttpClientContext localContext = HttpClientContext.create();
            localContext.setAuthCache(authCache);

            HttpPost httppost = new HttpPost("/vnm-api/index.php");
            
            httppost.setEntity(new UrlEncodedFormEntity(nvps));
            
            CloseableHttpResponse response = httpclient.execute(target, httppost, localContext);
            String status =response.getStatusLine().toString();
			System.out.println(status);
            try {
            	JSONParser responseParser = new JSONParser();			
    			jsonObj = (JSONObject) responseParser.parse(new InputStreamReader(response.getEntity().getContent()));
    			System.out.println(jsonObj.toJSONString());            
            } finally {
                response.close();
            }
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
            try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

		
		return jsonObj;
	}
	
	
	public JSONObject sendPostReq(String Url,Header header,StringEntity params){

	
		CloseableHttpClient httpClient = HttpClients
		.custom()
		 .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();

		JSONObject jsonObj = null;
		try {		
			HttpPost post = new HttpPost(Url);
			if(null!=header){
				post.addHeader(header);
			}
			if(null!=params){
				post.setEntity(params);
			}
			HttpResponse response = httpClient.execute(post);		  
			
			String status =response.getStatusLine().toString();
			System.out.println(status);
			if(status.equalsIgnoreCase("HTTP/1.1 400 Bad Request")){
				System.out.println(EntityUtils.toString(response.getEntity()));
			}
			else{
				JSONParser responseParser = new JSONParser();			
				jsonObj = (JSONObject) responseParser.parse(new InputStreamReader(response.getEntity().getContent()));
			}
		} catch (UnsupportedOperationException | IOException | ParseException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return jsonObj;
	}
	

	public JSONObject sendGetReq(String Url,Header header){
		CloseableHttpClient httpClient = HttpClients
				.custom()
				 .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();
		JSONObject jsonObj = null;
		try {		
			HttpGet getReq = new HttpGet(Url);
			if(null!=header){
				getReq.addHeader(header);
			}
			HttpResponse response = httpClient.execute(getReq);		    
			String status =response.getStatusLine().toString();
			System.out.println(status);
			//System.out.println(EntityUtils.toString(response.getEntity()));
			if(status.equalsIgnoreCase("HTTP/1.1 200 OK"))
			{
				JSONParser responseParser = new JSONParser();			
				jsonObj = (JSONObject) responseParser.parse(new InputStreamReader(response.getEntity().getContent()));
			}
		} catch (UnsupportedOperationException | IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return jsonObj;
	}
	
	public String sendGetReqForDashURL(String Url,Header header){
		CloseableHttpClient httpClient = HttpClients
				.custom()
				 .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();
		String status=null;
		try {		
			HttpGet getReq = new HttpGet(Url);
			if(null!=header){
				getReq.addHeader(header);
			}
			HttpResponse response = httpClient.execute(getReq);		    
			status =response.getStatusLine().toString();
			//System.out.println(response.getStatusLine());
			//System.out.println(EntityUtils.toString(response.getEntity()));
			//if(status.equalsIgnoreCase("200 OK"))
			//{
				//JSONParser responseParser = new JSONParser();			
				//jsonObj = (JSONObject) responseParser.parse(new InputStreamReader(response.getEntity().getContent()));
			//}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return status;
	}
	
	public JSONObject sendPutReq(String Url,Header header,StringEntity params){
		CloseableHttpClient httpClient = HttpClients
		.custom()
		.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
		.build();
		JSONObject jsonObj = null;
		try {		
			HttpPut putReq = new HttpPut(Url);
			if(null!=header){
				putReq.addHeader(header);
			}
			if(null!=params){
				putReq.setEntity(params);
			}
			HttpResponse response = httpClient.execute(putReq);
			
			String status =response.getStatusLine().toString();
			//System.out.println(response.getStatusLine());
			//System.out.println(EntityUtils.toString(response.getEntity()));
			if(status.equalsIgnoreCase("HTTP/1.1 200 OK"))
			{	
				JSONParser responseParser = new JSONParser();			
				jsonObj = (JSONObject) responseParser.parse(new InputStreamReader(response.getEntity().getContent()));
			}
			else{
				System.out.println(response.getStatusLine());
				System.out.println(EntityUtils.toString(response.getEntity()));
			}
		} catch (Exception e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return jsonObj;
	}
	
	public JSONObject sendDeleteReq(String Url,Header header){
		CloseableHttpClient httpClient = HttpClients
				.custom()
				 .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();
		JSONObject jsonObj = null;
		try {		
			HttpDelete delete = new HttpDelete(Url);
			if(null!=header){
				delete.addHeader(header);
			}
			HttpResponse response = httpClient.execute(delete);	
			//System.out.println(response.getStatusLine());
			//System.out.println(EntityUtils.toString(response.getEntity()));
			JSONParser responseParser = new JSONParser();			
			jsonObj = (JSONObject) responseParser.parse(new InputStreamReader(response.getEntity().getContent()));
		} catch (UnsupportedOperationException | IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return jsonObj;
	}
	
	

}
