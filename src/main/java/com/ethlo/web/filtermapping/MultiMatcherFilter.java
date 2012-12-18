package com.ethlo.web.filtermapping;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.FirewalledRequest;

import com.ethlo.web.util.ImmutableHttpServletResponse;
import com.ethlo.web.webclient.plugins.FilterPlugin;

/**
 * 
 * @author Morten Haraldsen
 */
public class MultiMatcherFilter implements Filter
{
	private List<FilterPlugin> plugins;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
	{
		final FirewalledRequest request = new DefaultHttpFirewall().getFirewalledRequest((HttpServletRequest) req);
		final HttpServletResponse response = (HttpServletResponse) res;
		for (FilterPlugin plugin : plugins)
		{
			if (! plugin.filterBefore(request, response))
			{
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
			}
		}
		
		chain.doFilter(req, res);
		
		final ImmutableHttpServletResponse immutableResonse = new ImmutableHttpServletResponse(response);
		for (FilterPlugin plugin : plugins)
		{
			plugin.filterAfter(request, immutableResonse);
		}
	}

	public void setPlugins(List<FilterPlugin> plugins)
	{
		this.plugins = plugins;
	}

	public void destroy(){}
	public void init(FilterConfig arg0) throws ServletException{}
}
