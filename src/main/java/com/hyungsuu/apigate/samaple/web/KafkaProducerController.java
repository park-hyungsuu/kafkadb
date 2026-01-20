package com.hyungsuu.apigate.samaple.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyungsuu.apigate.samaple.kafka.KafkaProducer;
import com.hyungsuu.apigate.samaple.vo.UserReqVo;
import com.hyungsuu.common.exception.GlobalException;
import com.hyungsuu.common.vo.BaseResponseVo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/kafka")
public class KafkaProducerController {
	@Autowired
    private Environment env;
	@Autowired
    private KafkaProducer kafkaProducer;


    @PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody @Valid UserReqVo userReqVo,BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) throws GlobalException {

        /* Kafka Messaging Queue에 전달 */
  
    	kafkaProducer.insert("my_topic_user", userReqVo);
        
		BaseResponseVo baseResVo = new BaseResponseVo();
		baseResVo.setSuccess();

        return new ResponseEntity<BaseResponseVo>(baseResVo, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody @Valid UserReqVo userReqVo,BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) throws GlobalException {

        /* Kafka Messaging Queue에 전달 */
  
    	kafkaProducer.update("my_topic_user", userReqVo);
        
		BaseResponseVo baseResVo = new BaseResponseVo();
		baseResVo.setSuccess();

        return new ResponseEntity<BaseResponseVo>(baseResVo, HttpStatus.OK);
    }
    
    
//    @PostMapping("/delete")
//    public ResponseEntity<?> delete(@RequestBody @Valid UserReqVo userReqVo,BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) throws GlobalException {
//
//        /* Kafka Messaging Queue에 전달 */
//  
//    	kafkaProducer.update("my_topic_user", userReqVo);
//        
//		BaseResponseVo baseResVo = new BaseResponseVo();
//		baseResVo.setSuccess();
//
//        return new ResponseEntity<BaseResponseVo>(baseResVo, HttpStatus.OK);
//    }
}
