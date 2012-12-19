package com.ethlo.web.filtermapping;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.web.util.AnyRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;

import com.ethlo.web.filtermapping.RequestMultiMatcher.MatcherPolicy;

/**
 * 
 * @author Morten Haraldsen
 */
public class LogicalMappingTest
{
	private final RequestMatcher any = new AnyRequestMatcher();
	private final RequestMatcher never = new NeverRequestMatcher(); 
	
	@Test
	public void testMatcherPolicyAllInclude()
	{
		final RequestMultiMatcher matcher = new RequestMultiMatcher();
		matcher.setIncludesPolicy(MatcherPolicy.ALL);
		final MockHttpServletRequest request = new MockHttpServletRequest();
		
		matcher.setIncludes(any, any);
		Assert.assertTrue(matcher.matches(request));
		
		matcher.setIncludes(any, any, never);
		Assert.assertFalse(matcher.matches(request));
	}
	
	@Test
	public void testMatcherPolicyAllExclude()
	{
		final RequestMultiMatcher matcher = new RequestMultiMatcher();
		matcher.setExcludesPolicy(MatcherPolicy.ALL);
		final MockHttpServletRequest request = new MockHttpServletRequest();
		
		matcher.setExcludes(never, never);
		Assert.assertTrue(matcher.matches(request));
		
		matcher.setExcludes(never, any);
		Assert.assertTrue(matcher.matches(request));
	}
	
	@Test
	public void testMatcherPolicyAnyInclude()
	{
		final RequestMultiMatcher matcher = new RequestMultiMatcher();
		matcher.setIncludesPolicy(MatcherPolicy.ANY);
		final MockHttpServletRequest request = new MockHttpServletRequest();
		
		matcher.setIncludes(any, never);
		Assert.assertTrue(matcher.matches(request));
		
		matcher.setIncludes(any, any, never, never);
		Assert.assertTrue(matcher.matches(request));
		
		matcher.setIncludes(never);
		Assert.assertFalse(matcher.matches(request));
	}
	
	@Test
	public void testMatcherPolicyAnyExclude()
	{
		final RequestMultiMatcher matcher = new RequestMultiMatcher();
		matcher.setExcludesPolicy(MatcherPolicy.ANY);
		final MockHttpServletRequest request = new MockHttpServletRequest();
		
		matcher.setExcludes(never);
		Assert.assertTrue(matcher.matches(request));
		
		matcher.setExcludes(never, any);
		Assert.assertFalse(matcher.matches(request));
		
		matcher.setExcludes(any);
		Assert.assertFalse(matcher.matches(request));
	}
}
