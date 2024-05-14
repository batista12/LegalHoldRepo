package com.legal.hold.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.legal.hold.entity.LegalHoldModel;
import com.legal.hold.entity.SearchLookupResponse;
import com.legal.hold.exception.RecordNotFoundException;
import com.legal.hold.service.Service;
import com.legal.hold.utils.ApplicationUtility;

@RestController
@RequestMapping(value = "/lookup")
public class LookupController {

	@Autowired
	Service service;

	@PostMapping(value = "/searchLookup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public SearchLookupResponse getLegalHoldDet(@RequestBody String sso) throws RecordNotFoundException, Exception {
	
	  if (!isValidJsonFormat(sso)) {
            throw new Exception("Invalid JSON format");
        }

        JSONObject jsonObject = parseJson(sso);
        String ssoString = (String) jsonObject.get("sso");
        List<String> resSSO = Arrays.asList(ssoString.split(","));

        System.out.println("Validating SSO");
        // Validate SSOs
        List<String> validSSOs = validateSSOs(resSSO);

        System.out.println("Getting Details");
        // Get Legal Hold Details
        List<LegalHoldModel> resList = service.getLegalHoldDetails(validSSOs);

        System.out.println("Generating Response");
        // Generate response
        return generateResponse(resList, resSSO, validSSOs);
    }

    private JSONObject parseJson(String sso) throws Exception {
        try {
            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(sso);
        } catch (Exception e) {
            throw new Exception("Invalid JSON format");
        }
    }

    private boolean isValidJsonFormat(String sso) {
        // Check if the input string is valid JSON format
        try {
            parseJson(sso);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private List<String> validateSSOs(List<String> resSSO) {
        // Validate SSOs format and length
        return resSSO.stream()
                .filter(ApplicationUtility::isValidSSO)
                .filter(s -> s.length() == 9)
                .collect(Collectors.toList());
    }

    private SearchLookupResponse generateResponse(List<LegalHoldModel> resList, List<String> resSSO, List<String> validSSOs) {
        String erroredSso = "";
        String messageString = "";

        // Find invalid SSOs
        List<String> invalidSSos = resSSO.stream()
                .filter(s -> !validSSOs.contains(s))
                .collect(Collectors.toList());

        if (!invalidSSos.isEmpty()) {
            erroredSso = "Invalid SSO - " + invalidSSos.toString();
        }

        // Find SSOs with no active legal holds
        List<String> recFound = resList.stream()
                .map(LegalHoldModel::getSsoid)
                .collect(Collectors.toList());

        List<String> noRecFound = validSSOs.stream()
                .filter(s -> !recFound.contains(s))
                .collect(Collectors.toList());

        if (!noRecFound.isEmpty()) {
            messageString = "No active legal holds identified at this time for: " + noRecFound.toString();
        }

        return new SearchLookupResponse(resList, messageString, erroredSso);
    }
}


