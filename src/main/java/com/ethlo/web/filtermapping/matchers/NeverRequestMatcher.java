package com.ethlo.web.filtermapping.matchers;

import javax.servlet.http.HttpServletRequest;


/**
 * Never matches any request
 * 
 * @author Morten Haraldsen
 */
public class NeverRequestMatcher implements RequestMatcher
{
	@Override
	public boolean matches(HttpServletRequest request)
	{
		return false;
	}
}
