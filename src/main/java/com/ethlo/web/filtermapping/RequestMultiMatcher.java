package com.ethlo.web.filtermapping;

import java.util.Arrays;
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
	private MatcherPolicy includesPolicy = MatcherPolicy.ALL;
	private MatcherPolicy excludesPolicy = MatcherPolicy.ANY;
	
	public static enum MatcherPolicy
	{
		/**
		 * The request needs to match ANY of the matchers in the corresponding list
		 */
		ANY,
		
		/**
		 * The request needs to match ALL of the matchers in the corresponding list
		 */
		ALL;
	}
	
	@Override
	public final boolean matches(HttpServletRequest request)
	{
		if (includes == null && excludes == null)
		{
			return true;
		}
		
		if (excludes != null)
		{
			if (this.excludesPolicy == MatcherPolicy.ANY)
			{
				for (RequestMatcher matcher : excludes)
				{
					if (matcher.matches(request))
					{
						System.out.println(request.getRequestURI() + " excluded due to matcher " + matcher);
						return false;
					}
				}
			}
			else if (this.excludesPolicy == MatcherPolicy.ALL)
			{
				int matches = 0;
				for (RequestMatcher matcher : excludes)
				{
					if (! matcher.matches(request))
					{
						matches++;
					}
				}
				return matches == excludes.size() - 1;
			}
		}
		
		if (includes != null)
		{
			if (this.includesPolicy == MatcherPolicy.ALL)
			{
				for (RequestMatcher matcher : includes)
				{
					if (! matcher.matches(request))
					{
						System.out.println(request.getRequestURI() + " not included due to matcher " + matcher);
						return false;
					}
				}
				return true;
			}
			else if (this.includesPolicy == MatcherPolicy.ANY)
			{
				int matches = 0;
				for (RequestMatcher matcher : includes)
				{
					if (! matcher.matches(request))
					{
						matches++;
					}
				}
				return matches == includes.size() - 1;
			}
		}
		
		return excludes != null && includes == null;
	}

	public void setExcludes(RequestMatcher... excludes)
	{
		this.excludes = Arrays.asList(excludes);
	}
	
	public void setIncludes(RequestMatcher... includes)
	{
		this.includes = Arrays.asList(includes);
	}

	public void setIncludesPolicy(MatcherPolicy includesPolicy)
	{
		this.includesPolicy = includesPolicy;
	}

	public void setExcludesPolicy(MatcherPolicy excludesPolicy)
	{
		this.excludesPolicy = excludesPolicy;
	}
}
