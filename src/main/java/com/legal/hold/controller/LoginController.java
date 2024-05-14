package com.legal.hold.controller;

import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jayway.jsonpath.JsonPath;
import com.legal.hold.entity.LegalHoldError;
import com.legal.hold.model.LoginModel;
import com.legal.hold.service.Service;

@RestController()
@RequestMapping(value = "/login")
public class LoginController {

	@Autowired
	Service service;

	@GetMapping(value = "/userInfo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getLoginInfo(HttpServletRequest request) {

		LoginModel loginModel = new LoginModel();
		try {
//			Enumeration<String> requestHeaders = request.getHeaderNames();
//			while (requestHeaders.hasMoreElements()) {
//				String name = requestHeaders.nextElement();
//				String value = request.getHeader(name);
//				System.out.println(name + " = " + value);
//			}

			ServletUriComponentsBuilder compBuilder = ServletUriComponentsBuilder.fromRequestUri(request);
			String baseUrl = compBuilder.replacePath(null).build().toUriString();
			System.out.println("Base URL: " + baseUrl);
			if (baseUrl.contains("localhost")) {

				loginModel.setSsoid("212574054");
				loginModel.setFullName("Pushpinder Singh");
				loginModel.setFirstName("Pushpinder");
				loginModel.setLastName("Singh");
				loginModel.setEmail("pushpinder.singh1@ge.com");
				loginModel.setBusiness("GE Aerospace");

			} else {
				if (null == request.getHeader("x-amzn-oidc-identity")) {
					LegalHoldError error = new LegalHoldError(HttpStatus.UNAUTHORIZED.value(),
							"Login not found. Please login once again");
					return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
				}

				loginModel.setSsoid(request.getHeader("x-amzn-oidc-identity"));
				String oidcToken = request.getHeader("x-amzn-oidc-data");
				String[] oidcChunks = oidcToken.split("\\.");

				Base64.Decoder decoder = Base64.getUrlDecoder();
				String oidcPayload = new String(decoder.decode(oidcChunks[1]));

				loginModel.setFullName(JsonPath.read(oidcPayload, "$.cn"));
				loginModel.setFirstName(JsonPath.read(oidcPayload, "$.firstname"));
				loginModel.setLastName(JsonPath.read(oidcPayload, "$.lastname"));
				loginModel.setBusiness(JsonPath.read(oidcPayload, "$.gehrindustrygroup"));

				String employeetype = JsonPath.read(oidcPayload, "$.employeetype");
				if (employeetype.equals("Functional")) {
					loginModel.setEmail("Test FSSO");
				} else {
					loginModel.setEmail(JsonPath.read(oidcPayload, "$.mail"));
				}
			}

			String sso = loginModel.getSsoid();
			loginModel.setRoleId(service.getRoleId(sso));
			loginModel.setRoleName(service.getRoleName(sso));

		} catch (Exception e) {
			LegalHoldError error = new LegalHoldError(HttpStatus.UNAUTHORIZED.value(),
					"Login not found. Please login once again");
			return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
		}
		return ResponseEntity.ok(loginModel);

	}

}
