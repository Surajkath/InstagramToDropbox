package com.app.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.parser.ParseException;

import com.jayway.jsonpath.JsonPath;



public abstract class  Client {

	
	private String clientKey;
	public Client(String clientKey, String clientPassword, String callbackUrl) {
		super();
		this.clientKey = clientKey;
		this.clientPassword = clientPassword;
		this.callbackUrl = callbackUrl;
	}

	private String clientPassword;
	private String callbackUrl;
	
	
	public String getClientKey() {
		return clientKey;
	}

	public String getClientPassword() {
		return clientPassword;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public abstract void redirect(HttpServletRequest request, HttpServletResponse response)throws IOException;

	//most of the resources on the Net are URL's so I am sending back a String array.You can overload this method accordingly
	public abstract String[] fetchUserData(HttpServletRequest request, HttpServletResponse response) throws ClientProtocolException, IOException, ParseException, IllegalAccessException;

	protected abstract String getAccessTokenUrl();
	protected abstract String getAccessTokenFromResponse(String response);
	
	protected abstract List<NameValuePair> getAccessTokenUrlParaments();
	
	protected String getAccessToken() throws ClientProtocolException, IOException, ParseException{
		HttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(getAccessTokenUrl()); 
		post.setEntity(new UrlEncodedFormEntity(getAccessTokenUrlParaments()));
		
		HttpResponse response = client.execute(post);
		BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));
 
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		return getAccessTokenFromResponse(result.toString());
	}

}
