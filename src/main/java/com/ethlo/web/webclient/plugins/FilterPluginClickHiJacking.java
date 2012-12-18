package com.ethlo.web.webclient.plugins;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author Morten Haraldsen
 */
public class FilterPluginClickHiJacking extends BeforeFilterPlugin
{
	@Override
	protected boolean doFilterBefore(HttpServletRequest request, HttpServletResponse response)
	{
		response.setHeader("X-Frame-Options", "SAMEORIGIN");
		response.setHeader("Frame-Options", "SAMEORIGIN");
		return true;
	}
}