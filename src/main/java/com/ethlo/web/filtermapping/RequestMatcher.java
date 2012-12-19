package com.ethlo.web.filtermapping;

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
