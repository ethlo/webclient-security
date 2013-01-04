package com.ethlo.web.filtermapping.matchers;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


/**
 * Simple matching on path extension
 * 
 * @author Morten Haraldsen
 */
public class ExtensionRequestMatcher extends BaseRequestMatcher
{
	private final List<String> extensions;
	
	public ExtensionRequestMatcher(String... extensions)
	{
		this.extensions = Arrays.asList(extensions);
	}
	
	@Override
	public boolean matches(HttpServletRequest request)
	{
		final String extension = super.getExtension(request);
		for (String candidate : extensions)
		{
			if (candidate.equals(extension))
			{
				return true;
			}
		}
		return false;
	}
}
