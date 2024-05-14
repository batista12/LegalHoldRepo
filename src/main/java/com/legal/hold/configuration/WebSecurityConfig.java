package com.legal.hold.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http.headers()
            .httpStrictTransportSecurity()
            .requestMatcher(AnyRequestMatcher.INSTANCE)
            .includeSubDomains(true).maxAgeInSeconds(31536000).and()
            .cacheControl().disable()
            .frameOptions().disable();       
        }
}