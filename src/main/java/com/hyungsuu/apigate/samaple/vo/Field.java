package com.hyungsuu.apigate.samaple.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

// 데이터를 저장하기 위해서 어떤 필드가 있는지 저장

@Data
@AllArgsConstructor
public class Field {

    private String type;
    private boolean optional;
    private String field;

}