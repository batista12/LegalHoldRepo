package com.legal.hold.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

	@Value("${spring.datasource.driver-class-name}")
	String driverClassname;

	@Value("${spring.datasource.url}")
	String url;

	@Value("${spring.datasource.username}")
	String userName;

	@Value("${spring.datasource.password}")
	String passWord;

}
