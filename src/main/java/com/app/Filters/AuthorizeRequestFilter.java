package com.app.Filters;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;


public class AuthorizeRequestFilter implements ContainerRequestFilter {
	@Context private HttpServletRequest httpRequest;
	int count =0;

	public ContainerRequest filter(ContainerRequest request) {		
		if(request.getRequestUri().toString().contains("/signinToStorage")) {
			String requestTokenValue = httpRequest.getParameter("state");
			String sessionTokenValue = (String) httpRequest.getSession().getAttribute("CSRF-token");
			if(requestTokenValue == null || sessionTokenValue == null || !sessionTokenValue.equalsIgnoreCase(requestTokenValue)) {
				ResponseBuilder builder = null;
				String response = "Unauthorized Access";
				
		        builder = Response.status(Response.Status.UNAUTHORIZED).entity(response);
		        throw new WebApplicationException(builder.build());
			}	
		}        
		return request;
	}

}

