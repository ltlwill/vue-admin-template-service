package com.efe.ms.bankservice.config;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.efe.ms.bankservice.request.ApplicationInterceptor;
import com.efe.ms.bankservice.util.OSUtils;

/**
 * 应用配置
 *
 * @author TianLong Liu
 * @date 2019年11月6日 上午11:24:10
 */
@Configuration
public class ApplicationConfiguration extends WebMvcConfigurationSupport {
	
	private static final String DEFAULT_CHARSET = "UTF-8";

	@Autowired
	private EnvironmentProperties envProperties;

	/**
	 * 增加自定义静态资源配置
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 将磁盘上的文件资源映射为静态资源（类似于tomcat的虚拟目录）,可添加多个（注：需以file:开头，/结尾）
		registry.addResourceHandler(envProperties.getVirtualPathPattern())
				.addResourceLocations(
						OSUtils.isLinuxOS() ? envProperties
								.getLinuxVirtualDirPattern() : envProperties
								.getVirtualDirPattern()); // linux 环境使用linux系统目录模式
	}

	/**
	 * 增加默认的视图
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		String entrance = envProperties.getAppEntrance();
		if (entrance != null && !"".equals(entrance.trim())) {
			entrance = entrance.lastIndexOf(".") > -1 ? entrance.substring(0,
					entrance.lastIndexOf(".")) : entrance;
		}
		registry.addViewController("/").setViewName(entrance);
	}

	/**
	 * 增加拦截器
	 */
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new ApplicationInterceptor())
				.addPathPatterns("/**").excludePathPatterns("");
		// registry.addInterceptor(null).addPathPatterns("").excludePathPatterns("");
	}
	
	/**
	 * 
	 * <p>解决返回数据时 中文乱码问题（responseBodyConverter，configureMessageConverters，configureContentNegotiation三个方法结合）: </p>
	 * @param
	 * @author TianLong Liu
	 * @date 2019年11月25日 上午10:34:12
	 * @return HttpMessageConverter<String>
	 */
	/*@Bean
	public HttpMessageConverter<String> responseBodyConverter() {
		StringHttpMessageConverter converter = new StringHttpMessageConverter(
				Charset.forName(DEFAULT_CHARSET));
		return converter;
	}

	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		converters.add(responseBodyConverter());
	}

	@Override
	public void configureContentNegotiation(
			ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(false);
	}*/
}
