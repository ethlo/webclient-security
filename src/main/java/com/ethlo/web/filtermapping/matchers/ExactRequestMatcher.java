package com.ethlo.web.filtermapping.matchers;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


/**
 * Fast matching with no wild-card support
 * 
 * @author Morten Haraldsen
 */
public class ExactRequestMatcher extends BaseRequestMatcher
{
	private final List<String> targets;
	
	public ExactRequestMatcher(String... targets)
	{
		this.targets = Arrays.asList(targets);
	}
	
	@Override
	public boolean matches(HttpServletRequest request)
	{
		final String requestPath = super.getRequestPath(request);
		for (String target : targets)
		{
			if (requestPath.equals(target))
			{
				return true;
			}
		}
		return false;
	}
}
