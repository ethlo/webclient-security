#  :warning: **This project is archived**

# Why?

The reason for this project is the lack of fine tuned filtering in the standard filter-mapping rules, like reg-ex or Ant path.
With this project it is easy to combine multiple rules for filters using AND/OR logic and a multitude of request parsers.

Request parsers include:
* URL (regex, ant)
* User-agent (name, version)
* User-Agent type (browser, mobile-browser, etc)
* ... and it is very easy to add your own!

The security plugins include:
* Anti-CSRF (token session wide)
* CSP (Content-Security-Policy)
* Anti click hi-jacking
* ...and again it is very easy to add your own!

# Build status

[![Build Status](https://travis-ci.org/ethlo/webclient-security.png?branch=master)](https://travis-ci.org/ethlo/webclient-security)

# Maven repository
http://ethlo.com/maven

# Maven artifact
```xml
<dependency
	<groupId>com.ethlo.web</groupId>
	<artifactId>webclient-security</artifactId>
	<version>1.0-SNAPSHOT</version>
</dependency>
```

# Examples
Spring example config:
```xml
<bean id="webClientSecurityFilter" class="com.ethlo.web.filtermapping.MultiMatcherFilter">
  	<property name="plugins">
			<list>
				<bean class="com.ethlo.web.webclient.plugins.FilterPluginNoCache">
					<property name="matcher">
						<bean class="com.ethlo.web.filtermapping.matchers.AntPathRequestMatcher">
							<constructor-arg value="/latest_status.js" />
						</bean>
					</property>
				</bean>
				<bean class="com.ethlo.web.webclient.plugins.FilterPluginCsrf">
					<property name="conditional" value="false" />
					<property name="matcher">
						<bean class="com.ethlo.web.filtermapping.matchers.RequestMultiMatcher">
							<property name="includes">
								<bean class="com.ethlo.web.filtermapping.matchers.AntPathRequestMatcher">
									<constructor-arg value="/api/**" />
								</bean>		
							</property>
							<property name="excludes">
								<bean class="com.ethlo.web.filtermapping.matchers.AntPathRequestMatcher">
									<constructor-arg value="/api/unsecured/**" />
								</bean>
							</property>
						</bean>
					</property>
				</bean>
				<bean class="com.ethlo.web.webclient.plugins.FilterPluginCsp">
					<property name="policy" value="default-src 'self'; script-src 'self' *.gstatic.com *.googleapis.com http://maps.google.com; style-src 'self' 'unsafe-inline'" />
					<property name="reportOnly" value="true" />
					<property name="addLegacyHeader" value="false" />
					<property name="matcher">
						<bean class="com.ethlo.web.filtermapping.matchers.RequestMultiMatcher">
							<property name="includesPolicy" value="ALL" />
							<property name="includes">
								<list>
									<bean class="com.ethlo.web.filtermapping.matchers.AntPathRequestMatcher">
										<constructor-arg value="/html/**" />
									</bean>
									<bean class="com.ethlo.web.filtermapping.matchers.UserAgentTypeRequestMatcher">
										<property name="userAgentTypes">
											<list>
												<value>BROWSER</value>
												<value>MOBILE_BROWSER</value>
											</list>
										</property>
									</bean>		
								</list>
							</property>
						</bean>
					</property>
				</bean>
			</list>
		</property>
	</bean>
  ```
