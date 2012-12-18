package com.ethlo.web.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class ImmutableHttpServletResponse implements HttpServletResponse
{
	private HttpServletResponse wrapped;
	
	public ImmutableHttpServletResponse(HttpServletResponse response)
	{
		this.wrapped = response;
	}

	public void addCookie(Cookie cookie)
	{
		throw new UnsupportedOperationException();
	}

	public boolean containsHeader(String name)
	{
		return wrapped.containsHeader(name);
	}

	public String encodeURL(String url)
	{
		return wrapped.encodeURL(url);
	}

	public String getCharacterEncoding()
	{
		return wrapped.getCharacterEncoding();
	}

	public String encodeRedirectURL(String url)
	{
		return wrapped.encodeRedirectURL(url);
	}

	public String getContentType()
	{
		return wrapped.getContentType();
	}

	@SuppressWarnings("deprecation")
	public String encodeUrl(String url)
	{
		return wrapped.encodeUrl(url);
	}

	@SuppressWarnings("deprecation")
	public String encodeRedirectUrl(String url)
	{
		return wrapped.encodeRedirectUrl(url);
	}

	public ServletOutputStream getOutputStream() throws IOException
	{
		throw new UnsupportedOperationException();
	}

	public void sendError(int sc, String msg) throws IOException
	{
		throw new UnsupportedOperationException();
	}

	public PrintWriter getWriter() throws IOException
	{
		throw new UnsupportedOperationException();
	}

	public void sendError(int sc) throws IOException
	{
		throw new UnsupportedOperationException();
	}

	public void setCharacterEncoding(String charset)
	{
		throw new UnsupportedOperationException();
	}

	public void sendRedirect(String location) throws IOException
	{
		throw new UnsupportedOperationException();
	}

	public void setDateHeader(String name, long date)
	{
		throw new UnsupportedOperationException();
	}

	public void setContentLength(int len)
	{
		throw new UnsupportedOperationException();
	}

	public void addDateHeader(String name, long date)
	{
		throw new UnsupportedOperationException();
	}

	public void setContentType(String type)
	{
		throw new UnsupportedOperationException();
	}

	public void setHeader(String name, String value)
	{
		throw new UnsupportedOperationException();
	}

	public void addHeader(String name, String value)
	{
		throw new UnsupportedOperationException();
	}

	public void setBufferSize(int size)
	{
		throw new UnsupportedOperationException();
	}

	public void setIntHeader(String name, int value)
	{
		throw new UnsupportedOperationException();
	}

	public void addIntHeader(String name, int value)
	{
		throw new UnsupportedOperationException();
	}

	public void setStatus(int sc)
	{
		throw new UnsupportedOperationException();
	}

	public int getBufferSize()
	{
		return wrapped.getBufferSize();
	}

	public void flushBuffer() throws IOException
	{
		wrapped.flushBuffer();
	}

	public void setStatus(int sc, String sm)
	{
		throw new UnsupportedOperationException();
	}

	public void resetBuffer()
	{
		throw new UnsupportedOperationException();
	}

	public int getStatus()
	{
		return wrapped.getStatus();
	}

	public boolean isCommitted()
	{
		throw new UnsupportedOperationException();
	}

	public String getHeader(String name)
	{
		return wrapped.getHeader(name);
	}

	public void reset()
	{
		throw new UnsupportedOperationException();
	}

	public Collection<String> getHeaders(String name)
	{
		return wrapped.getHeaders(name);
	}

	public void setLocale(Locale loc)
	{
		throw new UnsupportedOperationException();
	}

	public Collection<String> getHeaderNames()
	{
		return wrapped.getHeaderNames();
	}

	public Locale getLocale()
	{
		return wrapped.getLocale();
	}
}
