package com.hyungsuu.apigate.samaple.web;




import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.hyungsuu.apigate.samaple.service.UserService;
import com.hyungsuu.apigate.samaple.vo.UserReqVo;
import com.hyungsuu.apigate.samaple.vo.UserResVo;
import com.hyungsuu.common.exception.GlobalException;
import com.hyungsuu.common.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value="/api")
public class UserController {

	
	@Autowired
    private UserService userService;

	@Autowired
	private MessageSource messageSource;

    /**
     * @param UserReqVo
     * @param model
     * @return SelectSampleResVo
     * @throws Exception
     */

	@Operation(summary = "회원 목록", description = "회원 목록을 조회합니다.")
	@Parameters({
		@Parameter(name="jwtToken", description="jwtToken", required= true),
	})
    @ApiResponse(responseCode = "200",description = "조회 성공")
	@RequestMapping(value="/user/selectUser", method=RequestMethod.POST, consumes = "application/json;charset=UTF-8", produces="application/json;charset=UTF-8")
	public ResponseEntity<UserResVo> selectUser(@RequestBody HashMap<String, Object> userMap,@RequestHeader HttpHeaders header, HttpServletRequest request, HttpServletResponse response) throws GlobalException {
	
		try {
			
			
//			log.info("selectSample. Start()==>" +  bindingResult.getErrorCount() +"::"+messageSource.getMessage("CODE.001" ,new String[] {},  Locale.KOREAN));
			log.info("selectUser. Start()==>" +  userMap.toString());
			UserResVo userResVo = new UserResVo();
			userResVo = userService.selectUser(userMap);

			userResVo.setSuccess();
	

			return new ResponseEntity<UserResVo>(userResVo, HttpStatus.OK);
		} catch (GlobalException ge) { // MicroService 처리 중 사용자 정의 에러 발생 시
			if(ge.getMessage() == null || ge.getMessage().equals("")) {
//				ge.setMessage(messageSource.getMessage("code." + ge.getCode(),  Locale.KOREAN));
				ge.setMessage("fail");
			}
			
			throw ge;
		} catch (Exception e) { // MicroService 처리 중 기타 예외 발생 시
			log.info("GlobalException ==> {} ", e );
			throw new GlobalException("600", "aaaa", e);
		} finally {
			log.info("selectUser. End()" );
		}
    }
	
	
    /**
     * @param UserReqVo
     * @param model
     * @return UserResVo
     * @throws Exception
     */

	@Operation(summary = "회원 등록", description = "회원 등록합니다.")
    @ApiResponse(responseCode = "200",description = "등록 성공")
	@RequestMapping(value="/unAuth/insertUser", method=RequestMethod.POST, consumes = "application/json;charset=UTF-8", produces="application/json;charset=UTF-8")
	public ResponseEntity<UserResVo> insertUser(@RequestBody @Valid UserReqVo userReqVo,BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) throws GlobalException {
		
		try {
			
			
			log.info("insertUser. Start()==>" +  bindingResult.getErrorCount() +"::"+messageSource.getMessage("CODE.001" ,new String[] {},  Locale.KOREAN));
			log.info("insertUser. Start()==>" +  userReqVo.toString());
			CommonUtil.checkBindingResult(bindingResult);
			UserResVo userResVo = new UserResVo();
			int retVal = userService.insertUser(userReqVo);

			if (retVal == 1) {
				userResVo.setSuccess();
			} else {
				userResVo.setFail("600", "11111");
			}
	
			
			return new ResponseEntity<UserResVo>(userResVo, HttpStatus.OK);
		} catch (GlobalException ge) { // MicroService 처리 중 사용자 정의 에러 발생 시
			if(ge.getMessage() == null || ge.getMessage().equals("")) {
				ge.setMessage("fail");
			}
			
			throw ge;
		} catch (Exception e) { // MicroService 처리 중 기타 예외 발생 시
			log.info("Exception ==> {}",e);
			throw new GlobalException("600", "aaaa", e);
		} finally {
			log.info("insertUser. End()" );
		}
    }
	
