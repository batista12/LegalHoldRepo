package com.legal.hold.controller;

import java.net.URL;
import org.springframework.web.util.HtmlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.jayway.jsonpath.JsonPath;
import com.legal.hold.entity.SsoResponse;
import com.legal.hold.service.LookupService;
import com.legal.hold.utils.ApplicationUtility;

@RestController()
@RequestMapping(value = "/lookup")
public class SsoController {
	
	@Autowired
	LookupService lookupservice;

	@GetMapping(value = "/sso/{sso}", produces = MediaType.APPLICATION_JSON_VALUE)
	public SsoResponse getSsolookup(@PathVariable("sso") String sso) throws Exception {
		try {
			System.out.println("Started SSO Filtration");
			if (!isValidUrl(sso)) {
				throw new IllegalArgumentException("Invalid URL");         
			}
			
			String escapedSso = HtmlUtils.htmlEscape(sso);
			
			System.out.println("Started SSO Validation");
			if(ApplicationUtility.isValidSSO(escapedSso)) {
				String result = lookupservice.getSsolookup(escapedSso);

				if (result.length() <= 40) {
					return new SsoResponse(escapedSso, "", "", "");
				} else {
					String ssoid = JsonPath.read(result, "$.uid");
					String name = JsonPath.read(result, "$.cn");
					String business = JsonPath.read(result, "$.gehrindustrygroup");
					String employeetype = JsonPath.read(result, "$.employeetype");
					String email;
					if (employeetype.equals("Functional")) {
						email = "";
					} else {
						email = JsonPath.read(result, "$.mail");
					}
	
					return new SsoResponse(ssoid, name, business, email);
				}
			}else {
				System.out.println("SSO Verfication Ended - INVALID SSO");
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "SSO Not found");
			}
		} catch (RuntimeException exc) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "SSO Not found", exc);
		}
	}
	
	private boolean isValidUrl(String url) {
		try {
			// Attempt to parse the URL
			new URL(url).toURI();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
