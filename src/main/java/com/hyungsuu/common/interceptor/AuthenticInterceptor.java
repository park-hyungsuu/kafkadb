package com.hyungsuu.common.interceptor;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyungsuu.common.exception.GlobalException;
import com.hyungsuu.common.util.JwtTokenUtil;
import com.hyungsuu.common.vo.BaseResponseVo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class AuthenticInterceptor extends WebContentInterceptor {



//	/**
//	 * 세션에 계정정보(LoginVO)가 있는지 여부로 인증 여부를 체크한다.
//	 * 계정정보(LoginVO)가 없다면, 로그인 페이지로 이동한다.
//	 */
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
//
//		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
//
//		if (loginVO.getId() != null) {
//			return true;
//		} else {
//			ModelAndView modelAndView = new ModelAndView("redirect:/login/loginForm.do");
//			throw new ModelAndViewDefiningException(modelAndView);
//		}
//	}

	
	/**
	 * 세션에 계정정보(LoginVO)가 있는지 여부로 인증 여부를 체크한다.
	 * 계정정보(LoginVO)가 없다면, 로그인 페이지로 이동한다.
	 */
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws ServletException {
		log.info("preHandle"+req.getHeader("jwtToken"));


		if (req.getHeader("jwtToken") == null) {
			GlobalException ge = new GlobalException("4444","fail");
			throw new ServletException("1111",ge);
		}
		if ( JwtTokenUtil.isTokenExpired(req.getHeader("jwtToken"))) {
			return true;
		} else {

            HandlerMethod method = (HandlerMethod) handler;
            log.debug("Controller: {}", method.getBeanType().getSimpleName());
            log.debug("Method: {}", method.getMethod().getName());
			BaseResponseVo baseResponseVo = new BaseResponseVo();
			baseResponseVo.setCode("2000");
			baseResponseVo.setMessage("JWT Token 만료 및 유효하지 않습니다.");
			
			ObjectMapper mapper = new ObjectMapper(); 
	
			res.setStatus(HttpServletResponse.SC_OK);
			res.setHeader("Content-type", "application/json;charset=UTF-8");
			res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			res.setHeader("Pragma", "no-cache");
	
			try {
				res.getWriter().write(mapper.writeValueAsString(baseResponseVo));
				res.getWriter().flush();
			} catch (IOException e) {
				log.info("IOException {}",e);
				GlobalException ge = new GlobalException("4444","fail", e);
				throw new ServletException("1111",ge);
			}
		
			return false;
		}
	}
}
