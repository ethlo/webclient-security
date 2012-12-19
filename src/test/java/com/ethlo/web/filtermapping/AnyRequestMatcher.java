package com.ethlo.web.filtermapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Always matches any request
 * 
 * @author Morten Haraldsen
 */
public class AnyRequestMatcher implements RequestMatcher
{
	@Override
	public boolean matches(HttpServletRequest request)
	{
		return true;
	}
}
