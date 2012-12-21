package com.ethlo.web.webclient.plugins;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This plugin wraps another filter, allowing you to take full control of the mapping for it
 * 
 * @author Morten Haraldsen
 */
public class FilterPluginFilter extends BeforeFilterPlugin
{
	private Filter filter;
	
	@Override
	public boolean doFilterBefore(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			filter.doFilter(request, response, new FilterChain()
			{
				@Override
				public void doFilter(ServletRequest request, ServletResponse response) 	throws IOException, ServletException{}
			});
		}
		catch (IOException | ServletException e)
		{
			throw new RuntimeException(e);
		}
		return true;
	}

	public Filter getFilter()
	{
		return filter;
	}

	public void setFilter(Filter filter)
	{
		this.filter = filter;
	}
}
