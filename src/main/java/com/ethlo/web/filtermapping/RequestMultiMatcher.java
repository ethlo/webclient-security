package com.ethlo.web.filtermapping;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.RequestMatcher;

/**
 * 
 * @author Morten Haraldsen
 */
public class RequestMultiMatcher implements RequestMatcher
{
	private List<RequestMatcher> includes;
	private List<RequestMatcher> excludes;
	
	@Override
	public final boolean matches(HttpServletRequest request)
	{
		if (includes == null && excludes == null)
		{
			return true;
		}
		
		if (excludes != null)
		{
			for (RequestMatcher matcher : excludes)
			{
				if (matcher.matches(request))
				{
					return false;
				}
			}
		}
		
		if (includes != null)
		{
			for (RequestMatcher matcher : includes)
			{
				if (matcher.matches(request))
				{
					return true;
				}
			}
		}
		
		return excludes != null && includes == null;
	}

	public void setExcludes(List<RequestMatcher> excludes)
	{
		this.excludes = excludes;
	}
	
	public void setIncludes(List<RequestMatcher> includes)
	{
		this.includes = includes;
	}
}
