package com.ziponia.iamport.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessTokenResponse {

    /**
     * 반환된 access token
     */
    private String access_token;

    /**
     * 현재 시간
     */
    private Integer now;

    /**
     * 토큰이 만료 될 시간
     */
    private Integer expired_at;
}
