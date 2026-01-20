package com.hyungsuu.apigate.samaple.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hyungsuu.apigate.samaple.dao.CommonDAO;
import com.hyungsuu.apigate.samaple.service.UserService;
import com.hyungsuu.apigate.samaple.vo.UserReqVo;
import com.hyungsuu.apigate.samaple.vo.UserResVo;
import com.hyungsuu.common.exception.GlobalException;
import com.hyungsuu.common.util.JwtTokenUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {


//	@Autowired
//    private TempletDAO templetDAO;
	
	@Autowired
    private CommonDAO commonDAO;

	@Override
	public UserResVo selectUser(HashMap<String, Object> userMap) throws Exception {
	
		UserResVo selectSampleResVo = new UserResVo();

		HashMap<String, Object> rtnMap = commonDAO.selectOne("User.selectUser", userMap);
		if (rtnMap != null) {
		log.info("ProcessBusiness. Start() " + rtnMap.toString()+"AAAAAA");
		}
		selectSampleResVo.setData(rtnMap);
			log.info("ProcessBusiness. Start() " + selectSampleResVo.toString());
		return selectSampleResVo;

	}

	
	@Override
	@Transactional
	public int insertUser(UserReqVo userReqVo) throws GlobalException, Exception  {
	
		int rtnMap = 0;
		try {
			rtnMap = commonDAO.insert("User.insertUser", userReqVo);
		} catch (DataIntegrityViolationException e) { // MicroService 처리 중 기타 예외 발생 시
			
			log.info("GlobalException ==>" +"||"+e.getMessage() +"||"+e.toString() );
			throw new GlobalException("600", "aaaa", e);
		} catch (DataAccessException e) { // MicroService 처리 중 기타 예외 발생 시
			
			log.info("GlobalException ==>" +"||"+e.getMessage() +"||"+e.toString() );
			throw new GlobalException("600", "aaaa", e);
		} catch (Exception e) { // MicroService 처리 중 기타 예외 발생 시
			log.info("GlobalException ==>" +"||"+e.getMessage() +"||"+e.toString() );
			throw e;
		}
	
		return rtnMap;

	}


	@Override
	public int updateUser(UserReqVo userReqVo) throws GlobalException, Exception {
		int rtnMap = 0;
		try {
			rtnMap = commonDAO.update("User.updateUser", userReqVo);
		} catch (DataIntegrityViolationException e) { // MicroService 처리 중 기타 예외 발생 시
			
			log.info("GlobalException ==>" +"||"+e.getMessage() +"||"+e.toString() );
			throw new GlobalException("600", "aaaa", e);
		} catch (DataAccessException e) { // MicroService 처리 중 기타 예외 발생 시
			
			log.info("GlobalException ==>" +"||"+e.getMessage() +"||"+e.toString() );
			throw new GlobalException("600", "aaaa", e);
		} catch (Exception e) { // MicroService 처리 중 기타 예외 발생 시
			log.info("GlobalException ==>" +"||"+e.getMessage() +"||"+e.toString() );
			throw e;
		}

		return rtnMap;
	}


	@Override
	public int deleteUser(HashMap<String, Object> userMap) throws GlobalException, Exception {
		int rtnMap = 0;
		try {
			rtnMap = commonDAO.delete("User.deleteUser", userMap);
		} catch (DataIntegrityViolationException e) { // MicroService 처리 중 기타 예외 발생 시
			
			log.info("GlobalException ==>" +"||"+e.getMessage() +"||"+e.toString() );
			throw new GlobalException("600", "aaaa", e);
		} catch (DataAccessException e) { // MicroService 처리 중 기타 예외 발생 시
			
			log.info("GlobalException ==>" +"||"+e.getMessage() +"||"+e.toString() );
			throw new GlobalException("600", "aaaa", e);
		} catch (Exception e) { // MicroService 처리 중 기타 예외 발생 시
			log.info("GlobalException ==>" +"||"+e.getMessage() +"||"+e.toString() );
			throw e;
		}

		return rtnMap;
	}


	@Override
	public List<Object> selectUserPage(HashMap<String, Object> userMap) throws GlobalException, Exception {


		List<Object> rtnList =  (List<Object>) commonDAO.listWithPaging("User.selectUserPage", userMap, (int)userMap.get("startData"), (int) userMap.get("pageSize"));
		log.info("ProcessBusiness. Start() " + rtnList.toString()+"AAAAAA");	

		return rtnList;
	}

}
