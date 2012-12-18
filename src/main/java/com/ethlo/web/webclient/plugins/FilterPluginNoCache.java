package com.ethlo.web.webclient.plugins;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ethlo.web.filtermapping.VersionNumber;

/**
 * http://stackoverflow.com/questions/49547/making-sure-a-web-page-is-not-cached-across-all-browsers
 * 
 * @author Morten Haraldsen
 */
public class FilterPluginNoCache extends BeforeFilterPlugin
{
	@Override
	protected boolean doFilterBefore(HttpServletRequest request, HttpServletResponse response)
	{
		final VersionNumber httpVersion = super.getHttpVersion(request);
		if (httpVersion == VersionNumber.UNDETERMINED)
		{
			response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate"); // HTTP 1.1.
			response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		}
		else if (httpVersion.isGreaterThan(1, 0))
		{
			response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate"); // HTTP 1.1.
		}
		else
		{
			response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		}
		
		response.setDateHeader("Expires", 0); // Proxy
		
		return true;
	}
}
