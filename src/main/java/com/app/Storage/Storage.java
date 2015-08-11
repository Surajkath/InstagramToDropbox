package com.app.Storage;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dropbox.core.json.JsonReader.FileLoadException;

public abstract class Storage {

	private String appKey ;
	private String appPassword;
	private String callBackUrl;
	
	public String getAppKey() {
		return appKey;
	}

	public String getAppPassword() {
		return appPassword;
	}

	public String getCallBackUrl() {
		return callBackUrl;
	}

	public Storage(String appKey, String appPassword, String callBackUrl) {
		super();
		this.appKey = appKey;
		this.appPassword = appPassword;
		this.callBackUrl = callBackUrl;
	}

	public abstract void redirect(HttpServletRequest request, HttpServletResponse response) throws FileLoadException, IOException;
	
	public abstract boolean storeData(HttpServletRequest request, HttpServletResponse response,String[] resourceUrls) throws Exception;
}
