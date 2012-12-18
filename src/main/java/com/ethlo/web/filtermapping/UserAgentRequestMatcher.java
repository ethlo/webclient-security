package com.ethlo.web.filtermapping;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.util.RequestMatcher;

import net.sf.uadetector.UserAgent;
import net.sf.uadetector.UserAgentFamily;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

public class UserAgentRequestMatcher implements RequestMatcher, InitializingBean
{
	private final static UserAgentStringParser userAgentParser = UADetectorServiceFactory.getResourceModuleParser();

	private List<String> userAgentIncludes;
	private List<String> userAgentExcludes;

	private List<UserAgentFamily> excludedUserAgentEnums;
	private List<UserAgentFamily> includedUserAgentEnums;
	
	public UserAgent getUserAgent(HttpServletRequest request)
	{
		return userAgentParser.parse(request.getHeader("User-Agent"));
	}
	
	private List<UserAgentFamily> createUaList(List<String> uaNames)
	{
		final List<UserAgentFamily> retVal = new ArrayList<>(uaNames.size());
		for (String s : uaNames)
		{
			retVal.add(getUserAgentEnum(s));
		}
		return retVal;
	}

	private UserAgentFamily getUserAgentEnum(String userAgentStr)
	{
		 return UserAgentFamily.valueOf(userAgentStr.toUpperCase());
	}
	
	@Override
	public void afterPropertiesSet() throws Exception
	{
		if (this.userAgentExcludes != null)
		{
			this.excludedUserAgentEnums = createUaList(this.userAgentExcludes);
		}
		
		if (this.userAgentIncludes != null)
		{
			this.includedUserAgentEnums = createUaList(this.userAgentIncludes);
		}
	}

	@Override
	public boolean matches(HttpServletRequest request)
	{
		final UserAgent userAgent = getUserAgent(request);
		if (excludedUserAgentEnums != null)
		{
			if (this.excludedUserAgentEnums.contains(userAgent.getFamily()))
			{
				return false;
			}
		}
		
		if (includedUserAgentEnums != null)
		{
			if (this.includedUserAgentEnums.contains(userAgent.getFamily()))
			{
				return true;
			}
		}
		
		return excludedUserAgentEnums != null && includedUserAgentEnums == null;	
	}
	
	public static UserAgentStringParser getUserAgentParser()
	{
		return userAgentParser;
	}
}
