package com.legal.hold.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
public class LookupService {

	static long tokentime = Calendar.getInstance().getTimeInMillis();

	class SentryData {
		private String access_token;
		private String token_type;
		private String expires_in;

		public String getAccess_token() {
			return access_token;
		}

		public void setAccess_token(String access_token) {
			this.access_token = access_token;
		}

		public String getToken_type() {
			return token_type;
		}

		public void setToken_type(String token_type) {
			this.token_type = token_type;
		}

		public String getExpires_in() {
			return expires_in;
		}

		public void setExpires_in(String expires_in) {
			this.expires_in = expires_in;
		}
	}

	private static String getAccToken() {
		HttpClient client = HttpClientBuilder.create().build();
		SentryData tokendata = null;
		HttpPost method;
		try {
			method = new HttpPost(
					"https://fssfed.ge.com/fss/as/token.oauth2?grant_type=client_credentials&client_id=lckSI9OaglPA2qWqRKdTwdYT2HeLgBsP4uvUXx7T8d1LRHb3&client_secret=mgMDZ5tuPNp17YOwtNFtunbfCnuwZVqDDijFwDWoTw24CyNW1WOfyjtoab003hbh&scope=api");
			// method.addHeader("Authorization","Basic
			// Q0RJU1NPU2VhcmNoX0NsaWVudDpjYzMxZDEyZDEwZDdkOGRlMWY5NzdkZWEwZGIzZTg0ZjMwY2Q1MTcx");
			HttpResponse resp = client.execute(method);
			BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			Gson gson = new GsonBuilder().create();
			tokendata = gson.fromJson(result.toString(), SentryData.class);
			tokentime = Calendar.getInstance().getTimeInMillis();
		} catch (Exception e) {
			System.out.println("Error in getAccToken(): Exception" + e);
		} catch (Throwable t) {
			System.out.println("Error in getAccToken(): Exception" + t);
		}
		if (tokendata == null || tokendata.getAccess_token().equals("")) {
			System.out.println("Error In getAccessToken");
		}
		return tokendata.getAccess_token();
	}

	public String getSsolookup(String sso) throws Exception {
		String jsonString = "";
		StringBuffer response = new StringBuffer();
		String currenttoken = getAccToken();
		try {
			String baseUrl = "https://api.ge.com/digital/ssogenericread/v2/";
			String query1 = "ReadData?readData={\"directoryBranch\":\"400\",\"permitAbbreviatedRecords\":\"false\",\"abbreviatedFieldList\":{\"field\":[\"georaclehrid\",\"givenName\",\"sn\"]},\"primaryKey\":{\"uid\":\"";
			String query2 = "\"},\"fieldList\":{\"field\":[\"uid\",\"cn\",\"mail\",\"gehrindustrygroup\",\"employeetype\"]}}";
			String query3 = "&responsetype=json";
			String finaluri = baseUrl + query1 + sso + query2 + query3;
			URL url = new URL(finaluri);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Bearer " + currenttoken);
			// int responseCode = conn.getResponseCode();
			// System.out.println("Response Code: " + responseCode);
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputline = "";
			while ((inputline = rd.readLine()) != null) {
				response.append(inputline);
			}
			jsonString = response.toString();
			// jsonString = jsonString.replace("{\"result\":{", "");
		} catch (Exception e) {
			System.out.println("Error in getSsolookup: Exception" + e);
		} catch (Throwable t) {
			System.out.println("Error in getSsolookup: Exception" + t);
		}
		if (jsonString.equals("")) {
			System.out.println("Error In getSsolookup()");
		}
		return jsonString;
	}

}
