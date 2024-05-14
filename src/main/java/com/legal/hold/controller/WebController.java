package com.legal.hold.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.legal.hold.entity.LegalAssistantsModel;
import com.legal.hold.entity.StatusResponse;
import com.legal.hold.service.Service;
import com.legal.hold.utils.ApplicationUtility;

@RestController
@RequestMapping(value = "/hold_lookup")
public class WebController {

	@Autowired
	Service service;

	@GetMapping(value = "/status/{sso}", produces = MediaType.APPLICATION_JSON_VALUE)
	public StatusResponse getSso(@PathVariable("sso") String sso) throws SQLException {
		try {
        	if(ApplicationUtility.isValidSSO(sso)) {
        		String ssa = service.getSso(sso);
    			int l = ssa.length();
    			if (l == 2) {
    				return new StatusResponse(sso, "no");
    			} else {
    				return new StatusResponse(sso, "yes");
    			}
        	}
        	System.out.println("SSO Verfication Ended - INVALID SSO");
			return new StatusResponse(sso, "Enter Valid SSO!!");
		} catch (RuntimeException exc) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "SSO Not found", exc);
		}
	}

	@GetMapping(value = "/assistants/{sso}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<LegalAssistantsModel> getAssistants(@PathVariable("sso") String sso) throws SQLException {
		try {
			if(ApplicationUtility.isValidSSO(sso)) {
				return service.getAssistants(sso);
			} else {
				System.out.println("SSO Verfication Ended - INVALID SSO");
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "SSO Not found");
			}
		} catch (RuntimeException exc) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "SSO Not found", exc);
		}
	}

}
