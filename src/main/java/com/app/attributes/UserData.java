package com.app.attributes;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

public abstract class UserData {
	
	
	public abstract String[]  getData(String accessToken) throws IllegalAccessException, ClientProtocolException, IOException;

}
