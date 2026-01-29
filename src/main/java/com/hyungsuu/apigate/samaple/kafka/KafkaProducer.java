package com.hyungsuu.apigate.samaple.kafka;

import java.util.Arrays;
import java.util.List;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyungsuu.apigate.samaple.vo.Field;
import com.hyungsuu.apigate.samaple.vo.KafkaUserReqVo;
import com.hyungsuu.apigate.samaple.vo.KafkaUserUpdVo;
import com.hyungsuu.apigate.samaple.vo.Schema;
import com.hyungsuu.apigate.samaple.vo.UserReqPayloadVo;
import com.hyungsuu.apigate.samaple.vo.UserReqVo;
import com.hyungsuu.apigate.samaple.vo.UserUpdPayloadVo;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;

@Service
@Slf4j
public class KafkaProducer {

    private KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }


    List<Field> insertFields = Arrays.asList(
    		new Field("string", false, "user_id"),
            new Field("string", false, "user_passwd"),
            new Field("string", false, "user_auth"),
            new Field("string", false, "user_name"),
  //          new Field("int64", false,"org.apache.kafka.connect.data.Timestamp", 1, "reg_date")   데아타를 Timestamp 로 보낼때
            new Field("string", true, "reg_date"),
            new Field("string", false, "upd_date") //  데이타를 LocalDateTime.now().toString()
    );

    
    List<Field> updateFields = Arrays.asList(
    		new Field("string", false, "user_id"),
            new Field("string", false, "user_passwd"),
            new Field("string", false, "user_auth"),
            new Field("string", false, "user_name"),
  //          new Field("int64", false,"org.apache.kafka.connect.data.Timestamp", 1, "reg_date")   데아타를 Timestamp 로 보낼때
 //           new Field("string", true, "reg_date"),
            new Field("string", false, "upd_date") //  데이타를 LocalDateTime.now().toString()
    );
    
    private Schema insertSchema = Schema.builder()
            .type("struct")
            .fields(insertFields)
            .optional(false)
            .name("user")
            .build();

    private Schema updateSchema = Schema.builder()
            .type("struct")
            .fields(updateFields)
            .optional(false)
            .name("user")
            .build();


    public UserReqVo insert(String topic, UserReqVo userReqVo){
        UserReqPayloadVo payload = UserReqPayloadVo.builder()
                .user_id(userReqVo.getUserId())
                .user_passwd(userReqVo.getUserPasswd())
                .user_auth(userReqVo.getUserAuth())
                .user_name(userReqVo.getUserName())
                .reg_date(LocalDateTime.now().toString())
                .upd_date(LocalDateTime.now().toString())
                .build();
        		

        KafkaUserReqVo kafkaUserReqVo = new KafkaUserReqVo(insertSchema, payload);
        log.info("user send data " + kafkaUserReqVo.toString());
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";
        try{
            jsonInString = mapper.writeValueAsString(kafkaUserReqVo);
        }
        catch(JsonProcessingException e){
            e.printStackTrace();
        }
        log.info("user send data " + jsonInString);
        kafkaTemplate.send(topic, jsonInString);
        log.info("user send data " + kafkaUserReqVo);


        return userReqVo;
    }
    
    public UserReqVo update(String topic, UserReqVo userReqVo){
        UserUpdPayloadVo payload = UserUpdPayloadVo.builder()
                .user_id(userReqVo.getUserId())
                .user_passwd(userReqVo.getUserPasswd())
                .user_auth(userReqVo.getUserAuth())
                .user_name(userReqVo.getUserName())
                .upd_date(LocalDateTime.now().toString())
                .build();

        KafkaUserUpdVo kafkaUserReqVo = new KafkaUserUpdVo(updateSchema, payload);
        log.info("user send data " + kafkaUserReqVo.toString());
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";
        try{
            jsonInString = mapper.writeValueAsString(kafkaUserReqVo);
        }
        catch(JsonProcessingException e){
            e.printStackTrace();
        }
        log.info("user send data " + jsonInString);
        kafkaTemplate.send(topic, jsonInString);

        log.info("user send data " + kafkaUserReqVo);


        return userReqVo;
    }
    
    
//    public UserReqVo delete(String topic, UserReqVo userReqVo){
//        UserReqPayloadVo payload = UserReqPayloadVo.builder()
//                .user_id(userReqVo.getUserId())
//                .build();
//
//        KafkaUserReqVo kafkaUserReqVo = new KafkaUserReqVo(schema, payload);
//        log.info("user send data " + kafkaUserReqVo.toString());
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = "";
//        try{
//            jsonInString = mapper.writeValueAsString(kafkaUserReqVo);
//        }
//        catch(JsonProcessingException e){
//            e.printStackTrace();
//        }
//        log.info("user send data " + jsonInString);
//        kafkaTemplate.send(topic, jsonInString);
//
//        log.info("user send data " + kafkaUserReqVo);
//
//
//        return userReqVo;
//    }
}
