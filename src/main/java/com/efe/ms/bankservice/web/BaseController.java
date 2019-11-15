package com.efe.ms.bankservice.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.efe.ms.bankservice.vo.BusinessResult;

/**
 * 基础控制器
 * @author TianLong Liu
 * @date 2019年11月6日 上午11:07:05
 */
public class BaseController {

	protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	protected BusinessResult jsonSuccess() {
        return BusinessResult.success();
    }
	
	protected BusinessResult jsonSuccess(Object data) {
		return BusinessResult.success().data(data);
	}

	protected BusinessResult jsonError(Object data,String message) {
        return BusinessResult.fail().data(data).message(message);
    }
    
    /**
	 * 得到request对象
	 */
	protected HttpServletRequest getHttpServletRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return request;
	}

}
