package com.ethlo.web.filtermapping;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.util.RequestMatcher;

/**
 * 
 * @author Morten Haraldsen
 */
public class RequestMultiMatcher implements RequestMatcher
{
	private static final Logger logger = LoggerFactory.getLogger(RequestMultiMatcher.class);
	
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
						logger.info("{} excluded due to matcher {}", request.getRequestURI(), matcher);
						return false;
					}
				}
			}
			else if (this.excludesPolicy == MatcherPolicy.ALL)
			{
				int matches = 0;
				for (RequestMatcher matcher : excludes)
				{
					if (matcher.matches(request))
					{
						matches++;
					}
				}
				return matches != excludes.size();
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
						logger.info("{} not included due to matcher {}", request.getRequestURI(), matcher);
						return false;
					}
				}
				return true;
			}
			else if (this.includesPolicy == MatcherPolicy.ANY)
			{
				for (RequestMatcher matcher : includes)
				{
					if (matcher.matches(request))
					{
						return true;
					}
				}
				return false;
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
