package com.hyungsuu.apigate.samaple.vo;

import java.io.Serializable;

import com.hyungsuu.common.vo.BaseResponseVo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@SuppressWarnings("serial")
@Data
@Schema(description = "Jwt 토큰 응답 Vo")
public class JwtTokenResVo extends BaseResponseVo implements Serializable {

	@NotNull
	@Schema(description = "응답 메세지",example = "Sample Value", requiredMode = Schema.RequiredMode.REQUIRED)
	private String jwtToken;
}
