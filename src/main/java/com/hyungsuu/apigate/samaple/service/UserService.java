package com.hyungsuu.apigate.samaple.service;

import java.util.HashMap;
import java.util.List;

import com.hyungsuu.apigate.samaple.vo.UserReqVo;
import com.hyungsuu.apigate.samaple.vo.UserResVo;
import com.hyungsuu.common.exception.GlobalException;





public interface UserService {


	UserResVo selectUser(HashMap<String, Object> userMap) throws Exception;
	int insertUser(UserReqVo userReqVo) throws GlobalException, Exception;
	int updateUser(UserReqVo userReqVo) throws GlobalException, Exception;
	int deleteUser(HashMap<String, Object> userMap) throws GlobalException, Exception;
	List<Object> selectUserPage(HashMap<String, Object> userMap) throws GlobalException, Exception;
	
}
