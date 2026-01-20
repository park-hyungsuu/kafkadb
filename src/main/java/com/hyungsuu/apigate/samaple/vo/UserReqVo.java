package com.hyungsuu.apigate.samaple.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;


@SuppressWarnings("serial")
@Builder 
@Data
@Alias("userReqVo")
@Schema(description = "샘플 조회 API")
public class UserReqVo implements Serializable {
	
	@NotNull
	@Size(min = 6, max = 32, message = "최소 6자리에서 최대 32자리 사이의 길이만 허용됩니다.")
	@Schema(description = "사용자 ID",example = "a1234566", requiredMode = Schema.RequiredMode.REQUIRED)
	private String userId;

	
	@NotNull(message = "사용자암호는 Null 일 수 없습니다.")
	@Size(min = 6, max = 20, message = "최소 6자리에서 최대 20자를 넘을수 없습니다.")
	@Schema(description = "사용자 암호",example = "user", requiredMode = Schema.RequiredMode.REQUIRED)
	private String userPasswd;
	
	@NotNull(message = "사용자권한은은 Null 일 수 없습니다.")
	@Size(max = 10, message = "사용자 권한는 10자를 넘을수 없습니다.")
	@Schema(description = "사용자 권한",example = "user", requiredMode = Schema.RequiredMode.REQUIRED)
	private String userAuth;
	
	
	@NotNull(message = "사용자이름은 Null 일 수 없습니다.")
	@Size(min = 2, max = 20, message = "사용자 이름는 20자를 넘을수 없습니다.")
	@Schema(description = "사용자 권한",example = "user", requiredMode = Schema.RequiredMode.REQUIRED)
	private String userName;

	

}
