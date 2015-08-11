package com.insttodropbox.store.login;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;





import com.app.Storage.DropBoxStorage;
import com.app.Storage.Storage;
import java.lang.Exception;
import com.dropbox.core.json.JsonReader.FileLoadException;


@Path("/loginStorage/")
public class LoginStorage {

	Storage storage = new DropBoxStorage("03hm18czqjqmbo3","hubjsfm67cma7v5","http://localhost:8080/InstagramToDropbox/rest/loginStorage/callBackStorage");

	@Path("/signinToStorage/")
	@GET
	public void loginInstagram(@Context HttpServletRequest request,@Context HttpServletResponse response) throws FileLoadException, IOException  {
		storage.redirect(request, response);
	}
	
	@Path("/callBackStorage/")
	@GET
	public void loginHttpClient(@Context HttpServletRequest request,@Context HttpServletResponse response) throws Exception {
		storage.storeData(request,response,(String[]) request.getSession().getAttribute("images") );
	}
}


