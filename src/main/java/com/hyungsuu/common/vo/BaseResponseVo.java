package com.hyungsuu.common.vo;


import java.io.Serializable;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@SuppressWarnings("serial")
public class BaseResponseVo implements Serializable {

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@NotNull
	@Schema(description = "응답코드",example = "0000", requiredMode = Schema.RequiredMode.REQUIRED)
	private String code = "";

    @NotNull
	@Schema(description = "응답 메세지",example = "성공", requiredMode = Schema.RequiredMode.REQUIRED)
    private String message;

	public void setSuccess() {
		this.code = "0000";
		this.message = "success";
	}

   public void setFail(String code, String message) {
		this.code = code;
		this.message = message;   
   }
}
