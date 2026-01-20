package com.hyungsuu.common.filter;

import java.io.IOException;

import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hyungsuu.common.interceptor.AuthenticInterceptor;
import com.hyungsuu.common.util.TimeUtil;

import org.slf4j.Logger;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// boot ver2 javax.servlet.Filter를 사용하는 경우
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.slf4j.MDC;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import com.hyungsuu.common.util.TimeUtil;
//
//import javax.servlet.annotation.WebFilter;
//
//
//

//public class InitMDCFilter implements Filter {
//  private FilterConfig config;
//  
//  @Value("${MDC.name}")
//  private String mdcName;
//  
//  private static final Logger log = LoggerFactory.getLogger(InitMDCFilter.class);
//
//
//  @Override
//  public void init(FilterConfig config) throws ServletException { 
//	  log.info("InitMDCFilter. 생성 " );
//	  this.config = config; 
//  }
//
//  @Override
//  public void destroy() {
//	  log.info("InitMDCFilter. 주ㅗㅇ료 " );
//  }
//
//
//@Override
//public void doFilter(ServletRequest req, ServletResponse res,
//		FilterChain chain) throws IOException, ServletException {
//
//   
//     // 시간및 난수로 SID 값을 세팅하여 MDC 값 세팅
//    String strSID = TimeUtil.getDateAndRandom();
//
//    MDC.put(mdcName, strSID);
//	log.info("ProcessBusiness. 1() " + strSID);
//    chain.doFilter(req, res);
//	log.info("ProcessBusiness. 2() " + strSID);
//    MDC.remove(mdcName);
//	
//}
//
//
//}

@Slf4j
public class InitMDCFilter extends OncePerRequestFilter {

  private FilterConfig config;

  private String mdcName ="SID";
  
   
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//      // 시간및 난수로 SID 값을 세팅하여 MDC 값 세팅
    	log.info("Start InitMDCFilter");
    	String strSID = TimeUtil.getDateAndRandom();
    	MDC.put(mdcName, strSID);
        filterChain.doFilter(request, response);
    	MDC.remove(mdcName);
    }


}