package com.ethlo.web.filtermapping.matchers;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author Morten Haraldsen
 */
public abstract class BaseRequestMatcher implements RequestMatcher
{
	protected final String getRequestUrl(HttpServletRequest request)
	{
		String url = getRequestPath(request);
        String query = request.getQueryString();
        if (query != null)
        {
            url += query;
        }
        return url;
	}
	
	protected final String getRequestPath(HttpServletRequest request)
	{
		String url = request.getServletPath();
        final String pathInfo = request.getPathInfo();
        if (pathInfo != null)
        {
            final StringBuilder sb = new StringBuilder(url);
            if (pathInfo != null)
            {
                sb.append(pathInfo);
            }
            url = sb.toString();
        }
        return url;
	}

	protected final String getExtension(HttpServletRequest request)
	{
		final String path = this.getRequestPath(request);
		final int index = path.lastIndexOf('.');
		if (index != -1)
		{
			return path.substring(index + 1);
		}
		return null;
	}
}
