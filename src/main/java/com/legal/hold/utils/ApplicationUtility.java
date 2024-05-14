package com.legal.hold.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import com.legal.hold.service.Service;

public class ApplicationUtility {
	
	@Autowired
	Service service;

	public static boolean isValidSSO(String sso) {
		
		System.out.println("SSO Verfication Started");
		Pattern p = Pattern.compile("^\\d{9}$");
		Matcher m = p.matcher(sso);
		return (sso!= null && m.matches()) ? true : false;
	}
	
	public static String filterAndProcessSSO(String sso) {
		
		System.out.println("Started SSO Filtration");
		
		if(sso == null || sso.isEmpty()) {
			return null;
		}
		
		// Remove HTML escape
//		String unescapeSSO = HtmlUtils.htmlEscape(sso);
		
		StringBuilder result = new StringBuilder();
		for (char ch : sso.toCharArray()) {
			if (Character.isDigit(ch)) {
				result.append(ch);
			}
		}
		String cleanedSSO =  result.toString();	
		
//		System.out.println("Started SSO Validation");
//		Pattern p = Pattern.compile("^\\d{9}$");
//		Matcher m = p.matcher(cleanedSSO);
		
		return cleanedSSO;
	}
}
