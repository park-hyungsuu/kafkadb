package com.hyungsuu.apigate.samaple.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hyungsuu.apigate.samaple.dao.CommonDAO;
import com.hyungsuu.apigate.samaple.service.JwtService;
import com.hyungsuu.apigate.samaple.vo.JwtTokenReqVo;
import com.hyungsuu.apigate.samaple.vo.JwtTokenResVo;
import com.hyungsuu.apigate.samaple.vo.RefreshTokenReqVo;
import com.hyungsuu.common.exception.GlobalException;
import com.hyungsuu.common.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("jwtService")
public class JwtServiceImpl implements JwtService {

	@Autowired
    private CommonDAO commonDAO;

	
	@Value("${jwt.expTime}")
	private int jwtExpTime;
	
	@Transactional
	public JwtTokenResVo generateJwtToken(JwtTokenReqVo jwtTokenReqVo) throws GlobalException, Exception  {
	
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		try {
			rtnMap = commonDAO.selectOne("User.selectUserAndPass", jwtTokenReqVo);
	
		} catch (Exception e) { // MicroService 처리 중 기타 예외 발생 시
			log.info("GlobalException ==>" +"||"+e.getMessage() +"||"+e.toString() );
			throw e;
		}

		// 데이타가 없는 경우
		if (rtnMap == null) {
			throw new GlobalException("600", "aaaa");
		}
		JwtTokenResVo jwtTokenResVo = new JwtTokenResVo();
		String jwtToken = null;
//		Date date = new Date(System.currentTimeMillis());
		long date = System.currentTimeMillis();

			jwtToken = JwtTokenUtil.generateToken(jwtTokenReqVo.getUserId(), (String)rtnMap.get("userAuth"), jwtExpTime,date);

			jwtTokenResVo.setJwtToken(jwtToken);
	
		return jwtTokenResVo;

	}

	@Override
	public JwtTokenResVo getRefreshJwtToken(RefreshTokenReqVo refreshTokenReqVo) throws GlobalException, Exception {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		try {
			rtnMap = commonDAO.selectOne("User.selectUser", refreshTokenReqVo);
	
		} catch (Exception e) { // MicroService 처리 중 기타 예외 발생 시
			log.info("GlobalException ==>" +"||"+e.getMessage() +"||"+e.toString() );
			throw e;
		}

		// 데이타가 없는 경우
		if (rtnMap == null) {
			throw new GlobalException("600", "aaaa");
		}
		JwtTokenResVo jwtTokenResVo = new JwtTokenResVo();
		String jwtToken = null;
		
//		Date date = new Date(System.currentTimeMillis());
		long date = System.currentTimeMillis();
		jwtToken = JwtTokenUtil.generateToken(refreshTokenReqVo.getUserId(), (String)rtnMap.get("userAuth"), jwtExpTime, date);
		jwtTokenResVo.setJwtToken(jwtToken);

		return jwtTokenResVo;
	}
}
