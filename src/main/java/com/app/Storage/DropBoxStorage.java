package com.app.Storage;


import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxSessionStore;
import com.dropbox.core.DbxStandardSessionStore;
import com.dropbox.core.DbxWebAuth;
import com.dropbox.core.DbxWriteMode;
import com.dropbox.core.json.JsonReader.FileLoadException;

public class DropBoxStorage extends Storage {
	
	
	public DropBoxStorage(String appKey, String appPassword, String callBackUrl) {
		super(appKey, appPassword, callBackUrl);
	}
	
	
	private String getAuthenticatedUrl(HttpServletRequest request, HttpServletResponse response) throws FileLoadException, IOException {
		DbxWebAuth auth = initDropBoxAuth(request); 
		String authorizePageUrl = auth.start();
		return authorizePageUrl;
		
	}
	
	
	private DbxWebAuth initDropBoxAuth(HttpServletRequest request){
		DbxRequestConfig requestConfig = new DbxRequestConfig("text-edit/0.1", Locale.getDefault().toString());
		 DbxAppInfo appInfo = new DbxAppInfo(getAppKey(),getAppPassword());
		 HttpSession session = request.getSession(true);
		
		 String sessionKey = "dropbox-auth-csrf-token";
		 DbxSessionStore csrfTokenStore = new DbxStandardSessionStore(session, sessionKey);
		 return new DbxWebAuth(requestConfig, appInfo, getCallBackUrl(), csrfTokenStore);
	}

	@Override
	public void redirect(HttpServletRequest request,
			HttpServletResponse response) throws FileLoadException, IOException {
		String authenticateUrl = getAuthenticatedUrl(request, response);
		response.sendRedirect(authenticateUrl);
		
	}

	@Override
	public boolean storeData(HttpServletRequest request,
			HttpServletResponse response, String[] images) throws DbxException, IOException, DbxWebAuth.Exception {
		DbxAuthFinish authFinish=null;
		DbxRequestConfig requestConfig = new DbxRequestConfig("text-edit/0.1", Locale.getDefault().toString());
		DbxWebAuth auth = initDropBoxAuth(request);
		authFinish = auth.finish(request.getParameterMap());
		String accessToken = authFinish.accessToken;
		DbxClient client = new DbxClient(requestConfig, accessToken);
		int i=0;
		for(String image:images){
			URL url = new URL(image); 
			URLConnection conn = url.openConnection();
			try {
			    DbxEntry.File uploadedFile = client.uploadFile("/magnum-opus"+i+".jpg",
			        DbxWriteMode.add(),conn.getContentLength(), conn.getInputStream());
			    System.out.println("Uploaded: " + uploadedFile.humanSize);
			    i++;
			} finally {
			    
			}
		}
		return true;
	}
}
