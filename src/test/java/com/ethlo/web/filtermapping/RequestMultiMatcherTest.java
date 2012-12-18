package com.ethlo.web.filtermapping;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.ethlo.web.webclient.plugins.FilterPluginCsp;
/**
 * 
 * @author Morten Haraldsen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:/my-servlet-context.xml")
public class RequestMultiMatcherTest
{
	@Autowired
	private WebApplicationContext wac;

	private SimpleController controller = new SimpleController();
	
	private MockMvc mockMvc;
    
    @Test
    public void testFilterSimple() throws Exception
    {
    	final MultiMatcherFilter filter = new MultiMatcherFilter();
    	this.mockMvc = MockMvcBuilders
    			.standaloneSetup(controller)
    			.addFilters(filter)
    			.build();
    	
    	final FilterPluginCsp plugin = new FilterPluginCsp();
    	plugin.setPolicy("my csp policy");
    	filter.setPlugins(plugin);
    	
        this.mockMvc.perform(get("/secured"))
        	.andExpect(status().isOk())
        	.andExpect(header().string("Content-Security-Policy", plugin.getPolicy()));
    }
    
    @Test
    public void testIncludedByBrowserType() throws Exception
    {
    	final MultiMatcherFilter filter = new MultiMatcherFilter();
    	this.mockMvc = MockMvcBuilders
    			.standaloneSetup(controller)
    			.addFilters(filter)
    			.build();
    	
    	final FilterPluginCsp plugin = new FilterPluginCsp();
    	final UserAgentTypeRequestMatcher matcher = new UserAgentTypeRequestMatcher();
    	matcher.setUserAgentTypes("browser", "mobile_browser");
    	plugin.setMatcher(matcher);
    	plugin.setPolicy("my csp policy");
    	filter.setPlugins(plugin);
    	
    	// Chromium User-Agent header
        this.mockMvc.perform(get("/secured")
        		.header("User-Agent", "User-Agent:Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.4 (KHTML, like Gecko) Ubuntu/12.10 Chromium/22.0.1229.94 Chrome/22.0.1229.94 Safari/537.4"))
        	.andExpect(status().isOk())
        	.andExpect(header().string("Content-Security-Policy", plugin.getPolicy()));
        
        // No User-Agent header
        this.mockMvc.perform(get("/secured"))
    		.andExpect(status().isOk())
    		.andExpect(header().string("Content-Security-Policy", (String)null));
    }
    
    @Test
    public void testExcudedByBrowserType() throws Exception
    {
    	final MultiMatcherFilter filter = new MultiMatcherFilter();
    	this.mockMvc = MockMvcBuilders
    			.standaloneSetup(controller)
    			.addFilters(filter)
    			.build();
    	
    	final FilterPluginCsp plugin = new FilterPluginCsp();
    	
    	final UserAgentTypeRequestMatcher uaTypeMatcher = new UserAgentTypeRequestMatcher();
    	uaTypeMatcher.setUserAgentTypes("unknown");
    	
    	final RequestMultiMatcher matcher = new RequestMultiMatcher();
    	matcher.setExcludes(uaTypeMatcher);
    	
    	plugin.setMatcher(matcher);
    	plugin.setPolicy("my csp policy");
    	filter.setPlugins(plugin);
    	
    	// Chromium User-Agent header
        this.mockMvc.perform(get("/secured")
        		.header("User-Agent", "User-Agent:Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.4 (KHTML, like Gecko) Ubuntu/12.10 Chromium/22.0.1229.94 Chrome/22.0.1229.94 Safari/537.4"))
        	.andExpect(status().isOk())
        	.andExpect(header().string("Content-Security-Policy", plugin.getPolicy()));
        
        // No User-Agent header
        this.mockMvc.perform(get("/secured"))
    		.andExpect(status().isOk())
    		.andExpect(header().string("Content-Security-Policy", (String)null));
    }
}
