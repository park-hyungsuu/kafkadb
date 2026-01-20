package com.hyungsuu.apigate.samaple.vo;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@SuppressWarnings("serial")
@Data
@Schema(description = "Jwt 토큰 요청 Vo")
public class JwtTokenReqVo implements Serializable {

	@NotNull
	@Size(min = 6, max = 32, message = "최소 6자리에서 최대 32자리 사이의 길이만 허용됩니다.")
	@Schema(description = "사용자 ID",example = "a1234566", requiredMode = Schema.RequiredMode.REQUIRED)
	private String userId;

	@NotNull
	@Size(max = 30, message = "사용자 권한")
	@Schema(description = "사용자 권한",example = "user", requiredMode = Schema.RequiredMode.REQUIRED)
	private String userAuth;

}
