package com.ethlo.web.filtermapping.matchers;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ethlo.web.filtermapping.HttpMethod;

/**
 * Uses a regular expression to decide whether a supplied the URL of a supplied {@code HttpServletRequest}.
 *
 * Can also be configured to match a specific HTTP method.
 *
 * The match is performed against the {@code servletPath + pathInfo + queryString} of the request and is case-sensitive
 * by default. Case-insensitive matching can be used by using the constructor which takes the {@code caseInsensitive}
 * argument.
 *
 * @author Luke Taylor
 * @since 3.1
 */
public final class RegexRequestMatcher implements RequestMatcher
{
    private final static Log logger = LogFactory.getLog(RegexRequestMatcher.class);

    private final Pattern pattern;
    private final HttpMethod httpMethod;

    public RegexRequestMatcher(String pattern) {
        this(pattern, null, false);
    }
    
    /**
     * Creates a case-sensitive {@code Pattern} instance to match against the request.
     *
     * @param pattern the regular expression to compile into a pattern.
     * @param httpMethod the HTTP method to match. May be null to match all methods.
     */
    public RegexRequestMatcher(String pattern, String httpMethod) {
        this(pattern, httpMethod, false);
    }

    /**
     * As above, but allows setting of whether case-insensitive matching should be used.
     *
     * @param pattern the regular expression to compile into a pattern.
     * @param httpMethod the HTTP method to match. May be null to match all methods.
     * @param caseInsensitive if true, the pattern will be compiled with the {@link Pattern#CASE_INSENSITIVE} flag set.
     */
    public RegexRequestMatcher(String pattern, String httpMethod, boolean caseInsensitive) {
        if (caseInsensitive) {
            this.pattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        } else {
            this.pattern = Pattern.compile(pattern);
        }
        this.httpMethod = httpMethod != null ? HttpMethod.valueOf(httpMethod) : null;
    }

    /**
     * Performs the match of the request URL ({@code servletPath + pathInfo + queryString}) against
     * the compiled pattern. If the query string is present, a question mark will be prepended.
     *
     * @param request the request to match
     * @return true if the pattern matches the URL, false otherwise.
     */
    public boolean matches(HttpServletRequest request) {
        if (httpMethod != null && httpMethod != HttpMethod.valueOf(request.getMethod())) {
            return false;
        }

        String url = request.getServletPath();
        String pathInfo = request.getPathInfo();
        String query = request.getQueryString();

        if (pathInfo != null || query != null) {
            StringBuilder sb = new StringBuilder(url);

            if (pathInfo != null) {
                sb.append(pathInfo);
            }

            if (query != null) {
                sb.append('?').append(query);
            }
            url = sb.toString();
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Checking match of request : '" + url + "'; against '" + pattern + "'");
        }

        return pattern.matcher(url).matches();
    }
}