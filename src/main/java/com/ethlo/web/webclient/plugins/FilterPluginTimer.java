package com.ethlo.web.webclient.plugins;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Send a header with the timing of the request
 * 
 * @author Morten Haraldsen
 */
public class FilterPluginTimer extends BeforeFilterPlugin
{
	private final static String REQUEST_RECEIVED_HEADER_NAME = "X-Request-Received";
	
	@Override
	protected boolean doFilterBefore(HttpServletRequest request, HttpServletResponse response)
	{
		response.setHeader(REQUEST_RECEIVED_HEADER_NAME, Long.toString(System.currentTimeMillis()));
		return true;
	}
}
