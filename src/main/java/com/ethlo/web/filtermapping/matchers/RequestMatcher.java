package com.ethlo.web.filtermapping.matchers;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author Morten Haraldsen
 *
 */
public interface RequestMatcher
{
	boolean matches(HttpServletRequest request);
}
