package com.hyungsuu.apigate.samaple.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@SuppressWarnings("serial")
@Data
@Alias("refreshTokenReqVo")
@Schema(description = "Refresh 토큰 요청 Vo")
public class RefreshTokenReqVo implements Serializable {

	@NotNull
	@Size(min = 6, max = 32, message = "최소 6자리에서 최대 32자리 사이의 길이만 허용됩니다.")
	@Schema(description = "사용자 ID",example = "a1234566", requiredMode = Schema.RequiredMode.REQUIRED)
	private String userId;

	@NotNull(message = "Token는 Null 일 수 없습니다.")
	@Schema(description = "jwtToken",example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzMWFhYWFhYTc3MTE3OGUiLCJ1c2VySW", requiredMode = Schema.RequiredMode.REQUIRED)
	private String jwtToken;

}
