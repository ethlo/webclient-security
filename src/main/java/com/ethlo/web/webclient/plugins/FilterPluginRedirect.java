package com.ethlo.web.webclient.plugins;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Send a header with the timing of the request
 * 
 * @author Morten Haraldsen
 */
public class FilterPluginRedirect extends BeforeFilterPlugin
{
	private boolean forward = true;
	private String path;
	
	@Override
	protected boolean doFilterBefore(HttpServletRequest request, HttpServletResponse response)
	{
		if (forward)
		{
			final RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(path);
			try
			{
				dispatcher.forward(request, response);
			}
			catch (ServletException | IOException e)
			{
				throw new RuntimeException(e);
			}
		}
		else
		{
			try
			{
				response.sendRedirect(path);
			}
			catch (IOException e)
			{
				throw new RuntimeException(e);
			}
		}
		
		return false;
	}

	public void setForward(boolean forward)
	{
		this.forward = forward;
	}

	public void setPath(String path)
	{
		this.path = path;
	}
}
