package com.ethlo.web.filtermapping;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.uadetector.UserAgent;
import net.sf.uadetector.UserAgentType;

import org.springframework.security.web.util.RequestMatcher;

/**
 * @author Morten Haraldsen
 */
public class UserAgentTypeRequestMatcher implements RequestMatcher
{
	private Set<UserAgentType> userAgentTypes;
	
	private Set<UserAgentType> createUaTypeList(String[] uaTypeNames)
	{
		final Set<UserAgentType> retVal = new HashSet<>(uaTypeNames.length);
		for (String name : uaTypeNames)
		{
			retVal.add(getUserAgentType(name));
		}
		return retVal;
	}

	private UserAgentType getUserAgentType(String s)
	{
		return UserAgentType.valueOf(s.toUpperCase());
	}

	@Override
	public boolean matches(HttpServletRequest request)
	{
		final UserAgent userAgent = UserAgentRequestMatcher.getUserAgent(request);
		return this.userAgentTypes.contains(userAgent.getType());
	}

	public void setUserAgentTypes(String... userAgentTypeIncludes)
	{
		this.userAgentTypes = null;
		if (userAgentTypeIncludes != null)
		{
			this.userAgentTypes = createUaTypeList(userAgentTypeIncludes);
		}
	}
}
