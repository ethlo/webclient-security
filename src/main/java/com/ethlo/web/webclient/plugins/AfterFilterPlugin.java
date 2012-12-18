package com.ethlo.web.webclient.plugins;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 
 * @author Morten Haraldsen
 */
public abstract class AfterFilterPlugin extends AroundFilterPlugin
{
	protected abstract void doFilterAfter(HttpServletRequest request, HttpServletResponse response);
	
	protected final boolean doFilterBefore(HttpServletRequest request, HttpServletResponse response)
	{
		return true;
	}
}
