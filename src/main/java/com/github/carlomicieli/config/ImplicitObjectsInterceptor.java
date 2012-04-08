/*
 * Copyright 2012 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.carlomicieli.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 
 * Original code at <http://forum.thymeleaf.org/Thymeleaf-and-Spring-Security-td3205099.html>
 *
 */
public class ImplicitObjectsInterceptor extends HandlerInterceptorAdapter {

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (modelAndView != null
				&& !modelAndView.getViewName().startsWith("redirect:")) {
			FilterInvocation filterInvocation = new FilterInvocation(request,
					response, new FilterChain() {
						public void doFilter(ServletRequest request,
								ServletResponse response) throws IOException,
								ServletException {
							throw new UnsupportedOperationException();
						}
					});
			Authentication authentication = SecurityContextHolder.getContext()
					.getAuthentication();
			WebSecurityExpressionRoot sec = new WebSecurityExpressionRoot(
					authentication, filterInvocation);
			sec.setTrustResolver(new AuthenticationTrustResolverImpl());
			modelAndView.getModel().put("sec", sec);
		}
	}
}
