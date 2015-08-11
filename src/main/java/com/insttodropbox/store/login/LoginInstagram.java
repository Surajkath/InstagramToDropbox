package com.insttodropbox.store.login;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;







import org.json.simple.parser.ParseException;

import com.app.client.Client;
import com.app.client.InstagramClient;
import com.sun.jersey.spi.dispatch.RequestDispatcher;




@Path("/login")
public class LoginInstagram {

	Client client = new InstagramClient("116754be184c42978a454e4e321d9c55","ed311edbdb1d4bf4aa96c6df0a026487","http://localhost:8080/InstagramToDropbox/rest/login/callBackInstagram");;
	@Path("/signinToInstagram/")
	@GET
	public void loginInstagram(@Context HttpServletRequest request,@Context HttpServletResponse response) throws IOException  {
		client.redirect(request, response);
	}

	@Path("/callBackInstagram/")
	@GET
	public void loginHttpClient(@Context HttpServletRequest request,@Context HttpServletResponse response) throws IOException, IllegalAccessException, ParseException  {
		request.getSession().setAttribute("images", client.fetchUserData(request, response));
		String accessCodeInstagram = request.getParameter("code");
		//setting session attribute should be in logic but I deliberately put it here 
		//so people can see it and decide for themselves how to consider CSRF protection(this is just a sample way to do it ideally you need to check every request you get which is very much application dependent)
		request.getSession().setAttribute("CSRF-token",accessCodeInstagram);
		response.sendRedirect("http://localhost:8080/InstagramToDropbox/rest/loginStorage/signinToStorage?state="+accessCodeInstagram);
	}
}

