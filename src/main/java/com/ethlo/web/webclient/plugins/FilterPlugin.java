package com.ethlo.web.webclient.plugins;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author Morten Haraldsen
 *
 */
public interface FilterPlugin
{
	/**
	 * Before
	 * @param request
	 * @param response
	 * @return true if the chain should continue, false if request is denied
	 */
	public boolean filterBefore(HttpServletRequest request, HttpServletResponse response);

	/**
	 * After
	 * @param request
	 * @param response
	 * @return true if the chain should continue, false if request is denied
	 */
	public void filterAfter(HttpServletRequest request, HttpServletResponse response);
}
