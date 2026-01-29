package com.hyungsuu.apigate.samaple.vo;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;


@SuppressWarnings("serial")
@Builder 
@Data
public class UserUpdPayloadVo implements Serializable {
	
	private String user_id;
	private String user_passwd;
	private String user_auth;
	private String user_name;
	private String upd_date;

}
