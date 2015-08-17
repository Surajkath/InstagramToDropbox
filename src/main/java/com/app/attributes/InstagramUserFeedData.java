package com.app.attributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;


import com.jayway.jsonpath.JsonPath;

//You can JackSon for creating user Objects but I found Instagram Response very dynamic so I used

public class InstagramUserFeedData extends UserData {

	
	final String accessUrl = "https://api.instagram.com/v1/users/self/feed?access_token=";

@Override
public String[] getData(String accessToken) throws IllegalAccessException, ClientProtocolException, IOException {
		if(accessToken ==  null)
			throw new IllegalAccessException("No AccessToken");
		String url = accessUrl + accessToken;
		HttpClient client = HttpClients.createDefault();
		HttpGet request = new HttpGet(url);
		HttpResponse response = client.execute(request);
		BufferedReader rd = new BufferedReader(
	                   new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		List<String> urls = JsonPath.read(result.toString(), "$.data[*].images.standard_resolution.url");
		return urls.toArray(new String[urls.size()]);
  }
	
}
