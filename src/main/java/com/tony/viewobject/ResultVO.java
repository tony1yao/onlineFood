package com.tony.viewobject;

/*
* The result included in HttpResponse.
* */

import lombok.Data;

@Data
public class ResultVO<T> {

    /*Status of the response*/
    private  Integer code;

    /* Detailed info of the response */
    private  String msg;

    /* Content of the response */
    private  T data;
}
