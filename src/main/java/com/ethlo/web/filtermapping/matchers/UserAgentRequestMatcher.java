package com.ethlo.web.filtermapping.matchers;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.uadetector.UserAgent;
import net.sf.uadetector.UserAgentFamily;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

/**
 * @author Morten Haraldsen
 */
public class UserAgentRequestMatcher implements RequestMatcher
{
	private final static UserAgentStringParser userAgentParser = UADetectorServiceFactory.getResourceModuleParser();
	private Set<UserAgentFamily> includedUserAgentEnums;
	
	public static UserAgent getUserAgent(HttpServletRequest request)
	{
		final String userAgentStr = request.getHeader("User-Agent");
		return userAgentParser.parse(userAgentStr != null ? userAgentStr : "");
	}
	
	private Set<UserAgentFamily> createUaList(String[] uaNames)
	{
		final Set<UserAgentFamily> retVal = new HashSet<>(uaNames.length);
		for (String name : uaNames)
		{
			retVal.add(getUserAgentEnum(name));
		}
		return retVal;
	}

	private UserAgentFamily getUserAgentEnum(String userAgentStr)
	{
		 return UserAgentFamily.valueOf(userAgentStr.toUpperCase());
	}

	@Override
	public boolean matches(HttpServletRequest request)
	{
		final UserAgent userAgent = getUserAgent(request);				
		return this.includedUserAgentEnums.contains(userAgent.getFamily());
	}

	public void setUserAgentIncludes(String... userAgentIncludes)
	{
		this.includedUserAgentEnums = null;
		if (userAgentIncludes != null)
		{
			this.includedUserAgentEnums = createUaList(userAgentIncludes);
		}
	}
}
