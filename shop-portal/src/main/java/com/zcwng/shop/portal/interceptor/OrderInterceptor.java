package com.zcwng.shop.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.itshidu.web.tools.CookieUtil;
import com.zcwng.shop.portal.service.UserService;
import com.zcwng.shop.portal.vo.TbUser;

@Component
public class OrderInterceptor implements HandlerInterceptor{

	@Autowired UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String loginUrl = userService.getLoginURL()+"?redirect="+getBaseURL(request);		
		String token = CookieUtil.getCookie("SHOP_TOKEN", request).getValue();
		if(StringUtils.isEmpty(token)) {
			response.sendRedirect(loginUrl);
			return false;
		}
		TbUser user = userService.getUserByToken(token);
		if(user==null) {
			response.sendRedirect(loginUrl);
			return false;
		}
		request.setAttribute("user", user);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	private String getBaseURL(HttpServletRequest request) {
		String url = request.getScheme()
					+ "://"
					+ request.getServerName()
					+ ":"
					+ request.getServerPort()
					+ request.getContextPath()
					+ request.getRequestURI();
		return url;
	}

}
