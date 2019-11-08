package com.efe.ms.bankservice.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 自定义拦截器（需要注册，继承WebMvcConfigurerAdapter中注册）
 * 
 * @author liutianlong
 *
 */
public class ApplicationInterceptor implements HandlerInterceptor {
	private final static Logger logger = LoggerFactory
			.getLogger(ApplicationInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		logger.debug("ApplicationInterceptor 处理请求完成之后，视图渲染之后执行...");
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		logger.debug("ApplicationInterceptor 处理请求完成之后，视图渲染之前...");
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		logger.debug("ApplicationInterceptor 处理请求之前执行...");
		return true; // true:继续执行，false:取消当前请求
	}

}
