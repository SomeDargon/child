package com.child.common.interceptor;

import com.child.common.exception.BusinessException;
import com.child.common.exception.ErrorCode;
import com.child.common.utils.ConstantsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

public class AppSecurityInterceptor extends HandlerInterceptorAdapter {


	private static final Logger logger = LoggerFactory
			.getLogger(AppSecurityInterceptor.class);



	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response, Object handler) throws Exception {

		String requestUri = request.getRequestURI();

		String authToken = request.getHeader(ConstantsUtils.AUTH_TOKEN_HEADER_NAME);
		if (null == authToken || authToken.equals("")) {
			throw new BusinessException(ErrorCode.AUTHTOKEN_ERROR);
		}

		return true;
	}


	@Override
	public void postHandle(HttpServletRequest request,
						   HttpServletResponse response, Object handler,
						   ModelAndView modelAndView) throws Exception {

		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
								HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

		super.afterCompletion(request, response, handler, ex);
	}




}
