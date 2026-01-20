package com.hyungsuu.apigate.samaple.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hyungsuu.apigate.samaple.service.JwtService;

import com.hyungsuu.apigate.samaple.vo.JwtTokenReqVo;
import com.hyungsuu.apigate.samaple.vo.JwtTokenResVo;
import com.hyungsuu.apigate.samaple.vo.RefreshTokenReqVo;
import com.hyungsuu.common.exception.GlobalException;
import com.hyungsuu.common.util.CommonUtil;
import com.hyungsuu.common.util.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
//import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value="/api")
public class JwtController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private JwtService jwtService;
	

    /**
     * @param JwtTokenReqVo
     * @param model
     * @return JwtTokenResVo
     * @throws Exception
     */

//	@ApiOperation(nickname="getJwtToken", value = "JWT 토근 조회", notes="API를 사용하기 위해 JWT 토근 조회")
    // Resquest 헤더에 값 세팅할때
//	@ApiImplicitParams({
//	@ApiImplicitParam(name="apiKeyHashcd", value="API Key Hash한 코드", required= true, dataType="string", paramType ="header"),
//	})
    // Response 헤더 값 세팅할때
//	@ApiResponses(
//			value = {
//					@ApiResponse(code = 200, message ="OK", responseContainer = "List",
//							responseHeaders = {
//									@ResponseHeader(name="apiTrnsmisNo", description="API 전문번호", response = String.class)
//					})
//			})
	
	@Operation(summary = "token 생성", description = "token 생성합니다")
    @RequestMapping(value="/token/getJwtToken", method=RequestMethod.POST, consumes = "application/json;charset=UTF-8", produces="application/json;charset=UTF-8")
    public ResponseEntity<JwtTokenResVo> getJwtToken(@Valid @RequestBody JwtTokenReqVo jwtTokenReqVo, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) throws GlobalException {

		try {
			log.info("getJwtToken. Start()" );
			log.info("getJwtToken. bindingResult  ==>" +  bindingResult.getErrorCount());
			
			CommonUtil.checkBindingResult(bindingResult);  // Request VO binding 결과 처리
		
			JwtTokenResVo jwtTokenResVo= jwtService.generateJwtToken(jwtTokenReqVo);

			jwtTokenResVo.setSuccess();
			log.info("getJwtToken. End() " );
			return new ResponseEntity<JwtTokenResVo>(jwtTokenResVo, HttpStatus.OK);
		} catch (GlobalException ge) { // MicroService 처리 중 사용자 정의 에러 발생 시
			if(ge.getMessage() == null || ge.getMessage().equals("")) {
				ge.setMessage("fail");
			}
			throw ge;
		} catch (Exception e) { // MicroService 처리 중 기타 예외 발생 시
			e.printStackTrace();
			throw new GlobalException("600", "fail");
		} finally {
				log.info("getJwtToken. End() " );
		}
    }

	
	@Operation(summary = "Refresh Token 생성", description = "Refresh Token 생성합니다")
    @RequestMapping(value="/token/getRefreshToken", method=RequestMethod.POST, consumes = "application/json;charset=UTF-8", produces="application/json;charset=UTF-8")
    public ResponseEntity<JwtTokenResVo> getRefreshJwtToken(@Valid @RequestBody RefreshTokenReqVo refreshTokenReqVo, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) throws GlobalException {

		try {
			log.info("getRefreshToken. Start()" );
			
			log.info("getRefreshJwtToken. bindingResult  ==>" +  bindingResult.getErrorCount());
		
			CommonUtil.checkBindingResult(bindingResult);  // Request VO binding 결과 처리
		
			JwtTokenResVo jwtTokenResVo= jwtService.getRefreshJwtToken(refreshTokenReqVo);

			jwtTokenResVo.setSuccess();
			
			return new ResponseEntity<JwtTokenResVo>(jwtTokenResVo, HttpStatus.OK);
		} catch (GlobalException ge) { // MicroService 처리 중 사용자 정의 에러 발생 시
			if(ge.getMessage() == null || ge.getMessage().equals("")) {
				ge.setMessage("fail");
			}
			throw ge;
		} catch (Exception e) { // MicroService 처리 중 기타 예외 발생 시
			log.debug("getRefreshJwtToken. Exception :{}" + e);
			throw new GlobalException("600", "fail");
		} finally {
			log.info("getRefreshJwtToken. End() " );
		}
    }
}
