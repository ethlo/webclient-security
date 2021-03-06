package com.ethlo.web.filtermapping;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ethlo.web.util.ImmutableHttpServletResponse;
import com.ethlo.web.webclient.plugins.FilterPlugin;

/**
 * 
 * @author Morten Haraldsen
 */
public class MultiMatcherFilter implements Filter
{
	private final static Logger logger = LoggerFactory.getLogger(MultiMatcherFilter.class);
	
	private List<FilterPlugin> plugins;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
	{
		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;
		for (FilterPlugin plugin : plugins)
		{
			final boolean evalResult = plugin.filterBefore(request, response);
			if (! evalResult)
			{
				logger.debug("Skipping further processing due to false response from plugin " + plugin);
				return;
			}
		}
		
		chain.doFilter(req, res);
		
		final ImmutableHttpServletResponse immutableResonse = new ImmutableHttpServletResponse(response);
		for (FilterPlugin plugin : plugins)
		{
			plugin.filterAfter(request, immutableResonse);
		}
	}

	public void setPlugins(FilterPlugin... plugins)
	{
		this.plugins = Arrays.asList(plugins);
	}

	public void destroy(){}
	public void init(FilterConfig filterConfig) throws ServletException{}
}
