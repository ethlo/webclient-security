package com.ethlo.web.webclient.plugins;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ethlo.web.filtermapping.HttpMethod;

/**
 * Check for valid method in HTTP request
 * 
 * @author Morten Haraldsen
 */
public class FilterPluginHttpMethodChecker extends BeforeFilterPlugin
{
	private Set<HttpMethod> allowedMethods = new TreeSet<>();
	
	public FilterPluginHttpMethodChecker(HttpMethod... allowedMethods)
	{
		this.allowedMethods = new TreeSet<>(Arrays.asList(allowedMethods));
	}
	
	@Override
	protected boolean doFilterBefore(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		final HttpMethod method = getMethod(request);
		if (method == null || !allowedMethods.contains(method))
		{
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return false;
		}
		return true;
	}

	private HttpMethod getMethod(HttpServletRequest request)
	{
		try
		{
			return HttpMethod.valueOf(request.getMethod());
		}
		catch (IllegalArgumentException exc)
		{
			return null;
		}
	}
}
