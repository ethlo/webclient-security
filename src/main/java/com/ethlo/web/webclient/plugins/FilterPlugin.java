package com.ethlo.web.webclient.plugins;

import java.io.IOException;

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
	 * @throws IOException 
	 */
	public boolean filterBefore(HttpServletRequest request, HttpServletResponse response) throws IOException;

	/**
	 * After
	 * @param request
	 * @param response
	 * @return true if the chain should continue, false if request is denied
	 * @throws IOException 
	 */
	public void filterAfter(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
