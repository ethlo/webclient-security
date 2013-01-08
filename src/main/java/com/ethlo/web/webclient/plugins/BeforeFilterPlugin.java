package com.ethlo.web.webclient.plugins;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author Morten Haraldsen
 */
public abstract class BeforeFilterPlugin extends AroundFilterPlugin
{
	protected abstract boolean doFilterBefore(HttpServletRequest request, HttpServletResponse response) throws IOException;
	
	protected final void doFilterAfter(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		
	}
}
