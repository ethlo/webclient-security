package com.ethlo.web.webclient.plugins;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Send correct cache headers to avoid unnecessary traffic
 * 
 * @author Morten Haraldsen
 */
public class FilterPluginCache extends BeforeFilterPlugin
{
	private long ttlMilliseconds = 3600 * 24 * 7 * 365;
	
	@Override
	protected boolean doFilterBefore(HttpServletRequest request, HttpServletResponse response)
	{
		final SimpleDateFormat httpDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        httpDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        
		final long now = System.currentTimeMillis();
        response.setHeader("Last-Modified", httpDateFormat.format(new Date(now)));
        response.setHeader("Expires", httpDateFormat.format(new Date(System.currentTimeMillis() + ttlMilliseconds)));
        response.setHeader("Cache-Control", "max-age=" + (ttlMilliseconds / 1000));
        response.setHeader("ETag", generateEtag(ttlMilliseconds));
		return true;
	}
	
    private String generateEtag(long ttlMilliseconds)
    {
        StringBuffer stringBuffer = new StringBuffer();
        Long eTagRaw = System.currentTimeMillis() + ttlMilliseconds;
        String eTag = stringBuffer.append("\"").append(eTagRaw).append("\"").toString();
        return eTag;
    }
    
    public void setTtlMilliseconds(long ttl)
    {
    	this.ttlMilliseconds = ttl;
    }
}
