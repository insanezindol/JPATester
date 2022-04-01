package com.example.jpatester.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseInfo {

    private int code = 0;
    private String msg = "ok";
    private Object data;

}
