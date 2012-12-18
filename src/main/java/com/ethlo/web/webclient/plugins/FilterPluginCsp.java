package com.ethlo.web.webclient.plugins;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.uadetector.UserAgent;
import net.sf.uadetector.UserAgentFamily;

/**
 * Content Security Policy (CSP) is a computer security concept, to prevent Cross-Site Scripting and related attacks.
 * 
 * The official name of the HTTP header field is Content-Security-Policy. Mozilla Firefox and the preview 
 * release of Internet Explorer 10 use the header name X-Content-Security-Policy. Google Chrome and 
 * Safari (web browser) use X-WebKit-CSP. Chrome supports the standard header name as of version 25.
 * 
 * http://en.wikipedia.org/wiki/Content_Security_Policy
 * 
 * @author Morten Haraldsen
 */
public class FilterPluginCsp extends BeforeFilterPlugin
{
	public final static String CSP_LEGACY_WEBKIT_HEADER = "X-WebKit-CSP";
	public final static String CSP_LEGACY_HEADER = "X-Content-Security-Policy";
	public final static String CSP_HEADER = "Content-Security-Policy";
	public final static String CSP_REPORT_ONLY_HEADER = "Content-Security-Policy-Report-Only";
	public final static String CSP_REPORT_ONLY_LEGACY_HEADER = "X-Content-Security-Policy-Report-Only";
	
	private boolean addLegacyHeader = false;
	private boolean reportOnly = false;
	private String policy;
	
	@Override
	public boolean doFilterBefore(HttpServletRequest request, HttpServletResponse response)
	{
		final UserAgent userAgent = super.getUserAgent(request);

		response.addHeader(reportOnly ? CSP_REPORT_ONLY_HEADER : CSP_HEADER, policy);
		
		/*
		if(userAgent.getFamily() == UserAgentFamily.IE)
		{
			response.addHeader("X-Xss-Protection", "1; mode=block");	
		}
		*/
		if (isWebKit(userAgent) && !reportOnly && addLegacyHeader)
		{
			response.addHeader(CSP_LEGACY_WEBKIT_HEADER, policy);
		}
		else if (addLegacyHeader)
		{
			response.addHeader(reportOnly ? CSP_REPORT_ONLY_LEGACY_HEADER : CSP_LEGACY_HEADER, policy);
		}
		return true;
	}

	private boolean isWebKit(UserAgent userAgent)
	{
		return userAgent.getFamily() == UserAgentFamily.SAFARI
			|| userAgent.getFamily() == UserAgentFamily.CHROME;
	}

	public void setPolicy(String policy)
	{
		this.policy = policy;
	}

	public void setReportOnly(boolean reportOnly)
	{
		this.reportOnly = reportOnly;
	}
	
	public void setAddLegacyHeader(boolean b)
	{
		this.addLegacyHeader = b;
	}

	public String getPolicy()
	{
		return this.policy;
	}
}
