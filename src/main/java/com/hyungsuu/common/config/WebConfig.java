package com.hyungsuu.common.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.hyungsuu.common.filter.XssFilter;
import com.hyungsuu.common.filter.InitMDCFilter;
import com.hyungsuu.common.interceptor.AuthenticInterceptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {



	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}


	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")  // 모든 경로에 대해 CORS 설정을 적용합니다.
			.allowedOrigins("*")	// 모든 오리진(출처)에서의 요청을 허용합니다.
			.allowedMethods("*")	// 허용되는 HTTP 메서드를 지정합니다.
			.allowedHeaders("*");	// 모든 헤더를 허용합니다.
//			.allowCredentials(true); // 인증된 요청을 허용합니다 (예: 쿠키, HTTP 인증).
	}
	
 

	@Bean
	public FilterRegistrationBean<InitMDCFilter> loggingFilter() {

		log.debug("________________________");
		FilterRegistrationBean<InitMDCFilter> logFilter = new FilterRegistrationBean<InitMDCFilter>();
		logFilter.setFilter(new InitMDCFilter());
		logFilter.setOrder(0);
		logFilter.addUrlPatterns("/api1/*", "/api/*");
		logFilter.setName("InitMDCFilter");
		return logFilter;
	}

	@Bean
	public FilterRegistrationBean<XssFilter> xssFilter() {

		FilterRegistrationBean<XssFilter> xssFilter = new FilterRegistrationBean<>();
		xssFilter.setFilter(new XssFilter());
		xssFilter.setOrder(1);
		xssFilter.addUrlPatterns("/api/*","/api1/*");
		xssFilter.setName("XssFilter");

		return xssFilter;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthenticInterceptor( ))
			.order(0)
			.addPathPatterns("/api/**") // 모든 URL에 적용
			.excludePathPatterns("/api/token/**", "/api/unAuth/**", "/api/kafka/**"); // 특정 URL 제외
	}
	
}