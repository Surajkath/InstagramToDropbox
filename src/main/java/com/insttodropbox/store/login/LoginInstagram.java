package com.insttodropbox.store.login;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import com.app.client.Client;
import com.app.client.InstagramClient;

@Path("/login")
public class LoginInstagram {

	Client client = new InstagramClient(“Client_Key”,”Client_Password”,”callBackUrl”); 
        //in my case the callback url is /../login/callBackInstagram
  	//I haven’t done the dependency injection here you can do it with spring or any other framework you like

	@Path("/signinToInstagram/")
	@GET
	public void loginInstagram(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws IOException {
		client.redirect(request, response);
	}

	@Path("/callBackInstagram/")
	@GET
	public void loginHttpClient(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws IOException,
			IllegalAccessException {
		request.getSession().setAttribute("images",
				client.fetchUserData(request, response));
		String accessCodeInstagram = request.getParameter("code");
		// setting session attribute should be in logic but I deliberately put
		// it here
		// so people can see it and decide for themselves how to consider CSRF
		// protection(this is just a sample way to do it ideally you need to
		// check every request you get which is very much application dependent)
		request.getSession().setAttribute("CSRF-token", accessCodeInstagram);
		response.sendRedirect("http://localhost:8080/InstagramToDropbox/rest/loginStorage/signinToStorage?state="
				+ accessCodeInstagram);
	}
}
