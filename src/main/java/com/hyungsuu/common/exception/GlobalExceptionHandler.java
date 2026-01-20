package com.hyungsuu.common.exception;


import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.hyungsuu.common.vo.BaseResponseVo;

import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
@RestController
public class GlobalExceptionHandler {

	private String mdcName = "SID";
	
	@ExceptionHandler(GlobalException.class)
	public ResponseEntity<BaseResponseVo> handleGlobalException(GlobalException e) {
		log.info("GlobalException is happened!");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));

		BaseResponseVo baseResVo = new BaseResponseVo();
		baseResVo.setCode(e.getCode());
		baseResVo.setMessage(e.getMessage());

		// Exception시에는 globalFilter에서 처리 못하니 ExceptionHandler에사 MDC 삭제
       	MDC.remove(mdcName);
		return new ResponseEntity<BaseResponseVo>(baseResVo, headers, HttpStatus.OK);
	}

	// Request Body가 없는경우 Json 이나 XML 포맷에 안 맞을때
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<BaseResponseVo> handleBHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		e.printStackTrace();
		log.info("HttpMessageNotReadableException is happened! {}" + e);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));

		BaseResponseVo baseResVo = new BaseResponseVo();
		baseResVo.setCode("2222");
		baseResVo.setMessage("HttpMessageNotReadableException is happened!");

		// Exception시에는 globalFilter에서 처리 못하니 ExceptionHandler에사 MDC 삭제
       	MDC.remove(mdcName);
		return new ResponseEntity<BaseResponseVo>(baseResVo, headers, HttpStatus.OK);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<BaseResponseVo> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		log.info("MethodArgumentNotValidException is happened!" + ex.getBindingResult().getErrorCount());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));

		BaseResponseVo baseResVo = new BaseResponseVo();
		baseResVo.setCode("3333");
		baseResVo.setMessage("MethodArgumentNotValidException is happened. {}" + ex);

		// Exception시에는 globalFilter에서 처리 못하니 ExceptionHandler에사 MDC 삭제
       	MDC.remove(mdcName);
		return new ResponseEntity<BaseResponseVo>(baseResVo, headers, HttpStatus.OK);

	}

	
	
	
	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<BaseResponseVo> handleNoResourceFoundException(NoResourceFoundException ex) {
		log.info("NoResourceFoundException is happened!" );

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));

		BaseResponseVo baseResVo = new BaseResponseVo();
		baseResVo.setCode("3333");
		baseResVo.setMessage("NoResourceFoundException is happened. {}" + ex);

		// Exception시에는 globalFilter에서 처리 못하니 ExceptionHandler에사 MDC 삭제
       	MDC.remove(mdcName);
		return new ResponseEntity<BaseResponseVo>(baseResVo, headers, HttpStatus.OK);

	}
	
	@ExceptionHandler(ServletException.class)
	public ResponseEntity<BaseResponseVo> handleServletException(ServletException ex) {
		log.info("Exception is happened!  {}" + ex);
		HttpHeaders headers = new HttpHeaders();
		BaseResponseVo baseResVo = new BaseResponseVo();
		
		if (ex.getRootCause() instanceof GlobalException) {
			GlobalException e = (GlobalException) ex.getRootCause();

			headers.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));
			baseResVo.setCode(e.getCode());
			baseResVo.setMessage(e.getMessage());
			
		} else {

			headers.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));
			baseResVo.setCode("44444");
			baseResVo.setMessage("ServletException is happened!");
		}
		// Exception시에는 globalFilter에서 처리 못하니 ExceptionHandler에사 MDC 삭제
       	MDC.remove(mdcName);
       	
		return new ResponseEntity<BaseResponseVo>(baseResVo, headers, HttpStatus.OK);
	}
	

	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<BaseResponseVo> handleValidationExceptions(Exception ex) {
		log.info("Exception is happened!  {}" + ex);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));

		BaseResponseVo baseResVo = new BaseResponseVo();
		baseResVo.setCode("44444");
		baseResVo.setMessage("Exception is happened!");
		// Exception시에는 globalFilter에서 처리 못하니 ExceptionHandler에사 MDC 삭제
       	MDC.remove(mdcName);
		return new ResponseEntity<BaseResponseVo>(baseResVo, headers, HttpStatus.OK);
	}
}