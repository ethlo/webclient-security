package com.ethlo.web.webclient.plugins;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 	response.addHeader("Access-Control-Allow-Headers", "Authorization");
	response.addHeader("Access-Control-Allow-Headers", "X-NoAuthChallenge");
	response.addHeader("Access-Control-Allow-Headers", "X-Requested-With");
	response.addHeader("Access-Control-Allow-Headers", "Content-Type");
	response.addHeader("Access-Control-Allow-Headers", "X-Host");
 * 
 * @author mha
 */
public class FilterPluginCors extends BeforeFilterPlugin
{
	private String allowOrigins;
	private boolean allowCredentials;
	private List<String> allowHeaders;
	private int maxAge = 604800;
	private List<String> allowedMethods = Arrays.asList("POST", "GET", "OPTIONS");
	
	@Override
	public boolean doFilterBefore(HttpServletRequest request, HttpServletResponse response)
	{
		// Check white-list for allowOrigin
		response.addHeader("Access-Control-Allow-Origin", allowOrigins);
		response.addHeader("Access-Control-Allow-Credentials", Boolean.toString(this.allowCredentials));
		
		if ("OPTIONS".equalsIgnoreCase(request.getMethod()))
		{
			response.addHeader("Access-Control-Allow-Methods", toCsvString(allowedMethods));
			response.addHeader("Access-Control-Max-Age", Integer.toString(maxAge ));
			
			for (String header : allowHeaders)
			{
				response.addHeader("Access-Control-Allow-Headers", header);
			}
		}
		
		return true;
	}

	private String toCsvString(List<String> allowedMethods)
	{
		String retVal = "";
		int index = 0;
		for (String s : allowedMethods)
		{
			if (index > 0)
			{
				retVal += ",";
			}
			retVal += s;
			index++;
		}
		return retVal;
	}
}