	@Operation(summary = "회원 수정", description = "회원 수정합니다.")
	@Parameters({
		@Parameter(name="jwtToken", description="jwtToken", required= true),
	})
    @ApiResponse(responseCode = "200",description = "등록 성공")
	@RequestMapping(value="/user/updateUser", method=RequestMethod.POST, consumes = "application/json;charset=UTF-8", produces="application/json;charset=UTF-8")
	public ResponseEntity<UserResVo> updateUser(@RequestBody @Valid UserReqVo userReqVo,BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) throws GlobalException {
		
		try {
			
			
			log.info("updateUser. Start()==>" +  bindingResult.getErrorCount() +"::"+messageSource.getMessage("CODE.001" ,new String[] {},  Locale.KOREAN));
			log.info("updateUser. Start()==>" +  userReqVo.toString());
			CommonUtil.checkBindingResult(bindingResult);
			UserResVo userResVo = new UserResVo();
			int retVal = userService.updateUser(userReqVo);

			if (retVal == 1) {
				userResVo.setSuccess();
			} else {
				userResVo.setFail("600", "11111");
			}
	
			log.info("updateUser. End()" );
			return new ResponseEntity<UserResVo>(userResVo, HttpStatus.OK);
		} catch (GlobalException ge) { // MicroService 처리 중 사용자 정의 에러 발생 시
			if(ge.getMessage() == null || ge.getMessage().equals("")) {
//				ge.setMessage(messageSource.getMessage("code." + ge.getCode(),  Locale.KOREAN));
				ge.setMessage("fail");
			}
			
			throw ge;
		} catch (Exception e) { // MicroService 처리 중 기타 예외 발생 시
			log.info("Exception ==> {}",e);
			throw new GlobalException("600", "aaaa", e);
		}
    }
	
	   /**
     * @param UserReqVo
     * @param model
     * @return SelectSampleResVo
     * @throws Exception
     */

	@Operation(summary = "회원 목록", description = "회원 목록을 조회합니다.")
	@Parameters({
		@Parameter(name="jwtToken", description="jwtToken", required= true),
	})
    @ApiResponse(responseCode = "200",description = "조회 성공")
	@RequestMapping(value="/user/deleteUser", method=RequestMethod.POST, consumes = "application/json;charset=UTF-8", produces="application/json;charset=UTF-8")
	public ResponseEntity<UserResVo> deleteUser(@RequestBody HashMap<String, Object> userMap,BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) throws GlobalException {
		
		try {
			
			
			log.info("selectSample. Start()==>" +  bindingResult.getErrorCount() +"::"+messageSource.getMessage("CODE.001" ,new String[] {},  Locale.KOREAN));
			log.info("selectSample. Start()==>" +  userMap.toString());
			UserResVo userResVo = new UserResVo();
			int retVal = userService.deleteUser(userMap);

			userResVo.setSuccess();
	
			log.info("selectSample. End()" );
			return new ResponseEntity<UserResVo>(userResVo, HttpStatus.OK);
		} catch (GlobalException ge) { // MicroService 처리 중 사용자 정의 에러 발생 시
			if(ge.getMessage() == null || ge.getMessage().equals("")) {
//				ge.setMessage(messageSource.getMessage("code." + ge.getCode(),  Locale.KOREAN));
				ge.setMessage("fail");
			}
			throw ge;
		} catch (Exception e) { // MicroService 처리 중 기타 예외 발생 시
			log.info("Exception ==> {}",e);
			throw new GlobalException("600", "aaaa", e);
		}
    }
	
	   /**
  * @param UserReqVo
  * @param model
  * @return SelectSampleResVo
  * @throws Exception
  */

	@Operation(summary = "회원 목록", description = "회원 목록을 조회합니다.")
	@Parameters({
		@Parameter(name="jwtToken", description="jwtToken", required= true),
	})
	@ApiResponse(responseCode = "200",description = "조회 성공")
	@RequestMapping(value="/admin/selectUserPage", method=RequestMethod.POST, consumes = "application/json;charset=UTF-8", produces="application/json;charset=UTF-8")
	public ResponseEntity<UserResVo> selectUserPage(@RequestBody HashMap<String, Object> userMap, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) throws GlobalException {
		
		try {
			
			int startData = 1;
			int endData = 1;
			int pageSize = 10;
			int curPage =1;
			
			if (userMap.containsKey("curPage")) curPage = (int) userMap.get("curPage");
			startData = (curPage -1) * pageSize;
			endData = (curPage ) * pageSize;
			
			userMap.put("startData", startData);
			userMap.put("pageSize", pageSize);
			log.info("selectSample. Start()==>" +  bindingResult.getErrorCount() +"::"+messageSource.getMessage("CODE.001" ,new String[] {},  Locale.KOREAN));
			log.info("selectSample. Start()==>" +  userMap.toString());
			UserResVo userResVo = new UserResVo();
			List<Object> rtnList  = userService.selectUserPage(userMap);

			log.info("selectSample. Start()==>" +  rtnList.toString());	
			userResVo.setSuccess();
			userResVo.setData(rtnList);
	
			log.info("selectSample. End()" );
			return new ResponseEntity<UserResVo>(userResVo, HttpStatus.OK);
		} catch (GlobalException ge) { // MicroService 처리 중 사용자 정의 에러 발생 시
			if(ge.getMessage() == null || ge.getMessage().equals("")) {
//				ge.setMessage(messageSource.getMessage("code." + ge.getCode(),  Locale.KOREAN));
				ge.setMessage("fail");
			}
			throw ge;
		} catch (Exception e) { // MicroService 처리 중 기타 예외 발생 시
			log.info("Exception ==> {}",e);
			throw new GlobalException("600", "aaaa", e);
		}
 }
	
}
