package com.app.client;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.parser.ParseException;

import com.app.attributes.InstagramUserFeedData;
import com.app.attributes.UserData;
import com.jayway.jsonpath.JsonPath;


public class InstagramClient extends Client {

	String accessToken;
	String accessCode;
	final String redirectUrl= "https://api.instagram.com/oauth/authorize/?client_id=116754be184c42978a454e4e321d9c55&redirect_uri=http://localhost:8080/InstagramToDropbox/rest/login/callBackInstagram&response_type=code";
	final String accessTokenUrl = "https://api.instagram.com/oauth/access_token";
	
	UserData userData = new InstagramUserFeedData();
	public InstagramClient(String clientKey,String clientPassword, String callbackUrl){
		super(clientKey, clientPassword, callbackUrl);
	}
	
	
	@Override
	public void redirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendRedirect(redirectUrl);
	}

	@Override
	public String[] fetchUserData(HttpServletRequest request,
			HttpServletResponse response) throws ClientProtocolException, IOException, ParseException, IllegalAccessException {
		accessCode = request.getParameter("code");
		String accessToken = getAccessToken();
		return userData.getData(accessToken);
	}

	@Override
	protected String getAccessTokenUrl() {
		return accessTokenUrl;
	}

	
	@Override
	protected String getAccessTokenFromResponse(String response) {
		return accessToken =  JsonPath.read(response,"$.access_token");
	}

	@Override
	protected List<NameValuePair> getAccessTokenUrlParaments() {
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("client_id", getClientKey()));
		urlParameters.add(new BasicNameValuePair("client_secret", getClientPassword()));
		urlParameters.add(new BasicNameValuePair("grant_type","authorization_code"));
		urlParameters.add(new BasicNameValuePair("redirect_uri", getCallbackUrl()));
		urlParameters.add(new BasicNameValuePair("code", accessCode));
		return urlParameters;
	}
		
}
