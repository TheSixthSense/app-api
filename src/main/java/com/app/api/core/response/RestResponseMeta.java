package com.app.api.core.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class RestResponseMeta implements Serializable {

    private static final long serialVersionUID = 144476231638185217L;

    private String userMessage = "";
    private String systemMessage = "";
}
