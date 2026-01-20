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


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RestController
public class CustomErrorController implements ErrorController {

	private String mdcName = "SID";
	
    @RequestMapping("/error")
    public ResponseEntity<BaseResponseVo> handleError(HttpServletRequest request) {
    	Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    	
    	HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));
		BaseResponseVo baseResVo = new BaseResponseVo();
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
        		baseResVo.setCode("3333");
        		baseResVo.setMessage( status.toString() +" is happened. ");
            } else  {
        		baseResVo.setCode("3333");
        		baseResVo.setMessage( status.toString() +" is happened. ");
            }
        }



       	MDC.remove(mdcName);
		return new ResponseEntity<BaseResponseVo>(baseResVo, headers, HttpStatus.OK);

    }
}