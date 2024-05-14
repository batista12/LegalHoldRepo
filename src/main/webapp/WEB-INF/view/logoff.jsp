<%response.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains; preload"); %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<% 
Cookie[] cookies=request.getCookies();
for(Cookie cookie:cookies) {
	if (cookie.getName() != null){
		if(cookie.getName().equals("mod_auth_openidc_session") || 
				cookie.getName().equals("PF.PERSISTENT")|| 
				cookie.getName().equals("PF")){
			cookie.setValue("");
			cookie.setMaxAge(0);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
	}
}
String redirectURL  ="https://ssologin.ssogen2.corporate.ge.com/logoff/logoff.jsp";
response.sendRedirect(redirectURL);
%>
