package com.hyungsuu.common.util;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.hyungsuu.common.exception.GlobalException;
import com.hyungsuu.common.vo.BaseResponseVo;

public class CommonUtil {

	
	private static final Logger log = LoggerFactory.getLogger(CommonUtil.class);
	/**
	 * Request BindingResult를 분석하여 Binding 에러 발생 시 "요청 파라미터 검증 실패" 관련 상세 응답 메세지를 생성하여 예외를 발생시킨다.

	 */
	public static void checkBindingResult(BindingResult bindingResult) throws GlobalException {

		if (bindingResult.hasErrors()) {

			String errorMsg = "에러 메시지  (";
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
	  		int errorCount = fieldErrors.size();
	  		for(int m = 0; m < errorCount; m++) {
	  			FieldError fieldError = fieldErrors.get(m);
	  			log.info(" - " + fieldError.getField() + "(" + fieldError.getObjectName() + ") : " + fieldError.getDefaultMessage());
	  			errorMsg += fieldError.getField() + " : " + fieldError.getDefaultMessage() + ",";
	  		}
	  		errorMsg = errorMsg.substring(0, errorMsg.length() - 1) + ")";

	  		throw new GlobalException("1001", errorMsg);
    	}
		
	}
	
	                                       
	public static void setSuccessResponse(BaseResponseVo baseResponseVO) {
		
		baseResponseVO.setCode("1000");
		baseResponseVO.setMessage("Success");
    }
}
