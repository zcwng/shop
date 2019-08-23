package com.zcwng.shop.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

	public static String getCookie(HttpServletRequest request,String cookieName) {
		Cookie[] cookies = request.getCookies();
		if(cookies !=null) {
			for(Cookie cookie:cookies) {
				if(cookie.getName().equals(cookieName)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
	
	public static void writeCookie(HttpServletResponse response,String cookieName,String value) {
		Cookie cookie = new Cookie(cookieName,value);
		cookie.setDomain("demo.com");
		cookie.setPath("/");
		cookie.setMaxAge(3600);
		response.addCookie(cookie);
	}
	
	public static void removeCookie(HttpServletResponse response,String cookieName) {
		Cookie cookie = new Cookie(cookieName,null);
		cookie.setDomain("demo.com");
		cookie.setPath("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}
}
