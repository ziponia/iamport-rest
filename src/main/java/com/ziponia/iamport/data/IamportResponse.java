package com.ziponia.iamport.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class IamportResponse<T> {

    private Integer code;
    private String message;
    private T response;
}
