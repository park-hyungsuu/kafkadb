package com.hyungsuu.apigate.samaple.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
public class KafkaUserUpdVo implements Serializable {
    private Schema schema;
    private UserUpdPayloadVo payload;
}
