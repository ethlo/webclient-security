package com.ethlo.web.webclient.plugins;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.uadetector.UserAgent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ethlo.web.filtermapping.HttpMethod;
import com.ethlo.web.filtermapping.VersionNumber;
import com.ethlo.web.filtermapping.matchers.RequestMatcher;
import com.ethlo.web.filtermapping.matchers.UserAgentRequestMatcher;

/**
 * 
 * @author Morten Haraldsen
 */
public abstract class AroundFilterPlugin implements FilterPlugin
{
	protected Logger logger = LoggerFactory.getLogger(AroundFilterPlugin.class);
	private RequestMatcher matcher;
	private Set<HttpMethod> methods;
	
	@Override
	public final boolean filterBefore(HttpServletRequest request, HttpServletResponse response)
	{
		if (matches(request))
		{
			return this.doFilterBefore(request, response);
		}
		else
		{
			logger.debug("No match for filter " + this.getClass().getSimpleName() + " for request " + request.getRequestURI());
		}
		return true;
	}
	
	private boolean matches(HttpServletRequest request)
	{
		return matchesMethod(request) && (matcher == null || matcher.matches(request));
	}

	private boolean matchesMethod(HttpServletRequest request)
	{
		return methods == null ? true : this.methods.contains(HttpMethod.valueOf(request.getMethod())); 
	}

	@Override
	public final void filterAfter(HttpServletRequest request, HttpServletResponse response)
	{
		if (matches(request))
		{
			this.doFilterAfter(request, response);
		}
		else
		{
			logger.debug("No match for filter " + this.getClass().getSimpleName() + " for request " + request.getRequestURI());
		}
	}
	
	public void setMatcher(RequestMatcher matcher)
	{
		this.matcher = matcher;
	}

	protected abstract boolean doFilterBefore(HttpServletRequest request, HttpServletResponse response);
	
	protected abstract void doFilterAfter(HttpServletRequest request, HttpServletResponse response);

	public UserAgent getUserAgent(HttpServletRequest request)
	{
		return UserAgentRequestMatcher.getUserAgent(request);
	}

	protected VersionNumber getHttpVersion(HttpServletRequest request)
	{
		final String protocol = request.getProtocol();
		if (protocol != null)
		{
			final String[] protocolAndVersion = protocol.split("/");
			final String[] versionNumbers = protocolAndVersion[1].split("\\.");
			final int major = Integer.parseInt(versionNumbers[0]);
			final int minor = Integer.parseInt(versionNumbers[1]);
			return new VersionNumber(major, minor);
		}
		return VersionNumber.UNDETERMINED;
	}

	public void setHttpMethods(HttpMethod... methods)
	{
		this.methods = new HashSet<>(Arrays.asList(methods));
	}
}
