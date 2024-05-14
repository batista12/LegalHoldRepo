package com.legal.hold.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.legal.hold.entity.AppAlert;
import com.legal.hold.entity.LegalHoldError;
import com.legal.hold.entity.UserDetail;
import com.legal.hold.entity.UserInListUserResponse;
import com.legal.hold.service.Service;
import com.legal.hold.utils.ApplicationUtility;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {
//localhost:8080/legalholdservice/lookup/searchLookup
//localhost:8080/legalholdservice/admin/addUser
//localhost:8080/legalholdservice/admin/removeUser
//localhost:8080/legalholdservice/admin/listUser

	@Autowired
	Service service;

	@PostMapping(value = "/addUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addUser(@RequestBody UserDetail user, HttpServletRequest request) {
		try {
			String sso = request.getHeader("x-amzn-oidc-identity");
			String loggedinUserSso = ApplicationUtility.filterAndProcessSSO(sso);
			
			if (null == loggedinUserSso) {
				LegalHoldError error = new LegalHoldError(HttpStatus.UNAUTHORIZED.value(),
						"Login not found. Please login once again");
				return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
			}

			user.setLoggedinSso(loggedinUserSso);
			
			if (service.addUser(user)) {
				LegalHoldError error = new LegalHoldError(HttpStatus.OK.value(), "Added successfully");
				return new ResponseEntity<>(error, HttpStatus.OK);
			} else {
				LegalHoldError error = new LegalHoldError(HttpStatus.OK.value(), "Failed while added", false);
				return new ResponseEntity<>(error, HttpStatus.OK);
			}
			
		}catch(Exception e){
			LegalHoldError error = new LegalHoldError(HttpStatus.UNAUTHORIZED.value(),
					"Login not found. Please login once again");
			return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
		}
	}


	@PostMapping(value = "/removeUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> removeUser(@RequestBody String sso, HttpServletRequest request)
			throws ParseException {
		try {
			String rawSSO = request.getHeader("x-amzn-oidc-identity");
			String loggedinUserSso = ApplicationUtility.filterAndProcessSSO(rawSSO);
			if (null == loggedinUserSso) {
				LegalHoldError error = new LegalHoldError(HttpStatus.UNAUTHORIZED.value(),
						"Login not found. Please login once again");
				return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
			}

			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(sso);
			sso = (String) jsonObject.get("ssoid");
			if (service.removeUser(sso, loggedinUserSso)) {
				LegalHoldError error = new LegalHoldError(HttpStatus.OK.value(), "Removed successfully");
				return new ResponseEntity<>(error, HttpStatus.OK);
			} else {
				LegalHoldError error = new LegalHoldError(HttpStatus.OK.value(),
						"Failed during remove, please try again later", false);
				return new ResponseEntity<>(error, HttpStatus.OK);
			}

		} catch (Exception e) {
			LegalHoldError error = new LegalHoldError(HttpStatus.UNAUTHORIZED.value(),
					"Login not found. Please login once again");
			return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
		}

	}

	@GetMapping(value = "/listUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> listUser(HttpServletRequest request) {
		try {
			String rawSSO = request.getHeader("x-amzn-oidc-identity");
			String loggedinUserSso = ApplicationUtility.filterAndProcessSSO(rawSSO);
			if (null == loggedinUserSso) {
				LegalHoldError error = new LegalHoldError(HttpStatus.UNAUTHORIZED.value(), "Invalid SSO");
				return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
			}

			List<UserInListUserResponse> resList = service.listUser();
			if (!resList.isEmpty()) {

				for (int i = 0; i < resList.size(); i++) {
					UserInListUserResponse user = resList.get(i);
					String sso = user.getSsoid();
					if (sso.equals(loggedinUserSso)) {
						resList.remove(i);
						user.setIsEditable(false);
						resList.add(user);
						break;
					}
				}
				return ResponseEntity.ok(resList);
			} else {
				LegalHoldError error = new LegalHoldError(HttpStatus.OK.value(), "No Records Exist", false);
				return new ResponseEntity<>(error, HttpStatus.OK);
			}
		} catch (Exception e) {
			LegalHoldError error = new LegalHoldError(HttpStatus.UNAUTHORIZED.value(),
					"Login not found. Please login once again");
			return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping(value = "/getAlertData", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAlertData(@RequestParam String alertType) {
		if (alertType == null || alertType.isEmpty()) {
			LegalHoldError error = new LegalHoldError(HttpStatus.BAD_REQUEST.value(), "Alert type not present");
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
		AppAlert alertRes = service.getAlertDetails(alertType);
		if (alertRes != null) {
			return ResponseEntity.ok(alertRes);
		} else {
			LegalHoldError error = new LegalHoldError(HttpStatus.NOT_FOUND.value(), "Invalid Alert type");
			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		}
	}

}
