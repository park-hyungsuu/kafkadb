package com.hyungsuu.apigate.samaple.service;


import com.hyungsuu.apigate.samaple.vo.JwtTokenReqVo;
import com.hyungsuu.apigate.samaple.vo.JwtTokenResVo;
import com.hyungsuu.apigate.samaple.vo.RefreshTokenReqVo;
import com.hyungsuu.common.exception.GlobalException;





public interface JwtService {


	JwtTokenResVo generateJwtToken(JwtTokenReqVo jwtTokenReqVo) throws GlobalException, Exception;

	JwtTokenResVo getRefreshJwtToken(RefreshTokenReqVo refreshTokenReqVo) throws GlobalException, Exception;
	
	
}
